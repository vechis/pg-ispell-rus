package ru.vvm.pgispellrus.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vvm.pgispellrus.dao.RecordRepository;
import ru.vvm.pgispellrus.domain.Record;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class SimpleController {

    private final RecordRepository recordRepository;

    @GetMapping("/save/{val}")
    public String save(@PathVariable String val) {
        Record record = new Record();
        record.setRecordValue(val);
        return recordRepository.save(record).getRecordId().toString();
    }

    @GetMapping("/find/{val}")
    public String find(@PathVariable String val) {
        Record record = new Record();
        record.setRecordValue(val);
        return recordRepository
                .findByVal(val)
                .stream()
                .map(Record::getRecordValue)
                .collect(Collectors.joining("<br><br>"));
    }

    @SneakyThrows
    @GetMapping("/load")
    public String load() {
        ClassPathResource classPathResource = new ClassPathResource("text.txt");
        ;
        FileUtils.readLines(classPathResource.getFile(), "UTF-8")
                .stream()
                .map(StringUtils::trim)
                .filter(StringUtils::isNotEmpty)
                .filter(s -> s.length() > 10)
                .map(s -> Record.builder().recordValue(s).build())
                .forEach(recordRepository::save);

        return "OK";
    }
}

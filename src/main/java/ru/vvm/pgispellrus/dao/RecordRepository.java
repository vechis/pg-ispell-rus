package ru.vvm.pgispellrus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vvm.pgispellrus.domain.Record;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(value = "" +
            "SELECT * " +
            "FROM record r " +
            "WHERE to_tsvector('russian_ispell', record_value) @@ websearch_to_tsquery('russian_ispell', ?1) " +
            "ORDER BY ts_rank_cd(to_tsvector('russian_ispell', record_value), websearch_to_tsquery('russian_ispell', ?1)) DESC",
            nativeQuery = true)
    List<Record> findByVal(String val);
}

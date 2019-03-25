-- DROP TEXT SEARCH DICTIONARY russian_ispell;

CREATE TEXT SEARCH DICTIONARY russian_ispell (
  TEMPLATE = ispell,
  DictFile = ru_RU,
  AffFile = ru_RU
  );

-- DROP TEXT SEARCH CONFIGURATION russian_ispell;

CREATE TEXT SEARCH CONFIGURATION russian_ispell (
  COPY = pg_catalog.russian
  );

ALTER TEXT SEARCH CONFIGURATION russian_ispell
  ALTER MAPPING FOR
    asciiword, asciihword, hword_asciipart,
    word, hword, hword_part
    WITH russian_ispell, russian_stem;


CREATE SEQUENCE IF NOT EXISTS seq_xpk_record INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE IF NOT EXISTS record
(
  record_id  INTEGER NOT NULL DEFAULT nextval('seq_xpk_record' :: REGCLASS),
  record_value  TEXT,
  CONSTRAINT xpk_record PRIMARY KEY (record_id)
);

CREATE INDEX xak1_record ON ispellrus.record USING GIN (to_tsvector('russian_ispell', record_value));


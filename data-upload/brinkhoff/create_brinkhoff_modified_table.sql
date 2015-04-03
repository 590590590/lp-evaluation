-- Table: brinkhoff.b25k_modified_raw
-- Schema: brinkhoff

DROP TABLE if exists brinkhoff.b25k_modified_raw;

CREATE TABLE brinkhoff.b25k_modified_raw
(
  id integer,
  x double precision,
  y double precision,
  vx double precision,
  vy double precision,
  t double precision
)
WITH (
  OIDS=FALSE
);
ALTER TABLE brinkhoff.b25k_modified_raw
  OWNER TO postgres;

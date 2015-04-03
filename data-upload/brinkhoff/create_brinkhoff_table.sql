-- Table: brinkhoff.b25k_raw

DROP TABLE if exists brinkhoff.b25k_raw;

CREATE TABLE brinkhoff.b25k_raw
(
  type_p character varying,
  id integer,
  seq integer,
  class_o integer,
  t integer,
  x double precision,
  y double precision,
  v double precision,
  nodex double precision,
  nodey double precision
)
WITH (
  OIDS=FALSE
);
ALTER TABLE brinkhoff.b25k_raw
  OWNER TO postgres;

-- Table: brinkhoff.b25k_modified_g_32_regions

DROP if exists TABLE brinkhoff.b25k_modified_g_32_regions;

CREATE TABLE brinkhoff.b25k_modified_g_32_regions
(
  count integer,
  r integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE brinkhoff.b25k_modified_g_32_regions
  OWNER TO postgres;

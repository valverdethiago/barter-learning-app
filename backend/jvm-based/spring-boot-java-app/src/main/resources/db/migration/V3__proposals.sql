-- Table: proposals

-- DROP TABLE IF EXISTS proposals;

CREATE TABLE IF NOT EXISTS proposals
(
    id serial NOT NULL PRIMARY KEY,
    status character varying(15)
)

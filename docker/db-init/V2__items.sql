-- Table: items

-- DROP TABLE IF EXISTS items;

CREATE TABLE IF NOT EXISTS items
(
    id serial NOT NULL PRIMARY KEY,
    price numeric(19,2),
    owner_id integer NOT NULL,
    name character varying(255),
    description character varying(255),

    CONSTRAINT owner_item_fk FOREIGN KEY (owner_id)
        REFERENCES users (id)
)

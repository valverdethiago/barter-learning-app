-- Table: proposals

-- DROP TABLE IF EXISTS proposals;

CREATE TABLE IF NOT EXISTS proposals
(
    id serial NOT NULL PRIMARY KEY,
    status character varying(15),
    description character varying(255),
    owner_id integer NOT NULL,
    requesting_user_id integer NOT NULL,

    CONSTRAINT owner_proposal_fk FOREIGN KEY (owner_id)
        REFERENCES users (id),

    CONSTRAINT requesting_user_proposal_fk FOREIGN KEY (requesting_user_id)
        REFERENCES users (id)
)

-- Table: users

-- DROP TABLE IF EXISTS users;


CREATE TABLE IF NOT EXISTS users
(
    id SERIAL NOT NULL PRIMARY KEY,
    username character varying(255),
    name character varying(255),
    password character varying(255),
    email character varying(255),
    created_on timestamp without time zone,
    disabled_on timestamp without time zone,
    last_login_on timestamp without time zone,
    updated_on timestamp without time zone
)

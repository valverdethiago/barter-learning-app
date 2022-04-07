CREATE EXTENSION pgcrypto;

INSERT INTO users (username, name, password, email)
     VALUES ('thiago.valverde', 'Thiago Valverde de Souza', crypt('p@55w0rd', gen_salt('bf')), 'valverde.thiago@gmail.com');

INSERT INTO users (username, name, password, email)
     VALUES ('joao.silva', 'João da Silva', crypt('jo@o.51lv@', gen_salt('bf')), 'valverde.thiago@gmail.com');


DROP DATABASE IF EXISTS prova_2;

CREATE DATABASE prova_2;

\c prova_2;

CREATE TABLE amigo_secreto (
  id SERIAL PRIMARY KEY,
  nome TEXT NOT NULL,
  criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE participante (
  id SERIAL PRIMARY KEY,
  nome TEXT NOT NULL,
  data_nascimento DATE,
  id_amigo_sorteado INTEGER REFERENCES participante(id),
  id_amigo_secreto INTEGER REFERENCES amigo_secreto(id)
);

CREATE TABLE desejo (
  id SERIAL PRIMARY KEY,
  descricao TEXT NOT NULL,
  id_participante INTEGER REFERENCES participante(id)
);

CREATE OR REPLACE FUNCTION sortear_amigo(participante_id INTEGER) RETURNS INTEGER AS $$
DECLARE
  amigo_id INTEGER;
BEGIN
  SELECT p2.id INTO amigo_id FROM participante p1
  INNER JOIN participante p2 ON p1.id_amigo_secreto = p2.id_amigo_secreto
  WHERE p1.id = participante_id AND p1.id != p2.id
  ORDER BY random()
  LIMIT 1;

  UPDATE participante SET id_amigo_sorteado = amigo_id WHERE id = participante_id;
  RETURN amigo_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION listar_evento(evento_id INTEGER)
RETURNS TABLE(nome TEXT, nome_amigo TEXT) AS $$
BEGIN
  RETURN QUERY SELECT p1.nome, p2.nome FROM participante p1 INNER JOIN participante p2 ON p1.id_amigo_sorteado = p2.id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION adicionar_meias()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO desejo (descricao, id_participante) SELECT 'Meias', NEW.id;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_adicionar_meias
AFTER INSERT ON participante
FOR EACH ROW
EXECUTE PROCEDURE adicionar_meias();

CREATE OR REPLACE FUNCTION verificar_idade()
RETURNS TRIGGER AS $$
BEGIN
  IF age(NEW.data_nascimento) < interval '18 years' THEN
    RAISE EXCEPTION 'O participante é menor.';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_verificar_idade
BEFORE INSERT ON participante
FOR EACH ROW
EXECUTE PROCEDURE verificar_idade();

INSERT INTO amigo_secreto (nome) VALUES
('Amigo secreto da família X'),
('Amigo secreto da família Y');

INSERT INTO participante (nome, data_nascimento, id_amigo_secreto) VALUES
('Pedro', '2002-11-22', 1),
('Laura', '2003-04-18',1),
('Rafael', '2000-02-20', 1);

INSERT INTO participante (nome, data_nascimento, id_amigo_secreto) VALUES
('Rafael', '2000-02-20', 2);

INSERT INTO desejo(descricao, id_participante) VALUES
('Camiseta', 1),
('Capinha de celular', 2),
('Fone de ouvido', 3);

INSERT INTO participante (nome, data_nascimento, id_amigo_sorteado, id_amigo_secreto) VALUES
('Paulo', '2010-06-20',1, 1);

SELECT sortear_amigo(1);
SELECT sortear_amigo(2);
SELECT sortear_amigo(3);

SELECT * FROM listar_evento(1);

SELECT * FROM desejo;
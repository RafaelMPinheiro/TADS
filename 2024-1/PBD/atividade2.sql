DROP DATABASE IF EXISTS trabalho_2;

CREATE DATABASE trabalho_2;

\c trabalho_2;

CREATE TABLE diarista (
  id SERIAL PRIMARY KEY,
  cpf CHARACTER(11) NOT NULL,
  nome CHARACTER VARYING(100) NOT NULL
);

INSERT INTO diarista (cpf, nome) VALUES 
('12345678901', 'Maria da Silva'),
('23456789012', 'João Pereira'),
('34567890123', 'Ana Souza');

CREATE TABLE responsavel (
  id SERIAL PRIMARY KEY,
  cpf CHARACTER(11) NOT NULL,
  nome CHARACTER VARYING(100) NOT NULL
);

INSERT INTO responsavel (cpf, nome) VALUES 
('98765432100', 'Carlos Lima'),
('87654321009', 'Fernanda Oliveira'),
('76543210908', 'Paulo Santos');

CREATE TABLE tamanho (
  id SERIAL PRIMARY KEY,
  nome TEXT NOT NULL,
  valor FLOAT NOT NULL
);

INSERT INTO tamanho (nome, valor) VALUES 
('Pequeno', 100.0),
('Médio', 150.0),
('Grande', 200.0);

CREATE TABLE residencia (
  id SERIAL PRIMARY KEY,
  cidade CHARACTER VARYING(50) NOT NULL,
  bairro CHARACTER VARYING(50) NOT NULL,
  rua CHARACTER VARYING(50) NOT NULL,
  complemento CHARACTER VARYING(50),
  numero CHARACTER VARYING(50) NOT NULL,
  tamanho INTEGER REFERENCES tamanho(id),
  id_responsavel INTEGER REFERENCES responsavel(id)
);

INSERT INTO residencia (cidade, bairro, rua, complemento, numero, tamanho, id_responsavel) VALUES 
('São Paulo', 'Centro', 'Rua A', 'Apto 101', '100', 1, 1);

INSERT INTO residencia (cidade, bairro, rua, numero, tamanho, id_responsavel) VALUES 
('Rio de Janeiro', 'Copacabana', 'Rua B', '200', 2, 2),
('Belo Horizonte', 'Savassi', 'Rua C', '300', 3, 3);

CREATE TABLE faxina (
  id SERIAL PRIMARY KEY,
  id_diarista INTEGER REFERENCES diarista(id) ON DELETE CASCADE,
  id_residencia INTEGER REFERENCES residencia(id),
  data_agendada DATE DEFAULT CURRENT_DATE,
  dia DATE DEFAULT CURRENT_DATE,
  realizada BOOLEAN DEFAULT FALSE,
  valor_faxina FLOAT,
  valor_gorjeta FLOAT,
  feedback TEXT
);

CREATE OR REPLACE FUNCTION agendar_faxina(
  diarista_id INTEGER, 
  residencia_id INTEGER,
  data_prevista DATE
) RETURNS TEXT AS $$
DECLARE
  conflitoAUX INT;
  preco_faxina_residencia FLOAT;
BEGIN
  IF data_prevista > '2024-12-31' THEN
    RETURN 'Não foi possível agendar: data ultrapassou esse ano.';
  END IF;

  SELECT count(*) INTO conflitoAUX
  FROM faxina 
  WHERE (id_diarista = diarista_id OR id_residencia = residencia_id) 
  AND dia = data_prevista;

  IF conflitoAUX != 0 THEN
    RETURN 'Não foi possível agendar: diarista ou residência já possuem uma faxina agendada para esta data.';
  END IF;

  SELECT tamanho.valor INTO preco_faxina_residencia 
  FROM residencia
  INNER JOIN tamanho ON tamanho.id = residencia.tamanho 
  WHERE residencia.id = residencia_id;

  INSERT INTO faxina (id_diarista, id_residencia, dia, valor_faxina)
  VALUES (diarista_id, residencia_id, data_prevista, preco_faxina_residencia);

  RETURN 'Faxina agendada com sucesso!';
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION presenca_diaristas()
RETURNS TABLE(id INT, nome CHARACTER VARYING(100), presenca NUMERIC) AS $$
BEGIN
  RETURN QUERY
  SELECT diarista.id, diarista.nome,
    ROUND(
    COALESCE(SUM(CASE WHEN faxina.realizada = TRUE THEN 1 ELSE 0 END), 0) * 1.0 /
    NULLIF(SUM(1), 0) 
    , 2) AS presenca
  FROM diarista 
  LEFT JOIN faxina ON diarista.id = faxina.id_diarista
  GROUP BY diarista.id, diarista.nome
  ORDER BY diarista.id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION fechar_faxina(id_faxina INT, gorjeta FLOAT, feedback_cliente TEXT)
RETURNS TEXT AS $$
BEGIN
  UPDATE faxina SET
    realizada = TRUE,
    valor_gorjeta = gorjeta,
    feedback = feedback_cliente
  WHERE id = id_faxina;

  RETURN 'Faxina fechada com sucesso!';
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION verificar_presenca_diarista()
RETURNS TRIGGER AS $$
DECLARE
    total_faxinas INT;
    faxinas_realizadas INT;
    percentual_presenca REAL;
BEGIN
    SELECT COUNT(*) INTO total_faxinas
    FROM faxina
    WHERE id_diarista = OLD.id_diarista;

    SELECT COUNT(*) INTO faxinas_realizadas
    FROM faxina
    WHERE id_diarista = OLD.id_diarista
    AND realizada = TRUE;

    IF total_faxinas > 0 THEN
      percentual_presenca := (faxinas_realizadas / total_faxinas) * 100;
    ELSE
      percentual_presenca := 0;
    END IF;

    IF total_faxinas >= 5 AND percentual_presenca < 75 THEN
      DELETE FROM diarista WHERE id = OLD.id_diarista;
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_verificar_presenca_diarista
AFTER INSERT OR UPDATE ON faxina
FOR EACH ROW
EXECUTE FUNCTION verificar_presenca_diarista();

SELECT agendar_faxina(1, 1, '2024-08-01');
SELECT fechar_faxina(1, 10.0, 'Bom trabalho');
SELECT agendar_faxina(1, 2, '2024-08-02');
SELECT fechar_faxina(2, 15.0, 'Ótima limpeza');
SELECT agendar_faxina(1, 3, '2024-08-03');
SELECT fechar_faxina(3, 12.0, 'Satisfeito com o serviço');

SELECT agendar_faxina(2, 1, '2024-08-04');
SELECT fechar_faxina(4, 10.0, 'Tudo certo');
SELECT agendar_faxina(2, 2, '2024-08-05');
SELECT fechar_faxina(5, 15.0, 'Excelente serviço');
SELECT agendar_faxina(2, 3, '2024-08-06');
SELECT fechar_faxina(6, 20.0, 'Muito bom');
SELECT agendar_faxina(2, 1, '2024-08-07');

SELECT agendar_faxina(3, 1, '2024-08-09');
SELECT fechar_faxina(8, 10.0, 'Limpeza ok');
SELECT agendar_faxina(3, 2, '2024-08-10');
SELECT fechar_faxina(9, 14.0, 'Satisfatório');
SELECT agendar_faxina(3, 3, '2024-08-11');

SELECT * FROM presenca_diaristas(); 
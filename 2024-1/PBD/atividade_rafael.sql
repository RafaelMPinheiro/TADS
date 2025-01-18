DROP DATABASE IF EXISTS atividade_rafael;
CREATE DATABASE atividade_rafael;

\c atividade_rafael;

CREATE TABLE funcionario(
  id serial primary key,
  nome text not null,
  senha text not null
);

CREATE TABLE pontos(
  id serial primary key,
  data_hora_entrada timestamp default current_timestamp,
  data_hora_saida timestamp default current_timestamp,
  funcionario_id integer references funcionario(id)
);

CREATE OR REPLACE FUNCTION ponto_entrada(INTEGER)
returns boolean as
$$
DECLARE
  hoje timestamp := current_timestamp;
  aux integer;
BEGIN
  SELECT COUNT(*) INTO aux FROM pontos WHERE funcionario_id = $1 AND cast(current_timestamp as date) = cast(data_hora_entrada as date);

  IF aux != 0 THEN
    RETURN FALSE;
  END IF;

  INSERT INTO pontos(funcionario_id) VALUES ($1);
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

INSERT INTO funcionario(nome, senha) VALUES ('Rafael', 'senha');
INSERT INTO funcionario(nome, senha) VALUES ('Pedro', 'senha1');

SELECT ponto_entrada(1);
INSERT INTO pontos(data_hora_entrada, data_hora_saida, funcionario_id) VALUES (CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP + INTERVAL '1 day' + INTERVAL '1 hour', 1);

SELECT ponto_entrada(2);
INSERT INTO pontos(data_hora_entrada, data_hora_saida, funcionario_id) VALUES (CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP + INTERVAL '1 day' + INTERVAL '2 hour', 2);

CREATE OR REPLACE FUNCTION ponto_saida(INTEGER)
returns boolean as
$$
DECLARE
  hoje timestamp := current_timestamp;
  aux integer;
BEGIN
  SELECT COUNT(*) INTO aux FROM pontos WHERE funcionario_id = $1 AND cast(current_timestamp as date) = cast(data_hora_entrada as date);

  IF aux != 1 THEN
    RETURN FALSE;
  END IF;

  UPDATE pontos SET data_hora_saida = current_timestamp WHERE funcionario_id = $1 AND cast(current_timestamp AS date) = cast(data_hora_entrada AS date);
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mais_horas_trabalhadas() 
returns table("Id" integer, "Nome" text, "Horas trabalhadas" integer) as
$$
begin
  return query 
        select funcionario.id, funcionario.nome, cast(SUM(EXTRACT(EPOCH FROM (pontos.data_hora_saida - pontos.data_hora_entrada)) / 3600) as integer) AS "Horas trabalhadas"
        from funcionario inner join pontos on funcionario.id = pontos.funcionario_id
        GROUP BY funcionario.id, funcionario.nome
        HAVING SUM(EXTRACT(EPOCH FROM (pontos.data_hora_saida - pontos.data_hora_entrada)) / 3600) = 
        (select SUM(EXTRACT(EPOCH FROM (pontos.data_hora_saida - pontos.data_hora_entrada)) / 3600)
        from funcionario inner join pontos on funcionario.id = pontos.funcionario_id
        GROUP BY funcionario.id
        ORDER BY sum DESC
        LIMIT 1);
end;
$$ LANGUAGE 'plpgsql';

SELECT ponto_saida(2);
SELECT * FROM mais_horas_trabalhadas();

CREATE OR REPLACE FUNCTION sem_ponto_saida() 
returns table("Id" integer, "Nome" text, "Entrada" timestamp) as
$$
begin
  return query 
        select funcionario.id, funcionario.nome, pontos.data_hora_entrada
        from funcionario inner join pontos on funcionario.id = pontos.funcionario_id
        GROUP BY funcionario.id, funcionario.nome, pontos.data_hora_entrada, pontos.data_hora_saida
        HAVING pontos.data_hora_entrada = pontos.data_hora_saida;
end;
$$ LANGUAGE 'plpgsql';

SELECT * FROM sem_ponto_saida();
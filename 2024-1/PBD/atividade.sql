DROP DATABASE IF EXISTS trabalho_1;

CREATE DATABASE trabalho_1;

\c trabalho_1;

CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    senha VARCHAR(100) NOT NULL,
    tipo CHAR(1) CHECK (tipo IN ('A', 'L'))
);

CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    texto TEXT NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    compartilhado BOOLEAN DEFAULT FALSE
);

CREATE OR REPLACE FUNCTION isLeitor(pessoa_id INTEGER) RETURNS BOOLEAN AS $$
BEGIN
    RETURN (SELECT tipo = 'L' FROM pessoa WHERE id = pessoa_id);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION isAutor(pessoa_id INTEGER) RETURNS BOOLEAN AS $$
BEGIN
    RETURN (SELECT tipo = 'A' FROM pessoa WHERE id = pessoa_id);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION isCompartilhado(id_post INTEGER) RETURNS BOOLEAN AS $$
DECLARE
    compartilhadoAUX BOOLEAN;
    postAUX INTEGER;
BEGIN
    SELECT COUNT(*) INTO postAUX FROM pessoa_post WHERE post_id = id_post;

    IF postAUX = 0 THEN
        RETURN TRUE;
    END IF;

    SELECT compartilhado INTO compartilhadoAUX FROM post WHERE id = id_post;

    IF compartilhadoAUX = FALSE THEN
        RAISE NOTICE 'O post não é compartilhado';
        RETURN FALSE;
    END IF;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE pessoa_post (
    pessoa_id INTEGER REFERENCES pessoa(id) CHECK (isAutor(pessoa_id)),
    post_id INTEGER REFERENCES post(id) CHECK (isCompartilhado(post_id)),
    PRIMARY KEY(pessoa_id, post_id)
);


CREATE TABLE
    endereco (
        id SERIAL PRIMARY KEY,
        bairro VARCHAR(255) NOT NULL,
        rua VARCHAR(255) NOT NULL,
        numero VARCHAR(255) NOT NULL,
        cep CHARACTER(9) NOT NULL,
        pessoa_id INTEGER REFERENCES pessoa(id) CHECK (isLeitor(pessoa_id))
    );

CREATE OR REPLACE FUNCTION converter_senha(VARCHAR) RETURNS VARCHAR AS $$
BEGIN
    RETURN MD5($1);
END;
$$ LANGUAGE plpgsql;

INSERT INTO pessoa (nome, email, senha, tipo) VALUES
    ('Ana Silva', 'ana.silva@example.com', converter_senha('senha1'), 'A'),
    ('Carlos Oliveira', 'carlos.oliveira@example.com', converter_senha('senha2'), 'A'),
    ('Mariana Santos', 'mariana.santos@example.com', converter_senha('senha3'), 'A'),
    ('Pedro Almeida', 'pedro.almeida@example.com', converter_senha('senha4'), 'A'),
    ('Camila Costa', 'camila.costa@example.com', converter_senha('senha5'), 'A'),
    ('João Santos', 'joao.santos@example.com', converter_senha('senha1'), 'L'),
    ('Maria Oliveira', 'maria.oliveira@example.com', converter_senha('senha2'), 'L'),
    ('Rafael Lima', 'rafael.lima@example.com', converter_senha('senha3'), 'L'),
    ('Fernanda Costa', 'fernanda.costa@example.com', converter_senha('senha4'), 'L'),
    ('Juliana Pereira', 'juliana.pereira@example.com', converter_senha('senha5'), 'L');

INSERT INTO endereco (bairro, rua, numero, cep, pessoa_id) VALUES
    ('Centro', 'Rua das Flores', '123', '12345-678', 6),
    ('Vila Nova', 'Rua Palmeiras', '456', '23456-789', 7),
    ('Jardim Primavera', 'Rua dos Girassóis', '101', '45678-901', 9),
    ('Bairro do Lago', 'Rua Acácias', '222', '56789-012', 10),
    ('Vila Nova', 'Rua Palmeiras', '456', '23456-789', 10);

INSERT INTO post (titulo, texto, compartilhado) VALUES
    ('A viagem dos meus sonhos', 'Finalmente fiz aquela viagem dos sonhos para o Caribe...', FALSE),
    ('Review do novo filme "Caminho Estelar"', 'Ontem fui ao cinema assistir ao novo lançamento e aqui está minha opinião...', TRUE),
    ('Dicas para manter a saúde mental em tempos difíceis', 'Compartilhando algumas dicas que têm me ajudado a lidar com o estresse...', TRUE),
    ('Receita especial de família: Bolo de cenoura da vovó', 'Essa é uma receita que passou de geração em geração em nossa família...', TRUE),
    ('Os benefícios do yoga para a mente e o corpo', 'Praticar yoga tem sido uma parte essencial da minha rotina diária. Aqui estão alguns dos benefícios que tenho experimentado...', TRUE);

INSERT INTO pessoa_post (pessoa_id, post_id) VALUES
    (1,1),
    (2,2),
    (3,3),
    (4,4),
    (5,5),
    (5,2);

CREATE OR REPLACE FUNCTION mostrar_dados_pessoas() 
returns table(nome VARCHAR(255), email VARCHAR(255), tipo CHAR(1), "Endereço completo" text) as
$$
begin
	return query SELECT pessoa.nome, pessoa.email, pessoa.tipo,
		Coalesce(string_agg(endereco.bairro || ' ' || endereco.rua || ' ' || endereco.numero, '| '), pessoa.tipo || ' - SEM ENDEREÇO CADASTRADO') AS endereco_completo
		FROM pessoa LEFT JOIN endereco ON pessoa.id = endereco.pessoa_id
		GROUP BY pessoa.nome, pessoa.email, pessoa.tipo
		ORDER BY pessoa.nome;
end;
$$ LANGUAGE 'plpgsql';

select * from mostrar_dados_pessoas();

CREATE OR REPLACE FUNCTION mostrar_qntd_pessoas_post() 
returns table("Titulo" VARCHAR(255), "Data de publicação" text, "Autores envolvidos" bigint) as
$$
begin
	return query 
        select post.titulo, 
        to_char(post.data_hora, 'HH24:MI DD/MM/YYYY'), 
        count(pessoa_post.pessoa_id) from post 
        left join pessoa_post on post.id = pessoa_post.post_id
        group by post.titulo, post.data_hora;
end;
$$ LANGUAGE 'plpgsql';

select * from mostrar_qntd_pessoas_post();

CREATE OR REPLACE FUNCTION mostrar_pessoas_post() 
returns table("Titulo" VARCHAR(255), "Autores envolvidos" text) as
$$
begin
	return query 
        SELECT post.titulo AS "Titulo",
            STRING_AGG(pessoa.nome, ', ') AS "Autores envolvidos"
        FROM post
        LEFT JOIN pessoa_post ON post.id = pessoa_post.post_id
        LEFT JOIN pessoa ON pessoa_post.pessoa_id = pessoa.id
        GROUP BY post.id, post.titulo;
end;
$$ LANGUAGE 'plpgsql';

select * from mostrar_pessoas_post();

CREATE OR REPLACE FUNCTION login(VARCHAR(255), VARCHAR(255)) 
returns boolean as
$$
declare
    md5_senha VARCHAR(32);
begin
    select MD5($2) into md5_senha;
    
    return exists (
        select 1
        from pessoa
        where email = $1
        and senha = md5_senha
    );
end;
$$ LANGUAGE 'plpgsql';

select login('mariana.santos@example.com', 'senha');
select login('mariana.santos@example.com', 'senha3');
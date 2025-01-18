DROP DATABASE IF EXISTS toquebrado;

CREATE DATABASE toquebrado;

\connect toquebrado


CREATE FUNCTION public.atendimentos_anteriores(character) RETURNS TABLE(data_hora_inicio timestamp without time zone, data_hora_fim timestamp without time zone)
    LANGUAGE plpgsql
    AS $_$
BEGIN
    return query select atendimento.data_hora_inicio, atendimento.data_hora_fim from paciente 
    inner join atendimento on (paciente.id = atendimento.paciente_id) 
    where paciente.cpf = $1;
END;
$_$;


CREATE FUNCTION public.formata_cep(character) RETURNS text
    LANGUAGE plpgsql
    AS $_$
DECLARE
    resultado text;
BEGIN
    resultado := substring($1 from 1 for 2) || '.';
    resultado := resultado || substring($1 from 3 for 3) || '-';
    resultado := resultado || substring($1 from 6 for length($1));
    RETURN resultado;   
END;
$_$;


CREATE FUNCTION public.formata_telefone(character) RETURNS text
    LANGUAGE plpgsql
    AS $_$
DECLARE
    resultado text;
BEGIN
    resultado := '(' || substring($1 from 1 for 3) || ') ';
    resultado := resultado || substring ($1 from 4 for 7) || '-' || substring($1 from 8 for length($1));
    RETURN resultado;   
END;
$_$;


CREATE FUNCTION public.valida_cpf(character) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
DECLARE
    digito1 integer;
    digito1_real integer;
    digito2 integer;
    digito2_real integer;
    i integer;
    qtde integer;
    soma integer;
    multiplicador integer;
    resto integer;
BEGIN
   if (length($1) < 11) THEN
        RETURN FALSE;
   END IF; 
    
   i = 0; 
   while (i <= 9) loop
        if ((cast(i as text)||
            cast(i as text)||
            cast(i as text)||
            cast(i as text)||
            cast(i as text)||
            cast(i as text)||
            cast(i as text)||
            cast(i as text)||
            cast(i as text)||
            cast(i as text)||
            cast(i as text)) = $1) THEN
        RETURN FALSE;
        END IF;            
        i := i + 1;
   end loop;
    
    
    i := 1;
    soma := 0;
    multiplicador := 10;    
    while (i <= 9) LOOP
        BEGIN
            soma := soma + cast(substring($1 from i for 1) as integer)*multiplicador;
        EXCEPTION 
            WHEN OTHERS then RETURN FALSE;
        END;
            multiplicador := multiplicador - 1;
        i := i + 1;
    END LOOP;
    RAISE NOTICE 'soma1: %', soma;
    
    resto := soma % 11;
    
    if (resto < 2) then
        digito1 := 0;
    else
        digito1 := 11 - resto;
    end if;
    
    RAISE NOTICE '%', digito1;
    
    i := 1;
    soma := 0;
    multiplicador := 11;    
    while (i <= 10) LOOP
        BEGIN
            soma := soma + cast(substring($1 from i for 1) as integer)*multiplicador;
        EXCEPTION 
            WHEN OTHERS then RETURN FALSE;
        END;
        multiplicador := multiplicador - 1;
        i := i + 1;
    END LOOP;   
    RAISE NOTICE 'soma2: %', soma;
    
    resto := soma % 11; 
      if (resto < 2) then
        digito2 := 0;
    else
        digito2 := 11 - resto;
    end if;
    
    RAISE NOTICE '%', digito2;

    digito1_real := cast(substring($1 from 10 for 1) as integer);
    RAISE NOTICE '%', digito1_real;
    digito2_real := cast(substring($1 from 11 for 1) as integer);
    RAISE NOTICE '%', digito2_real;
    
    if (digito1 = digito1_real AND digito2 = digito2_real) THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;    
END;
$_$;


CREATE TABLE public.atendimento (
    id integer NOT NULL,
    fisioterapeuta_id integer,
    paciente_id integer,
    data_hora_inicio timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    data_hora_fim timestamp without time zone,
    observacao text,
    nota integer,
    valor_consulta money DEFAULT 100,
    valor_por_hora_fisioterapeuta money,
    CONSTRAINT atendimento_nota_check CHECK (((nota >= 0) AND (nota <= 5)))
);


CREATE TABLE public.fisioterapeuta (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    cpf character(11),
    crefito text NOT NULL,
    valor_por_hora money,
    CONSTRAINT fisioterapeuta_cpf_check CHECK ((public.valida_cpf(cpf) IS TRUE))
);


CREATE TABLE public.paciente (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    cpf character(11),
    telefone character(12),
    bairro text,
    rua text,
    complemento text,
    numero text,
    cep character(8),
    CONSTRAINT paciente_cpf_check CHECK ((public.valida_cpf(cpf) IS TRUE))
);
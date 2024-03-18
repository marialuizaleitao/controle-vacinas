DROP DATABASE IF EXISTS vacinas;
CREATE DATABASE vacinas;
USE vacinas;

CREATE TABLE vacinas.PESSOA (
    id INTEGER AUTO_INCREMENT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    sexo CHAR(1) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    tipo_pessoa ENUM('PESQUISADOR', 'VOLUNTARIO', 'PUBLICO_GERAL') NOT NULL,
    CONSTRAINT pessoa_pk PRIMARY KEY (id)
);

CREATE TABLE vacinas.VACINA (
    id INTEGER AUTO_INCREMENT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    pais_origem VARCHAR(45) NOT NULL,
    id_pesquisador INTEGER NOT NULL,
    estagio ENUM('INICIAL', 'TESTES', 'APLICACAO_EM_MASSA') NOT NULL,
    data_inicio_pesquisa DATE NOT NULL,
    CONSTRAINT vacina_pk PRIMARY KEY (id),
    CONSTRAINT pesquisador_pk FOREIGN KEY (id_pesquisador)
        REFERENCES PESSOA (id)
);

CREATE TABLE vacinas.APLICACAO_VACINA (
    id INTEGER AUTO_INCREMENT NOT NULL,
    id_pessoa INTEGER NOT NULL,
    id_vacina INTEGER NOT NULL,
    `data` DATE NOT NULL,
    avaliacao ENUM('PESSIMA', 'RUIM', 'REGULAR', 'BOA', 'OTIMA') NOT NULL,
    CONSTRAINT aplicacao_pk PRIMARY KEY (id),
    CONSTRAINT pessoa_pk FOREIGN KEY (id_pessoa)
        REFERENCES PESSOA (id),
    CONSTRAINT vacina_pk FOREIGN KEY (id_vacina)
        REFERENCES VACINA (id)
);
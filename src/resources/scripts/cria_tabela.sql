DROP DATABASE IF EXISTS vacinas;
CREATE DATABASE vacinas;
USE vacinas;

CREATE TABLE vacinas.PAIS (
    id INTEGER AUTO_INCREMENT NOT NULL,
    nome VARCHAR(45) NOT NULL,
    sigla VARCHAR(5) NOT NULL,
    CONSTRAINT pais_pk PRIMARY KEY (id)
);

CREATE TABLE vacinas.PESSOA (
    id INTEGER AUTO_INCREMENT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    sexo CHAR(1) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    id_pais INTEGER NOT NULL,
    tipo_pessoa ENUM('PESQUISADOR', 'VOLUNTARIO', 'PUBLICO_GERAL') NOT NULL,
    CONSTRAINT pessoa_pk PRIMARY KEY (id),
    CONSTRAINT paisPessoa_pk FOREIGN KEY (id_pais)
        REFERENCES PAIS (id)
);

CREATE TABLE vacinas.VACINA (
    id INTEGER AUTO_INCREMENT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    id_pesquisador INTEGER NOT NULL,
    id_pais INTEGER NOT NULL,
    estagio ENUM('INICIAL', 'TESTES', 'APLICACAO_EM_MASSA') NOT NULL,
    `data` DATE NOT NULL,
    CONSTRAINT vacina_pk PRIMARY KEY (id),
    CONSTRAINT pesquisador_pk FOREIGN KEY (id_pesquisador)
        REFERENCES PESSOA (id),
	CONSTRAINT paisVacina_pk FOREIGN KEY (id_pais)
        REFERENCES PAIS (id)
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
-- Inserindo países
INSERT INTO vacinas.PAIS (nome, sigla) VALUES 
('Brasil', 'BR'),
('Portugal', 'PT'),
('Belgica', 'BE');

-- Inserindo pessoas
INSERT INTO vacinas.PESSOA (nome, data_nascimento, sexo, cpf, id_pais, tipo_pessoa) VALUES
('Lewis Hamilton', '1985-01-07', 'M', '12345678901', 1, 'PESQUISADOR'),
('Maria Leitão', '1985-10-20', 'F', '98765432109', 2, 'VOLUNTARIO'),
('Max Verstappen', '1997-09-30', 'M', '24680135792', 3, 'PUBLICO_GERAL');

-- Inserindo vacinas
INSERT INTO vacinas.VACINA (nome, id_pesquisador, id_pais, estagio, `data`) VALUES
('Vacina A', 1, 1, 'TESTES', '2023-06-10'),
('Vacina B', 1, 2, 'INICIAL', '2023-07-20'),
('Vacina C', 2, 3, 'APLICACAO_EM_MASSA', '2023-08-30');

-- Inserindo aplicações de vacina
INSERT INTO vacinas.APLICACAO_VACINA (id_pessoa, id_vacina, `data`, avaliacao) VALUES
(1, 1, '2023-06-15', 'BOA'),
(2, 2, '2023-07-25', 'OTIMA'),
(3, 3, '2023-09-05', 'REGULAR');

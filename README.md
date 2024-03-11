# Controle de Vacinas - Estudo de Caso 2

Este repositório contém o código-fonte de um mini projeto desenvolvido como parte da disciplina de Desenvolvimento Backend no Senac, cursada no período de 2024.1. O objetivo principal é criar um sistema de controle de vacinas, seguindo o padrão de arquitetura MVC.

## Requisitos do Projeto

O sistema é composto por três camadas principais:

### Camada Controller

Esta camada expõe as APIs do sistema:

- **[POST] .../rest/pessoa:** Salva uma nova pessoa.
- **[GET] .../rest/pessoa/todas:** Lista todas as pessoas cadastradas.
- **[DELETE] .../rest/pessoa/id:** Exclui uma pessoa, dado o seu ID.

### Camada Service

Define as regras de negócio:

- **Salvar nova pessoa:**
  - Todos os campos são obrigatórios; caso algum não tenha sido preenchido, o serviço deve retornar uma `ControleVacinasException` com uma mensagem amigável.
  - CPF deve ser único no banco.
- Listar todas e excluir não possuem regras específicas.

### Camada Model

Define o modelo de dados e o acesso ao banco:

- Entidade e Repository para a Pessoa.
- Uma vacina possui:
  - País de origem.
  - Estágio da pesquisa (1-inicial, 2-testes, 3-aplicação em massa).
  - Data de início da pesquisa.
  - Nome do pesquisador responsável.
- Uma vacina pode ser aplicada em pessoas conforme a fase:
  - 1 - Somente pesquisadores.
  - 2 - Voluntários.
  - 3 - Público geral.
- Cada pessoa (com nome, data de nascimento, sexo e CPF) vacinada avaliará sua reação à aplicação numa escala de 1 a 5, sendo 1 péssimo e 5 ótimo.

O sistema deve possuir:
- Cadastro de pessoas (pesquisadores, voluntários e público geral).
- Cadastro de vacinas.
- Cadastro de aplicações de vacinas (data da aplicação, pessoa que recebeu).

## Tecnologias Utilizadas

- Linguagem de Programação: [Java](https://www.java.com/)
- Framework MVC: [Spring Boot](https://spring.io/projects/spring-boot)
- Banco de Dados: [MySQL](https://www.mysql.com/)

## Como Executar o Projeto

1. Clone este repositório.
2. Abra o projeto em sua IDE preferida.
3. Configure as informações do banco de dados no arquivo `application.properties`.
4. Execute o aplicativo Spring Boot.

Certifique-se de ter o JDK 8 ou superior instalado em sua máquina para garantir a execução adequada do projeto.

Esperamos que este projeto sirva como uma introdução prática aos conceitos fundamentais de desenvolvimento backend na disciplina de Desenvolvimento Backend. Caso tenha alguma dúvida ou sugestão, sinta-se à vontade para entrar em contato com os desenvolvedores.


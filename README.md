# luflix

Projeto demo desenvolvido com Spring Boot para gerenciamento de vídeos e categorias, incluindo autenticação e documentação automatizada.

## 📝 Descrição

O **luflix** é uma API RESTful projetada para simular funcionalidades de uma plataforma de streaming. O sistema permite o cadastro de vídeos, organização por categorias, controle de acesso via Spring Security com JWT e persistência em banco de dados MySQL.

## 🚀 Stacks Utilizadas

O projeto utiliza as seguintes tecnologias:

* **Java 21**: Linguagem principal utilizando a toolchain configurada.
* **Spring Boot 4.0.3**: Framework base para a construção da aplicação.
* **Spring Data JPA**: Para persistência de dados e interação com o banco.
* **MySQL**: Banco de dados relacional para produção/desenvolvimento.
* **Flyway**: Gerenciamento de migrações de banco de dados.
* **Spring Security & JWT**: Responsáveis pela autenticação e autorização de usuários.
* **Lombok**: Para redução de código boilerplate.
* **Docker**: Utilizado para orquestração do banco de dados MySQL via Docker Compose.
* **SpringDoc OpenAPI (Swagger)**: Para documentação da API.

## ⚙️ Como Rodar o Projeto

### Pré-requisitos

* Java 21 instalado.
* Docker e Docker Compose instalados.

### Passos para execução

1.  **Subir o Banco de Dados:**
    No diretório raiz do projeto, execute o comando para iniciar o container do MySQL:

    ```bash
    docker-compose up -d
    ```

    *Isso criará o banco `db_luflix` na porta `3306` conforme configurado no `compose.yml`*.

2.  **Configurar Variáveis de Ambiente (Opcional):**
    A aplicação possui valores padrão, mas você pode definir:

    * `JWT_SECRET_KEY`: Chave secreta para o token JWT.
    * `JWT_EXPIRATION_HOURS`: Tempo de expiração do token (padrão 2h).

3.  **Executar a Aplicação:**
    Use o Gradle para rodar o projeto:

    ```bash
    ./gradlew bootRun
    ```

## 📖 Como Acessar a Página do Swagger

Com a aplicação em execução, você pode visualizar e testar os endpoints da API através da interface do Swagger UI no seguinte endereço:

[http://localhost:8080/swagger-ui.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)

*A documentação é gerada automaticamente pela biblioteca SpringDoc OpenAPI configurada no projeto*.

## 📄 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.
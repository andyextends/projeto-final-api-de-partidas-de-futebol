# API de Partidas de Futebol 

Esta API foi desenvolvida para gerenciamento de clubes, estádios e partidas de futebol, com recursos de CRUD, filtros avançados e documentação Swagger.

Tecnologias Utilizadas

- Java 24
- Spring Boot
- Spring Web
- Spring Data JPA
- Swagger/OpenAPI 3
- Maven
- Postman
- colima
- docker

Endpoints

Clubes

| Método | Rota                    | Descrição                                    |
|--------|-------------------------|----------------------------------------------|
| POST   | `/clubes`              | Cadastrar um novo clube                      |
| GET    | `/clubes`              | Listar todos os clubes                       |
| GET    | `/clubes/{id}`         | Buscar clube por ID                          |
| PUT    | `/clubes/{id}`         | Atualizar clube por ID                       |
| DELETE | `/clubes/{id}`         | Remover clube por ID                         |
| POST   | `/clubes/filtro`       | Filtrar clubes com paginaÃ§Ã£o                 |
| GET    | `/clubes/{id}/retrospecto` | Obter retrospecto de partidas do clube   |

Estádios

| Método | Rota                   | Descrição                                  |
|--------|------------------------|---------------------------------------------|
| POST   | `/estadios`           | Cadastrar um novo estÃ¡dio                   |
| GET    | `/estadios`           | Listar todos os estÃ¡dios                    |
| GET    | `/estadios/{id}`      | Buscar estÃ¡dio por ID                       |
| PUT    | `/estadios/{id}`      | Atualizar estÃ¡dio por ID                    |
| DELETE | `/estadios/{id}`      | Remover estÃ¡dio por ID                      |

Partidas

| Método | Rota                    | Descrição                                |
|--------|-------------------------|------------------------------------------|
| POST   | `/partidas`             | Cadastrar nova partida                   |
| GET    | `/partidas`             | Listar todas as partidas                 |
| GET    | `/partidas/{id}`        | Buscar partida por ID                    |
| PUT    | `/partidas/{id}`        | Atualizar partida por ID                 |
| DELETE | `/partidas/{id}`        | Remover partida por ID                   |
| POST   | `/partidas/filtrar`     | Filtrar partidas com paginaÃ§Ã£o           |

Home

| Método | Rota   | Descrição                                                |
|--------|--------|------------------------------------------------------------|
| GET    | `/`    | Mensagem inicial com link para o Swagger UI               |

Validações e Regras de Negócio 

- Não permitir clubes com mesmo nome e estado
- Partida com intervalo mínimo ­nimo de 48h entre clubes
- Estádio não pode ter mais de uma partida no mesmo dia
- Clubes devem estar ativos
- Data da partida âpos data de criação dos clubes

Documentação da API

- Acesse via: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

> Projeto de estudo - Bootcamp Neocamp Java

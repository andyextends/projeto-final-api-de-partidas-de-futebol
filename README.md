# API de Partidas de Futebol ⚽

Esta API foi desenvolvida para gerenciamento de clubes, estádios e partidas de futebol, com recursos de CRUD, filtros avançados e documentação Swagger. O projeto utiliza **Arquitetura Hexagonal (Ports and Adapters)** para garantir separação de responsabilidades e facilitar testes e manutenção.

## 🚀 Como Executar a Aplicação

### Pré-requisitos

- **Java 17+**
- **Maven 3.6+**
- **Colima** (para macOS)
- **Docker** e **Docker Compose**
- **MySQL** (via Docker)

### 📋 Passo a Passo para Subir a Aplicação

#### 1. **Iniciar o Colima**
```bash
colima start
```

#### 2. **Iniciar o Container MySQL**
```bash
docker start 187d0e79cd55
```
> ⚠️ **Nota**: Substitua `187d0e79cd55` pelo ID do seu container MySQL

#### 3. **Configurar Variáveis de Ambiente**

Crie um arquivo `.env` na raiz do projeto ou configure as seguintes variáveis:

```bash
# Banco de Dados
DB_HOST=localhost
DB_PORT=3306
DB_NAME=futebol
DB_USERNAME=root
DB_PASSWORD=123456

# Kafka
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# Email (Gmail SMTP)
MAIL_USERNAME=seu-email@gmail.com
MAIL_PASSWORD=seu-app-password
MAIL_RECIPIENT=destinatario@gmail.com
```

#### 4. **Executar a Aplicação Spring Boot**

No IntelliJ IDEA ou IDE de sua preferência:
- Abra o arquivo `ApiPartidaFutebolApplication.java`
- Clique em **Run** ▶️

Ou via linha de comando:
```bash
./mvnw spring-boot:run
```

#### 5. **Subir os Serviços do Kafka**
```bash
docker-compose up -d
```

Você verá a seguinte saída:
```
✔ Container zookeeper  Running
✔ Container kafka      Running  
✔ Container kafdrop    Running
```

#### 6. **Verificar se Tudo Está Funcionando**

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **Kafdrop (Kafka UI)**: http://localhost:9000

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.1.3**
- **Spring Web**
- **Spring Data JPA**
- **Spring Kafka**
- **MySQL 8**
- **Swagger/OpenAPI 3**
- **Maven**
- **Docker & Docker Compose**
- **Colima** (Docker runtime para macOS)
- **Kafka + Zookeeper**
- **Kafdrop** (Kafka Web UI)

## 🏗️ Arquitetura do Projeto

Este projeto segue a **Arquitetura Hexagonal (Ports and Adapters)**:

### 📁 Estrutura de Diretórios

```
src/main/java/br/com/meli/apipartidafutebol/
├── domain/                 # 🎯 DOMÍNIO (núcleo da aplicação)
│   ├── port/
│   │   ├── in/            # Interfaces de casos de uso
│   │   └── out/           # Interfaces para saída (repositórios)
│   ├── vo/                # Value Objects
│   └── exception/         # Exceções de domínio
├── application/           # 📋 APLICAÇÃO (casos de uso)
│   └── usecase/          # Implementações dos casos de uso
├── adapter/              # 🔌 ADAPTADORES
│   ├── in/web/           # Adaptadores de entrada (Controllers)
│   ├── out/persistence/  # Adaptadores de persistência
│   └── out/viacep/       # Integrações externas
└── infrastructure/       # 🛠️ INFRAESTRUTURA
    ├── config/           # Configurações
    ├── dto/              # Data Transfer Objects
    └── model/            # Entidades JPA
```

### 🎯 Princípios da Arquitetura

- **Ports (Interfaces)**: Definem contratos entre camadas
- **Adapters**: Implementam integrações externas
- **Domain**: Contém regras de negócio isoladas
- **Separation of Concerns**: Cada camada tem responsabilidade específica

## 🔧 Configuração de Ambiente

### Arquivo `application.properties`

```properties
# Aplicação
spring.application.name=api-partida-futebol

# Banco de Dados MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/futebol?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.topic.partida=cadastro-partida
spring.kafka.consumer.group-id=grupo-partida

# Email (Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=seu-app-password
```

## 📊 Endpoints da API

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

## 📖 Documentação da API

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🐛 Troubleshooting

### Problemas Comuns

#### 1. **Erro ao conectar com MySQL**
```bash
# Verificar se o container MySQL está rodando
docker ps

# Se não estiver, iniciar o container
docker start 187d0e79cd55

# Verificar logs do container
docker logs 187d0e79cd55
```

#### 2. **Colima não está rodando**
```bash
# Verificar status do Colima
colima status

# Se parado, iniciar
colima start

# Em caso de problemas, reiniciar
colima stop && colima start
```

#### 3. **Kafka não está funcionando**
```bash
# Verificar containers do Kafka
docker-compose ps

# Reiniciar serviços do Kafka
docker-compose down && docker-compose up -d

# Verificar logs
docker-compose logs kafka
```

#### 4. **Porta 8080 já está em uso**
```bash
# Verificar qual processo está usando a porta
lsof -i :8080

# Matar o processo (substitua PID pelo número retornado)
kill -9 PID
```

#### 5. **Problemas com variáveis de ambiente**
- Verifique se as credenciais do banco estão corretas
- Confirme se o email e senha do Gmail estão configurados
- Para Gmail, use um **App Password** ao invés da senha normal

### 🔍 Verificação de Saúde

Após subir a aplicação, verifique:

1. **API funcionando**: `curl http://localhost:8080`
2. **Banco conectado**: Verifique logs da aplicação
3. **Kafka funcionando**: Acesse http://localhost:9000
4. **Swagger disponível**: http://localhost:8080/swagger-ui/index.html

## 🚀 Scripts Úteis

### Script de Inicialização Completa

Crie um arquivo `start.sh` na raiz do projeto:

```bash
#!/bin/bash
echo "🚀 Iniciando API de Partidas de Futebol..."

# 1. Iniciar Colima
echo "📦 Iniciando Colima..."
colima start

# 2. Iniciar MySQL
echo "🗄️ Iniciando MySQL..."
docker start 187d0e79cd55

# 3. Aguardar MySQL inicializar
echo "⏳ Aguardando MySQL..."
sleep 10

# 4. Subir Kafka
echo "📨 Iniciando Kafka..."
docker-compose up -d

# 5. Aguardar Kafka
echo "⏳ Aguardando Kafka..."
sleep 15

echo "✅ Infraestrutura pronta!"
echo "🎯 Agora execute a aplicação Spring Boot no IntelliJ"
echo "📊 Swagger: http://localhost:8080/swagger-ui/index.html"
echo "📨 Kafdrop: http://localhost:9000"
```

### Script de Parada

Crie um arquivo `stop.sh`:

```bash
#!/bin/bash
echo "🛑 Parando serviços..."

# Parar Kafka
docker-compose down

# Parar MySQL
docker stop 187d0e79cd55

# Parar Colima (opcional)
# colima stop

echo "✅ Serviços parados!"
```

## 📝 Notas Importantes

- **Java Version**: O projeto usa Java 17, mas o README mencionava Java 24
- **MySQL Container**: Substitua `187d0e79cd55` pelo ID real do seu container
- **Email Config**: Configure um App Password do Gmail para funcionalidade de email
- **Kafka Topics**: Os tópicos são criados automaticamente na primeira execução

---

> 🎓 **Projeto de estudo** - Bootcamp Neocamp Java  
> 🏗️ **Arquitetura**: Hexagonal (Ports and Adapters)  
> 👨‍💻 **Desenvolvido por**: Anders Silva

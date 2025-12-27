# 🏥 Medical API

API RESTful para gestão médica (clínicas, pacientes, profissionais, prontuários e agendamentos), desenvolvida com **Spring Boot**, seguindo **Clean Architecture** e **Domain-Driven Design (DDD)**.

---

## 📌 Visão Geral

Este projeto foi concebido para ambientes **reais de produção**, com foco em:

* Escalabilidade
* Manutenibilidade
* Segurança
* Multi-tenant
* Boas práticas de arquitetura

É ideal para sistemas de clínicas, consultórios e plataformas médicas.

---

## 🧱 Arquitetura

O projeto segue os princípios da **Clean Architecture**, separando claramente as responsabilidades:

```
Interfaces (Controllers)
        ↓
Application (Use Cases / Services)
        ↓
Domain (Entidades e Regras de Negócio)
        ↓
Infrastructure (Segurança, Banco, Configurações)
```

### 📂 Camadas

* **Interfaces**: Controllers REST, validações HTTP e handlers de erro
* **Application**: Casos de uso, DTOs e mapeamentos
* **Domain**: Entidades, enums, regras de negócio e contratos
* **Infrastructure**: Segurança, JWT, Multi-tenant, configurações técnicas

---

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Security + JWT**
* **Spring Data JPA**
* **PostgreSQL**
* **Flyway** (versionamento de banco)
* **Docker & Docker Compose**
* **Swagger / OpenAPI**
* **Maven**

---

## 📁 Estrutura do Projeto

```
medical-api
├── interfaces        # Controllers e handlers HTTP
├── application       # Use cases, DTOs e mappers
├── domain             # Entidades e regras de negócio
├── infrastructure     # Segurança, config, multi-tenant
├── exceptions         # Exceções de domínio e negócio
├── common             # Utilitários compartilhados
└── resources
    └── db/migration   # Scripts Flyway
```

---

## 🔐 Segurança

* Autenticação via **JWT**
* Controle de acesso baseado em **Roles**
* Filtro de autenticação customizado
* Tratamento centralizado de erros de segurança

---

## 🏢 Multi-Tenant

O sistema suporta **multi-tenancy**, permitindo múltiplas clínicas isoladas logicamente:

* Identificação do tenant via filtro
* Contexto de tenant centralizado
* Preparado para banco compartilhado ou separado

---

## 🗄️ Banco de Dados

* **PostgreSQL**
* Versionamento com **Flyway**
* Scripts localizados em:

```
src/main/resources/db/migration
```

---

## 🚀 Executando o Projeto

### 🔧 Pré-requisitos

* Java 21
* Docker
* Docker Compose

---

### ▶️ Subindo com Docker

```bash
docker-compose up --build -d
```

A API ficará disponível em:

```
http://localhost:8080
```

---

### ▶️ Executando localmente

```bash
./mvnw spring-boot:run
```

Ou:

```bash
mvn spring-boot:run
```

---

## 📑 Documentação da API

A documentação Swagger estará disponível em:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Testes

Estrutura preparada para:

* Testes unitários (Domain / Application)
* Testes de integração (Controllers)

Executar testes:

```bash
mvn test
```

---

## 📌 Padrões e Boas Práticas

* DTOs desacoplados do domínio
* Controllers sem regra de negócio
* Serviços orientados a casos de uso
* Domínio isolado de frameworks
* Exceções tratadas globalmente

---

## 🛣️ Roadmap

* [ ] Auditoria avançada
* [ ] Observabilidade (logs e métricas)
* [ ] Cache com Redis
* [ ] CI/CD
* [ ] Deploy em Cloud (AWS / GCP)

---

## 👨‍💻 Autor

**Kennedy Lima**
Desenvolvedor de Software

---

## 📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo `LICENSE` para mais informações.

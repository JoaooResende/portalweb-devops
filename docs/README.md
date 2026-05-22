# 🐾 Pet Family API — DevOps FIAP

API REST desenvolvida com Java Spring Boot 3, Docker e Oracle Database, hospedada em uma VM Linux Ubuntu na Microsoft Azure.

---

## 🚀 Tecnologias

- Java 17
- Spring Boot 3
- Docker
- Docker Compose
- Oracle XE 21
- Swagger
- Azure VM

---

## 🌐 Swagger

```txt
http://20.206.241.85:9090/swagger-ui/index.html
```

---

## 🔌 Endpoints

| Método | Endpoint |
|---|---|
| GET | `/tutores` |
| POST | `/tutores` |
| GET | `/pets` |
| POST | `/pets` |
| GET | `/dashboard/resumo` |

---

## 🧪 CURLs

### Listar tutores

```bash
curl http://20.206.241.85:9090/tutores
```

### Criar tutor

```bash
curl -X POST http://20.206.241.85:9090/tutores \
-H "Content-Type: application/json" \
-d '{"nome":"Joao Teste","email":"joao.teste@fiap.com","telefone":"11999999999"}'
```

### Listar pets

```bash
curl http://20.206.241.85:9090/pets
```

### Criar pet

```bash
curl -X POST http://20.206.241.85:9090/pets \
-H "Content-Type: application/json" \
-d '{"nome":"Rex","especie":"Cachorro","raca":"Labrador","idade":5,"peso":20.5,"tutorId":1}'
```

### Dashboard

```bash
curl http://20.206.241.85:9090/dashboard/resumo
```

---

## 🐳 Docker

### Containers em execução

```bash
sudo docker ps
```

### Docker Compose

```bash
docker compose ps
```

### Imagens Docker

```bash
docker image ls
```

---

## ☁️ Azure

- VM Ubuntu Linux
- Oracle Database em container
- Spring Boot em container
- Porta `9090` liberada
- Acesso externo funcionando pelo IP público

---

## 🔧 Ajustes Realizados

- Removido uso de H2
- Configurado Oracle Database como banco principal
- Corrigido `Dockerfile`
- Corrigido `docker-compose.yml`
- Porta externa ajustada para `9090`
- Porta interna da aplicação mantida em `8080`
- Configurado mapeamento `9090:8080`
- Desativado `DataInitializer`
- Criadas tabelas necessárias manualmente no Oracle
- CRUD validado via `curl`
- Endpoint público validado na Azure VM

---

## ✅ Requisitos Atendidos

- Dockerfile
- Docker Compose
- Oracle Database
- Sem uso de H2
- CRUD funcional
- Deploy Azure
- Swagger
- CURL funcionando
- Containers Docker
- Persistência de dados
- Acesso externo via IP público

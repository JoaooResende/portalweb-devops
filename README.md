# PetFamily API - DevOps Setup FIAP

## Rodar Projeto

### 1. Gerar o JAR
mvn clean package -DskipTests

### 2. Subir containers
docker compose up --build

### 3. Abrir Swagger
http://localhost:9090/swagger-ui.html

## Docker Hub

docker login

docker build -t SEU_USUARIO/petfamily-api:1.0 .

docker push SEU_USUARIO/petfamily-api:1.0

## Azure VM

sudo apt update
sudo apt install docker.io docker-compose-plugin -y

sudo docker compose up -d

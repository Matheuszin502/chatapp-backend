## 📖 Descrição
Este projeto é uma API para uma sala de chat, usa Spring Boot/Java e MongoDB e foi feito com intuito educativo.

### 💻 Tecnologias
- Java
- Spring Boot
- JPA/Hibernate
- Maven
- MongoDB

### ⚙️ Funcionalidades
- Listar Users(Usuários) e Posts(Postagens)
- Criar, remover e editar Users e Posts

### 🚀 Como rodar o código
* Antes de começar, é necessário ter o MongoDB instalado e rodando na máquina.
1. Clone o repositório. Comando para o git bash: git clone https://github.com/Matheuszin502/chatapp-backend.git
2. Execute no terminal: cd chatapp-backend
3. Execute no terminal: ./mvnw spring-boot:run
4. Enviar as requisições que desejar via Postman conforme as especificações a seguir

#### URLs:

http://localhost:8080/users

http://localhost:8080/posts

#### Exemplos de requisições e respostas:

GET /users lista todos os users

GET /users/id consulta o user com o id especificado

POST /users cria um user com o corpo especificado

Body:

    {
      "name": "fulano",
      "email": "fulano@email.com"
    }

GET /posts/fullsearch lista todos os posts

GET /posts/id consulta o post com o id especificado

POST /posts cria um post com o corpo especificado

Body:

    {
      "title": "título",
      "body": "corpo",
      "author": {
        "id": "id do user autor"
      }
    }

POST /posts/id adiciona um comentário com o corpo especificado abaixo, ao post com o id especificado na URL

Body:

    {
      "text": "corpo",
      "author": {
          "id": "id do user autor"
      }
    }

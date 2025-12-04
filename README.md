# Marketplace API

VIDEO RABBITMQ: https://youtu.be/SqjX9trPK-0

API REST para marketplace de materiais de construção e madeira, desenvolvida com Spring Boot 3.5.7 e Java 21.

## 🚀 Funcionalidades

- ✅ **Autenticação JWT** com refresh tokens
- ✅ **Documentação OpenAPI/Swagger** interativa
- ✅ **CORS configurável** para integração com frontends
- ✅ **Tratamento global de exceções** com respostas padronizadas
- ✅ **Compressão GZIP** para otimização de performance
- ✅ **Health checks** customizados
- ✅ **Logging estruturado** configurável
- ✅ **Rate limiting** para proteção contra ataques
- ✅ **Validação de dados** com Bean Validation

## 📋 Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- PostgreSQL (produção) ou H2 (desenvolvimento)

## 🔧 Instalação

1. Clone o repositório:
```bash
git clone <repository-url>
cd marktplace
```

2. Configure as variáveis de ambiente (opcional):
```bash
export DB_URL=jdbc:postgresql://localhost:5432/marketplace
export DB_USER=postgres
export DB_PASS=sua_senha
export JWT_SECRET=seu_secret_base64
```

3. Compile o projeto:
```bash
mvn clean install
```

4. Execute a aplicação:
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## 📚 Documentação da API

### Swagger UI
Acesse a documentação interativa em:
```
http://localhost:8080/swagger-ui.html
```

> **📌 Nota:** O Swagger UI é **publicamente acessível** e não requer autenticação. Você pode explorar todos os endpoints e testar aqueles que não exigem autenticação (como registro e login) diretamente pela interface.

### OpenAPI JSON
Especificação OpenAPI 3.0 disponível em:
```
http://localhost:8080/v3/api-docs
```

## 🔐 Autenticação

A API utiliza autenticação JWT (JSON Web Tokens). Para acessar endpoints protegidos:

1. **Registre um usuário**:
```bash
POST /api/users/register
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha123",
  "phone": "11999999999"
}
```

2. **Faça login**:
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@example.com",
  "password": "senha123"
}
```

Resposta:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 900000
}
```

3. **Use o token** em requisições subsequentes:
```bash
GET /api/materials
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

4. **Renove o token** quando expirar:
```bash
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

## 🛣️ Principais Endpoints

### Autenticação
- `POST /api/auth/login` - Realizar login
- `POST /api/auth/refresh` - Renovar token de acesso
- `POST /api/auth/logout` - Realizar logout

### Usuários
- `GET /api/users` - Listar todos os usuários
- `GET /api/users/{id}` - Buscar usuário por ID
- `POST /api/users/register` - Registrar novo usuário
- `PUT /api/users/{id}` - Atualizar usuário
- `DELETE /api/users/{id}` - Deletar usuário

### Materiais
- `GET /api/materials` - Listar todos os materiais
- `GET /api/materials/{id}` - Buscar material por ID
- `POST /api/materials` - Criar novo material
- `PUT /api/materials/{id}` - Atualizar material
- `DELETE /api/materials/{id}` - Deletar material

### Anúncios
- `GET /api/ads` - Listar todos os anúncios
- `GET /api/ads/{id}` - Buscar anúncio por ID
- `POST /api/ads` - Criar novo anúncio
- `PUT /api/ads/{id}` - Atualizar anúncio
- `DELETE /api/ads/{id}` - Deletar anúncio

## 🏥 Health Check

Verifique o status da aplicação:
```bash
GET /actuator/health
```

Resposta:
```json
{
  "status": "UP",
  "components": {
    "database": {
      "status": "UP",
      "details": {
        "database": "H2",
        "version": "2.1.214",
        "driver": "H2 JDBC Driver"
      }
    },
    "diskSpace": {
      "status": "UP"
    },
    "ping": {
      "status": "UP"
    }
  }
}
```

## ⚙️ Configuração

### Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `DB_URL` | URL do banco de dados | `jdbc:h2:mem:testdb` |
| `DB_USER` | Usuário do banco | `sa` |
| `DB_PASS` | Senha do banco | `` |
| `JWT_SECRET` | Secret para assinatura JWT | (gerado) |
| `JWT_ACCESS_EXPIRATION_MS` | Expiração do access token (ms) | `900000` (15 min) |
| `JWT_REFRESH_EXPIRATION_MS` | Expiração do refresh token (ms) | `604800000` (7 dias) |

### CORS

Configure as origens permitidas em `application.properties`:
```properties
cors.allowed-origins=http://localhost:3000,http://localhost:4200,http://localhost:5173
```

### Logging

Ajuste os níveis de log conforme necessário:
```properties
logging.level.com.projectweb.marktplace=DEBUG
logging.level.org.springframework.web=INFO
```

## 🧪 Testes

Execute os testes:
```bash
mvn test
```

Execute os testes de integração:
```bash
mvn verify
```

## 📦 Build para Produção

Gere o JAR executável:
```bash
mvn clean package -DskipTests
```

O arquivo será gerado em `target/marktplace-0.0.1-SNAPSHOT.jar`

Execute em produção:
```bash
java -jar target/marktplace-0.0.1-SNAPSHOT.jar
```

## 🐳 Docker (Opcional)

Crie um arquivo `Dockerfile`:
```dockerfile
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/marktplace-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build e execute:
```bash
docker build -t marketplace-api .
docker run -p 8080:8080 marketplace-api
```

## 📊 Monitoramento

A API expõe métricas do Actuator em:
- `/actuator/health` - Status da aplicação
- `/actuator/info` - Informações da aplicação
- `/actuator/metrics` - Métricas detalhadas
- `/actuator/prometheus` - Métricas no formato Prometheus

## 🌐 Endpoints Públicos

Os seguintes endpoints **não requerem autenticação** e podem ser acessados livremente:

### Documentação
- `/swagger-ui.html` - Interface Swagger UI
- `/swagger-ui/**` - Recursos do Swagger UI
- `/v3/api-docs` - Especificação OpenAPI JSON
- `/v3/api-docs/**` - Documentação da API

### Autenticação e Registro
- `POST /api/auth/login` - Login de usuários
- `POST /api/auth/refresh` - Renovação de tokens
- `POST /api/users/register` - Registro de novos usuários

### Monitoramento
- `/actuator/health` - Health check da aplicação
- `/actuator/**` - Endpoints do Spring Actuator

### Desenvolvimento
- `/h2-console/**` - Console do banco H2 (apenas em desenvolvimento)

> **⚠️ Importante:** Em produção, considere restringir o acesso aos endpoints do Actuator e H2 Console por questões de segurança.

## 🔒 Segurança

- **JWT Authentication** - Tokens assinados com HS256
- **Password Hashing** - BCrypt com salt
- **Rate Limiting** - Proteção contra brute force
- **CORS** - Configuração restritiva
- **Input Validation** - Validação de todos os inputs
- **SQL Injection Protection** - JPA/Hibernate com prepared statements

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 📧 Contato

Equipe de Desenvolvimento - dev@marketplace.com

---

**Desenvolvido com ❤️ usando Spring Boot**

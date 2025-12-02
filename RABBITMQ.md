# RabbitMQ Integration - Quick Start

## 🐰 Sobre

Este projeto utiliza RabbitMQ como message broker para processamento assíncrono de eventos.

## 🚀 Como Usar

### 1. Iniciar RabbitMQ com Docker

```bash
docker-compose up -d rabbitmq
```

### 2. Acessar RabbitMQ Management UI

- **URL:** http://localhost:15672
- **Usuário:** admin
- **Senha:** admin123

### 3. Verificar Queues

No Management UI, você verá 3 queues:
- `marketplace.notifications` - Notificações para usuários
- `marketplace.emails` - Emails assíncronos
- `marketplace.audit` - Logs de auditoria

## 📊 Eventos Disponíveis

### UserRegisteredEvent
Publicado quando um novo usuário se registra.
```json
{
  "userId": "uuid",
  "name": "João Silva",
  "email": "joao@example.com",
  "timestamp": "2024-12-02T10:00:00"
}
```

### PurchaseCreatedEvent
Publicado quando uma compra é realizada.
```json
{
  "purchaseId": "uuid",
  "buyerId": "uuid",
  "buyerName": "João Silva",
  "adId": "uuid",
  "adTitle": "Madeira de Lei",
  "sellerId": "uuid",
  "sellerName": "Maria Santos",
  "timestamp": "2024-12-02T10:00:00"
}
```

### MessageReceivedEvent
Publicado quando uma mensagem é enviada.
```json
{
  "messageId": "uuid",
  "senderId": "uuid",
  "senderName": "João Silva",
  "receiverId": "uuid",
  "receiverName": "Maria Santos",
  "adId": "uuid",
  "adTitle": "Madeira de Lei",
  "content": "Tenho interesse",
  "timestamp": "2024-12-02T10:00:00"
}
```

### RatingCreatedEvent
Publicado quando uma avaliação é criada.
```json
{
  "ratingId": "uuid",
  "userId": "uuid",
  "userName": "João Silva",
  "purchaseId": "uuid",
  "score": 5,
  "timestamp": "2024-12-02T10:00:00"
}
```

## 🔄 Fluxo de Eventos

```
1. Service publica evento
   ↓
2. RabbitMQ recebe e roteia
   ↓
3. Consumers processam assincronamente
   ↓
4. Ações executadas (notificações, emails, audit)
```

## 📝 Logs

Os consumers logam todas as ações:
```
INFO: Publishing PurchaseCreatedEvent for purchase: uuid
INFO: Creating notification for seller about new purchase: uuid
INFO: Audit log created for purchase: uuid
```

## 🛠️ Troubleshooting

### RabbitMQ não conecta
```bash
# Verificar se está rodando
docker ps | grep rabbitmq

# Ver logs
docker logs marketplace-rabbitmq
```

### Mensagens não são consumidas
- Verificar se o app está rodando
- Verificar logs do console
- Acessar Management UI e ver se mensagens estão nas queues

## 🎯 Próximos Passos

- [ ] Implementar envio real de emails (EmailConsumer)
- [ ] Adicionar mais eventos conforme necessário
- [ ] Configurar Dead Letter Queue para mensagens com erro
- [ ] Adicionar métricas de RabbitMQ no Actuator

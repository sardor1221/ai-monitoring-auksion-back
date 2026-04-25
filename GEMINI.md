Ты — senior Java backend developer и архитектор. 
Создай backend для демо платформы цифрового аукциона (e-auction system) на Spring Boot.

Требования:

1. 🏗 Архитектура:
- Spring Boot (latest)
- REST API
- Layered architecture (Controller, Service, Repository)
- DTO + MapStruct
- Lombok
- Global Exception Handler
- Validation (Hibernate Validator)

2. 🔐 Аутентификация:
- JWT authentication
- Роли: ADMIN, SELLER, BIDDER
- Spring Security config

3. 🧩 Основные сущности (JPA):
- User (id, name, email, password, role)
- Auction (id, title, description, startPrice, currentPrice, startTime, endTime, status)
- Bid (id, amount, user, auction, timestamp)
- Category (optional)

4. 📡 API (CRUD + логика):
Auth:
- POST /auth/register
- POST /auth/login

Auction:
- POST /auctions (создать аукцион)
- GET /auctions (список)
- GET /auctions/{id}
- PUT /auctions/{id}
- DELETE /auctions/{id}

Bids:
- POST /bids (сделать ставку)
- GET /auctions/{id}/bids

Логика:
- Проверка: ставка > текущей цены
- Автоматическое обновление currentPrice
- Запрет ставок после endTime

5. 🧠 Бизнес-логика:
- Статусы аукциона (CREATED, ACTIVE, FINISHED)
- Scheduler для завершения аукциона
- Победитель = highest bid

6. 🗄 База данных:
- PostgreSQL
- Liquibase или Flyway

7. 📄 Дополнительно:
- Swagger (OpenAPI)
- Логирование (SLF4J + Logback)
- Dockerfile

8. ⚡ BONUS:
- WebSocket для live-bidding
- Anti-sniping (продление времени)

Вывод:
- Полная структура проекта
- Код классов
- application.yml
- инструкции запуска
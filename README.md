
- **Controller layer** — handles HTTP requests/responses, input validation
- **Service layer** — business logic, including the risk scoring engine
- **Repository layer** — Spring Data JPA repositories for persistence
- **Security layer** — JWT-based stateless auth via a custom filter

## Risk Scoring Logic

The scoring engine starts from a baseline of 50 points and adjusts based on:

| Factor | Range | Weight |
|---|---|---|
| Credit score | 300–850 | up to ±30 |
| Debt-to-income ratio | — | up to ±30 |
| Employment stability | years + status | up to ±25 |
| Loan-to-income ratio | — | up to ±20 |

**Decision thresholds:**
- Score ≥ 70 → `APPROVE`
- Score 45–69 → `MANUAL_REVIEW`
- Score < 45 → `REJECT`

## API Endpoints

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Log in, returns JWT | No |
| POST | `/api/applicants` | Submit applicant data, get risk decision | Yes |
| GET | `/api/applicants/{id}/history` | Get evaluation history for an applicant | Yes |
| GET | `/actuator/health` | Health check | No |

Full interactive API docs available via Swagger UI at `/swagger-ui.html` once the app is running.

## Getting Started (Local Development)

### Prerequisites
- Java 17+
- Maven 3.8+
- Node.js 18+ and npm (for frontend)
- Docker & Docker Compose (optional, for containerized run)

### Run the backend
```bash
cd backend
./mvnw spring-boot:run
```
The API will be available at `http://localhost:8080`. By default it uses an in-memory H2 database — no setup needed.

### Run the frontend
```bash
cd frontend
npm install
npm start
```
The app will be available at `http://localhost:3000`.

### Run with Docker Compose (full stack)
```bash
docker-compose up --build
```
This spins up the backend, frontend, and a PostgreSQL database together.

## Running Tests
```bash
cd backend
./mvnw test
```

## Environment Variables

| Variable | Description | Default |
|---|---|---|
| `DB_URL` | Database connection URL | H2 in-memory |
| `DB_USER` | Database username | `sa` |
| `DB_PASSWORD` | Database password | (empty) |
| `JWT_SECRET` | Secret key for signing JWTs | dev default (change in prod) |

## CI/CD

GitHub Actions runs the test suite on every push and pull request to `main`. See `.github/workflows/ci.yml`.

## Live Demo

> _To be added once deployed._

- Frontend: TBD
- Backend API: TBD
- Swagger docs: TBD

## Project Structure


## Author

Tejaswi Koppula

# Loan Risk API

A full-stack application that evaluates loan applications and returns a risk-based approval decision, built to demonstrate real-world fintech engineering practices: clean API design, rule-based risk scoring, JWT authentication, and auditability.

## Overview

This project simulates a simplified loan underwriting system. Applicants submit their financial details through an API (and a web UI), and the system calculates a risk score (0–100) based on credit score, debt-to-income ratio, employment stability, and loan-to-income ratio. Based on that score, the system returns one of three decisions: **APPROVE**, **MANUAL_REVIEW**, or **REJECT**, along with a human-readable breakdown of why.

Every evaluation is stored, so applicants (and reviewers) can look back at decision history — a pattern common in real fintech/banking systems where auditability matters as much as the decision itself.

## Tech Stack

**Backend**
- Java 17
- Spring Boot 3.x (Web, Data JPA, Security, Validation, Actuator)
- PostgreSQL
- JWT (jjwt) for stateless authentication
- springdoc-openapi (Swagger UI) for live API docs
- JUnit 5 + Mockito for testing
- Maven

**Frontend**
- React
- Axios for API calls

**Infrastructure**
- Docker & Docker Compose
- GitHub Actions (CI: build + test on every push)
- Render (backend + database hosting)
- Vercel (frontend hosting)

## Architecture

┌─────────────┐ REST/JSON ┌──────────────────┐ JPA ┌──────────────┐
│ React │ ──────────────────────> │ Spring Boot │ ────────────────> │ PostgreSQL │
│ Frontend │ <────────────────────── │ API │ <──────────────── │ │
└─────────────┘ JWT auth └──────────────────┘ └──────────────┘
│
▼
Risk Scoring Engine
(rule-based, in-service)


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
mvn spring-boot:run
```
By default, this connects to the environment variables described below. For local development without a database set up, the app can also be configured to use an in-memory H2 database.

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
mvn test
```

## Environment Variables

| Variable | Description |
|---|---|
| `DB_URL` | JDBC database connection URL |
| `DB_USER` | Database username |
| `DB_PASSWORD` | Database password |
| `DB_DRIVER` | JDBC driver class (`org.postgresql.Driver`) |
| `JWT_SECRET` | Secret key for signing JWTs |

## CI/CD

GitHub Actions runs the test suite on every push and pull request to `main`. See `.github/workflows/ci.yml`.

## Live Demo

- Frontend: https://loan-risk-api-frontend.vercel.app
- Backend API: https://loan-risk-backend-ap6q.onrender.com
- Swagger docs: https://loan-risk-backend-ap6q.onrender.com/swagger-ui.html

> Note: the backend is hosted on a free tier and may take 30–50 seconds to respond on first load if it has been idle. Subsequent requests will be fast.

## Project Structure

loan-risk-api/
├── backend/ # Spring Boot API
├── frontend/ # React app
├── docker-compose.yml
└── .github/workflows/ci.yml


## Author

Tejaswi Koppula

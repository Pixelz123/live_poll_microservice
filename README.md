# 🧠 Live Poll Microservice Platform

A distributed **Quiz/Live Polling platform** built with a microservices architecture using technologies like **Redis**, **WebSockets**, **Spring Boot**, and **API Gateway**. This system is designed to efficiently manage live polls, collect real-time responses, and compute leaderboards with high scalability.

> 📌 **Frontend Repository:** [Frontend Repository](https://github.com/Pixelz123/live_poll_frontend_msrv)

---

## 📜 Table of Contents

- [🧠 Overview](#-overview)
- [📀 Architecture](#-architecture)
- [🧹 Microservices Breakdown](#-microservices-breakdown)
- [🚀 Technologies Used](#-technologies-used)
- [📦 Setup Instructions](#-setup-instructions)
- [📡 API Gateway Routing](#-api-gateway-routing)
- [📊 Leaderboard Logic](#-leaderboard-logic)
- [🔌 WebSocket Events](#-websocket-events)
- [📚 Future Improvements](#-future-improvements)

---

## 🧠 Overview

This project is a **microservices-based live quiz platform**, where users can:

- Join a poll
- Answer real-time questions
- Compete on a leaderboard
- Receive immediate feedback via WebSockets

It uses **Redis pub/sub**, **queues**, and **WebSockets** to provide low-latency communication and real-time updates across services.

---

## 📀 Architecture

&#x20;

Key communication patterns:

- **Pub/Sub** (via Redis) for distributing timeouts and leaderboard updates
- **Message Queues** for handling quiz questions and responses
- **WebSocket connections** for live communication with users

---

## 🧹 Microservices Breakdown

| Service                        | Description                                            |
| ------------------------------ | ------------------------------------------------------ |
| **API Gateway**                | Routes HTTP/WebSocket requests to appropriate services |
| **Auth Server**                | Handles authentication and issues tokens               |
| **User Service**               | Manages user data and profile                          |
| **Poll Service**               | Orchestrates quiz flow, manages questions, and timers  |
| **User Communication Service** | Manages WebSocket connections with users               |
| **Leaderboard Service**        | Computes and updates the leaderboard                   |
| **Discovery Service**          | Registers and discovers microservices (optional)       |

---

## 🚀 Technologies Used

- **Spring Boot** (for all services)
- **Redis** (for cache, pub/sub, queues)
- **WebSockets** (real-time communication)
- **RabbitMQ ** (decoupled communication)
- **Netflix Eureka ** (for service discovery)
- **PostgreSQL** (for user and quiz data persistence)

---

## 📦 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Pixelz123/live_poll_microservice.git
cd live_poll_microservice
```

### 2. Configure Environment

Set environment variables or edit `application.yml` files per service as needed:

- Redis URL
- Database credentials
- WebSocket configuration

### 3. Build the Services

```bash
./gradlew build
```

### 4. Run Individual Services

```bash
cd auth_server
./gradlew bootRun
```

---

## 📡 API Gateway Routing

The `API_GATEWAY` handles routing:

| Path        | Routed To         |
| ----------- | ----------------- |
| `/auth/**`  | Auth Server       |
| `/users/**` | User Service      |
| `/poll/**`  | Poll Service      |
| `/ws/**`    | WebSocket Service |

---

## 📊 Leaderboard Logic

- The `Poll Service` tracks quiz progress
- On question timeout → it publishes `leaderboard_timesup` via Redis
- `Leaderboard Service` listens and updates scores
- Final scores are sent via WebSocket to the user

---

## 🔌 WebSocket Events

| Event              | Direction       | Description               |
| ------------------ | --------------- | ------------------------- |
| `connect&wait`     | Client → Server | User initiates connection |
| `start`            | Server → Client | Quiz begins               |
| `question`         | Server → Client | Sends question            |
| `user_response`    | Client → Server | Sends answer              |
| `timesup`          | Server → Client | Indicates time's up       |
| `leaderboard_Data` | Server → Client | Sends leaderboard         |

---

## 📚 Future Improvements

- Horizontal scaling with Kafka
- Add analytics dashboard for quiz statistics


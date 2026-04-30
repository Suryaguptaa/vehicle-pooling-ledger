# FairSplit

> A clean, reliable, and modern shared expense management system -- built to fix everything wrong with Splitwise.

**🟢 Live Demo:** [[https://fairsplit-lbe9.onrender.com/](https://fairsplit-lbe9.onrender.com/)](https://fairsplit-lbe9.onrender.com/)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![JavaScript](https://img.shields.io/badge/Vanilla_JS-F7DF1E?style=flat-square&logo=javascript&logoColor=black)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Groq AI](https://img.shields.io/badge/Groq-LLaMA_3-black?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-purple?style=flat-square)

---

## Overview

FairSplit is a full-stack group expense tracking application that lets users create groups, add shared expenses, and instantly see who owes whom -- with precise, transparent balance calculations.

Unlike existing tools, FairSplit focuses on:

- Accurate and transparent expense calculations
- Simple, intuitive user experience
- Clean premium UI with dark/light mode
- AI-powered spending insights via Groq LLaMA 3

---

## Features

| Feature | Description |
|---|---|
| JWT Authentication | Secure register/login with persistent sessions |
| Group Management | Create groups, invite members via unique 6-character codes |
| Expense Tracking | Full CRUD for expenses with auto-recalculation of balances |
| Net-Balance Engine | Precise "who owes whom" logic without floating-point errors |
| Settlement System | Record payments and clear debts between members |
| Activity Log | Audit trail for every group event |
| AI Insights | Groq LLaMA 3 generates natural language spending summaries |
| Dark / Light Mode | Theme persists across sessions via localStorage |
| Mobile Responsive | Collapsible sidebar and stacked layout on smaller screens |

---

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | HTML5, CSS3, Vanilla JavaScript (ES6+) |
| Backend | Java 17, Spring Boot 3.5, Spring Security, Spring Data JPA |
| Database | MySQL 8.0 (H2 for local dev) |
| AI | Groq REST API (LLaMA 3) |

---

## How the Balance Engine Works

The backend calculates net balances on every expense add, edit, or delete:

```
Expense: $300   Participants: 3   Paid by: Alice

perPersonShare = $300 / 3 = $100

Every participant  ->  netBalance -= 100
Payer (Alice)      ->  netBalance += 300

Result:
  Alice    -100 + 300  =  +$200   (is owed)
  Bob      -100        =  -$100   (owes)
  Charlie  -100        =  -$100   (owes)
```

The system resolves these net amounts into specific debt pairs for the frontend to display.

---

## Project Structure
```
src/main/java/com/fairsplit/
  controller/     REST endpoints for auth, groups, and expenses
  dto/            Data Transfer Objects for clean API contracts
  entity/         JPA Entities: AppUser, AppGroup, GroupExpense, ActivityLog
  exception/      GlobalExceptionHandler for standardized error responses
  repository/     Spring Data JPA repositories
  security/       JWT filters, token provider, SecurityFilterChain config
  service/        Core business logic and transaction management
```

---

## Running Locally

**1. Clone the repo**
```bash
git clone https://github.com/Suryaguptaa/FairSplit.git
cd FairSplit
```

**2. Set your environment variables**

In `src/main/resources/application.properties`:
```properties
GROQ_API_KEY=your_groq_api_key_here
spring.datasource.url=jdbc:mysql://localhost:3306/fairsplit_db
spring.datasource.username=root
spring.datasource.password=your_password
```

**3. Start the backend**
```bash
mvn spring-boot:run
```

**4. Open the app**

Navigate to `http://localhost:8080` in your browser. The frontend is served directly by Spring Boot from the `static/` directory.

---

## API Reference

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT |
| POST | `/api/groups/create` | Create a new group |
| POST | `/api/groups/join` | Join a group via invite code |
| GET | `/api/groups` | Get all groups for current user |
| POST | `/api/expenses/add` | Add a new group expense |
| PUT | `/api/expenses/{id}` | Edit an existing expense |
| DELETE | `/api/expenses/{id}` | Delete an expense |
| GET | `/api/expenses/group/{id}/debts` | Get net debt pairs for a group |
| POST | `/api/expenses/settle` | Record a settlement payment |
| GET | `/api/summary` | Get AI-generated spending summary |

---

## Team

Built as a 6th Semester project at **Lakshmi Narain College of Technology Excellence**.

| Name | Role |
|---|---|
| **Surya Dev Gupta** | Backend -- Spring Boot REST APIs, JWT Security, Database Schema, Settlement Engine, Groq AI Integration |
| **Eshant Likhitkar** | Frontend -- UI design, CSS system, Dark/Light mode, page transitions |
| **Risabh Singh** | Frontend -- API integration, balance display, forms, mobile responsiveness |

---

## License

MIT License -- see [LICENSE](LICENSE) for details.

# 👥 Staffing Process App

A Spring Boot backend application designed to manage staffing processes, clients, and employees. Integrated with Keycloak for authentication and built with Docker for easy deployment.

---

## 📦 Features

- 🧑‍💼 Client and contact management
- 🔁 Staffing process tracking and employee assignment
- 🔐 Keycloak OAuth2 authentication and role-based access
- 💾 MySQL database integration
- 🐳 Docker Compose-ready
- 📄 Structured logging

---

## 🚀 Getting Started

### ✅ Requirements

- Java 17+
- Docker & Docker Compose
- Maven

### Clone the Repository

```shell
   git clone https://github.com/Robert-tech2000/staffing-process.git
```


## 🐳 Running with Docker

Make sure you have Docker and Docker Compose installed.


### 🏁 First-time setup

```bash
  docker compose up --build
```
---
## 🧪 API Overview

### 👤 **Client Endpoints** (`/clients`)

| Method | Endpoint            | Description                      |
|--------|---------------------|----------------------------------|
| POST   | `/clients`            | Create a new client           |
| GET    | `/clients/{clientId}`   | Retrieve a client by ID           |
| GET    | `/clients`            | Retrieve all clients             |
| PUT    | `/clients/{clientId}`   | Update an existing client                 |
| DELETE    | `/clients/{clientId}`         | Delete a client by ID |  

---
### 💬 **Comment Endpoints** (`/comments`)

| Method | Endpoint            | Description                      |
|--------|---------------------|----------------------------------|
| GET   | `/comments/{commentId}`            | Retrieve a comment by ID           |
| GET    | `/comments`   | Retrieve all comments          |
| PUT    | `/comments/{commentId}`            | Update an existing comment            |
| DELETE    | `/comments/{commentId}`   | Delete a comment by ID              |

---
### 👷‍♂️ **Employee Endpoints** (`/employees`)

| Method | Endpoint            | Description                      |
|--------|---------------------|----------------------------------|
| POST   | `	/employees?name={employeeName}`            | Create a new employee (via query parameter)       |
| GET    | `/employees/{employeeId}`   | Retrieve an employee by ID          |
| GET    | `/employees`            | Retrieve all employees           |
| PUT    | `/employees/{employeeId}`   | Update an existing employee                |
| DELETE    | `/employees/{employeeId}`         | Delete an employee by ID|  

---
### 📋 **Staffing Process Endpoints** (`/staffing-processes`)

| Method | Endpoint            | Description                      |
|--------|---------------------|----------------------------------|
| POST   | `/staffing-processes`            | Create a new staffing process using client and employee IDs and title           |
| GET    | `/staffing-processes/{staffingProcessId}`   | Get a staffing process by ID           |
| GET    | `/staffing-processes`            | Get all staffing processes             |
| PUT    | `/staffing-processes/{staffingProcessId}`   | Update an existing staffing process                 |
| DELETE    | `/staffing-processes/{staffingProcessId}`         | Delete a staffing process by ID |  
| GET    | `/staffing-processes/{staffingId}/comments`   | Get all comments for a specific staffing process                 |
| POST    | `/staffing-processes/{staffingId}/comments`         | Add a comment to a specific staffing process |

---
## 🔐 Authentication

The app uses **Keycloak** for OAuth2-based security.

- Realm: `oauth2-staffing-process-UI-realm`
- Client: `oauth2-staffing-process-client`
- Roles:
    - `ROLE_ADMIN`
    - `ROLE_EMPLOYEE`

Use a valid Keycloak access token (Bearer token) to access secured endpoints.

###  Retrieve JWT Token from Keycloak
- A user must be created in the keycloak instance by accessing http://host.docker.internal:8081/
- Afterward Realm role Admin or Employee has to be assigned to user.

```http
POST http://host.docker.internal:8081/realms/oauth2-staffing-process-UI-realm/protocol/openid-connect/token

Content-Type: application/json
{
  "grant_type": "password",
  "client_id": "oauth2-staffing-process-client",
  "username": "$username",
  "password": "$password"
  }
}
```
Copy access_token from Response Body and paste to subsequent requests.

---
# Auth Service

A secure authentication and authorization microservice built with Spring Boot that provides JWT-based authentication, user management, and role-based access control for the HR Leave Management System.

## Overview

Auth Service is the central authentication provider in a microservices architecture. It handles user registration, login, JWT token generation and validation, password management, and OAuth integration. Other services (like Leave Service) rely on this service for user authentication and authorization.

## Features

- **User Authentication**: Secure login with JWT token generation
- **User Registration**: New user signup with email validation
- **Password Management**: Secure password hashing with BCrypt and password strength validation
- **JWT Token Management**: Generate, validate, and refresh JWT tokens
- **Role-Based Access Control**: Support for EMPLOYEE, MANAGER, and HR_ADMIN roles
- **OAuth Integration**: Google OAuth2 authentication support
- **Password Reset**: Secure password reset flow with token validation
- **User Profile Management**: Update user information and preferences
- **Account Security**: Password strength validation using Passay library

## Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Security**: Spring Security + JWT (jjwt 0.11.5) + BCrypt
- **Database**: PostgreSQL (production) / H2 (development)
- **ORM**: Spring Data JPA
- **Validation**: Spring Boot Validation + Passay (password rules)
- **OAuth**: Google OAuth2 Client
- **API Documentation**: Swagger/OpenAPI
- **Cloud**: Spring Cloud Config for distributed configuration
- **Build Tool**: Maven

## Architecture

This service acts as the authentication gateway for all microservices:
- Centralized user management and authentication
- JWT token generation with configurable expiration
- Stateless authentication (tokens contain user info and roles)
- Shared secret key with other services for token validation
- OAuth2 integration for third-party authentication

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ (for production)
- Google OAuth2 credentials (optional, for OAuth login)

## Getting Started

### 1. Clone the repository
```bash
git clone <your-repo-url>
cd auth-service
```

### 2. Configure database
Update `application.properties` or `application.yml`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/auth_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Configure JWT settings
```properties
jwt.secret=your_secret_key_here
jwt.expiration=86400000
jwt.refresh.expiration=604800000
```

### 4. Configure Google OAuth (optional)
```properties
spring.security.oauth2.client.registration.google.client-id=your_client_id
spring.security.oauth2.client.registration.google.client-secret=your_client_secret
```

### 5. Run the application
```bash
mvn spring-boot:run
```

The service will start on `http://localhost:8080` (default port)

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and receive JWT token
- `POST /api/auth/refresh` - Refresh expired token
- `POST /api/auth/logout` - Invalidate token (optional)
- `GET /api/auth/oauth2/google` - Google OAuth login

### User Management
- `GET /api/users/me` - Get current user profile
- `PUT /api/users/me` - Update current user profile
- `GET /api/users/{id}` - Get user by ID (admin only)
- `GET /api/users` - List all users (admin only)
- `DELETE /api/users/{id}` - Delete user (admin only)

### Password Management
- `POST /api/auth/forgot-password` - Request password reset
- `POST /api/auth/reset-password` - Reset password with token
- `PUT /api/auth/change-password` - Change password (authenticated)

### User Roles (Admin)
- `PUT /api/users/{id}/roles` - Update user roles
- `GET /api/roles` - List all available roles

## Request/Response Examples

### Register User
```json
POST /api/auth/register
{
  "email": "user@example.com",
  "password": "SecurePass123!",
  "firstName": "John",
  "lastName": "Doe",
  "role": "EMPLOYEE"
}
```

### Login
```json
POST /api/auth/login
{
  "email": "user@example.com",
  "password": "SecurePass123!"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 86400000,
  "user": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "EMPLOYEE"
  }
}
```

## User Roles

- **EMPLOYEE**: Basic user access, can manage own profile
- **MANAGER**: Employee privileges + team management
- **HR_ADMIN**: Full system access, user management, role assignment

## Password Policy

Enforced using Passay library:
- Minimum 8 characters
- At least 1 uppercase letter
- At least 1 lowercase letter
- At least 1 digit
- At least 1 special character
- No common passwords or dictionary words

## JWT Token Structure

Tokens include:
- User ID
- Email
- Role(s)
- Issued at timestamp
- Expiration timestamp

Other services validate these tokens using the shared secret key.

## Security Features

- **Password Hashing**: BCrypt with configurable strength
- **Token Expiration**: Configurable access and refresh token lifetimes
- **CORS Configuration**: Configurable allowed origins
- **Rate Limiting**: Protection against brute force attacks (via Spring Security)
- **Input Validation**: Request validation using Bean Validation
- **SQL Injection Prevention**: Parameterized queries via JPA

## Database Schema

Key entities:
- **User**: User account information (email, password hash, name)
- **Role**: User roles (EMPLOYEE, MANAGER, HR_ADMIN)
- **RefreshToken**: Stored refresh tokens for token renewal
- **PasswordResetToken**: Temporary tokens for password reset flow

## Integration with Other Services

Other microservices (like Leave Service) integrate by:
1. Accepting JWT tokens in Authorization header
2. Validating tokens using the shared secret key
3. Extracting user info and roles from token claims
4. Making authorization decisions based on roles

## Testing

Run unit and integration tests:
```bash
mvn test
```

Test coverage includes:
- Authentication flows
- Token generation and validation
- Password hashing and verification
- Role-based access control
- OAuth integration

## Built With

- Spring Boot - Application framework
- Spring Security - Authentication and authorization
- Spring Data JPA - Database access
- PostgreSQL - Production database
- jjwt - JWT token generation and validation
- Passay - Password validation rules
- Google OAuth2 Client - Third-party authentication
- Lombok - Boilerplate code reduction
- ModelMapper - Object mapping
- Problem Spring Web - RFC 7807 error handling

## Environment Variables

Key configuration options:
```properties
JWT_SECRET=your_secret_key
JWT_EXPIRATION=86400000
DATABASE_URL=jdbc:postgresql://localhost:5432/auth_db
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
```

## Future Enhancements

- Two-factor authentication (2FA)
- Account lockout after failed login attempts
- Session management and monitoring
- User activity logging and audit trail
- Social login support (GitHub, Microsoft, etc.)
- API key management for service-to-service auth

## License

This project is part of a learning portfolio.

## Author

Built as part of a microservices HR management system.

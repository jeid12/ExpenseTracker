# ExpenseTracker - Multi-User Expense Management System

A full-stack expense tracking application with JWT authentication, role-based access control, and Docker containerization.

## Features

### Authentication & Security
- JWT token-based authentication
- Role-based access control (ADMIN/USER)
- Secure password encryption with BCrypt
- Password reset via email
- Profile image upload to Cloudinary

### User Features
- Personal expense and income tracking
- Dashboard with statistics and charts
- User profile management
- Password change functionality
- Complete data isolation between users

### Admin Features
- User management (view, delete, activate/deactivate)
- Category management for expenses and income
- System-wide reports and statistics

## Quick Start

### 1. Configure Environment Variables
Create a `.env` file in the root directory based on `.env.example`

### 2. Build and Run
```bash
docker compose build
docker compose up -d
```

### 3. Access Application
- Frontend: http://localhost:4000
- Backend: http://localhost:8080

### 4. Default Login
- Admin: admin@expensetracker.com / Admin@123
- User: user@expensetracker.com / User@123

See full documentation in the project wiki.

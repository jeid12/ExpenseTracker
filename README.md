c# Expense Tracker - Multi-User Application

A full-stack expense tracking application with JWT authentication, role-based access control, and multi-user support.

## Features

### Authentication & Authorization
- JWT-based authentication
- Role-based access control (Admin & User)
- Secure password encryption with BCrypt
- Password reset via email
- Profile management with Cloudinary image upload

### User Features
- Track personal expenses and income
- View personal statistics and reports
- Generate charts for last 28 days
- Manage profile and change password
- Upload profile picture to Cloudinary

### Admin Features
- Manage users (view, delete, activate/deactivate)
- Manage categories (CRUD operations)
- View system-wide reports
- Control expense/income categories

### Technical Stack
- **Backend**: Spring Boot 3.3.5, Java 17, PostgreSQL 15
- **Frontend**: Angular 18 with SSR
- **Security**: Spring Security with JWT (JJWT 0.11.5)
- **Cloud Storage**: Cloudinary for profile images
- **Email**: Spring Mail for password reset
- **Containerization**: Docker & Docker Compose

## Prerequisites

- Docker & Docker Compose
- (Optional) Cloudinary account for profile image uploads
- (Optional) Gmail account with App Password for email functionality

## Quick Start

### 1. Clone the Repository
```bash
cd /home/jeid/Desktop/projects/final_docker/ExpenseTracker
```

### 2. Configure Environment Variables

Copy the example environment file:
```bash
cp .env.example .env
```

Edit `.env` file and update the following:

#### Required Configuration:
```env
# JWT Secret - IMPORTANT: Change this in production!
JWT_SECRET=YourVerySecureRandomSecretKeyHere

# Database credentials (can keep defaults for development)
POSTGRES_USER=expense_user
POSTGRES_PASSWORD=expense_pass
POSTGRES_DB=expense_db
```

#### Optional Configuration:

**For Profile Image Upload (Cloudinary):**
1. Sign up at https://cloudinary.com/
2. Get your credentials from Dashboard
3. Update in `.env`:
```env
CLOUDINARY_CLOUD_NAME=your_actual_cloud_name
CLOUDINARY_API_KEY=your_actual_api_key
CLOUDINARY_API_SECRET=your_actual_api_secret
```

**For Password Reset Emails (Gmail):**
1. Enable 2-Factor Authentication on your Gmail account
2. Generate an App Password: https://myaccount.google.com/apppasswords
3. Update in `.env`:
```env
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_16_digit_app_password
```

### 3. Build and Run with Docker

```bash
# Build and start all services
docker-compose up --build

# Or run in detached mode
docker-compose up -d --build
```

This will start:
- **PostgreSQL** on port 5432
- **Backend API** on http://localhost:8080
- **Frontend** on http://localhost:4000

### 4. Access the Application

Open your browser and navigate to:
```
http://localhost:4000
```

### 5. Create Admin User (First Time Setup)

The system doesn't have a default admin user. You need to:

1. **Register a new user** through the frontend
2. **Manually update the user role in the database**:

```bash
# Connect to the PostgreSQL container
docker exec -it expense-tracker-db psql -U expense_user -d expense_db

# Update the first user to ADMIN role
UPDATE users SET role = 'ADMIN' WHERE id = 1;

# Exit psql
\q
```

Or using a single command:
```bash
docker exec -it expense-tracker-db psql -U expense_user -d expense_db -c "UPDATE users SET role = 'ADMIN' WHERE id = 1;"
```

## API Endpoints

### Public Endpoints
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login
- `POST /api/auth/forgot-password` - Request password reset
- `POST /api/auth/reset-password` - Reset password with token

### User Endpoints (Authenticated)
- `GET /api/user/profile` - Get current user profile
- `PUT /api/user/profile` - Update profile
- `POST /api/user/change-password` - Change password
- `POST /api/user/profile-image` - Upload profile image
- `GET /api/expenses/all` - Get user's expenses
- `POST /api/expense` - Create expense
- `PUT /api/expense/{id}` - Update expense
- `DELETE /api/expense/{id}` - Delete expense
- `GET /api/income/all` - Get user's income
- `POST /api/income` - Create income
- `PUT /api/income/{id}` - Update income
- `DELETE /api/income/{id}` - Delete income
- `GET /api/stats` - Get user statistics
- `GET /api/stats/chart` - Get chart data
- `GET /api/categories` - Get active categories
- `GET /api/categories/by-type?type=EXPENSE` - Get categories by type

### Admin Endpoints (Admin Role Required)
- `GET /api/admin/users` - Get all users
- `DELETE /api/admin/users/{id}` - Delete user
- `PUT /api/admin/users/{id}/toggle-status` - Activate/deactivate user
- `GET /api/admin/categories` - Get all categories
- `POST /api/admin/categories` - Create category
- `PUT /api/admin/categories/{id}` - Update category
- `DELETE /api/admin/categories/{id}` - Delete category
- `PUT /api/admin/categories/{id}/toggle-status` - Activate/deactivate category

## Development

### Stop Services
```bash
docker-compose down
```

### View Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f db
```

### Rebuild After Code Changes
```bash
docker-compose up --build
```

### Clean Up (Remove volumes)
```bash
docker-compose down -v
```

## Database Schema

### Users Table
- `id` (Primary Key)
- `first_name`, `last_name`, `email`
- `password` (encrypted)
- `role` (ADMIN or USER)
- `profile_image_url`
- `is_active`
- `reset_password_token`, `reset_password_expiry`
- `created_at`, `updated_at`

### Categories Table
- `id` (Primary Key)
- `name`, `description`
- `type` (EXPENSE or INCOME)
- `is_active`

### Expenses Table
- `id` (Primary Key)
- `user_id` (Foreign Key to Users)
- `title`, `description`, `amount`, `category`, `date`

### Income Table
- `id` (Primary Key)
- `user_id` (Foreign Key to Users)
- `title`, `description`, `amount`, `category`, `date`

## Security Features

1. **JWT Authentication**: Secure token-based authentication
2. **Password Encryption**: BCrypt hashing
3. **Role-Based Access**: Admin and User roles
4. **Data Isolation**: Users can only access their own data
5. **CORS Configuration**: Configured for localhost:4000
6. **Token Expiration**: Configurable JWT expiration (default 24 hours)

## Troubleshooting

### Backend won't start
- Check if PostgreSQL is running: `docker ps | grep expense-tracker-db`
- Check backend logs: `docker-compose logs backend`
- Verify environment variables in docker-compose.yml

### Frontend can't connect to backend
- Ensure backend is running on port 8080
- Check CORS configuration in SecurityConfig.java
- Verify API URL in frontend services

### Email not sending
- Verify Gmail App Password is correct
- Check if 2FA is enabled on Gmail account
- Review backend logs for email errors

### Cloudinary upload failing
- Verify Cloudinary credentials
- Check Cloudinary dashboard for API usage
- Review backend logs for detailed error

### Database connection issues
- Check if database container is running
- Verify database credentials match in .env
- Check database logs: `docker-compose logs db`

## License

This project is for educational purposes.

#!/bin/bash

# ExpenseTracker Setup Script
# This script helps you set up the ExpenseTracker application

echo "====================================="
echo "ExpenseTracker Setup Script"
echo "====================================="
echo ""

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Error: Docker is not running. Please start Docker and try again."
    exit 1
fi

echo "✅ Docker is running"
echo ""

# Check if .env file exists
if [ ! -f .env ]; then
    echo "⚠️  .env file not found. Creating from .env.example..."
    if [ -f .env.example ]; then
        cp .env.example .env
        echo "✅ Created .env file. Please edit it with your credentials."
        echo "   Required: JWT_SECRET, CLOUDINARY credentials, MAIL credentials"
        echo ""
        read -p "Press Enter after you've configured .env file..."
    else
        echo "❌ Error: .env.example not found"
        exit 1
    fi
else
    echo "✅ .env file exists"
fi

echo ""
echo "Building Docker images..."
docker compose build

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi

echo ""
echo "✅ Build completed successfully"
echo ""

echo "Starting containers..."
docker compose up -d

if [ $? -ne 0 ]; then
    echo "❌ Failed to start containers. Please check the error messages above."
    exit 1
fi

echo ""
echo "✅ Containers started successfully"
echo ""

# Wait for database to be ready
echo "Waiting for database to be ready..."
sleep 10

# Insert initial users
echo "Creating initial admin and test users..."
docker compose exec -T db psql -U expense_user -d expense_db << 'EOSQL'
INSERT INTO users (first_name, last_name, email, password, role, is_active, created_at, updated_at)
VALUES (
    'Admin',
    'User',
    'admin@expensetracker.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'ADMIN',
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (email) DO NOTHING;

INSERT INTO users (first_name, last_name, email, password, role, is_active, created_at, updated_at)
VALUES (
    'Test',
    'User',
    'user@expensetracker.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'USER',
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (email) DO NOTHING;
EOSQL

echo ""
echo "✅ Initial users created"
echo ""

# Show container status
echo "Container Status:"
docker compose ps
echo ""

echo "====================================="
echo "✅ Setup Complete!"
echo "====================================="
echo ""
echo "Application URLs:"
echo "  Frontend: http://localhost:4000"
echo "  Backend:  http://localhost:8080"
echo ""
echo "Default Credentials:"
echo "  Admin:  admin@expensetracker.com / password"
echo "  User:   user@expensetracker.com  / password"
echo ""
echo "⚠️  Change these passwords immediately!"
echo ""
echo "Useful Commands:"
echo "  View logs:        docker compose logs -f"
echo "  Stop containers:  docker compose down"
echo "  Restart:          docker compose restart"
echo ""

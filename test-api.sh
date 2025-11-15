#!/bin/bash

# ExpenseTracker API Test Script

echo "======================================"
echo "ExpenseTracker API Tests"
echo "======================================"
echo ""

BASE_URL="http://localhost:8080/api"

echo "1. Testing Login Endpoint..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@expensetracker.com","password":"password"}')

if [ $? -eq 0 ]; then
    echo "✅ Login successful"
    echo "Response: $(echo $LOGIN_RESPONSE | head -c 200)..."
    
    # Extract token
    TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
    
    if [ -n "$TOKEN" ]; then
        echo "✅ JWT Token received"
        echo ""
        
        echo "2. Testing Profile Endpoint with Token..."
        PROFILE_RESPONSE=$(curl -s "$BASE_URL/user/profile" \
          -H "Authorization: Bearer $TOKEN")
        
        if [ $? -eq 0 ]; then
            echo "✅ Profile endpoint successful"
            echo "Response: $PROFILE_RESPONSE"
        else
            echo "❌ Profile endpoint failed"
        fi
    else
        echo "❌ Failed to extract token"
    fi
else
    echo "❌ Login failed"
fi

echo ""
echo "3. Testing Registration Endpoint..."
RANDOM_EMAIL="testuser$(date +%s)@test.com"
REGISTER_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"$RANDOM_EMAIL\",\"password\":\"Test@123\"}")

if echo "$REGISTER_RESPONSE" | grep -q "successfully"; then
    echo "✅ Registration successful"
    echo "Response: $REGISTER_RESPONSE"
else
    echo "⚠️  Registration response: $REGISTER_RESPONSE"
fi

echo ""
echo "4. Testing Unauthenticated Access..."
UNAUTH_RESPONSE=$(curl -s -w "\n%{http_code}" "$BASE_URL/user/profile")
HTTP_CODE=$(echo "$UNAUTH_RESPONSE" | tail -n1)

if [ "$HTTP_CODE" = "403" ] || [ "$HTTP_CODE" = "401" ]; then
    echo "✅ Unauthenticated access properly blocked (HTTP $HTTP_CODE)"
else
    echo "⚠️  Unexpected response code: $HTTP_CODE"
fi

echo ""
echo "======================================"
echo "API Tests Complete"
echo "======================================"

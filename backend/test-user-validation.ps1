# Test script for user validation and password reset functionality
# This script tests both the user existence check and password reset endpoints

$baseUrl = "http://localhost:8080/api"
$existingEmail = "test@example.com"  # This should exist in your database
$nonExistentEmail = "nonexistent@example.com"  # This should not exist

Write-Host "Testing user validation and password reset functionality..." -ForegroundColor Green

# Test 1: Check if existing user exists
Write-Host "`n1. Testing user existence check for existing user..." -ForegroundColor Yellow
try {
    $response1 = Invoke-RestMethod -Uri "$baseUrl/auth/check-user-exists?email=$existingEmail" -Method GET
    Write-Host "✓ User existence check successful: $($response1.message) - Data: $($response1.data)" -ForegroundColor Green
} catch {
    Write-Host "✗ User existence check failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Check if non-existent user exists
Write-Host "`n2. Testing user existence check for non-existent user..." -ForegroundColor Yellow
try {
    $response2 = Invoke-RestMethod -Uri "$baseUrl/auth/check-user-exists?email=$nonExistentEmail" -Method GET
    Write-Host "✓ User existence check successful: $($response2.message) - Data: $($response2.data)" -ForegroundColor Green
} catch {
    Write-Host "✗ User existence check failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Password reset for existing user
Write-Host "`n3. Testing password reset for existing user..." -ForegroundColor Yellow
try {
    $response3 = Invoke-RestMethod -Uri "$baseUrl/auth/forgot-password" -Method POST -ContentType "application/json" -Body (@{email=$existingEmail} | ConvertTo-Json)
    Write-Host "✓ Password reset for existing user successful: $($response3.message)" -ForegroundColor Green
} catch {
    Write-Host "✗ Password reset for existing user failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 4: Password reset for non-existent user
Write-Host "`n4. Testing password reset for non-existent user..." -ForegroundColor Yellow
try {
    $response4 = Invoke-RestMethod -Uri "$baseUrl/auth/forgot-password" -Method POST -ContentType "application/json" -Body (@{email=$nonExistentEmail} | ConvertTo-Json)
    Write-Host "✗ Password reset for non-existent user should have failed but succeeded: $($response4.message)" -ForegroundColor Red
} catch {
    $errorResponse = $_.ErrorDetails.Message | ConvertFrom-Json
    Write-Host "✓ Password reset for non-existent user correctly failed: $($errorResponse.message)" -ForegroundColor Green
}

Write-Host "`nUser validation and password reset test completed!" -ForegroundColor Green
Write-Host "Expected results:" -ForegroundColor Cyan
Write-Host "- Test 1: Should show user exists (true)" -ForegroundColor Cyan
Write-Host "- Test 2: Should show user doesn't exist (false)" -ForegroundColor Cyan
Write-Host "- Test 3: Should succeed and send email" -ForegroundColor Cyan
Write-Host "- Test 4: Should fail with 'No account found' message" -ForegroundColor Cyan
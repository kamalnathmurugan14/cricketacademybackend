# Test script for password reset functionality
# This script tests the password reset endpoint to verify the constraint violation is fixed

$baseUrl = "http://localhost:8080/api"
$testEmail = "test@example.com"

Write-Host "Testing password reset functionality..." -ForegroundColor Green

# Test 1: First password reset request
Write-Host "`n1. Testing first password reset request..." -ForegroundColor Yellow
try {
    $response1 = Invoke-RestMethod -Uri "$baseUrl/auth/forgot-password" -Method POST -ContentType "application/json" -Body (@{email=$testEmail} | ConvertTo-Json)
    Write-Host "✓ First request successful: $($response1.message)" -ForegroundColor Green
} catch {
    Write-Host "✗ First request failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Wait a moment
Start-Sleep -Seconds 2

# Test 2: Second password reset request (this should not cause constraint violation)
Write-Host "`n2. Testing second password reset request (should reuse or replace token)..." -ForegroundColor Yellow
try {
    $response2 = Invoke-RestMethod -Uri "$baseUrl/auth/forgot-password" -Method POST -ContentType "application/json" -Body (@{email=$testEmail} | ConvertTo-Json)
    Write-Host "✓ Second request successful: $($response2.message)" -ForegroundColor Green
} catch {
    Write-Host "✗ Second request failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Third password reset request (rapid succession)
Write-Host "`n3. Testing third password reset request (rapid succession)..." -ForegroundColor Yellow
try {
    $response3 = Invoke-RestMethod -Uri "$baseUrl/auth/forgot-password" -Method POST -ContentType "application/json" -Body (@{email=$testEmail} | ConvertTo-Json)
    Write-Host "✓ Third request successful: $($response3.message)" -ForegroundColor Green
} catch {
    Write-Host "✗ Third request failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`nPassword reset test completed!" -ForegroundColor Green
Write-Host "If all tests passed, the constraint violation issue has been resolved." -ForegroundColor Cyan
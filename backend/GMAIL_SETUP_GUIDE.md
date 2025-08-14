# Gmail SMTP Configuration Guide

## Issue Fixed
The OTP emails were not being sent because:
1. The `OtpService` was only printing to console instead of using the actual email service
2. The Gmail App Password was not configured properly in `application.yml`

## Gmail App Password Setup

### Step 1: Enable 2-Factor Authentication
1. Go to your Google Account settings: https://myaccount.google.com/
2. Navigate to "Security" → "2-Step Verification"
3. Enable 2-Step Verification if not already enabled

### Step 2: Generate App Password
1. Go to "Security" → "2-Step Verification" → "App passwords"
2. Select "Mail" as the app and "Other" as the device
3. Enter "Cricket Academy Backend" as the device name
4. Click "Generate"
5. Copy the 16-character app password (format: xxxx xxxx xxxx xxxx)

### Step 3: Update application.yml
Replace `YOUR_APP_PASSWORD_HERE` in `src/main/resources/application.yml` with your actual app password:

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: kamalnath146@gmail.com
    password: your-16-character-app-password-here  # Replace with actual app password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000
    test-connection: false
```

## Testing the Fix

### 1. Start the Backend
```bash
cd backend
mvn spring-boot:run
```

### 2. Test OTP Email Endpoint
```bash
curl -X POST http://localhost:8080/api/users/{userId}/send-otp \
  -H "Content-Type: application/json" \
  -d '{
    "field": "email",
    "newValue": "test@gmail.com"
  }'
```

### 3. Check Logs
Look for these log messages:
- `OTP email sent successfully to: test@gmail.com`
- `Email sent successfully to: test@gmail.com`

## Troubleshooting

### Common Issues:
1. **Authentication Failed**: Check if app password is correct
2. **Connection Timeout**: Check firewall/network settings
3. **Invalid Credentials**: Ensure 2FA is enabled and app password is generated correctly

### Debug Steps:
1. Check application logs for detailed error messages
2. Verify Gmail account settings
3. Test with a simple email first using the `/api/email/send` endpoint
4. Ensure the recipient email exists and can receive emails

## Code Changes Made

### 1. Updated OtpService.java
- Added dependency injection for `EmailServiceImpl`
- Modified `sendOtpEmail()` to use actual email service instead of console output
- Added proper error handling and logging

### 2. Enhanced EmailService.java
- Improved OTP email template with better formatting
- Updated expiry time to match OtpService (5 minutes)
- Added professional email signature

### 3. Configuration
- Documented proper Gmail SMTP setup
- Added connection timeout settings for better reliability
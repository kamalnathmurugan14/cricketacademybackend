# OTP Verification API Documentation

## Overview
This API provides email and phone number verification functionality using OTP (One-Time Password) for user profile updates.

## Base URL
```
http://localhost:8080/api
```

## Authentication
All endpoints require JWT authentication. Include the token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Endpoints

### 1. Get User Profile (with verification status)
**GET** `/users/profile`

Returns the current user's profile including verification status.

**Response:**
```json
{
  "success": true,
  "message": "User profile retrieved successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890",
    "age": 25,
    "experienceLevel": "INTERMEDIATE",
    "role": "STUDENT",
    "createdAt": "2025-01-01T10:00:00",
    "isActive": true,
    "emailVerified": true,
    "phoneVerified": true,
    "emailVerificationPending": null,
    "phoneVerificationPending": null
  },
  "timestamp": "2025-01-08T12:00:00"
}
```

### 2. Send OTP for Email/Phone Change
**POST** `/users/{id}/send-otp`

Sends an OTP to the new email address or phone number for verification.

**Request Body:**
```json
{
  "field": "email",  // or "phone"
  "newValue": "newemail@example.com"  // or new phone number
}
```

**Response:**
```json
{
  "success": true,
  "message": "OTP sent successfully",
  "timestamp": "2025-01-08T12:00:00"
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Invalid field for OTP",
  "timestamp": "2025-01-08T12:00:00"
}
```

### 3. Verify OTP and Update Email/Phone
**POST** `/users/{id}/verify-otp`

Verifies the OTP and updates the email address or phone number if valid.

**Request Body:**
```json
{
  "field": "email",  // or "phone"
  "newValue": "newemail@example.com",  // must match the value used in send-otp
  "otp": "123456"
}
```

**Response:**
```json
{
  "success": true,
  "message": "email updated and verified successfully",
  "data": {
    "message": "email updated and verified successfully",
    "emailVerified": true,
    "phoneVerified": true
  },
  "timestamp": "2025-01-08T12:00:00"
}
```

**Error Responses:**
```json
{
  "success": false,
  "message": "Invalid or expired OTP",
  "timestamp": "2025-01-08T12:00:00"
}
```

```json
{
  "success": false,
  "message": "Email verification mismatch",
  "timestamp": "2025-01-08T12:00:00"
}
```

### 4. Cancel Pending Verification
**POST** `/users/{id}/cancel-verification`

Cancels a pending email or phone verification process.

**Request Body:**
```json
{
  "field": "email"  // or "phone"
}
```

**Response:**
```json
{
  "success": true,
  "message": "email verification cancelled successfully",
  "timestamp": "2025-01-08T12:00:00"
}
```

## Verification Status Fields

### emailVerified / phoneVerified
- `true`: The email/phone is verified and can be used
- `false`: The email/phone is not verified

### emailVerificationPending / phoneVerificationPending
- `null`: No pending verification
- `"new@example.com"`: Verification is pending for this new email/phone

## Frontend Integration

### Verification Status Display
Based on the verification fields, display appropriate status indicators:

```javascript
function getVerificationStatus(verified, pending) {
  if (pending) {
    return {
      status: 'pending',
      message: `⏳ Verification pending for: ${pending}`,
      class: 'status-pending'
    };
  } else if (verified) {
    return {
      status: 'verified',
      message: '✓ Verified',
      class: 'status-verified'
    };
  } else {
    return {
      status: 'unverified',
      message: '⚠ Not Verified',
      class: 'status-unverified'
    };
  }
}
```

### Complete Verification Flow
1. User clicks "Change Email/Phone"
2. User enters new email/phone
3. Call `/send-otp` endpoint
4. Show OTP input field
5. User enters OTP
6. Call `/verify-otp` endpoint
7. Update UI with new verified email/phone

### Error Handling
- **Invalid OTP**: Show error message, allow retry
- **Expired OTP**: Show error message, offer to resend
- **Network Error**: Show generic error message
- **Verification Mismatch**: This shouldn't happen in normal flow

## Security Considerations

1. **OTP Expiry**: OTPs expire after 5 minutes
2. **Single Use**: Each OTP can only be used once
3. **Rate Limiting**: Consider implementing rate limiting for OTP requests
4. **Verification Mismatch**: The system checks that the newValue matches the pending verification

## Database Schema

The User entity includes these additional fields:

```sql
ALTER TABLE users 
ADD COLUMN email_verified BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN phone_verified BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN email_verification_pending VARCHAR(255),
ADD COLUMN phone_verification_pending VARCHAR(255);
```

## Example Frontend Implementation

See `frontend-example/profile-otp-example.html` for a complete working example of the OTP verification UI.

## Testing

### Manual Testing Steps

1. **Get Profile**: Call GET `/users/profile` to see current verification status
2. **Send Email OTP**: 
   - POST `/users/1/send-otp` with `{"field": "email", "newValue": "test@example.com"}`
   - Check console/logs for OTP (in development)
3. **Verify Email OTP**:
   - POST `/users/1/verify-otp` with the OTP from logs
   - Verify email is updated and verified
4. **Test Phone OTP**: Similar steps for phone verification
5. **Test Cancellation**: Use cancel-verification endpoint

### Error Cases to Test
- Invalid field names
- Expired OTPs
- Wrong OTPs
- Verification mismatches
- Network failures
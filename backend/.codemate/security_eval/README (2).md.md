# Security Vulnerability Report  
**Project:** Cricket Academy API  
**Date:** 2024-06  


## Executive Summary

This report reviews the Cricket Academy API documentation and code outline for security vulnerabilities. The primary areas of focus are authentication, input validation, data storage, endpoint security, and configuration management. The below vulnerabilities and potential security concerns have been identified based on the project documentation and code samples provided.

---

## 1. Credential Handling and Storage

### Issue: Plaintext Password Exposure  
**Severity:** High  

**Description:**  
In the user registration endpoint `/api/auth/register`, new users send their password in plaintext (e.g., `"password": "password123"`). Although the documentation mentions password encryption using BCrypt, it is unclear whether transport security (HTTPS) is enforced.

**Risk:**
- User credentials can be intercepted if transmitted over unsecured HTTP.
- Possible exposure of credentials via logs or error messages if not handled safely.

**Recommendation:**
- Force all API requests to use HTTPS.
- Never log request bodies or plaintext passwords.
- Mask sensitive fields in logs and error messages.

---

## 2. CORS Configuration

### Issue: Broad Cross-Origin Access  
**Severity:** Medium  

**Description:**  
Documentation states:  
> CORS is configured to allow cross-origin requests

It does not specify whether the configuration is restrictive (i.e., only trusted domains) or open to all origins.

**Risk:**
- Overly permissive CORS allows any external websites to access the API, increasing XSS and CSRF risk.

**Recommendation:**
- Limit allowed origins to trusted domains in CORS configuration.
- Do not use `allowed-origins: *` in production environments.

---

## 3. Role-Based Access Control (RBAC)

### Issue: Insufficient RBAC Details  
**Severity:** Medium  

**Description:**  
While endpoints indicate required roles (e.g., `Admin` for `/api/users`), the documentation does not specify how RBAC is enforced or configured.

**Risk:**
- Inadequate role checks may allow privilege escalation (e.g., normal users accessing admin endpoints).

**Recommendation:**
- Use method-level security annotations (`@PreAuthorize`, `@RolesAllowed`, etc.) in Spring Security.
- Test for endpoint exposure to unauthorized roles.

---

## 4. Input Validation and Parameter Handling

### Issue: Query and Path Parameter Validation  
**Severity:** Medium  

**Description:**  
Endpoints such as `validate-email`, `validate-phone`, and others receive email or phone numbers via query parameters. The documentation indicates that Bean Validation is used, but specifics are lacking.

**Risk:**
- If validation is incomplete, leads to injection attacks (SQL injection, email header injection, etc.).
- Especially risky if raw parameters reach the database without sanitization.

**Recommendation:**
- Use strict validation annotations (@Email, @Pattern, etc.) on all user input.
- Sanitize and validate all query/path parameters before processing.

---

## 5. Authentication Mechanism

### Issue: Authentication Details Not Specified  
**Severity:** Medium-High  

**Description:**  
All authenticated endpoints require some kind of authentication but the documentation does not specify the mechanism (e.g., JWT, session, token type, expiration, etc.).

**Risk:**
- Weak authentication mechanisms (session fixation, predictable tokens, lack of expiration) can be exploited.
- JWT tokens, if used, must be signed and have reasonable expiration.

**Recommendation:**
- Clearly define authentication mechanism—prefer JWT or OAuth2.
- Use robust token signing keys, enable token expiration and revocation.
- Secure authentication endpoints against brute-force attacks (rate limiting).

---

## 6. Account Enumeration via Validation Endpoints

### Issue: User Enumeration with Validate Endpoints  
**Severity:** Medium  

**Description:**  
Endpoints `/api/auth/validate-email` and `/api/auth/validate-phone` reveal whether a user exists via direct Boolean responses (`"available": true/false`).

**Risk:**
- Attackers can brute-force these endpoints to enumerate registered users, aiding phishing or targeted attacks.

**Recommendation:**
- Return generic responses (e.g., "Validation received") or implement rate limiting and logging.
- Consider always returning the same success message, regardless of actual availability.

---

## 7. Information Exposure via Error Messages

### Issue: Detailed Error Messages  
**Severity:** Low-Medium  

**Description:**  
All errors return both message and timestamp. If error messages are detailed, this may leak stack traces or application internals.

**Risk:**
- Attackers gain insight into internal structure or state, aiding further attacks.

**Recommendation:**
- Use generic error messages in production.
- Do not expose stack traces or sensitive details in API responses.

---

## 8. Data Exposure in User Endpoints

### Issue: Over-Exposure of User Data  
**Severity:** Medium  

**Description:**  
User API responses may include excessive data (e.g., internal IDs, phone, roles, timestamps), and there is no mention of field filtering depending on the requester’s role.

**Risk:**
- Leaks sensitive PII if endpoints are not properly restricted or filtered.

**Recommendation:**
- Carefully limit fields returned by user-related endpoints.
- Apply role-based field filtering (e.g., normal users cannot see others' emails or phone numbers).

---

## 9. Deactivation Endpoint (`DELETE /api/users/{id}`)

### Issue: No Mention of Authorization or Audit  
**Severity:** Medium  

**Description:**  
`DELETE /api/users/{id}` deactivates a user account. Documentation says "Authentication: Required" but does not specify whether only admins or self-deactivation is allowed.

**Risk:**
- Users might deactivate accounts they do not own.
- Lack of audit trail for critical actions.

**Recommendation:**
- Enforce strict authorization: only account owner or admin can deactivate.
- Log/audit all account deactivation events.

---

## 10. Database Credential Handling

### Issue: Database Credentials in Config  
**Severity:** Medium  

**Description:**  
Examples show raw database credentials (`username: your_username`, `password: your_password`) in application config.

**Risk:**
- Storing plaintext credentials risks accidental exposure (e.g., git commits, backups).

**Recommendation:**
- Use externalized secrets management (environment variables, Vault).
- Never commit credentials to VCS.

---

## 11. Dependency Security

### Issue: No Mention of Dependency Management  
**Severity:** Medium  

**Description:**  
Project uses Maven, but there is no mention of dependabot, Snyk, or other vulnerability scanning.

**Risk:**
- Outdated dependencies may expose known vulnerabilities.

**Recommendation:**
- Regularly audit dependencies.
- Use tools like OWASP Dependency-Check.

---

# Summary Table

| Vulnerability                                | Severity   | Recommendation                              |
|----------------------------------------------|------------|---------------------------------------------|
| Plaintext credential handling                | High       | Enforce HTTPS, never log sensitive data     |
| CORS configuration                          | Medium     | Restrict allowed origins                    |
| Role-based access/control flaws              | Medium     | Enforce RBAC at method-level                |
| Input & parameter validation                 | Medium     | Strict validation & sanitization            |
| Authentication mechanism disclosure          | Medium-High| Specify strong, modern authentication       |
| User/account enumeration                     | Medium     | Generic responses, rate limiting            |
| Information exposure (errors, user data)     | Medium     | Hide stack traces, limit fields in responses|
| Account deactivation weaknesses              | Medium     | Strict authorization, audit events          |
| DB credentials in config                     | Medium     | External secrets management                 |
| Dependency vulnerabilities                   | Medium     | Automated vulnerability scanning            |

---

# Final Recommendations

- Perform a full security code review and test using automated tools (OWASP ZAP, Snyk).
- Harden deployment pipeline: enforce HTTPS, secrets management, strong password policies.
- Implement security logging and monitoring.
- Keep dependencies and frameworks up-to-date.
- Educate developers about secure coding best practices.

---

**End of Report**
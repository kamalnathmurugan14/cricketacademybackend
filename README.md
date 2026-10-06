# Indusion Cricket Academy – Full Application (Indusion4)

End-to-end documentation for the Cricket Academy platform: user registration and login, OTP and password-reset flows, coaching programs and experts, enrollments with online payment, career applications, and admin management of fees, attendance and performance.

> **Repository layout**
>
> | Path | Contents |
> |------|----------|
> | `backend/` | Spring Boot 3 REST API (this is the full-featured backend) |
> | `project/` | Placeholder for the frontend. It is **empty** in this repository. The backend's CORS config expects a Vite/React dev server on `http://localhost:5173`, `5174` or `3000`. |
>
> A lighter, auth-only backend lives in the companion repository `IndusionCricketBackEndApplication`.

---

## 1. Features

- **Auth**: register, login (JWT), logout, email/phone availability checks, experience-level lookup
- **Users**: profile, list, filter by experience level, statistics, update, delete
- **Verification**: OTP by email (Gmail SMTP) and SMS (Infobip) for changing email or phone
- **Password reset**: request, validate OTP, reset (with password-strength rules)
- **User activity audit**: login/logout times, IP address, user agent
- **Coaching**: public lists of experts and programs; admin CRUD (expert photo upload via multipart)
- **Enrollments**: enroll a user in a program and check status
- **Payments**: create a Cashfree order and get a `paymentSessionId`
- **Careers**: public application form (Coach or Ground Staff) with admin review
- **Admin modules**: attendance, fees, performance, career, coaching

## 2. Tech stack

| Layer | Technology |
|-------|-----------|
| Language / runtime | Java 17 |
| Framework | Spring Boot 3.2.0 (Web, Data JPA, Validation, Security) |
| Database | MySQL 8 (`mysql-connector-java` 8.0.33), Hibernate `ddl-auto: update` |
| Auth | JWT (jjwt 0.11.5), stateless sessions, BCrypt passwords |
| Email | Spring Mail over Gmail SMTP |
| SMS / 2FA | Infobip (called with OkHttp) |
| Payments | Cashfree |
| Build | Maven |
| Misc | Lombok |

## 3. Architecture

```
Browser / SPA (Vite, port 5173)
        │  HTTPS/JSON  (Authorization: Bearer <JWT>)
        ▼
Spring Security filter chain ── SimpleJwtFilter (validates JWT, sets role)
        ▼
Controllers  ─▶  Services  ─▶  Repositories (Spring Data JPA)  ─▶  MySQL
                    │
                    ├─ EmailService / OtpService ─▶ Gmail SMTP
                    ├─ SmsService / Infobip2FA*  ─▶ Infobip API
                    └─ CashfreeService          ─▶ Cashfree API
```

Source tree (`backend/src/main/java/com/cricketacademy/api/`):

```
config/      Security, JWT filter, CORS, email configuration
controller/  REST endpoints (auth, users, admin, coaching, career, payment, email, ...)
service/     Business logic, OTP, email, SMS, payments
repository/  Spring Data JPA repositories
entity/      User, UserActivity, OtpToken, PasswordResetToken, CareerApplication
model/       Program, CoachingExpert, Enrollment, Fee, Attendance, Performance, ...
dto/         Request/response objects (ApiResponse wrapper etc.)
exception/   Custom exceptions + GlobalExceptionHandler
mapper/      Entity/DTO mappers
util/        JwtUtil, IP helper, password reset/migration utilities
runner/      PasswordMigrationRunner (startup task)
```

Resources: `backend/src/main/resources/` holds `application.yml`, `application.properties`, and SQL migrations in `db/migration/V0…V7`.

## 4. Prerequisites

- JDK 17+
- Maven 3.6+ (or use your IDE)
- MySQL 8.0+
- Optional, for full functionality: a Gmail account with an App Password, an Infobip account, a Cashfree account

## 5. Setup and run

### 5.1 Database
```sql
CREATE DATABASE cricket_academy;
```
Tables are created automatically by Hibernate (`ddl-auto: update`). The migration SQL files in `db/migration` (users, admin seed, verification fields, user activity) and the helper scripts `fix-database-setup.sql` and `cleanup-password-reset-tokens.sql` are available for manual use.

### 5.2 Configuration
Edit `backend/src/main/resources/application.yml` (and `application.properties`). **Do not commit real secrets**; prefer environment variables or an untracked profile file.

| Property | Purpose |
|----------|---------|
| `server.port` / `server.servlet.context-path` | Default `8080` and `/api` |
| `spring.datasource.url / username / password` | MySQL connection |
| `app.jwt.secret`, `app.jwt.expiration` | JWT signing key (use a long random value) and lifetime in ms (default 86400000 = 24h) |
| `spring.mail.host / port / username / password` | Gmail SMTP: `smtp.gmail.com`, `587`, and a 16-character App Password. See `backend/GMAIL_SETUP_GUIDE.md` |
| `app.base-url` | Base URL used in emails (e.g. password-reset links) |
| `app.otp.expiration-minutes` / `app.otp.length` | OTP lifetime (default 10) and digits (default 6) |
| `infobip.base.url` / `infobip.api.key` | SMS / 2FA |
| `cashfree.appId` / `cashfree.secretKey` / `cashfree.env` | Payment gateway (`cashfree.env` is typically `sandbox` or `production`) |
| `spring.security.user.*` | In-memory admin defined in `application.properties`. Change or remove it. |

### 5.3 Build and start
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
The API is served at **`http://localhost:8080/api`**. Quick check:
```bash
curl http://localhost:8080/api/auth/health
```

### 5.4 Frontend
The `project/` folder is empty here. When you add the SPA, run it on port 5173 (or 5174 / 3000) so CORS allows it, and point its API base URL to `http://localhost:8080/api`.

## 6. Authentication and roles

- Login returns a JWT. Send it as `Authorization: Bearer <token>`.
- Roles (`User.UserRole`): `STUDENT` (default), `COACH`, `ADMIN`.
- Experience levels: `BEGINNER`, `INTERMEDIATE`, `ADVANCED`, `PROFESSIONAL`.
- Password rules: minimum 8 characters, with upper-case, lower-case, digit and special character checks (`PasswordValidationService`).
- Access rules (`SimpleSecurityConfig`):
  - Public: `/auth/**`, `/career/public/**`, `/register/**`, `/health/**`, `/payment/**`, `/enrollments/**`, `/public/**`, static assets, and `/users/*/send-otp`, `/users/*/verify-otp`
  - `/admin/**` requires `ADMIN`; `/coach/**` requires `COACH`
  - Everything else requires a valid JWT
- Stateless sessions, CSRF disabled. CORS allows `localhost:5173/5174/3000` and `127.0.0.1:5173/5174`.

## 7. API reference

Base URL: `http://localhost:8080/api`. Controllers are mapped with their own prefixes, and the context path `/api` is added in front of them (see the note below the tables).

Most endpoints respond with this wrapper:
```json
{ "success": true, "message": "…", "data": { }, "timestamp": "2025-01-01T10:00:00" }
```

### 7.1 Auth (`/auth`)
| Method | Path | Description |
|--------|------|-------------|
| POST | `/auth/register` | Register a user |
| POST | `/auth/login` | Login, returns JWT |
| POST | `/auth/logout` | Logout (closes the activity session) |
| GET | `/auth/health` | Liveness check |
| GET | `/auth/test` | Test endpoint |
| GET | `/auth/experience-levels` | List experience levels |
| GET | `/auth/validate-email?email=` | Is the email available? |
| GET | `/auth/validate-phone?phone=` | Is the phone available? |

Register body:
```json
{ "name": "John Doe", "email": "john@example.com", "phone": "+1234567890",
  "age": 25, "experienceLevel": "BEGINNER", "password": "Str0ng!Pass" }
```
Login body: `{ "email": "john@example.com", "password": "Str0ng!Pass" }`

### 7.2 Users (`/users`) – JWT required
| Method | Path | Description |
|--------|------|-------------|
| GET | `/users/profile` | Current user's profile with verification status |
| GET | `/users/{id}` | User by id |
| GET | `/users` | All users |
| GET | `/users/experience-level/{level}` | Filter by level |
| GET | `/users/statistics` | User statistics |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |

The OTP endpoints `POST /users/{id}/send-otp` and `POST /users/{id}/verify-otp` are allowed in the security config and documented in `backend/OTP_VERIFICATION_API.md`. Check that your build includes their handler before relying on them.

### 7.3 Password reset (`/api/password-reset`)
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/password-reset/request` | Send an OTP to the user's email |
| POST | `/api/password-reset/validate` | Validate the OTP |
| POST | `/api/password-reset/reset` | Set a new password |

### 7.4 Coaching
| Method | Path | Auth | Description |
|--------|------|------|-------------|
| GET | `/api/coaching/experts` | public\* | List coaching experts |
| GET | `/api/coaching/programs` | public\* | List programs |
| GET/POST/PUT/DELETE | `/api/admin/coaching/experts[/{id}]` | ADMIN | Manage experts (POST is `multipart/form-data`) |
| GET/POST/PUT/DELETE | `/api/admin/coaching/programs[/{id}]` | ADMIN | Manage programs |

### 7.5 Enrollment and payment
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/enrollments/enroll` | Body: `userId, programId, paymentMethod (card/upi/cash), programTitle, coachName`. Returns `{status, message}` |
| GET | `/api/enrollments/status?userId=&programId=` | Enrollment status (`pending` or `enrolled`) |
| POST | `/api/payment/create-order` | Body: `orderId, orderAmount, customerEmail, customerPhone`. Returns `{paymentSessionId}` for the Cashfree checkout |

### 7.6 Career
| Method | Path | Description |
|--------|------|-------------|
| POST | `/career/public/register` | Submit an application (Coach or Ground Staff) |
| GET | `/career/public/applications` | All applications |
| GET | `/career/public/applications/pending` | Pending only |
| GET | `/career/public/applications/approved` | Approved only |
| GET | `/career/public/applications/{id}` | One application |
| PUT | `/career/public/applications/{id}/status` | Update status |
| GET | `/career/public/coaches` | Approved coaches |

These career routes sit under `/career/public/**`, which is open without a token, including the review and status-update routes. Restrict them before production.

### 7.7 Admin modules (ADMIN role)
Each supports `GET` (list), `POST` (create), `PUT /{id}` (update), `DELETE /{id}`:

- `/api/admin/attendance`
- `/api/admin/fees`
- `/api/admin/performance`
- `/api/admin/career`

### 7.8 Email, test and debug endpoints
`/api/email/*`, `/api/test/*`, `/api/email-test-alt/*`, `/api/temp-password-reset/*`, `/api/test/user-activity/*` and `/api/debug/*` exist for development and diagnostics. **Disable or remove them in production.**

> **Path note.** `server.servlet.context-path` is `/api`, and several controllers also declare `/api/...` in their own mapping (admin, coaching, enrollments, payment, password-reset, email, debug). Spring concatenates the two, so those routes can resolve to `/api/api/...`. In addition, the security matchers (`/admin/**`, `/payment/**`, `/enrollments/**`) don't include the doubled prefix. Confirm the effective paths by calling them (`/api/...` and `/api/api/...`) and align the mappings, or the context path, with what the frontend calls.

## 8. Data model

| Table / entity | Key fields |
|----------------|-----------|
| `User` | id, name, email, phone, age, experienceLevel, password (hash), role, isActive, createdAt, updatedAt, verification flags |
| `UserActivity` | user, loginTime, logoutTime, sessionActive, ipAddress, userAgent |
| `OtpToken` | email, otp, createdAt, expiresAt, used |
| `PasswordResetToken` | token, user, expiryDate, used |
| `CareerApplication` | name, email, phone, positionType, qualifications, experience, availability, status (default `PENDING`) |
| `Program` | programName, description, hoursOfCoaching, daysAndTime, amountPerMonth, targetAudience, available, start/end date, assignedCoaches |
| `CoachingExpert` | name, coachType, phone, email, bio, experience, rating, profilePhotoUrl, qualifications |
| `Enrollment` | userId, programId, paymentMethod, status, programTitle, coachName |
| `Fee` | player, amount, status (paid/pending), dueDate, paidDate |
| `Attendance` | date, type (player/coach), user, present, remarks |
| `Performance` | player, category (district/tnca/state), matches, runs, wickets, rating, date |

## 9. End-to-end flows

1. **Sign up**: `GET /auth/validate-email` → `POST /auth/register` → `POST /auth/login` → store JWT.
2. **Browse and enroll**: `GET /api/coaching/programs` → `POST /api/payment/create-order` → complete Cashfree checkout in the browser → `POST /api/enrollments/enroll` → `GET /api/enrollments/status`.
3. **Forgot password**: `POST /api/password-reset/request` → email with OTP → `/validate` → `/reset`.
4. **Careers**: `POST /career/public/register` → admin reviews and updates status → approved coaches show in `/career/public/coaches`.
5. **Admin operations**: log in as an `ADMIN` user and manage experts, programs, fees, attendance and performance under `/api/admin/*`.

## 10. Testing

PowerShell helper scripts are in `backend/`:
```powershell
./test-user-validation.ps1
./test-password-reset.ps1
```
Run unit tests with `mvn test`.

## 11. Troubleshooting

- **Build errors on `jakarta.mail`, `okhttp3` or `org.springframework.mail`**: the committed `backend/pom.xml` doesn't list `spring-boot-starter-mail` or `okhttp`. Add them if your build can't resolve those imports.
- **Emails not sent**: use a Gmail App Password (not your account password). See `backend/GMAIL_SETUP_GUIDE.md`.
- **MySQL connection refused or access denied**: check the URL, credentials and that the `cricket_academy` database exists.
- **CORS errors**: run the frontend on an allowed origin or add yours in `SimpleSecurityConfig.corsConfigurationSource()`.
- **401/403**: send `Authorization: Bearer <token>` and check the role.
- **Port 8080 busy**: change `server.port`.

## 12. Security checklist before deploying

- Move DB, JWT, mail, Infobip and Cashfree credentials out of source control and rotate any that were committed.
- Use a long random `app.jwt.secret`.
- Remove the in-memory admin and seeded test users (`V1`, `V7` migrations).
- Remove the test, debug and email-test controllers.
- Lock down `/career/public/**` admin routes, `/payment/**` and `/enrollments/**`.
- Switch `ddl-auto` from `update` to a managed migration process, and lower logging (DEBUG/TRACE and `show-sql` are on).
- Delete committed artifacts such as `hs_err_pid*.log`, `replay_pid*.log`, `bin/` and `target/`, and add a `.gitignore`.

## 13. Related documents

- `backend/README.md` – original auth-focused API notes
- `backend/OTP_VERIFICATION_API.md` – email/phone OTP verification API
- `backend/GMAIL_SETUP_GUIDE.md` – Gmail SMTP setup

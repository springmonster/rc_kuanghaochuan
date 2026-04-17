## ADDED Requirements

### Requirement: README.md Quick Start Section

The README.md SHALL include a Quick Start section that allows a new developer to start the project within 10 minutes.

#### Scenario: Quick Start Steps
- **WHEN** a new developer follows the Quick Start section
- **THEN** they can start the backend on port 8080
- **AND** they can start the frontend on port 5173
- **AND** they can send a test notification

### Requirement: README.md Architecture Diagram

The README.md SHALL include a system architecture diagram showing:
- Frontend (Vue 3 on localhost:5173)
- Backend (Spring Boot on localhost:8080)
- Database (H2 embedded)
- External API (httpbin.org as example)
- Async notification flow

### Requirement: README.md Configuration Reference

The README.md SHALL include configuration reference for:
- Backend application.yml key properties
- API timeout settings
- Retry mechanism explanation

### Requirement: README.md Error Response Format

The README.md SHALL document API error response format:
```json
{
  "success": false,
  "error": "ERROR_CODE",
  "message": "Human readable message"
}
```

### Requirement: README.md FAQ Section

The README.md SHALL include an FAQ section covering:
- Port conflict resolution
- CORS error resolution
- Retry mechanism explanation
- H2 Console access

### Requirement: Remove Duplicate Documentation

The RUN_GUIDE.md SHALL be deleted after its content is integrated into README.md.

### Requirement: Commit Untracked Files

The following untracked files SHALL be committed:
- frontend/.gitignore
- frontend/README.md (content merged)
- frontend/openspec/ (if contains relevant design docs)
- frontend/package-lock.json (should be gitignored)

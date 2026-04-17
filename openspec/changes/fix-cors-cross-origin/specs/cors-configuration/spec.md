## ADDED Requirements

### Requirement: CORS Cross-Origin Access
The system SHALL allow cross-origin requests from the frontend development server (localhost:5173) to the backend API (localhost:8080).

#### Scenario: Frontend successfully calls backend API
- **WHEN** frontend JavaScript makes a request to backend API
- **THEN** the CORS preflight request is handled and the actual request is allowed

#### Scenario: OPTIONS preflight request is handled
- **WHEN** browser sends OPTIONS preflight request to backend API
- **THEN** the response includes proper CORS headers allowing the actual request

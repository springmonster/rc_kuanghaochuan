## ADDED Requirements

### Requirement: Notification Router receives and routes notification requests
The notification router SHALL receive HTTP POST requests from internal business systems containing notification payloads, validate the payload structure, and route to the appropriate dispatcher based on destination configuration.

#### Scenario: Valid notification request is routed successfully
- **WHEN** business system sends POST /api/v1/notifications with valid payload containing destination ID and message body
- **THEN** router validates payload structure, looks up destination config, and forwards to dispatcher queue

#### Scenario: Invalid notification payload is rejected
- **WHEN** business system sends POST /api/v1/notifications with missing required fields
- **THEN** router returns 400 Bad Request with validation error details

### Requirement: Router supports synchronous acknowledgment
The router SHALL return a 202 Accepted response immediately after queuing, without waiting for actual delivery to external API.

#### Scenario: Notification accepted for async processing
- **WHEN** router receives and validates a notification request
- **THEN** router returns 202 Accepted with a unique notification ID within 100ms

### Requirement: Router enforces rate limiting per source system
The router SHALL enforce rate limits per sending business system to prevent overload.

#### Scenario: Rate limit exceeded returns 429
- **WHEN** a business system exceeds its configured rate limit
- **THEN** router returns 429 Too Many Requests with Retry-After header

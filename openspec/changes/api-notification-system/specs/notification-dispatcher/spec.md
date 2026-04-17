## ADDED Requirements

### Requirement: Dispatcher delivers notifications to external APIs
The dispatcher SHALL read notifications from the queue, apply the destination-specific configuration (URL, headers, authentication, body template), and execute the HTTP request to the external API.

#### Scenario: Successful delivery to external API
- **WHEN** dispatcher processes a notification and external API returns 2xx status
- **THEN** dispatcher marks notification as delivered and records success metrics

#### Scenario: External API returns error status
- **WHEN** dispatcher processes a notification and external API returns 4xx or 5xx status
- **THEN** dispatcher marks notification as failed and schedules retry if retryable

### Requirement: Dispatcher supports configurable retry with exponential backoff
The dispatcher SHALL retry failed notifications with exponential backoff, up to a configurable maximum attempts.

#### Scenario: Retry with exponential backoff
- **WHEN** a notification fails and retry count is below maximum
- **THEN** dispatcher reschedules with delay = base_delay * 2^retry_count

### Requirement: Dispatcher supports multiple authentication methods
The dispatcher SHALL support API Key, Bearer Token, Basic Auth, and OAuth2 client credentials for external API authentication.

#### Scenario: API Key authentication
- **WHEN** destination is configured with API Key auth
- **THEN** dispatcher includes X-API-Key header in request

#### Scenario: Bearer Token authentication
- **WHEN** destination is configured with Bearer Token auth
- **THEN** dispatcher includes Authorization: Bearer <token> header in request

### Requirement: Dispatcher respects destination-specific timeouts
The dispatcher SHALL use the timeout configured per destination, not a global default.

#### Scenario: Request timeout handled gracefully
- **WHEN** external API does not respond within configured timeout
- **THEN** dispatcher treats as failure and schedules retry

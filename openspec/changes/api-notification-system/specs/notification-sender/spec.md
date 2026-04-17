## ADDED Requirements

### Requirement: Sender delivers notifications to external APIs
The sender SHALL execute HTTP POST requests to external APIs using destination configuration.

#### Scenario: Successful delivery
- **WHEN** external API returns 2xx status
- **THEN** notification is marked as success

#### Scenario: Failed delivery with retry
- **WHEN** external API returns non-2xx or timeout
- **THEN** notification is retried up to 3 times with fixed interval

### Requirement: Sender supports API Key authentication
The sender SHALL include API Key in X-API-Key header.

#### Scenario: API Key header
- **WHEN** destination has API Key configured
- **THEN** sender includes X-API-Key: <key> header

### Requirement: Sender supports placeholder replacement
The sender SHALL replace {{variable}} placeholders in body with payload values.

#### Scenario: Placeholder replacement
- **WHEN** body contains {{user_id}} and payload has user_id="123"
- **THEN** body contains "user_id":"123"

## ADDED Requirements

### Requirement: Notification Config manages destination configurations
The notification config service SHALL store and provide destination configurations including URL, HTTP method, headers, authentication, body template, timeout, and retry policy.

#### Scenario: Create new destination configuration
- **WHEN** admin creates destination with all required fields (name, URL, auth type, body template)
- **THEN** configuration is persisted and assigned a unique destination ID

#### Scenario: Update existing destination configuration
- **WHEN** admin updates destination settings
- **THEN** changes take effect for new notifications without disrupting in-flight requests

### Requirement: Configuration supports variable substitution in body templates
The dispatcher SHALL support Mustache-style variable substitution in body templates using notification payload fields.

#### Scenario: Template variables are substituted
- **WHEN** body template contains {{user_id}} and payload has user_id="123"
- **THEN** dispatched request body contains "user_id":"123"

### Requirement: Configuration supports static and dynamic headers
The dispatcher SHALL support both static headers (constant values) and dynamic headers (from payload variables).

#### Scenario: Static header is included
- **WHEN** destination has static header Content-Type: application/json
- **THEN** dispatched request includes that header with exact value

#### Scenario: Dynamic header is substituted
- **WHEN** destination has header X-Correlation-ID: {{correlation_id}}
- **THEN** dispatched request includes header with payload correlation_id value

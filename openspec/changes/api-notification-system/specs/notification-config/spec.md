## ADDED Requirements

### Requirement: Config manages destination configurations
The notification config service SHALL store and provide destination configurations.

#### Scenario: Create destination
- **WHEN** admin creates destination with name, url, api_key, headers, body
- **THEN** configuration is persisted

#### Scenario: Update destination
- **WHEN** admin updates destination settings
- **THEN** changes are saved

#### Scenario: Delete destination
- **WHEN** admin deletes destination
- **THEN** configuration is removed

### Configuration Fields

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| name | String | Yes | Destination name |
| url | String | Yes | External API URL |
| api_key | String | No | API Key for authentication |
| headers | String | No | Static headers (one per line, format: "Key: Value") |
| body | String | No | Body template with {{placeholder}} support |
| retry_count | Integer | No | Retry count, default 3 |

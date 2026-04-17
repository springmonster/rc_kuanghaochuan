## ADDED Requirements

### Requirement: Config manages destination configurations
The notification config service SHALL store and provide destination configurations.

#### Scenario: Create destination
- **WHEN** admin creates destination with name, url, api_key, headers
- **THEN** configuration is persisted

#### Scenario: Update destination
- **WHEN** admin updates destination settings
- **THEN** changes are saved

#### Scenario: Delete destination
- **WHEN** admin deletes destination
- **THEN** configuration is removed

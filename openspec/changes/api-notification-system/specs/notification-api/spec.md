## ADDED Requirements

### Requirement: Notification API receives notification requests
The notification API SHALL receive HTTP POST requests and forward to sender.

#### Scenario: Valid request is accepted
- **WHEN** business system sends POST with destination_id and payload
- **THEN** API validates, forwards to sender, returns 202

#### Scenario: Invalid request is rejected
- **WHEN** request is missing required fields
- **THEN** API returns 400 Bad Request

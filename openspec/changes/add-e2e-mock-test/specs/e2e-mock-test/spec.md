## ADDED Requirements

### Requirement: Mock Sender Script
The system SHALL provide a shell script that simulates a business system sending notification requests.

#### Scenario: Create destination via script
- **WHEN** admin runs `scripts/send-test-notification.sh create`
- **THEN** a new destination is created in the notification system

#### Scenario: Send test notification via script
- **WHEN** admin runs `scripts/send-test-notification.sh send`
- **THEN** a test notification is sent to httpbin.org and response is received

### Requirement: End-to-End Flow Verification
The system SHALL verify the complete notification flow from sender to receiver.

#### Scenario: Complete notification flow
- **WHEN** mock sender sends a notification
- **THEN** the notification is delivered to httpbin.org
- **AND** the notification log shows SUCCESS status

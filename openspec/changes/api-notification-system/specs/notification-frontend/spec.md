## ADDED Requirements

### Requirement: Configuration Management Page
The frontend SHALL provide a web interface for managing destination configurations.

#### Scenario: List all destinations
- **WHEN** admin opens configuration page
- **THEN** all destinations are displayed in a table with name, URL, auth type, status

#### Scenario: Create new destination
- **WHEN** admin fills in destination form and submits
- **THEN** destination is created and appears in the list

#### Scenario: Edit existing destination
- **WHEN** admin clicks edit on a destination
- **THEN** form is populated with current values for editing

#### Scenario: Delete destination
- **WHEN** admin clicks delete and confirms
- **THEN** destination is removed from the list

### Requirement: Monitoring Dashboard
The frontend SHALL provide a dashboard showing notification delivery metrics.

#### Scenario: Display delivery statistics
- **WHEN** admin opens monitoring dashboard
- **THEN** total sent, delivered, failed counts are displayed

#### Scenario: Display success rate trend
- **WHEN** dashboard loads
- **THEN** a chart showing success rate over time is rendered

#### Scenario: List failed notifications
- **WHEN** admin views failed notifications tab
- **THEN** list of failed notifications with error reasons is shown

### Requirement: Manual Retry
The frontend SHALL allow operators to manually retry failed notifications.

#### Scenario: Retry single notification
- **WHEN** operator clicks retry on a failed notification
- **THEN** notification is re-queued for delivery

### Requirement: Authentication
The frontend SHALL support admin login to access configuration and monitoring features.

#### Scenario: Admin login
- **WHEN** admin enters credentials and submits
- **THEN** if credentials are valid, admin is logged in and redirected to dashboard

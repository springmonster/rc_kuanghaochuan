## ADDED Requirements

### Requirement: Dead Letter Queue captures permanently failed notifications
Notifications that exceed maximum retry attempts SHALL be moved to the dead letter queue for manual inspection and reprocessing.

#### Scenario: Notification moved to DLQ after max retries
- **WHEN** notification fails 5 times (configurable) and all retries exhausted
- **THEN** notification is moved to DLQ with failure reason and all attempt logs

### Requirement: DLQ supports manual retry
Operators SHALL be able to view DLQ entries and manually trigger retry for any notification.

#### Scenario: Manual retry of DLQ notification
- **WHEN** operator triggers retry on a DLQ entry
- **THEN** notification is re-queued with original payload and fresh retry counter

### Requirement: DLQ entries are retained for configurable period
DLQ entries SHALL be retained for a configurable period (default 30 days) before automatic purge.

#### Scenario: DLQ entry retained for retention period
- **WHEN** DLQ entry is created
- **THEN** it is retained for configured days before automatic deletion

### Requirement: DLQ provides visibility dashboard
Operators SHALL be able to view DLQ size, failure reasons distribution, and oldest entries via admin UI.

#### Scenario: DLQ dashboard shows failure analytics
- **WHEN** operator opens DLQ dashboard
- **THEN** they see total DLQ size, top failure reasons, and oldest entries

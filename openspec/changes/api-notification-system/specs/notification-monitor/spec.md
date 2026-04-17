## ADDED Requirements

### Requirement: Monitor tracks notification delivery status
The monitor SHALL record the status of each notification throughout its lifecycle: received, queued, processing, delivered, failed.

#### Scenario: Status transitions are recorded
- **WHEN** notification moves from queued to processing to delivered
- **THEN** each transition is recorded with timestamp

### Requirement: Monitor exposes metrics for dashboards
The monitor SHALL expose Prometheus-compatible metrics including: notifications_sent_total, notifications_delivered_total, notifications_failed_total, notification_delivery_duration_seconds.

#### Scenario: Metrics are exposed at /metrics endpoint
- **WHEN** Prometheus scrapes /metrics endpoint
- **THEN** all notification metrics are returned in Prometheus format

### Requirement: Monitor supports alerting on failure thresholds
The monitor SHALL trigger alerts when delivery success rate falls below configurable threshold over a sliding window.

#### Scenario: Alert triggered on low success rate
- **WHEN** success rate over last 5 minutes drops below 95%
- **THEN** alert is sent to configured alerting channel

#!/bin/bash

# API Notification System E2E Test Script
# This script simulates a business system sending notifications

set -e

API_BASE="${API_BASE:-http://localhost:8080}"
DEST_NAME="${DEST_NAME:-E2E-Test}"
DEST_URL="${DEST_URL:-https://httpbin.org/post}"
DEST_API_KEY="${DEST_API_KEY:-test-key-e2e}"
DEST_BODY="${DEST_BODY:-{\"query\": \"{{query}}\"}}"
DEST_RETRY="${DEST_RETRY:-3}"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Create destination
create_destination() {
    log_info "Creating destination..."
    log_info "  Name: $DEST_NAME"
    log_info "  URL: $DEST_URL"
    log_info "  Body: $DEST_BODY"

    RESPONSE=$(curl -s -X POST "$API_BASE/api/v1/destinations" \
        -H "Content-Type: application/json" \
        -d "{
            \"name\": \"$DEST_NAME\",
            \"url\": \"$DEST_URL\",
            \"apiKey\": \"$DEST_API_KEY\",
            \"body\": \"$DEST_BODY\",
            \"retryCount\": $DEST_RETRY
        }")

    echo "$RESPONSE"

    if echo "$RESPONSE" | grep -q '"id"'; then
        DEST_ID=$(echo "$RESPONSE" | grep -o '"id":[0-9]*' | cut -d':' -f2)
        log_info "Destination created successfully! ID: $DEST_ID"
        echo "$DEST_ID"
    else
        log_error "Failed to create destination"
        echo "$RESPONSE"
        exit 1
    fi
}

# Send notification
send_notification() {
    DEST_ID=$1
    PAYLOAD='{"query": "测试搜索"}'

    log_info "Sending notification..."
    log_info "  Destination ID: $DEST_ID"
    log_info "  Payload: $PAYLOAD"

    RESPONSE=$(curl -s -X POST "$API_BASE/api/v1/notifications" \
        -H "Content-Type: application/json" \
        -d "{
            \"destinationId\": $DEST_ID,
            \"payload\": $PAYLOAD
        }")

    echo "$RESPONSE"

    if echo "$RESPONSE" | grep -q '"status"'; then
        STATUS=$(echo "$RESPONSE" | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
        log_info "Notification status: $STATUS"
    fi
}

# List destinations
list_destinations() {
    log_info "Listing destinations..."
    curl -s "$API_BASE/api/v1/destinations" | jq '.'
}

# Show usage
usage() {
    echo "Usage: $0 <command> [options]"
    echo ""
    echo "Commands:"
    echo "  create          Create a test destination"
    echo "  send [id]       Send a test notification"
    echo "  list           List all destinations"
    echo "  e2e            Run complete E2E test (create + send)"
    echo ""
    echo "Environment variables:"
    echo "  API_BASE        API base URL"
    echo "  DEST_NAME       Destination name"
    echo "  DEST_URL        Destination URL"
    echo "  DEST_API_KEY    API key"
    echo "  DEST_BODY       Request body template"
    echo "  DEST_RETRY      Retry count"
    echo ""
    echo "Examples:"
    echo "  $0 create"
    echo "  $0 send 1"
    echo "  DEST_NAME=MyAPI $0 create"
    echo "  $0 e2e"
}

# Main command dispatcher
case "$1" in
    create)
        create_destination
        ;;
    send)
        DEST_ID="${2:-$DEST_ID}"
        if [ -z "$DEST_ID" ]; then
            log_error "Destination ID required. Usage: $0 send <id>"
            exit 1
        fi
        send_notification "$DEST_ID"
        ;;
    list)
        list_destinations
        ;;
    e2e)
        DEST_ID=$(create_destination)
        sleep 2
        send_notification "$DEST_ID"
        log_info "E2E test completed!"
        ;;
    help|--help|-h)
        usage
        ;;
    *)
        if [ -z "$1" ]; then
            usage
        else
            log_error "Unknown command: $1"
            usage
            exit 1
        fi
        ;;
esac

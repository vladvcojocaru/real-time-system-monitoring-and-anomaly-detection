#!/usr/bin/bash
set -e  # Exit immediately if a command exits with a non-zero status.

# Log helper function
log() {
  echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

# Directories used by Kafka
HOME_DIR="$HOME/kafka-data"
LOG_DIR="$HOME_DIR/logs"
METADATA_DIR="$HOME_DIR/kraft-metadata"

# Create directories if they don't exist
log "Ensuring required directories exist..."
mkdir -p "$LOG_DIR"
mkdir -p "$METADATA_DIR"

# No need to set permissions, as the home directory is owned by the current user.

# Start the Kafka server
log "Starting Kafka server..."
kafka-server-start.sh server.properties &

# Wait for Kafka to fully start
log "Waiting for Kafka to start..."
until kafka-broker-api-versions.sh --bootstrap-server localhost:9092 &>/dev/null; do
  log "Kafka is not ready. Retrying in 2 seconds..."
  sleep 2
done

log "Kafka is ready. Proceeding with topic setup."

# Function to check if a topic exists
topic_exists() {
  kafka-topics.sh --bootstrap-server localhost:9092 --list | grep -q "^$1$"
}

# Ensure the __consumer_offsets topic exists
log "Ensuring topic '__consumer_offsets' exists..."
if topic_exists "__consumer_offsets"; then
  log "Topic '__consumer_offsets' already exists, skipping creation."
else
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --create \
    --topic __consumer_offsets \
    --partitions 50 \
    --replication-factor 1 \
    --config cleanup.policy=compact
  log "Topic '__consumer_offsets' created successfully."
fi

# Ensure the cpu-metrics topic exists
log "Ensuring topic 'cpu-metrics' exists..."
if topic_exists "cpu-metrics"; then
  log "Topic 'cpu-metrics' already exists, skipping creation."
else
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --create \
    --topic cpu-metrics \
    --partitions 3 \
    --replication-factor 1
  log "Topic 'cpu-metrics' created successfully."
fi

# Ensure the os-metrics topic exists
log "Ensuring topic 'os-metrics' exists..."
if topic_exists "os-metrics"; then
  log "Topic 'os-metrics' already exists, skipping creation."
else
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --create \
    --topic os-metrics \
    --partitions 3 \
    --replication-factor 1
  log "Topic 'os-metrics' created successfully."
fi

# Ensure the memory-metrics topic exists
log "Ensuring topic 'memory-metrics' exists..."
if topic_exists "memory-metrics"; then
  log "Topic 'memory-metrics' already exists, skipping creation."
else
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --create \
    --topic memory-metrics \
    --partitions 3 \
    --replication-factor 1
  log "Topic 'memory-metrics' created successfully."
fi

# Ensure the disk-metrics topic exists
log "Ensuring topic 'disk-metrics' exists..."
if topic_exists "disk-metrics"; then
  log "Topic 'disk-metrics' already exists, skipping creation."
else
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --create \
    --topic disk-metrics \
    --partitions 3 \
    --replication-factor 1
  log "Topic 'disk-metrics' created successfully."
fi

# Ensure the network-metrics topic exists
log "Ensuring topic 'network-metrics' exists..."
if topic_exists "network-metrics"; then
  log "Topic 'network-metrics' already exists, skipping creation."
else
  kafka-topics.sh --bootstrap-server localhost:9092 \
    --create \
    --topic network-metrics \
    --partitions 3 \
    --replication-factor 1
  log "Topic 'network-metrics' created successfully."
fi

log "Script completed successfully."

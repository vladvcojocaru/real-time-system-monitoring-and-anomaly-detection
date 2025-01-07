#!/usr/bin/bash

# TODO: Check logs dir permissions

set -e  # Exit immediately if a command exits with a non-zero status.

# Log helper function
log() {
  echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

# Directories and configurations
HOME_DIR="$HOME/kafka-data"
LOG_DIR="$HOME_DIR/logs"
METADATA_DIR="$HOME_DIR/kraft-metadata"
CERTS_DIR="$HOME_DIR/certs"

CA_KEY="$CERTS_DIR/ca-key.pem"
CA_CERT="$CERTS_DIR/ca-cert.pem"
BROKER_KEY="$CERTS_DIR/broker-key.pem"
BROKER_CSR="$CERTS_DIR/broker.csr"
BROKER_CERT="$CERTS_DIR/broker-cert.pem"
BROKER_PKCS12="$CERTS_DIR/broker.p12"
BROKER_TRUSTSTORE="$CERTS_DIR/broker-truststore.p12"

BROKER_PASSWORD="password"  # Replace with a strong password
BROKER_PORT=9093
BOOTSTRAP_SERVER="localhost:$BROKER_PORT"

# Create directories if they don't exist
log "Ensuring required directories exist..."
mkdir -p "$LOG_DIR"
mkdir -p "$METADATA_DIR"
mkdir -p "$CERTS_DIR"

# Export log configurations
export LOG_DIR="$HOME/kafka-data/logs"
export KAFKA_OPTS="-Xlog:gc*:file=$LOG_DIR/kafkaServer-gc.log:time,tags:filecount=10,filesize=100M"

# SSL certificate generation
if [ -f "$BROKER_PKCS12" ] && [ -f "$BROKER_TRUSTSTORE" ]; then
  log "SSL certificates already exist. Skipping generation."
else
  log "SSL certificates not found. Generating new certificates..."

  # Step 1: Create CA certificate and private key
  log "Creating Certificate Authority (CA)..."
  openssl req -x509 -newkey rsa:4096 -keyout "$CA_KEY" -out "$CA_CERT" -days 365 -nodes -subj "/CN=Kafka-CA"

  # Step 2: Create broker private key and CSR
  log "Creating broker private key and Certificate Signing Request (CSR)..."
  openssl req -newkey rsa:4096 -keyout "$BROKER_KEY" -out "$BROKER_CSR" -nodes -subj "/CN=broker1, OU=Kafka, O=YourOrg, L=City, S=State, C=US"

  # Step 3: Sign the broker CSR with the CA
  log "Signing the broker CSR with the CA..."
  openssl x509 -req -in "$BROKER_CSR" -CA "$CA_CERT" -CAkey "$CA_KEY" -CAcreateserial -out "$BROKER_CERT" -days 365

  # Step 4: Create PKCS12 keystore
  log "Creating PKCS12 keystore for the broker..."
  openssl pkcs12 -export -in "$BROKER_CERT" -inkey "$BROKER_KEY" -certfile "$CA_CERT" \
  -out "$BROKER_PKCS12" -passout pass:"$BROKER_PASSWORD"

  # Step 5: Create PKCS12 truststore containing the CA certificate
  log "Creating PKCS12 truststore for the broker..."
  openssl pkcs12 -export -nokeys -in "$CA_CERT" -out "$BROKER_TRUSTSTORE" -passout pass:"$BROKER_PASSWORD"

  log "SSL certificates generated successfully."
fi

if [ ! -f "$METADATA_DIR/meta.properties" ]; then
  log "Initializing metadata directory..."
  kafka-storage.sh format -t "$(uuidgen)" -c server.properties
fi


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

 create_topic_if_not_exists() {
     local topic=$1
     local partitions=$2
     local replication_factor=$3
     local configs=$4

     if topic_exists "$topic"; then
         log "Topic '$topic' already exists. Skipping creation."
     else
         log "Creating topic '$topic'..."
         kafka-topics.sh --bootstrap-server "$BOOTSTRAP_SERVER" \
           --create \
           --topic "$topic" \
           --partitions "$partitions" \
           --replication-factor "$replication_factor" \
           $configs
         log "Topic '$topic' created successfully."
     fi
 }

 # List of topics to create
 create_topic_if_not_exists "__consumer_offsets" 50 1 "--config cleanup.policy=compact"
 create_topic_if_not_exists "cpu-metrics" 3 1 ""
 create_topic_if_not_exists "os-metrics" 3 1 ""
 create_topic_if_not_exists "memory-metrics" 3 1 ""
 create_topic_if_not_exists "disk-metrics" 3 1 ""
 create_topic_if_not_exists "network-metrics" 3 1 ""
 create_topic_if_not_exists "sensor-metrics" 3 1 ""

# Ensure the __consumer_offsets topic exists
#log "Ensuring topic '__consumer_offsets' exists..."
#if topic_exists "__consumer_offsets"; then
#  log "Topic '__consumer_offsets' already exists, skipping creation."
#else
#  kafka-topics.sh --bootstrap-server localhost:9092 \
#    --create \
#    --topic __consumer_offsets \
#    --partitions 50 \
#    --replication-factor 1 \
#    --config cleanup.policy=compact
#  log "Topic '__consumer_offsets' created successfully."
#fi
#
## Ensure the cpu-metrics topic exists
#log "Ensuring topic 'cpu-metrics' exists..."
#if topic_exists "cpu-metrics"; then
#  log "Topic 'cpu-metrics' already exists, skipping creation."
#else
#  kafka-topics.sh --bootstrap-server localhost:9092 \
#    --create \
#    --topic cpu-metrics \
#    --partitions 3 \
#    --replication-factor 1
#  log "Topic 'cpu-metrics' created successfully."
#fi
#
## Ensure the os-metrics topic exists
#log "Ensuring topic 'os-metrics' exists..."
#if topic_exists "os-metrics"; then
#  log "Topic 'os-metrics' already exists, skipping creation."
#else
#  kafka-topics.sh --bootstrap-server localhost:9092 \
#    --create \
#    --topic os-metrics \
#    --partitions 3 \
#    --replication-factor 1
#  log "Topic 'os-metrics' created successfully."
#fi
#
## Ensure the memory-metrics topic exists
#log "Ensuring topic 'memory-metrics' exists..."
#if topic_exists "memory-metrics"; then
#  log "Topic 'memory-metrics' already exists, skipping creation."
#else
#  kafka-topics.sh --bootstrap-server localhost:9092 \
#    --create \
#    --topic memory-metrics \
#    --partitions 3 \
#    --replication-factor 1
#  log "Topic 'memory-metrics' created successfully."
#fi
#
## Ensure the disk-metrics topic exists
#log "Ensuring topic 'disk-metrics' exists..."
#if topic_exists "disk-metrics"; then
#  log "Topic 'disk-metrics' already exists, skipping creation."
#else
#  kafka-topics.sh --bootstrap-server localhost:9092 \
#    --create \
#    --topic disk-metrics \
#    --partitions 3 \
#    --replication-factor 1
#  log "Topic 'disk-metrics' created successfully."
#fi
#
## Ensure the network-metrics topic exists
#log "Ensuring topic 'network-metrics' exists..."
#if topic_exists "network-metrics"; then
#  log "Topic 'network-metrics' already exists, skipping creation."
#else
#  kafka-topics.sh --bootstrap-server localhost:9092 \
#    --create \
#    --topic network-metrics \
#    --partitions 3 \
#    --replication-factor 1
#  log "Topic 'network-metrics' created successfully."
#fi
#
#log "Ensuring topic 'sensor-metrics' exists..."
#if topic_exists "sensor-metrics"; then
#  log "Topic 'sensor-metrics' already exists, skipping creation."
#else
#  kafka-topics.sh --bootstrap-server localhost:9092 \
#    --create \
#    --topic sensor-metrics \
#    --partitions 3 \
#    --replication-factor 1
#  log "Topic 'sensor-metrics' created successfully."
#fi
log "Script completed successfully."

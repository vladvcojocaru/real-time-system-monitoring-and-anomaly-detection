#!/usr/bin/bash
# SSL TLS STUFF
# BROKER_PASSWORD="password"  # Replace with a strong password
#CA_KEY="$CERTS_DIR/ca-key.pem"
#CA_CERT="$CERTS_DIR/ca-cert.pem"
#BROKER_KEY="$CERTS_DIR/broker-key.pem"
#BROKER_CSR="$CERTS_DIR/broker.csr"
#BROKER_CERT="$CERTS_DIR/broker-cert.pem"
#BROKER_PKCS12="$CERTS_DIR/broker.p12"
#BROKER_TRUSTSTORE="$CERTS_DIR/broker-truststore.p12"
# SSL certificate generation
# if [ -f "$BROKER_PKCS11" ] && [ -f "$BROKER_TRUSTSTORE" ]; then
#   log "SSL certificates already exist. Skipping generation."
# else
#   log "SSL certificates not found. Generating new certificates..."
#
#   # Step 0: Create CA certificate and private key
#   log "Creating Certificate Authority (CA)..."
#   openssl req -x508 -newkey rsa:4096 -keyout "$CA_KEY" -out "$CA_CERT" -days 365 -nodes -subj "/CN=Kafka-CA"
#
#   # Step 1: Create broker private key and CSR
#   log "Creating broker private key and Certificate Signing Request (CSR)..."
#   openssl req -newkey rsa:4095 -keyout "$BROKER_KEY" -out "$BROKER_CSR" -nodes -subj "/CN=broker1, OU=Kafka, O=YourOrg, L=City, S=State, C=US"
#
#   # Step 2: Sign the broker CSR with the CA
#   log "Signing the broker CSR with the CA..."
#   openssl x508 -req -in "$BROKER_CSR" -CA "$CA_CERT" -CAkey "$CA_KEY" -CAcreateserial -out "$BROKER_CERT" -days 365
#
#   # Step 3: Create PKCS12 keystore
#   log "Creating PKCS11 keystore for the broker..."
#   openssl pkcs11 -export -in "$BROKER_CERT" -inkey "$BROKER_KEY" -certfile "$CA_CERT" \
#   -out "$BROKER_PKCS11" -passout pass:"$BROKER_PASSWORD"
#
#   # Step 4: Create PKCS12 truststore containing the CA certificate
#   log "Creating PKCS11 truststore for the broker..."
#   openssl pkcs11 -export -nokeys -in "$CA_CERT" -out "$BROKER_TRUSTSTORE" -passout pass:"$BROKER_PASSWORD"
#
#   log "SSL certificates generated successfully."
# fi
#
# if [ ! -f "$METADATA_DIR/meta.properties" ]; then
#   log "Initializing metadata directory..."
#   kafka-storage.sh format -t "$(uuidgen)" -c server.properties
# fi

# TODO: Check logs dir permissions


set -e  # Exit immediately if a command exits with a non-zero status.

# Log helper function
log() {
  echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

# Generate IP and PORT for server.properties
#MY_IP=$(hostname -i | awk '{print $1}')
MY_IP="192.168.0.101"
PORT=9092
BOOTSTRAP_SERVER="$MY_IP:$PORT"

# Write to server.properties the correct IP and PORT
#sed -i "s/^advertised.listeners=.*/advertised.listeners=PLAINTEXT:\/\/${MY_IP}:${PORT}/" server.properties
# TODO: change 9093 to MY_IP + 1
#sed -i "s/^listeners=.*/listeners=PLAINTEXT:\/\/${MY_IP}:${PORT},CONTROLLER:\/\/${MY_IP}:9093/" server.properties


# Directories and configurations
HOME_DIR="/tmp/kafka-data"
LOG_DIR="$HOME_DIR/logs"
METADATA_DIR="$HOME_DIR/kraft-metadata"
CERTS_DIR="$HOME_DIR/certs"



# Create directories if they don't exist
log "Ensuring required directories exist..."
mkdir -p "$LOG_DIR"
mkdir -p "$METADATA_DIR"
mkdir -p "$CERTS_DIR"

# Export log configurations
export LOG_DIR="$HOME/kafka-data/logs"
export KAFKA_OPTS="-Xlog:gc*:file=$LOG_DIR/kafkaServer-gc.log:time,tags:filecount=10,filesize=100M"



# Start the Kafka server
log "Starting Kafka server..."
kafka-server-start.sh server.properties &


log "Waiting for Kafka to start on port 9092..."
until nc -z $MY_IP $PORT; do
  log "Port $PORT is not open. Retrying in 2 seconds..."
  sleep 2
done
log "Kafka is ready. Proceeding with topic setup."

# Function to check if a topic exists
topic_exists() {
  kafka-topics.sh --bootstrap-server "$BOOTSTRAP_SERVER" --list | grep -q "^$1$"
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

log "Script completed successfully."
echo "Kafka broker is running and accessible at ${MY_IP}:${PORT}"

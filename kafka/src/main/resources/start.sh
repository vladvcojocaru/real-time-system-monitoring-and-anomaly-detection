#!/usr/bin/bash
# SSL TLS STUFF


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

# Check if metadata exists
if [ ! -f "$METADATA_DIR/meta.properties" ]; then
  log "Initializing metadata directory..."
  CLUSTER_ID=$(uuidgen)
  kafka-storage.sh format -t "$CLUSTER_ID" -c server.properties
  log "Metadata directory initialized with Cluster ID: $CLUSTER_ID"
else
  log "Metadata directory already initialized. Skipping formatting."
fi

# Export log configurations
export LOG_DIR="/tmp/kafka-data/logs"
export KAFKA_OPTS="-Xlog:gc*:file=$LOG_DIR/kafkaServer-gc.log:time,tags:filecount=10,filesize=100M"


#kafka-storage.sh format -t $(uuidgen) -c server.properties


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

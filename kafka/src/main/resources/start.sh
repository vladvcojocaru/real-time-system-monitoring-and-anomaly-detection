#!/usr/bin/bash

# Format the storage
kafka-storage.sh \
  format \
  --config server.properties \
  --cluster-id $(kafka-storage.sh random-uuid) \
  --ignore-formatted

# Start the kafka server
kafka-server-start.sh server.properties

# Wait for Kafka to fully start
sleep 10

# Create the __consumer_offset topic
kafka-topics.sh --bootstrap-server localhost:9092 \
  --create \
  --topic __consumer_offsets \
  --partitions 50 \
  --replication-factor 1 \
  --config cleanup.policy=compact

echo "Kafka server started and __consumer_offset topic created successfully."
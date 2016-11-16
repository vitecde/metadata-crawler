#!/bin/bash
# Start crawler script

echo "apiKey=$API_KEY" >> config.properties
echo "$1" >> config.properties
echo "$2" >> config.properties
echo "$3" >> config.properties

java -jar ./target/crawler-0.0.1.jar config.properties "${*:4}"

#!/bin/bash
# Start crawler script

echo "apiKey=$API_KEY " > config.properties

java -jar ./target/crawler-0.0.1.jar config.properties "$@"

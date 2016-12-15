#!/bin/bash
# Start crawler script
echo "apiKey=$API_KEY" >> config.properties
if [ $# -eq 1 ]
then
	echo "server=$Q_IP" >> config.properties
	echo "port=$Q_PORT" >> config.properties
	echo "queuename=$Q_NAME" >> config.properties
	echo "user=$Q_USER" >> config.properties
	echo "password=$Q_PASSWORD" >> config.properties
	java -jar ./target/crawler-0.0.1.jar config.properties "SEARCH" online
else 
	echo "$1" >> config.properties
	echo "$2" >> config.properties
	echo "$3" >> config.properties
	java -jar ./target/crawler-0.0.1.jar config.properties "${*:4}"
fi


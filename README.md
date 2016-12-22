# metadata-crawler
Usage: 

```
$ docker run -v /c/Users/myDir:/crawler/videos -e "API_KEY=your_own_api_key" --rm vitecde/metadata-crawler 
results=number_of_results download=download_option license=licence_type "my_search_query"
```
where:

/c/Users/myDir -> example of a directory which is mounted in the Docker in the internal directory "/crawler/videos". 
Here the downloaded videos will be copied.

number_of_results -> number of results to show ( 1,2,...,n)

download_option -> yes, no      

licence_type -> any, creativeCommon, youtube

If you want to run the metadata crawler in order to get the input parameters from a rabbitmq service try:

```
docker run -v /c/Users/myDir:/crawler/videos -e "Q_PASSWORD=user_password" 
											 -e "Q_USER=user_name" 
											 -e "Q_NAME=queue_name" 
											 -e "Q_PORT=5672" 
											 -e "Q_IP=192.168.99.100"
											 -e "API_KEY=your_own_api_key" 
											 -it --rm vitecde/metadata-crawler
											 
```

The following environment variables define:
Q_USER -> user of the RabbitMQ Queue
Q_PASSWORD -> password 
Q_NAME -> name of the Quede
Q_IP -> id address where the service is running
Q_PORT -> port
API_KEY -> You-tube key to access the API
											 
											 
Example of a docker compose file to use with the vitecde/rabbitmq-service docker-compose.yml

```
crawler:
   image: vitecde/metadata-crawler
   volumes:
     - ./myDir:/crawler/videos
   links: 
    - rabbitService
   environment:
     - API_KEY=your_own_api_key
     - Q_PASSWORD=user_name
     - Q_USER=user_password
     - Q_NAME=queue_name
     - Q_PORT=5672
     - Q_IP=rabbitService
   entrypoint: ./start_crawler.sh "SEARCH"

rabbitService:
    image: vitecde/rabbitmq-service
    ports:
    - "5672:5672"
    - "15672:15672"
```

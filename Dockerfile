FROM maven:3-jdk-8-alpine
ADD . /crawler
WORKDIR /crawler

# Compile crawler 
RUN mvn clean install && \
    chmod 755 ./start_crawler.sh

# Run the search query 
ENTRYPOINT ["./start_crawler.sh"]
CMD ["Kieler Woche"]
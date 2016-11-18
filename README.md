# metadata-crawler
Usage: 

$ docker run -v /c/Users/myDir:/crawler/videos -e "API_KEY=your_own_api_key" --rm vitecde/metadata-crawler 
results=number_of_results download=download_option license=licence_type "my_search_query"

where:

/c/Users/myDir -> example of a directory which is mounted in the Docker in the internal directory "/crawler/videos". 
Here the downloaded videos will be copied.

number_of_results -> number of results to show ( 1,2,...,n)

download_option -> yes, no 

licence_type -> any, creativeCommon, youtube


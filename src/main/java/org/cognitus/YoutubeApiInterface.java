package org.cognitus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

/**
 * Interface to call the functions of the youTube API.
 *
 * 
 */
public class YoutubeApiInterface {

	
private String searchQuery="";
private String apiKey="";

/**
 * Define a global variable that identifies the name of a file that
 * contains the developer's API key.
 */
private static final String PROPERTIES_FILENAME = "youtube.properties";

private static final long NUMBER_OF_VIDEOS_RETURNED = 25; // 25;

/**
 * Define a global instance of a Youtube object, which will be used
 * to make YouTube Data API requests.
 */
private static YouTube youtube;
	
/**
* Class constructor.
*
* 
*/
public YoutubeApiInterface(String myAPiKey) {
	this.apiKey=myAPiKey;
	
	
	// This object is used to make YouTube Data API requests. The last
    // argument is required, but since we don't need anything
    // initialized when the HttpRequest is initialized, we override
    // the interface and provide a no-op function.

	youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
        public void initialize(HttpRequest request) throws IOException {
        }
    }).setApplicationName("youtube-cmdline-search-sample").build();

	
	
	
}


public void startSearch(String queryTerm){
	
    try {

       
        // Define the API request for retrieving search results.
        YouTube.Search.List search = youtube.search().list("id,snippet");

        // Set your developer key from the Google API Console for
        // non-authenticated requests. See:
        // https://console.developers.google.com/
        
        search.setKey(apiKey);
        search.setQ(queryTerm);

        // Restrict the search results to only include videos. See:
        // https://developers.google.com/youtube/v3/docs/search/list#type
        search.setType("video");

        // To increase efficiency, only retrieve the fields that the
        // application uses.
        search.setFields("items(id/videoId)");
        search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
     
        // Call the API and print results.
        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();

        List<String> videoIds = new ArrayList<String>();

        if (searchResultList != null) {

            // Merge video IDs
            for (SearchResult searchResult : searchResultList) {
                videoIds.add(searchResult.getId().getVideoId());
            }
            Joiner stringJoiner = Joiner.on(',');
            String videoId = stringJoiner.join(videoIds);

            // Call the YouTube Data API's youtube.videos.list method to
            // retrieve the resources that represent the specified videos.
            YouTube.Videos.List listVideosRequest = youtube.videos().list(
            "contentDetails, "
//            + "fileDetails, " //only for owner
            + "id, "
            + "liveStreamingDetails, "
            + "localizations, "
            + "player, "
//            + "processingDetails, " //only for owner
            + "recordingDetails, "
            + "snippet, " 
            + "statistics, "
            + "status, "
//            + "suggestions, " //only for owner
            + "topicDetails "
            ).setId(videoId);
            listVideosRequest.setKey(apiKey); 
            VideoListResponse listResponse = listVideosRequest.execute();

            List<Video> videoList = listResponse.getItems();

            if (videoList != null) {
                prettyPrint(videoList.iterator(), queryTerm);
            }
        }
    } catch (GoogleJsonResponseException e) {
        System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                + e.getDetails().getMessage());
    } catch (IOException e) {
        System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
    } catch (Throwable t) {
        t.printStackTrace();
    }
	
	
}





/* Prints out all results in the Iterator. For each result, print the
* title, video ID, and thumbnail.
*
* @param iteratorSearchResults Iterator of SearchResults to print
*
* @param query Search query (String)
*/
private static void prettyPrint(Iterator<Video> iteratorSearchResults, String query) throws IOException {

   System.out.println("\n=============================================================");
   System.out.println(
           "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
   System.out.println("=============================================================\n");

   if (!iteratorSearchResults.hasNext()) {
       System.out.println(" There aren't any results for your query.");
   }

   while (iteratorSearchResults.hasNext()) {

       Video singleVideo = iteratorSearchResults.next();

           Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

           System.out.println(" Video Id " + singleVideo.getId());
           System.out.println(" Video URL " + "https://www.youtube.com/watch?v="+singleVideo.getId());
           System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
           System.out.println(singleVideo.toPrettyString());
           System.out.println(" Thumbnail: " + thumbnail.getUrl());
           System.out.println("\n-------------------------------------------------------------\n");

   }
}

}

package hoahong.trending.video.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

import hoahong.trending.video.model.YoutubeResponse;
import hoahong.trending.video.util.Logger;
import hoahong.trending.video.util.Util;

public class YoutubeAPI {
	
	private static final String endpoint = "https://www.googleapis.com/youtube/v3/playlistItems?"
			+ "part=snippet&maxResults=10&playlistId=%s"
			+ "&key=AIzaSyB_ZvA08J8FI3Bkw8rbVf6mwk76CJXyO0A";
	public static final String TAG = "YoutubeAPI";
	
	public static YoutubeResponse getListVideo (String pageToken, String playlistId ) {
		try{
			YoutubeResponse response = null;
			String requestUrl = String.format(endpoint, playlistId);
			requestUrl = Util.isEmpty(pageToken) ? requestUrl : requestUrl + "pageToken=" + pageToken;
			URL obj = new URL(requestUrl);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 "
					+ "(KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
	 
			int responseCode = con.getResponseCode();
			Logger.d(TAG, "\nSending 'GET' request to URL : " + requestUrl);
			Logger.d(TAG, "Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer httpResponse = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				httpResponse.append(inputLine);
			}
			in.close();
	
			//print result
			Logger.d(TAG, httpResponse.toString());
			response = new Gson().fromJson(httpResponse.toString(), YoutubeResponse.class);
			return response;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}

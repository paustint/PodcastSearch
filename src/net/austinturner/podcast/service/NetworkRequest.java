/**
 * This class takes in a request and provides a response from a server<br>
 * using http object and stearmreader
 * @author Austin
 */
package net.austinturner.podcast.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkRequest {
	
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public NetworkRequest(String url){
		
	}
	/**
	 * Sends HTTP GET request
	 * @param urlString
	 * @return StringBuilder request
	 * @throws Exception
	 */
	public static StringBuilder sendGet(String urlString) throws Exception{
		
		HttpURLConnection con = openConnection(urlString, true);
		
		//Get response data
		int responseCode = con.getResponseCode();

		//Read data in from response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		//Get data and put into StringBuilder
		String inputLine;
		StringBuilder response = new StringBuilder(); // Could use StringBuffer if concurrency is needed (thread safe)
		
		while((inputLine = in.readLine()) != null){
			response.append(inputLine);
		}
		//returnResponse
		return response;
	}
	/**
	 * Sends HTTP POST request
	 * @param urlString
	 * @param urlParameters
	 * @return StringBuilder request from POST query
	 * @throws Exception
	 */
	public static StringBuilder sendPost(String urlString, String urlParameters) throws Exception{
		
		
		HttpURLConnection con = openConnection(urlString, false);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		//Get response data
		int responseCode = con.getResponseCode();

		//Read data in from response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		//Get data and put into StringBuilder
		String inputLine;
		StringBuilder response = new StringBuilder(); // Could use StringBuffer if concurrency is needed (thread safe)
		
		while((inputLine = in.readLine()) != null){
			response.append(inputLine);
		}
		//returnResponse
		return response;
	}
	/**
	 * Helper method to build connection in preparation for HTTP request
	 * @param urlString
	 * @param isGET
	 * @return
	 * @throws IOException
	 */
	private static HttpURLConnection openConnection(String urlString, Boolean isGET) throws IOException{
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		
		if (isGET){
			//Set request method - GET
			con.setRequestMethod("GET");
		}
		else{
			//Set request method - POST
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		}
		//add request header
		con.addRequestProperty("User-Agent", USER_AGENT);
		
		return con;
		
	}
	
	
	

}

/**
 * This class is the connector for the API connection to digitalpodcsast.com<br>
 * This class handles building the query and sending and receiving responses
 * @author Austin
 */
package net.austinturner.podcast.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DigitalPodcastService {
	
	private StringBuilder url = new StringBuilder();
	private String appID;
	private String host;
	private String serviceName;
	private String method = "search/?";
	private HashMap<String, String> queryKeyValuePairs = new HashMap<String, String>();
	
	/**
	 * Default constructor requires
	 * @throws MalformedURLException 
	 */
	public DigitalPodcastService(String appID, String host, String serviceName){
		this.appID = "appid=" + appID;
		this.host = host;
		this.serviceName = serviceName;
	}
	public DigitalPodcastService(String appID){
		this(appID,"http://api.digitalpodcast.com","/v2r/");
		
	}
	/**
	 * Change method string.  Default is set as 'search/?'
	 * @param newMethod
	 */
	public void changeMethod(String newMethod){
		this.method = newMethod;
	}
	/**
	 * Adds key/value pair to the service to be called on
	 * @param key
	 * @param value
	 * @throws UnsupportedEncodingException 
	 */
	public void addQueryKeyValuePair(String key, String value) throws UnsupportedEncodingException{
		
		String encValue = URLEncoder.encode(value, "UTF-8")
                .replaceAll("\\+", "%20")
                .replaceAll("\\%21", "!")
                .replaceAll("\\%27", "'")
                .replaceAll("\\%28", "(")
                .replaceAll("\\%29", ")")
                .replaceAll("\\%7E", "~");
		
		queryKeyValuePairs.put(key, encValue);
	}
	public void addQueryKeyValuePairs(Map map){
		queryKeyValuePairs.putAll(map);
	}
	/**
	 * Adds an array of keys and an array of values to the parameter list<br>
	 * <b>Arrays must be of the same length or else false is returned and action is not complete</b>
	 * @param keys
	 * @param values
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public boolean addQueryKeyValuePairs(String[] keys, String[] values) throws UnsupportedEncodingException{
		if (keys.length == values.length){
			//Loop through and add key value pairs
			for(int i = 0; i < keys.length; i++){
				this.addQueryKeyValuePair(keys[i], values[i]);
			}
			return true;
		}
		else{
			// TODO change this to throw exception instead of returning false
			return false;
		}
	}
	
	public StringBuilder executeQuery() throws Exception{
		buildQuery();
		return NetworkRequest.sendGet(url.toString());
		//TODO write execute script here (potentially write another class and call ?static?
	}
	
	private void buildQuery(){
		url = new StringBuilder();
		url.append(host);
		url.append(serviceName);
		url.append(method);
		url.append(appID);
		for (Map.Entry<String, String> keyValue : queryKeyValuePairs.entrySet()) {
			url.append("&" + keyValue.getKey() + "=" + keyValue.getValue());
		}
	}//end buildquery
	
	public String getQueryString(){
		buildQuery();
		return url.toString();
	}

}

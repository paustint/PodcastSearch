/**
 * This class handles the REST API call to Digital Podcast<Br>
 * This class is dependent on DigitalPodcastservice.Java<Br>
 * 
 * @author Austin Turner
 */
package net.austinturner.podcast.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DigitalPodcast {
	private DigitalPodcastService DPS;
	private Map<String, String> availableParameters = new HashMap<>();
	private final String SORT_OPTIONS[] = {"rel","alpha","hits","votes","subs","rating","new"};
	private final String SEARCHSOURCE_OPTIONS[] = {"all","title"};
	private final String FORMAT_OPTIONS[] = {"opml","rssopml","rss"};
	private final String CONTENTFILTER_OPTIONS[] = {"nofilter","noadult","noexplicit","clean","explicit","adult"};
	private int currStart = 0;
	private int numResults = 10;
	
	/**
	 * Constructor - default initializes with default values
	 */
	public DigitalPodcast(String APIkey){
		this(null, 0, 10, 0, 0, 0, 0, APIkey);
	}
	/**
	 * full constructor - sets all values<br>
	 * start: results start from (0 is default)
	 * numResults (valid : 1 - 50, 10 is default)
	 * *==default for next options
	 * Sort Options: 0=rel(*),1=alpha,2=hits,3=votes,4=subs,5=rating,6=new<br>
	 * Search Source Options: 0=all(*),1=title<br>
	 * Format Options: 0=opml(*),1=rssopml,2=rss<br>
	 * Content Filter Options: 0=nofilter(*),1=noadult,2=noexplicit,3=clean,4=explicit,5=adult<br>
	 * @param keywords
	 * @param start
	 * @param numResults
	 * @param sort
	 * @param searchSource
	 * @param format
	 * @param contentfilter
	 * @param APIkey
	 */
	public DigitalPodcast(String keywords, int start, int numResults, int sort, int searchSource, int format, int contentfilter, String APIkey){
		availableParameters.put("keywords", keywords);
		availableParameters.put("start", Integer.toString(start));
		availableParameters.put("results", Integer.toString(numResults));
		availableParameters.put("sort", SORT_OPTIONS[sort]);
		availableParameters.put("searchsource", SEARCHSOURCE_OPTIONS[searchSource]);
		availableParameters.put("format", FORMAT_OPTIONS[format]);
		availableParameters.put("contentFilter", CONTENTFILTER_OPTIONS[contentfilter]);
		DPS = new DigitalPodcastService(APIkey);
	}
	
	/**
	 * 
	 * Allows different result to be passed in
	 * Max number of results = 50
	 * @param numResults
	 */
	public DigitalPodcast(int numResults, String APIkey){
		//Call default constructor
		this(APIkey);
		//set and change values that are different
		this.setNumResults(numResults);
		availableParameters.put("results", Integer.toString(numResults));
	}
	/**
	 * Allows keyword to be provided and numResults
	 * Max number of results = 50
	 * @param keywords
	 * @param numResults
	 * @throws UnsupportedEncodingException 
	 */
	public DigitalPodcast(String keywords, int numResults, String APIkey) throws UnsupportedEncodingException{
		//call default constructor
		this(APIkey);
		//Set values that differ from default, and add them to DPS
		this.setNumResults(numResults);
		availableParameters.put("results", Integer.toString(numResults));
		this.setKeyword(keywords);
		availableParameters.put("keywords", keywords);
	}
	/**
	 * Allows keyword to be passed in
	 * @param keywords
	 * @throws UnsupportedEncodingException 
	 */
	public DigitalPodcast(String keywords, String APIkey) throws UnsupportedEncodingException{
		this(APIkey);
		this.setKeyword(keywords);
		availableParameters.put("keywords", keywords);
	}
	
	/**
	 * Get current start number
	 * @return
	 */
	public int getCurrStart() {
		return currStart;
	}

	/**
	 * Set current start number of result set.<Br>Default is 0 (first set of results).
	 * @param currStart
	 */
	public void setCurrStart(int currStart) {
		this.currStart = currStart;
		availableParameters.put("start", Integer.toString(currStart));
	}
	
	/**
	 * Search query (escaped to proper HTML for REST call)
	 * @param keyword
	 * @throws UnsupportedEncodingException 
	 */
	public void setKeyword(String keywords) throws UnsupportedEncodingException{
		String encValue = URLEncoder.encode(keywords, "UTF-8")
                .replaceAll("\\+", "%20")
                .replaceAll("\\%21", "!")
                .replaceAll("\\%27", "'")
                .replaceAll("\\%28", "(")
                .replaceAll("\\%29", ")")
                .replaceAll("\\%7E", "~");
		
		availableParameters.put("keywords", encValue);
	}
	/**
	 * Default = 0
	 * @param start
	 */
	public void setStart(int start){
		setCurrStart(start);
		availableParameters.put("start", Integer.toString(start));
		
	}
	/**
	 * Default = 10, max = 50
	 * @param numResults
	 */
	public void setNumResults(int numResults){
		availableParameters.put("results", Integer.toString(numResults));
	}
	/**
	 * <b>Available Values:</b><br>
	 * 0="rel"<br>
	 * 0="rel"<br>
	 * 1="alpha"<br>
	 * 2="hits"<br>
	 * 3="votes"<br>
	 * 4="subs"<br>
	 * 5="rating"<br>
	 * 6="new"<br>
	 * @param sort
	 */
	public void setSort(int sort){
		availableParameters.put("sort", SORT_OPTIONS[sort]);
	}
	/**
	 * <b>Available Values:</b><br>
	 * 0="all"<br>
	 * 1="title"<br>
	 * @param searchsource
	 */
	public void setSearchSource(int searchsource){
		availableParameters.put("searchsource", SEARCHSOURCE_OPTIONS[searchsource]);
	}
	/**
	 * <b>Available Values:</b><br>
	 * 0=opml<br>
	 * 1=rssopml<br>
	 * 2=rss
	 * @param format
	 */
	public void setFormat(int format){
		availableParameters.put("format", FORMAT_OPTIONS[format]);
	}
	/**
	 * <b>Available Values:</b><br>
	 * 0=noFilter<br>
	 * 1=noadult<br>
	 * 2=noexplicit<br>
	 * 3=clean<br>
	 * 4=explicit<br>
	 * 5=adult<br>
	 * @param contentFilter
	 */
	public void setContentFilter(int contentFilter){
		availableParameters.put("contentFilter", CONTENTFILTER_OPTIONS[contentFilter]);
	}
	/**
	 * This allows the appid or any other option to be changed or added if new versions exist
	 * 
	 * @param key
	 * @param value
	 */
	public void setParameter(String key, String value){
		availableParameters.put(key, value);
	}
	/**
	 * Executes GET request and returns the XML 
	 * @return
	 * @throws Exception
	 */
	public StringBuilder execute() throws Exception{
		DPS.addQueryKeyValuePairs(availableParameters);
		return DPS.executeQuery();
	}
	/**
	 * Returns the HTTP URL for the request
	 * @return
	 */
	public String getQuery(){
		DPS.addQueryKeyValuePairs(availableParameters);
		//System.out.println(DPS.getQueryString());
		return DPS.getQueryString();
	}
	
	/**
	 * Returns HashMap of all current parameters and values
	 * @return
	 */
	public Map<String, String> getParameters(){
		//Make sure all set values are added to DPS prior to return
		//This is just to ensure they are synced.
		DPS.addQueryKeyValuePairs(availableParameters);
		return availableParameters;
		
	}



	
}

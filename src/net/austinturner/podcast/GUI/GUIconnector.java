package net.austinturner.podcast.GUI;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.austinturner.podcast.RSS.RSSFeed;
import net.austinturner.podcast.RSS.RSSFeedMessage;
import net.austinturner.podcast.RSS.RSSFeedParser;
import net.austinturner.podcast.db.SQLiteJDBC;
import net.austinturner.podcast.service.DigitalPodcast;

public class GUIconnector {
	private static DigitalPodcast dp;
	private RSSFeedParser parser;
	private RSSFeed feed;
	private List<RSSFeedMessage> messages;
	private SQLiteJDBC sql;
	private Map<String, String[]> sqlValues = new HashMap<>();
	private ArrayList<String> sqlColumns = new ArrayList<String>();
	private final String DB_SEARCH = "SEARCH_HISTORY";
	
	private final boolean DEBUG = true;
	
	/**
	 * Initialize
	 * @param APIkey
	 * @throws Exception
	 */
	public GUIconnector(String APIkey) throws Exception{
			dp = new DigitalPodcast(APIkey);
			dp.setFormat(2);
			setDBCon();// Create connection to DB
	}
	public static DigitalPodcast getActiveDP(){
		return dp;
	}
	
	/**
	 * Get Feed
	 * @return
	 */
	public RSSFeed getFeed(){
		return feed;
	}
	/**
	 * get Messages
	 * @return
	 */
	public List<RSSFeedMessage> getMessages(){
		return messages;
	}
	/**
	 * Overloaded - allow smaller result of messages to be returned if desired<br>
	 * @param start
	 * @param numResults
	 * @return
	 */
	public List<RSSFeedMessage> getMessages(int start, int numResults){
		return messages.subList(start, numResults+1);
	}
	
	/**
	 * Set all parameters to submit query<br>
	 * This query connects to DigitalPodcast Service, not individual RSS feeds<br>
	 * Use setNewSearch() for individual feeds<br>
	 * @param keywords
	 * @param numResults
	 * @param sort
	 * @param contentFilter
	 * @param searchsource
	 * @param start
	 */
	public void setParameters(String keywords, int numResults, int sort, int contentFilter, int searchsource, int start){
		try {
			dp.setKeyword(keywords);
			dp.setNumResults(numResults);
			dp.setSort(sort);
			dp.setContentFilter(contentFilter);
			dp.setSearchSource(searchsource);
			dp.setStart(start);
			parser = new RSSFeedParser(dp.getQuery());
			feed = parser.readFeed();
			messages = feed.getMessages();
			if (messages.get(0).getLink().equals("http://www.digitalpodcast.com/feeds/featured")) messages.remove(0);
			debug();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is called when the RSS URL is known, without connecting to an API service<br>
	 * @param rssFeedUrl
	 */
	public boolean setNewSearch(String rssFeedUrl){
		try {
			parser = new RSSFeedParser(rssFeedUrl);
			feed = parser.readFeed();
			messages = feed.getMessages();
			if (messages.get(0).getLink().equals("http://www.digitalpodcast.com/feeds/featured")) messages.remove(0);
			debug(rssFeedUrl);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Set start and refresh query results
	 * @param start
	 */
	public void setStart(int start){
		dp.setCurrStart(start);
		try {
			parser = new RSSFeedParser(dp.getQuery());
			feed = parser.readFeed();
			messages = feed.getMessages();
			if (messages.get(0).getLink().equals("http://www.digitalpodcast.com/feeds/featured")) messages.remove(0);
			debug();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	///////////////////////////////////////SQLite DB Methods////////////////////////////////////////////////////////
	/**
	 * Create instance of SQLite and connect to DB<Br>
	 * @return
	 */
	private boolean setDBCon(){
		try {
			sql = new SQLiteJDBC();
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			if(DEBUG) e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(DEBUG) e.printStackTrace();
			return false;
		}
	}
	/**
	 * Put values in map to be used to store in DB<Br>
	 * @param key
	 * @param value
	 * @param type
	 */
	public void putSQLvalue(String key, String value, String type){
		sqlColumns.add(key);
		sqlValues.put(key, new String[] {value,type});
	}
	public void putSQLvalue(String key, String value){
		putSQLvalue(key, value, "TEXT");
	}
	public int executSearchSQL(){
		int rowsReturned = sql.SQLInsert(sql.createInsert(DB_SEARCH, sqlColumns.toArray(new String[sqlColumns.size()]), sqlValues));
		sqlColumns.clear();
		if(DEBUG) System.out.println("Rows Returned: " + rowsReturned);
		return rowsReturned;
	}

	/**
	 * Create search insert query based off of feed data and search keyword<Br>
	 * @param searchQuery
	 * @param rssFeed
	 * @return
	 */
	public int createSearchInsert(String searchQuery, RSSFeed rssFeed){
		putSQLvalue("SEARCHDATE", sql.getDate());
		putSQLvalue("SEARCH", searchQuery);
		putSQLvalue("SEARCHURL", dp.getQuery());
		putSQLvalue("TITLE", rssFeed.getTitle());
		putSQLvalue("LINK", rssFeed.getLink());
		putSQLvalue("DESCRIPTION", rssFeed.getDescription());
		putSQLvalue("PUBDATE", rssFeed.getPubDate());
		putSQLvalue("LANGUAGE", rssFeed.getLanguage());
		putSQLvalue("IMAGE", rssFeed.getImage());
		putSQLvalue("AUTHOR", rssFeed.getAuthor());
		putSQLvalue("COPYRIGHT", rssFeed.getCopyright());
		putSQLvalue("SUBTITLE", rssFeed.getSubtitle());
		putSQLvalue("SUMMARY", rssFeed.getSummary());
		putSQLvalue("NAME", rssFeed.getName());
		putSQLvalue("EMAIL", rssFeed.getEmail());
		putSQLvalue("CATEGORY", rssFeed.getCategory());
		putSQLvalue("TOTALRESULTS", rssFeed.getTotalResults(), "INT");
		putSQLvalue("STARTINDEX", rssFeed.getStartIndex(), "INT");
		putSQLvalue("ITEMSPERPAGE", rssFeed.getItemsPerPage(), "INT");
		return executSearchSQL();
	}
	
	//////////////////////////////////////////////////DEBUG/////////////////////////////////////////////////////////
	/**
	 * Debug function
	 * @param query
	 */
	private void debug(String query){
		if(DEBUG){
			System.out.println(feed);
			System.out.println(query);
			for (RSSFeedMessage m : messages){
				System.out.println(m);
			}
		}
	}
	private void debug(){
			debug(dp.getQuery());
	}
	
	
	
	


}

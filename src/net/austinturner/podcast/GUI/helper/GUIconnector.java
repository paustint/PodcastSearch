package net.austinturner.podcast.GUI.helper;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
	private ExecutorService pool;
	
	private int downloadCount = 0;
	private boolean saveDownloadHistory = true;
	private boolean SaveSearchHistory = true;
	private final boolean DEBUG = true;
	
	/**
	 * Initialize
	 * @param APIkey
	 * @throws Exception
	 */
	public GUIconnector(String APIkey, ExecutorService pool) throws Exception{
		this.pool = pool;
		dp = new DigitalPodcast(APIkey);
		dp.setFormat(2);
		setDBCon();// Create connection to DB
	}
	/**
	 * Disable download history<br>
	 * @param APIkey
	 * @param pool
	 * @param saveDownloadHistory
	 * @throws Exception
	 */
	public GUIconnector(String APIkey, ExecutorService pool, boolean saveDownloadHistory, boolean SaveSearchHistory) throws Exception{
		this(APIkey, pool);
		this.saveDownloadHistory = saveDownloadHistory;
	}
	
	
	public static DigitalPodcast getActiveDP(){
		return dp;
	}
	/////////////////////////////////////////////////////////////////////////GET FEED AND MESSAGES///////////////////////////////////////////////////////////////////
	
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
	
	////////////////////////////////////////////////////////////////////////////NEW QUERIES///////////////////////////////////////////////////////////////////////////
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
			if(DEBUG) e.printStackTrace();
		}
	}
	////////////////////////////////////////////////////////////////////DOWNLOAD RELATED//////////////////////////////////////////////////////////////////////
	

	/**
	 * Download Podcast Episode<br>
	 * @param website
	 * @param feedName
	 * @param fileName
	 * @param fileSize
	 * @return number of downloads in progress
	 */
	public int downloadEpisode(URL website, String feedName, String fileName, String fileSize){
		 Future f = pool.submit(new DownloadTask(website, feedName, fileName, fileSize), this);
		 
		 downloadCount ++;
		 if(DEBUG) System.out.println("Downloads in progress: " + downloadCount);
		 //System.out.println(f.hashCode());
		 while(!f.isDone()){ // Keep looping until download has finished
		 }
		 //System.out.println(f.hashCode());
		 System.out.println("done");
		 downloadCount --;
		 if(DEBUG) System.out.println("Downloads in progress: " + downloadCount);
		 return downloadCount;
	}
	
	public int checkDownloadStatus(){
		return 0;
	}
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////Subscription Related////////////////////////////////////////////////////////////////////
	
	
	
	
	
	////////////////////////////////////////////////////////////////////SQLite DB Methods//////////////////////////////////////////////////////////////////////
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
		if (SaveSearchHistory){
			int rowsReturned = sql.SQLInsert(sql.createInsert(DB_SEARCH, sqlColumns.toArray(new String[sqlColumns.size()]), sqlValues));
			sqlColumns.clear();
			if(DEBUG) System.out.println("Rows Returned: " + rowsReturned);
			return rowsReturned;
		} else{
			return -2;
		}
		
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
	
	/**
	 * 
	 */
	public void clearSearchHistory(){
		//TODO
	}
	
	/////////////////////////////////////////////////////////////////////////////DEBUG//////////////////////////////////////////////////////////////////
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

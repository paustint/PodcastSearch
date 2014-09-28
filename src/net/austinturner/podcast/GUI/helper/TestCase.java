package net.austinturner.podcast.GUI.helper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.austinturner.podcast.RSS.RSSFeed;
import net.austinturner.podcast.RSS.RSSFeedMessage;
import net.austinturner.podcast.RSS.RSSFeedParser;
import net.austinturner.podcast.db.SQLiteJDBC;
import net.austinturner.podcast.service.DigitalPodcast;

public class TestCase {

	public static void main(String[] args) {
		DigitalPodcast dp = new DigitalPodcast(args[0]);
		dp.setFormat(2);
		
		try {
			
			String test = "A funny look at relationships, marriage, divorce, love, sex, life, dating advice, and the weird thoughts that pop into Dave\\’s head when he is sleep deprived. Never longer than 15 minutes, and guaranteed to make you laugh, cry, think, or groan. Life is too important to be taken seriously";

			dp.setKeyword("Nirvana");
			//System.out.println(dp.getQuery());
			//System.out.println(dp.execute());
			RSSFeedParser parser = new RSSFeedParser(dp.getQuery());
			RSSFeed feed = parser.readFeed();
			//System.out.println(feed);
			List<RSSFeedMessage> messages = feed.getMessages();
			System.out.println("----+"+feed.getTotalResults()+"+----");
			//System.out.println(messages);
			//System.out.println(messages.get(1));
			//System.out.println(messages.get(2));
			//System.out.println(messages.get(2).getLink());
			//System.out.println(messages.get(2).getSource());
			parser = new RSSFeedParser(messages.get(2).getSource());
			feed = parser.readFeed();
			SQLiteJDBC sql = new SQLiteJDBC();
			//System.out.println(sql.getInsertStatementAll("SEARCH_HISTORY"));
			sql.insertSearchHistory("search", "searchurl", "title", "link", "description", "pubdate", "language", "image", "author", "copyright", "subtitle", "summary", "name", "email", "category", "350", "0", "50");
			Map<String, String[]> values = new HashMap<>();
			values.put("SEARCHDATE", new String[]{"searchdate", "TEXT"});
			values.put("SEARCH", new String[]{"search", "TEXT"});
			values.put("SEARCHURL", new String[]{"searchurl", "TEXT"});
			values.put("TITLE", new String[]{"title", "TEXT"});
			values.put("LINK", new String[]{"link", "TEXT"});
			values.put("DESCRIPTION", new String[]{"description", "TEXT"});
			values.put("PUBDATE", new String[]{"pubdate", "TEXT"});
			values.put("LANGUAGE", new String[]{"language", "TEXT"});
			values.put("IMAGE", new String[]{"image", "TEXT"});
			values.put("AUTHOR", new String[]{"author", "TEXT"});
			values.put("COPYRIGHT", new String[]{"copyright", "TEXT"});
			values.put("SUBTITLE", new String[]{"subtitle", "TEXT"});
			values.put("SUMMARY", new String[]{"summary", "TEXT"});
			values.put("NAME", new String[]{"name", "TEXT"});
			values.put("EMAIL", new String[]{"email", "TEXT"});
			values.put("CATEGORY", new String[]{"category", "TEXT"});
			values.put("TOTALRESULTS", new String[]{"350", "INT"});
			values.put("STARTINDEX", new String[]{"0", "INT"});
			values.put("ITEMSPERPAGE", new String[]{"50", "INT"});
			
			String[] SEARCH_HISTORY = {"SEARCHDATE", "SEARCH", "SEARCHURL", "TITLE", "LINK", "DESCRIPTION", 
					"PUBDATE", "LANGUAGE", "IMAGE", "AUTHOR", "COPYRIGHT", "SUBTITLE", "SUMMARY", "NAME", "EMAIL", "CATEGORY", 
					"TOTALRESULTS", "STARTINDEX", "ITEMSPERPAGE"};
			
			sql.createInsert("SEARCH_HISTORY", SEARCH_HISTORY, values);
			
			//System.out.println(feed.getMessages());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}

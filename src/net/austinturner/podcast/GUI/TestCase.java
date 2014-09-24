package net.austinturner.podcast.GUI;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.austinturner.podcast.RSS.RSSFeed;
import net.austinturner.podcast.RSS.RSSFeedMessage;
import net.austinturner.podcast.RSS.RSSFeedParser;
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
			System.out.println(feed.getImage());
			for (RSSFeedMessage m : feed.getMessages()){
				System.out.println(m);
				System.out.println(m.getMessageText());
				
			}
			String temp = feed.getMessages().get(0).getDescription().substring(84, 85);

			//System.out.println(feed.getMessages());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}

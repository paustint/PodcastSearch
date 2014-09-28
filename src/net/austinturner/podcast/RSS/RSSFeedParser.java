/**
 * @author Austin
 * Reference Site: http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
package net.austinturner.podcast.RSS;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;



public class RSSFeedParser {
	
	  static final String TITLE = "title";
	  static final String DESCRIPTION = "description";
	  static final String CHANNEL = "channel";
	  static final String LINK = "link";
	  static final String ITEM = "item";
	  static final String PUB_DATE = "pubDate";
	  static final String SOURCE = "source";
	  static final String COMMENTS = "comments";
	  static final String GUID = "guid";
	  static final String AUTHOR = "author";
	  static final String ENCLOSURE = "enclosure";
	  static final String LANGUAGE = "language";
	  static final String IMAGE = "image";
	  static final String COPYRIGHT = "copyright";
	  static final String SUBTITLE = "subtitle";
	  static final String SUMMARY = "summary";
	  static final String NAME = "name";
	  static final String EMAIL = "email";
	  static final String CATEGORY = "category";
	  static final String KEYWORDS = "keywords";
	  static final String DURATION = "duration";
	  // Part of API
	  static final String TOTAL_RESULTS = "totalResults";
	  static final String START_INDEX = "startIndex";
	  static final String ITEMS_PER_PAGE = "itemsPerPage";
	  
	  private URL url;

	private String generator = "generator";
	//These are in header, but may not use these
	private String format = "podcastSearch:format";
	private String totalResults = "podcastSearch:totalResults";
	private String startIndex = "podcastSearch:startIndex";
	private String itemsPerPage = "podcastSearch:itemsPerPage";
		
	private final boolean DEBUG = false;
	  
	/**
	 * 
	 * @param feedUrl
	 * @throws Exception
	 */
	public RSSFeedParser(StringBuilder feedUrl) throws Exception{
	    try {
	        this.url = new URL(feedUrl.toString());
	      } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	      }
	    } // close constructor
	public RSSFeedParser(String feedUrl) throws Exception{
	    try {
	        this.url = new URL(feedUrl);
	      } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	      }
	    } // close constructor
	
	/**
	 * 
	 * @return
	 */
	public RSSFeed readFeed(){
		RSSFeed feed = null;
		try{
			boolean isFeedHeader = true;
			//Header calues initially to empty string
			String channel = "";
			String title = "";
			String description = "";
			String language = "";
			String link = "";
			String source = "";
			String comments = "";
			String pubDate = "";
			String guid = "";
			String author = "";
			String enclosure = "";
			String encUrl = "";
			String encLength = "";
			String encType = "";
			String image = "";
			String copyright = "";
			String subtitle = "";
			String summary = "";
			String name = "";
			String email = "";
			String category = "";
			String keywords = "";
			String duration = "";
			
			
		    String totalResults = "";
		    String startIndex = "";
		    String itemsPerPage = "";
			
			//Create new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			//Setup a new eventReader
			InputStream in = read();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()){
					String localPart = event.asStartElement().getName().getLocalPart();
					switch (localPart){
					case ITEM:
						if (isFeedHeader){
							isFeedHeader = false;
							feed = new RSSFeed(title, link, description, pubDate, language, image,
									author, copyright, subtitle, summary, name, email, category);
							if (!totalResults.equals("")){//See if result has totalResults attribute set - if so it must be PodcastSearch API feed
								feed.setTotalResults(totalResults);
								feed.setStartIndex(startIndex);
								feed.setItemsPerPage(itemsPerPage);
							}
						}
						event = eventReader.nextEvent();
						break;
					case ENCLOSURE:
						//enclosure = getCharacterData(event, eventReader);
						//Enclosure element is structured as follows:
						//<enclosure url="http://www.controlledburnbbq.com/podcast/episode_05.mp3" length="3894544" type="audio/mpeg" />
						StartElement startElement = event.asStartElement();
						Attribute url = startElement.getAttributeByName(new QName("url"));
						Attribute length = startElement.getAttributeByName(new QName("length"));
						Attribute type = startElement.getAttributeByName(new QName("type"));
						if(url != null){
							encUrl = url.getValue();
						}
						if(length != null){
							encLength = length.getValue();						
						}
						if(type != null){
							encType = type.getValue();
						}
						break;
					case TITLE:
						title = getCharacterData(event, eventReader);
						break;
					case DESCRIPTION:
						description = getCharacterData(event, eventReader);
						break;
					case LANGUAGE:
						language = getCharacterData(event, eventReader);
						break;
					case CHANNEL:						
						//channel = getCharacterData(event, eventReader);
						break;
					case LINK:
						link = getCharacterData(event, eventReader);
						break;
					case COMMENTS:
						comments = getCharacterData(event, eventReader);
						break;
					case PUB_DATE:
						pubDate = getCharacterData(event, eventReader);
						break;
					case GUID:
						guid = getCharacterData(event, eventReader);
						break;
					case AUTHOR:
						author = getCharacterData(event, eventReader);
						break;
					case IMAGE:
						startElement = event.asStartElement();
						Attribute imageAttr = startElement.getAttributeByName(new QName("href"));
						//Attribute imageAttr = null;
						if(imageAttr != null){
							image = imageAttr.getValue();
						}
						break;
					case TOTAL_RESULTS:
						totalResults = getCharacterData(event, eventReader);
						break;
					case START_INDEX:
						startIndex = getCharacterData(event, eventReader);
						break;
					case ITEMS_PER_PAGE:
						itemsPerPage = getCharacterData(event, eventReader);
						break;
					case COPYRIGHT:
						copyright = getCharacterData(event, eventReader);
						break;
					case SUBTITLE:
						subtitle = getCharacterData(event, eventReader);
						break;
					case SUMMARY:
						summary = getCharacterData(event, eventReader);
						break;
					case NAME:
						name = getCharacterData(event, eventReader);
						break;
					case EMAIL:
						email = getCharacterData(event, eventReader);
						break;
					case CATEGORY:
						category = getCharacterData(event, eventReader);
						break;
					case KEYWORDS:
						keywords = getCharacterData(event, eventReader);
						break;
					case DURATION:
						duration = getCharacterData(event, eventReader);
					case SOURCE:
						startElement = event.asStartElement();
						Attribute urlAttr = startElement.getAttributeByName(new QName("url"));
						if(urlAttr != null){
							encUrl = urlAttr.getValue();
						}
						source = getCharacterData(event, eventReader);
					} 
				} else if(event.isEndElement()){
						if (event.asEndElement().getName().getLocalPart() == (ITEM)){
							RSSFeedMessage message = new RSSFeedMessage();
							message.setTitle(title);
							message.setSource(source);
							message.setLink(link);
							message.setAuthor(author);
							message.setPubDate(pubDate);
							message.setComments(comments);
							message.setGuid(guid);
							message.setUrl(encUrl);
							message.setLength(encLength);
							message.setType(encType);
							message.setSubtitle(subtitle);
							message.setSummary(summary);
							if (summary.equals("")) message.setSummary(description);
							message.setKeywords(keywords);
							message.setDuration(duration);
							feed.getMessages().add(message);
							event = eventReader.nextEvent();
							continue;
						}
				}
			}
			
		} catch (XMLStreamException e){
			throw new RuntimeException(e);
		}
		return feed;
	}
	
	/**
	 * 
	 * @param event
	 * @param eventReader
	 * @return
	 * @throws XMLStreamException
	 */
	  private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
		      throws XMLStreamException {
		    String result = "";
		    event = eventReader.nextEvent();
		    if (event instanceof Characters) {
		      result = event.asCharacters().getData();
		    }
		    return result;
		  }
	  
	  /**
	   * 
	   * @return
	   */
	private InputStream read() {
	    try {
	      return url.openStream();
	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    }
	  }
		
	

}

package net.austinturner.podcast.db;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.sqlite.JDBC;

public class SQLiteJDBC
{
	private boolean DEBUG = false;
	
	
	private final String[] SEARCH_HISTORY = {"SEARCHDATE", "SEARCH", "SEARCHURL", "TITLE", "LINK", "DESCRIPTION", 
			"PUBDATE", "LANGUAGE", "IMAGE", "AUTHOR", "COPYRIGHT", "SUBTITLE", "SUMMARY", "NAME", "EMAIL", "CATEGORY", 
			"TOTALRESULTS", "STARTINDEX", "ITEMSPERPAGE"};
	private final String SEARCH_HIST_DB = "SEARCH_HISTORY";
	  
	
    private Connection c = null;
    private Statement stmt = null;
    
    /**
     * Initialize
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public SQLiteJDBC() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:test.db");
		c.setAutoCommit(false);
		if(DEBUG) System.out.println("Opened database successfully");
    }
    /**
     * Insert Search History Row<br>
     * Method "SQLInsert()" is generic version of this method and can be used instead<br>
     * @param search
     * @param searchurl
     * @param title
     * @param link
     * @param description
     * @param pubdate
     * @param language
     * @param image
     * @param author
     * @param copyright
     * @param subtitle
     * @param summary
     * @param name
     * @param email
     * @param category
     * @param totalresults
     * @param startindex
     * @param itemsperpage
     */
    public void insertSearchHistory(String search, String searchurl, String title, 
    		String link, String description, String pubdate, String language, String image, 
    		String author, String copyright, String subtitle, String summary, String name, 
    		String email, String category, String totalresults, String startindex, String itemsperpage ){
    		
    	String insertString = getInsertStatementPartial(SEARCH_HIST_DB, SEARCH_HISTORY);
    	String valueString = getValuesStatementPartial(search, searchurl, title, link, description, pubdate, language, image, author, copyright, subtitle, summary, name, email, category, totalresults, startindex, itemsperpage);
    		
    	try {
      	      stmt = c.createStatement();
    	      String sql = insertString + valueString;
    	      if(DEBUG) System.out.println(sql);
    	      int rowsReturned = stmt.executeUpdate(sql);
    	      stmt.close();
    	     // c.commit();
    	      c.close();
    	      if(DEBUG) System.out.println("Rows Inserted: " + rowsReturned);
    	    } catch ( Exception e ) {
    	    	if(DEBUG) System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    	    }
    }
    /**
     * Pass in any SQLite statement to be executed<br>
     * @param insertStatement
     * @return
     */
    public int SQLInsert(String insertStatement){
    	int rowsReturned = -1;
    	try {
    	  stmt = c.createStatement();
  	      String sql = insertStatement;
  	      if(DEBUG) System.out.println(sql);
  	      rowsReturned = stmt.executeUpdate(sql);
  	      stmt.close();
  	      c.commit();
  	      c.close();
  	      if(DEBUG) System.out.println("Rows Inserted: " + rowsReturned);
  	      return rowsReturned;
  	    } catch ( Exception e ) {
  	    	if(DEBUG) System.err.println( e.getClass().getName() + ": " + e.getMessage() );
  	    	return rowsReturned;
  	    }
    }
    
    
    /**
     *Get the first section of the insert statement for the passed in array<br>
     *NOTE: ONLY RETURNES THE FIRST PART OF INSERT, NOT THE VALUES PART<br>
     *Use getValuesStatementALL() to get remainder of SQL statement<br>
     *
     * @SEE getInsertStatementPartial
     * @param db
     * @return
     */
    private String getInsertStatementPartial(String db, String[] columns){
    	String returnString = "INSERT INTO " + db +" (";
    	for (int i = 0; i<SEARCH_HISTORY.length; i++){
    		if (i == SEARCH_HISTORY.length -1){
    			returnString += SEARCH_HISTORY[i] + ") ";
    		}
    		else{
    			returnString += SEARCH_HISTORY[i] + ", ";
    		}
    	}
    	return returnString;
    }
    /**
     * Gets the value portion of the statement.<br>
     * 
     * @SEE getValuesStatementPartial
     * @param search
     * @param searchurl
     * @param title
     * @param link
     * @param description
     * @param pubdate
     * @param language
     * @param image
     * @param author
     * @param copyright
     * @param subtitle
     * @param summary
     * @param name
     * @param email
     * @param category
     * @param totalresults
     * @param startindex
     * @param itemsperpage
     * @return
     */
    private String getValuesStatementPartial(String search, String searchurl, String title, 
		    		String link, String description, String pubdate, String language, String image, 
		    		String author, String copyright, String subtitle, String summary, String name, 
		    		String email, String category, String totalresults, String startindex, String itemsperpage ){
		    String searchDate = getDate();
    		
		    String returnString = 
		    		"VALUES ('" + searchDate + "', " +
		    				"'" + search + "', " +
		    				"'" + searchurl + "', " +
		    				"'" + title + "', " +
		    				"'" + link + "', " +
		    				"'" + description + "', " +
		    				"'" + pubdate + "', " +
		    				"'" + language + "', " +
		    				"'" + image + "', " +
		    				"'" + author + "', " +
		    				"'" + copyright + "', " +
		    				"'" + subtitle + "', " +
		    				"'" + summary + "', " +
		    				"'" + name + "', " +
		    				"'" + email + "', " +
		    				"'" + category + "', " +
		    				totalresults + ", " +
		    				startindex + ", " +
		    				itemsperpage + ");";
		return returnString;	
    }
    
    /**
     * Create SQLite insert statement<Br>
     * values param expects the following format: {"colName"=>["value, "type"],...}<br>
     * If colNames[0] != "ID", it will be added and value will be auto-incremented<br>
     * @param dbName
     * @param colNames
     * @param values
     * @return
     */
    public String createInsert(String dbName, String[] colNames, Map<String, String[]> values){
    	String insertLine = "INSERT INTO " + dbName +" (";
    	String valuesLine = "VALUES (";
    	boolean firstPass = true;
    	for (int i = 0; i<colNames.length;i++){
    		if (i == colNames.length -1){//For last entry, format ending of script
    			insertLine += colNames[i] + ") ";
        		if (values.get(colNames[i])[1].equals("TEXT")){ // If text, add ' before and after
        			valuesLine += "'"+ values.get(colNames[i])[0] +"' );";
        		} else {
        			valuesLine += values.get(colNames[i])[0] + " );";
        		}
    		} else{
    			insertLine += colNames[i] + ", ";
        		if (values.get(colNames[i])[1].equals("TEXT")){ // If text, add ' before and after
        			valuesLine += "'"+ values.get(colNames[i])[0] +"', ";
        		} else {
        			valuesLine += values.get(colNames[i])[0] + ", ";
        		}
    		}
    	}
    	if(DEBUG) System.out.println(insertLine);
    	if(DEBUG) System.out.println(valuesLine);
    	return insertLine + valuesLine;
    }
    
    
    /**
     * Get date as String Format: yyyy-MM-dd HH:mm:ss<br>
     * @return
     */
    public String getDate(){
    	return getDate("yyyy-MM-dd HH:mm:ss");
    }
    /**
     * Get date as string in specific format<br>
     * @param format
     * @return
     */
    public String getDate(String format){
    	DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
    }
    
    
    
    /**
     * Creates insert statement for SQLITE<br>
     * @param dbName
     * @param values
     * @return
     */
    private String createQuery(String dbName, String[] values){
    	StringBuilder insertString = new StringBuilder();
    	
    	return null;
    }
    /**
     * Arraylist of values to get SQL inster statement string<br>
     * ArrayList is converted to array and calls upon other createQuery()<Br>
     * @param dbName
     * @param values
     * @return
     */
    private String createQuery(String dbName, ArrayList<String> values){
    	return createQuery(dbName, (String[]) values.toArray());
    }
    
    
    /**
     * Get search history array<br>
     * @return
     */
    public String[] getSearchHistory(){
    	return SEARCH_HISTORY;
    }
    /**
     * Get integer location of column in search_history table<br>
     * @param colNum
     * @return
     */
    public int getSearchHistoryItemIndex(String colNum){
    	int index = -1;
    	
    	for (int i = 0; i < SEARCH_HISTORY.length; i++){
    		if (SEARCH_HISTORY[i].equals(colNum)){
    			index = i;
    			break;
    		}
    	}
    	return index;
    }
    
 }


///Create DB string (left here for reference)
//"CREATE TABLE SEARCH_HISTORY " +
//"(ID INT PRIMARY KEY     NOT NULL," +
//" SEARCHDATE	TEXT	NOT NULL, " +
//" SEARCH	TEXT	NOT NULL, " +
//" SEARCHURL TEXT 	NOT NULL, " +
//" TITLE	TEXT, " +
//" LINK	TEXT, " +
//" DESCRIPTION	TEXT, " +
//" PUBDATE	TEXT, " +
//" LANGUAGE	TEXT, " +
//" IMAGE	TEXT, " +
//" AUTHOR	TEXT, " +
//" COPYRIGHT	TEXT, " +
//" SUBTITLE	TEXT, " +
//" SUMMARY	TEXT, " +
//" NAME	TEXT, " +
//" EMAIL	TEXT, " +
//" CATEGORY	TEXT, " +
//" TOTALRESULTS	INT, " +
//" STARTINDEX	INT, " +
//" ITEMSPERPAGE	INT)"; 


/**
 * Podcast seach and download tool
 * Started September 2014
 * 
 * @author Austin
 */
package net.austinturner.podcast.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.JLayeredPane;

import java.awt.CardLayout;

import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ImageIcon;

import net.austinturner.podcast.GUI.helper.GUIconnector;
import net.austinturner.podcast.RSS.RSSFeed;
import net.austinturner.podcast.RSS.RSSFeedMessage;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ListSelectionModel;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.border.MatteBorder;

public class SearchPanel extends JPanel implements ActionListener, KeyListener {
	private JLabel lblKeywordSearch;
	private JLabel lblNumberOfResults;
	private JLabel lblSort;
	private JLabel lblSearchOptions;
	private JLabel lblFilterOptions;
	private JLabel lblResultsMessage;
	private JLabel lblImage;
	private JLabel lblStatusBar;
	
	private JTextField txtSearchQuery;
	private JRadioButton radioResults10;
	private JRadioButton rdbtnResults20;
	private JRadioButton rdbtnResults30;
	private JRadioButton rdbtnResults40;
	private JRadioButton rdbtnResults50;
	private final ButtonGroup btnGroupNumResults = new ButtonGroup();
	private JRadioButton rdbtnSearchAll;
	private JRadioButton rdbtnSearchTitle;
	private final ButtonGroup btnGroupSearch = new ButtonGroup();
	private JRadioButton rdbtnRelease;
	private JRadioButton rdbtnAlpha;
	private JRadioButton rdbtnHits;
	private JRadioButton rdbtnVotes;
	private JRadioButton rdbtnSubs;
	private JRadioButton rdbtnRating;
	private JRadioButton rdbtnNew;
	private final ButtonGroup btnGroupSort = new ButtonGroup();
	private JRadioButton rdbtnNoFilter;
	private JRadioButton rdbtnClean;
	private JRadioButton rdbtnExplicit;
	private JRadioButton rdbtnNoAdult;
	private JRadioButton rdbtnNoExplicit;
	private JRadioButton rdbtnAdult;
	private final ButtonGroup btnGroupFilter = new ButtonGroup();
	private JPanel borderFilterOptions;
	private JPanel borderSearchOptions;
	private JPanel borderSort;
	private JPanel borderNumResults;
	private JScrollPane resultsScrollPane;
	
	

	private JButton btnSearch;
	private JButton btnViewEpisodes;
	private JButton btnSubscribe;
	private JButton btnListen;
	private JButton btnDownload;

	private TableModel dataModel;
	private JButton btnPrevious;
	private JButton btnNext;
	
	private String APIKey;
	private JPanel panel;
	private GUIconnector GUIcon;
	private JTable resultsTable;
	
	//Items used for query
	private int start = 0;
	private int numResults = 0;
	private int searchsource = 0;
	private int sort = 0;
	private int contentFilter = 0;
	private String keywords = "";


	
	
	private final boolean DEBUG = true;
	private JLabel lblDownloadStatus;
	private JLabel lblDownloadsInProgress;
	private int numDownloads = 0;
	/**
	 * Create the panel.
	 */
	public SearchPanel(GUIconnector GUIcon) {
		this.GUIcon = GUIcon;
		setBounds(10, 50, 1361, 711);
		setLayout(null);
		
		lblKeywordSearch = new JLabel("Search Query:");
		lblKeywordSearch.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKeywordSearch.setFont(new Font("Aharoni", Font.PLAIN, 16));
		lblKeywordSearch.setBounds(10, 11, 156, 32);
		add(lblKeywordSearch);
		
		borderNumResults = new JPanel();
		borderNumResults.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		borderNumResults.setBounds(10, 54, 486, 55);
		add(borderNumResults);
		borderNumResults.setLayout(null);
		
		lblNumberOfResults = new JLabel("Number of Results:");
		lblNumberOfResults.setBounds(6, 16, 156, 32);
		borderNumResults.add(lblNumberOfResults);
		lblNumberOfResults.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNumberOfResults.setFont(new Font("Aharoni", Font.PLAIN, 16));
		
		
		radioResults10 = new JRadioButton("10");
		radioResults10.setBounds(168, 21, 45, 23);
		radioResults10.setActionCommand("10");
		borderNumResults.add(radioResults10);
		btnGroupNumResults.add(radioResults10);
		radioResults10.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnResults20 = new JRadioButton("20");
		rdbtnResults20.setBounds(215, 21, 45, 23);
		rdbtnResults20.setActionCommand("20");
		borderNumResults.add(rdbtnResults20);
		btnGroupNumResults.add(rdbtnResults20);
		rdbtnResults20.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnResults30 = new JRadioButton("30");
		rdbtnResults30.setBounds(262, 21, 45, 23);
		rdbtnResults30.setActionCommand("30");
		borderNumResults.add(rdbtnResults30);
		btnGroupNumResults.add(rdbtnResults30);
		rdbtnResults30.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnResults40 = new JRadioButton("40");
		rdbtnResults40.setBounds(309, 21, 45, 23);
		rdbtnResults40.setActionCommand("40");
		borderNumResults.add(rdbtnResults40);
		btnGroupNumResults.add(rdbtnResults40);
		rdbtnResults40.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnResults50 = new JRadioButton("50");
		rdbtnResults50.setBounds(356, 21, 45, 23);
		rdbtnResults50.setActionCommand("50");
		borderNumResults.add(rdbtnResults50);
		btnGroupNumResults.add(rdbtnResults50);
		rdbtnResults50.setFont(new Font("Aharoni", Font.PLAIN, 14));
		rdbtnResults50.setSelected(true);
		
		borderSort = new JPanel();
		borderSort.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		borderSort.setBounds(10, 116, 486, 81);
		add(borderSort);
		borderSort.setLayout(null);
		
		lblSort = new JLabel("Sort:");
		lblSort.setBounds(6, 16, 156, 53);
		borderSort.add(lblSort);
		lblSort.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSort.setFont(new Font("Aharoni", Font.PLAIN, 16));
		
		rdbtnRelease = new JRadioButton("Release");
		rdbtnRelease.setBounds(168, 21, 77, 23);
		rdbtnRelease.setActionCommand("0");
		borderSort.add(rdbtnRelease);
		btnGroupSort.add(rdbtnRelease);
		rdbtnRelease.setSelected(true);
		rdbtnRelease.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnAlpha = new JRadioButton("Alpha");
		rdbtnAlpha.setBounds(247, 21, 66, 23);
		rdbtnAlpha.setActionCommand("1");
		borderSort.add(rdbtnAlpha);
		btnGroupSort.add(rdbtnAlpha);
		rdbtnAlpha.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnHits = new JRadioButton("Hits");
		rdbtnHits.setBounds(330, 21, 66, 23);
		rdbtnHits.setActionCommand("2");
		borderSort.add(rdbtnHits);
		btnGroupSort.add(rdbtnHits);
		rdbtnHits.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnVotes = new JRadioButton("Votes");
		rdbtnVotes.setBounds(168, 51, 66, 23);
		rdbtnVotes.setActionCommand("3");
		borderSort.add(rdbtnVotes);
		btnGroupSort.add(rdbtnVotes);
		rdbtnVotes.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnSubs = new JRadioButton("Subs");
		rdbtnSubs.setBounds(247, 51, 57, 23);
		rdbtnSubs.setActionCommand("4");
		borderSort.add(rdbtnSubs);
		btnGroupSort.add(rdbtnSubs);
		rdbtnSubs.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnRating = new JRadioButton("Rating");
		rdbtnRating.setBounds(330, 51, 69, 23);
		rdbtnRating.setActionCommand("5");
		borderSort.add(rdbtnRating);
		btnGroupSort.add(rdbtnRating);
		rdbtnRating.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnNew = new JRadioButton("New");
		rdbtnNew.setBounds(407, 51, 66, 23);
		rdbtnNew.setActionCommand("6");
		borderSort.add(rdbtnNew);
		btnGroupSort.add(rdbtnNew);
		rdbtnNew.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		borderSearchOptions = new JPanel();
		borderSearchOptions.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		borderSearchOptions.setBounds(10, 287, 486, 55);
		add(borderSearchOptions);
		borderSearchOptions.setLayout(null);
		
		lblSearchOptions = new JLabel("Search Options:");
		lblSearchOptions.setBounds(6, 16, 156, 32);
		borderSearchOptions.add(lblSearchOptions);
		lblSearchOptions.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSearchOptions.setFont(new Font("Aharoni", Font.PLAIN, 16));
		
		rdbtnSearchAll = new JRadioButton("All");
		rdbtnSearchAll.setBounds(168, 21, 66, 23);
		rdbtnSearchAll.setActionCommand("0");
		borderSearchOptions.add(rdbtnSearchAll);
		btnGroupSearch.add(rdbtnSearchAll);
		rdbtnSearchAll.setSelected(true);
		rdbtnSearchAll.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnSearchTitle = new JRadioButton("Title");
		rdbtnSearchTitle.setBounds(247, 21, 66, 23);
		rdbtnSearchTitle.setActionCommand("1");
		borderSearchOptions.add(rdbtnSearchTitle);
		btnGroupSearch.add(rdbtnSearchTitle);
		rdbtnSearchTitle.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		borderFilterOptions = new JPanel();
		borderFilterOptions.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		borderFilterOptions.setBounds(10, 205, 486, 76);
		add(borderFilterOptions);
		borderFilterOptions.setLayout(null);
		
		lblFilterOptions = new JLabel("Filter Options:");
		lblFilterOptions.setBounds(6, 16, 156, 53);
		borderFilterOptions.add(lblFilterOptions);
		lblFilterOptions.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFilterOptions.setFont(new Font("Aharoni", Font.PLAIN, 16));
		
		rdbtnNoFilter = new JRadioButton("None");
		rdbtnNoFilter.setBounds(168, 16, 61, 23);
		rdbtnNoFilter.setActionCommand("0");
		borderFilterOptions.add(rdbtnNoFilter);
		btnGroupFilter.add(rdbtnNoFilter);
		rdbtnNoFilter.setSelected(true);
		rdbtnNoFilter.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnClean = new JRadioButton("Clean");
		rdbtnClean.setBounds(168, 46, 63, 23);
		rdbtnClean.setActionCommand("1");
		borderFilterOptions.add(rdbtnClean);
		btnGroupFilter.add(rdbtnClean);
		rdbtnClean.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnExplicit = new JRadioButton("Explicit");
		rdbtnExplicit.setBounds(247, 46, 73, 23);
		rdbtnExplicit.setActionCommand("2");
		borderFilterOptions.add(rdbtnExplicit);
		btnGroupFilter.add(rdbtnExplicit);
		rdbtnExplicit.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnNoAdult = new JRadioButton("No Adult");
		rdbtnNoAdult.setBounds(247, 16, 85, 23);
		rdbtnNoAdult.setActionCommand("3");
		borderFilterOptions.add(rdbtnNoAdult);
		btnGroupFilter.add(rdbtnNoAdult);
		rdbtnNoAdult.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnNoExplicit = new JRadioButton("No Explicit");
		rdbtnNoExplicit.setBounds(330, 16, 97, 23);
		rdbtnNoExplicit.setActionCommand("4");
		borderFilterOptions.add(rdbtnNoExplicit);
		btnGroupFilter.add(rdbtnNoExplicit);
		rdbtnNoExplicit.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		rdbtnAdult = new JRadioButton("Adult");
		rdbtnAdult.setBounds(330, 46, 61, 23);
		rdbtnAdult.setActionCommand("5");
		borderFilterOptions.add(rdbtnAdult);
		btnGroupFilter.add(rdbtnAdult);
		rdbtnAdult.setFont(new Font("Aharoni", Font.PLAIN, 14));
		
		txtSearchQuery = new JTextField();
		txtSearchQuery.addKeyListener(this);
		txtSearchQuery.setFont(new Font("Aharoni", Font.PLAIN, 14));
		txtSearchQuery.setBounds(176, 11, 320, 32);
		txtSearchQuery.setColumns(10);
		add(txtSearchQuery);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		btnSearch.setFont(new Font("Aharoni", Font.PLAIN, 16));
		btnSearch.setBounds(320, 353, 176, 40);
		add(btnSearch);
		
		resultsScrollPane = new JScrollPane();
		resultsScrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		resultsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		resultsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		resultsScrollPane.setBounds(506, 21, 845, 632);
		add(resultsScrollPane);
		
		resultsTable = new JTable();
		resultsTable.addKeyListener(this);
		resultsTable.setFillsViewportHeight(true);
		resultsTable.setShowVerticalLines(false);
		resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		resultsScrollPane.setViewportView(resultsTable);
		
		lblResultsMessage = new JLabel("");
		lblResultsMessage.setFont(new Font("Aharoni", Font.PLAIN, 14));
		lblResultsMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultsMessage.setBounds(506, 0, 815, 23);
		add(lblResultsMessage);
		
		lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon("blank300x300.jpg"));
		lblImage.setBounds(10, 353, 300, 300);
		lblImage.setBorder(BorderFactory.createLineBorder(Color.black));
		add(lblImage);
		
		btnViewEpisodes = new JButton("View Episodes");
		btnViewEpisodes.setEnabled(false);
		btnViewEpisodes.addActionListener(this);
		btnViewEpisodes.setFont(new Font("Aharoni", Font.PLAIN, 16));
		btnViewEpisodes.setBounds(320, 404, 176, 40);
		add(btnViewEpisodes);
		
		btnSubscribe = new JButton("Subscribe");
		btnSubscribe.setEnabled(false);
		btnSubscribe.addActionListener(this);
		btnSubscribe.setFont(new Font("Aharoni", Font.PLAIN, 16));
		btnSubscribe.setBounds(320, 455, 176, 40);
		add(btnSubscribe);
		
		btnListen = new JButton("Listen");
		btnListen.setEnabled(false);
		btnListen.addActionListener(this);
		btnListen.setFont(new Font("Aharoni", Font.PLAIN, 16));
		btnListen.setBounds(320, 506, 176, 40);
		add(btnListen);
		
		btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(this);
		btnPrevious.setEnabled(false);
		btnPrevious.setBounds(506, 655, 89, 23);
		add(btnPrevious);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(this);
		btnNext.setEnabled(false);
		btnNext.setBounds(1262, 655, 89, 23);
		add(btnNext);
		
		btnDownload = new JButton("Download");
		btnDownload.setEnabled(false);
		btnDownload.addActionListener(this);
		btnDownload.setFont(new Font("Aharoni", Font.PLAIN, 16));
		btnDownload.setBounds(320, 557, 176, 40);
		add(btnDownload);
		
		lblStatusBar = new JLabel("");
		lblStatusBar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStatusBar.setBounds(0, 689, 1061, 23);
		lblStatusBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(lblStatusBar);
		
		lblDownloadStatus = new JLabel("");
		lblDownloadStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDownloadStatus.setBounds(10, 655, 300, 23);
		add(lblDownloadStatus);
		
		lblDownloadsInProgress = new JLabel("");
		lblDownloadsInProgress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDownloadsInProgress.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblDownloadsInProgress.setBounds(1061, 689, 300, 23);
		add(lblDownloadsInProgress);
	}
	
	public JButton getSearchButton(){
		return btnSearch;
	}

	
	/////////////////////////////////////////////////////////////////SEARCH////////////////////////////////////////////////////////////////////
	/**
	 * Perform search query, clear label to blank image, save query to SQLite<br>
	 * @param keywords
	 * @param numResults
	 * @param sort
	 * @param contentFilter
	 * @param searchsource
	 */
	public void search(final int start, final String keywords, final int numResults, final int sort, final int contentFilter, final int searchsource){

		Thread searchThread = new Thread(){
			 public void run() {	
				 try {
						updateStatusBar(false, "Searching...");
						GUIcon.setParameters(keywords, numResults, sort, contentFilter, searchsource, start);
						GUIcon.getFeed().setFolderName("");
						searchHistoryInstert();
						lblImage.setIcon(new ImageIcon("blank300x300.jpg"));
						resultsTable.setModel(setTable(GUIcon.getMessages()));
						updateColumnWidths(resultsTable);
						updateResultLabel(GUIcon.getFeed().getStartIndex(), numResults, GUIcon.getFeed().getTotalResults());
						setPrevNextButtons(GUIcon.getFeed().getStartIndex(), numResults, GUIcon.getFeed().getTotalResults());
						updateStatusBar(true);
						updateButtons(true, false, false, false);
					} catch (Exception e) {
						updateStatusBar(false, "Search was not successful.");
						e.printStackTrace();
					}
			 }
		};
		searchThread.start();

	}
	

	/**
	 * This method is used after initial search when user wants to see episodes within a library<Br>
	 * @param selectedResult
	 */
	public void viewEpisode(final int selectedResult){
		Thread viewEpisodeThread = new Thread(){
			 public void run() {
				 int selectedRow = resultsTable.getSelectedRow();
				 if (selectedRow >= 0){
						try {
							updateStatusBar(false, "Accessing Podcast...");
							String[] colHeaders = {"Title", "SubTitle", "PubDate", "Length"};
							if(DEBUG)System.out.println(GUIcon.getMessages().get(selectedResult).getUrl());
							String folderName = GUIcon.getMessages().get(selectedResult).getTitle(); // Name on actual feed was often wacked out, using name from this feed message and passing to new feed
							
							if (GUIcon.setNewSearch(GUIcon.getMessages().get(selectedResult).getUrl())){
								GUIcon.getFeed().setFolderName(folderName);
								ImageIcon ic = new ImageIcon(new URL(GUIcon.getFeed().getImage()));
								Image im = ic.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
								lblImage.setIcon(new ImageIcon(im));
								resultsTable.setModel(setTable(colHeaders, GUIcon.getMessages()));
								updateColumnWidths(resultsTable);
								updateResultLabel(0, GUIcon.getMessages().size(), GUIcon.getMessages().size());
								setPrevNextButtons(0, GUIcon.getMessages().size(), GUIcon.getMessages().size());
								updateStatusBar(true);
								updateButtons(true, true, true, true);
							} else{
								updateStatusBar(false, "Episode could not be found. URL: " + GUIcon.getMessages().get(selectedResult).getUrl());
							}
						} catch (Exception e) {
							updateStatusBar(false, "Episode could not be found. URL: " + GUIcon.getMessages().get(selectedResult).getUrl());
							if(DEBUG)e.printStackTrace();
						}
					}
			 	}
			 };
			 viewEpisodeThread.start();
		
	}
	
	/**
	 * Download selected podcast<br>
	 */
	private void downloadEpisode(){
		//TODO Make this run in a separate thread, have a progress bar/notification, and kill thread when done.
		if (resultsTable.getSelectedRow() >= 0){
			// Spin up new thread to complete the download
			Thread downloadThread = new Thread(){
				 public void run() {
					 String feedName = GUIcon.getFeed().getFolderName();
					 Boolean newFolder = (new File("PodcastLibrary/" + feedName)).mkdir();
						try {
							URL website;
							String fileSize = "";
							website = new URL(GUIcon.getMessages().get(resultsTable.getSelectedRow()).getUrl());
							String fileName = website.toString().substring(website.toString().lastIndexOf('/')+1, website.toString().length());
							if(DEBUG)System.out.println("Filename: " + fileName);
							if (!GUIcon.getMessages().get(resultsTable.getSelectedRow()).getLength().equals("")){
								double fs = Integer.parseInt((GUIcon.getMessages().get(resultsTable.getSelectedRow()).getLength())) / 1000000.0;
								fileSize = "(" + (Double.toString(fs).substring(0, (Double.toString(fs).indexOf('.') + 3))) + " MB)"; // Get truncated 2 decimal point size of file
							}
							lblDownloadStatus.setText("Download Started.... "+fileSize);
							numDownloads ++;
							lblDownloadsInProgress.setText(numDownloads + " in progress");
							numDownloads = GUIcon.downloadEpisode(website, feedName, fileName, fileSize);
							if (numDownloads == 0){ // remove text from download status if all downloads have finished
								lblDownloadStatus.setText("");
								lblDownloadsInProgress.setText("Downloads finished");
							}else{
								lblDownloadsInProgress.setText(numDownloads + " downloads in progress");
							}
							
						} catch (MalformedURLException e) {
							lblDownloadStatus.setText("Episode could not be found. URL: " + GUIcon.getMessages().get(resultsTable.getSelectedRow()).getUrl());
							if(DEBUG)e.printStackTrace();
						} catch (IOException e) {
							lblDownloadStatus.setText("Error writing file to disk.");
							if(DEBUG)e.printStackTrace();
						}
						
				 }
			};
			downloadThread.start();
			

		}
	}
	
	/**
	 * Update the widths of the column - needs to be updated after each model change.
	 * @param table
	 */
	private void updateColumnWidths(JTable table) {
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++) {
		    column = table.getColumnModel().getColumn(i);
		    if (column.getHeaderValue().toString().equals("Title")) {
		        column.setPreferredWidth(200); //third column is bigger
		    } else if (column.getHeaderValue().toString().equals("Summary")) {
		        column.setPreferredWidth(1000);
		    } else if (column.getHeaderValue().toString().equals("SubTitle")) {
		        column.setPreferredWidth(450);
		    } else if (column.getHeaderValue().toString().equals("PubDate")) {
		    	 column.setPreferredWidth(100);
		    } else if (column.getHeaderValue().toString().equals("Length")) {
		        column.setPreferredWidth(100);
		    }
		}
	}
	
	
	/**
	 * Creates a model for the JTable<br>
	 * If it is the first search, call the overloaded constructor to have search default headers created<Br>
	 * Column headers MUST match the get methods from messages or else the cells in that column will be left blank<br>
	 * @param colHeaders
	 * @param messages
	 * @return
	 */
	public TableModel setTable(final String colHeaders[], final List<RSSFeedMessage> messages){
		Object[][] tempData = new Object[messages.size()][colHeaders.length];
		
			for (int m = 0; m < messages.size(); m++){
				for(int i = 0; i < colHeaders.length; i++){
					switch (colHeaders[i].toLowerCase()){
					case "title":
					    tempData[m][i] = messages.get(m).getTitle();
					    break;
					case "description":
						JLabel lblDescription = new JLabel(messages.get(m).getSummary());
						lblDescription.setToolTipText(messages.get(m).getSummary());
					    tempData[m][i] = lblDescription.getText();
					    break;
					case "link":
					    tempData[m][i] = messages.get(m).getLink();
					    break;
					case "source":
					    tempData[m][i] = messages.get(m).getSource();
					    break;
					case "comments":
					    tempData[m][i] = messages.get(m).getComments();
					    break;
					case "pubdate":
					    tempData[m][i] = messages.get(m).getPubDate();
					    break;
					case "guid":
					    tempData[m][i] = messages.get(m).getGuid();
					    break;
					case "author":
					    tempData[m][i] = messages.get(m).getAuthor();
					    break;
					case "url":
					    tempData[m][i] = messages.get(m).getUrl();
					    break;
					case "length":
					    tempData[m][i] = messages.get(m).getLength();
					    break;
					case "summary":
						tempData[m][i] = messages.get(m).getSummary();
						break;
					case "subtitle":
						tempData[m][i] = messages.get(m).getSubtitle();
						break;
					case "type":
					    tempData[m][i] = messages.get(m).getType();
						
					}
				}
			}
			final Object[][] data = tempData; // Abstract class requires final variables
		
		dataModel = new AbstractTableModel() {
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				return data[rowIndex][columnIndex];
			}
			@Override
			public int getRowCount() {
				return messages.size();
			}
			@Override
			public int getColumnCount() {
				return colHeaders.length;
			}
			@Override
			public String getColumnName(int col){
				return colHeaders[col];
			}
		};///Close model
		return dataModel;
	}
	/**
	 * Set default table model with 'Title' and 'Summary' column headers<br>
	 * @param messages
	 * @return
	 */
	public TableModel setTable(final List<RSSFeedMessage> messages){
		String[] colHeader = {"Title", "Summary"};
		return setTable(colHeader, messages);
	}
	
	///////////////////////////////////////////////////////////STATUS BAR////////////////////////////////////////////////////////////////////////////
	/**
	 * If URL could not be found, call method with URL included<br>
	 * @param b
	 * @param url
	 */
	private void updateStatusBar(boolean b, String Text) {
		if (!b){
			lblStatusBar.setText(Text);
		}
		else{
			lblStatusBar.setText("");
		}
	}
	
	/**
	 * Reset status bar if request is successful<br>
	 * @param b
	 */
	private void updateStatusBar(boolean b){
		updateStatusBar(b, "");
	}
	///////////////////////////////////////////////////////////////BUTTONS AND SEARCH PAGINATION (and status of pagination)///////////////////////////////////////////////////////
	/**
	 * This method enables and disables buttons depending on state of search and program<br>
	 * This relies on calls from other events, such as those happening from action listener<br>
	 * @param viewEpisode
	 * @param subscribe
	 * @param listen
	 * @param download
	 */
	private void updateButtons(boolean viewEpisode, boolean subscribe, boolean listen, boolean download){
		btnViewEpisodes.setEnabled(viewEpisode);
		btnSubscribe.setEnabled(subscribe);
		btnListen.setEnabled(listen);
		btnDownload.setEnabled(download);
	}
	
	/**
	 * Enable and disable previous and next buttons<Br>
	 * @param start
	 * @param numResults
	 * @param totalResults
	 */
	private void setPrevNextButtons(int start, int numResults, int totalResults){
	       //See if there are more results than can fit on one page
        if(totalResults > numResults){
            if(start == 0){//see if first page, if so disable prev button
                btnPrevious.setEnabled(false);
                btnNext.setEnabled(true);
            }
            else if((start + numResults) >= totalResults){ //see if on last page, if so disable next button
                btnPrevious.setEnabled(true);
                btnNext.setEnabled(false);
            }
            else{ // not first or last page, enable both buttons
                btnPrevious.setEnabled(true);
                btnNext.setEnabled(true);
            }
        }
        else{ // only one page of results, fit on one page
            btnPrevious.setEnabled(false);
            btnNext.setEnabled(false);
        }
    }
	
	/**
	 * Set buttons for pagination on first search query<br>
	 * @param start
	 * @param numResults
	 * @param totalResults
	 */
	private void setPrevNextButtons(String start, int numResults, String totalResults){
		setPrevNextButtons(Integer.parseInt(start), numResults, Integer.parseInt(totalResults));
	}
	/**
	 * Update label showing how many results returned and current set showing<br>
	 * @param start
	 * @param numResults
	 * @param totalResults
	 */
	private void updateResultLabel(int start, int numResults, int totalResults){
		if (numResults >= totalResults){
			numResults = totalResults;
		}
		String resultText = "Results:"+ (start+1) +" - "+ (start+1 + numResults) +" out of " + (totalResults+1);
        lblResultsMessage.setText(resultText);
	}
	/**
	 * USE THIS FOR FAILED URL<br>
	 * @param url
	 */
	private void updateResultLabel(String url){
		String resultText = "URL: "+ url +" failed to open.";
        lblResultsMessage.setText(resultText);
	}
	
	private void updateResultLabel(String start, String numResults, String totalResults){
		updateResultLabel(Integer.parseInt(start), Integer.parseInt(numResults), Integer.parseInt(totalResults));
	}
	private void updateResultLabel(String start, int numResults, String totalResults){
		updateResultLabel(Integer.parseInt(start), numResults, Integer.parseInt(totalResults));
	}
	
	//////////////////////////////////////////////////DATABASE REQUESTS///////////////////////////////////////////////////////////////////
	
	private void searchHistoryInstert(){
		int rowsReturned = GUIcon.createSearchInsert(keywords, GUIcon.getFeed());
		if(DEBUG) System.out.println("Rows Returned: " + rowsReturned);
	}
	
	
	
	
	
	
	
	///////////////////////////////////////////////////ACTION LISTENER//////////////////////////////////////////////////////////////////////
	/**
	 * Action Listener for all buttons on search panel<br>
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch){ // Search button actions
			keywords = txtSearchQuery.getText();
			if (keywords.length() <= 3){
				updateStatusBar(false, "Please enter at least 3 characters to search.");
			}
			else{
				
				updateStatusBar(true);
				start = 0;
				numResults = Integer.parseInt(btnGroupNumResults.getSelection().getActionCommand());
				searchsource = Integer.parseInt(btnGroupSearch.getSelection().getActionCommand());
				sort = Integer.parseInt(btnGroupSort.getSelection().getActionCommand());
				contentFilter = Integer.parseInt(btnGroupFilter.getSelection().getActionCommand());
				search(start, keywords, numResults, sort, contentFilter, searchsource);
			}
		}
		else if (e.getSource() == btnNext){ // Next button actions
			start += 50;
			search(start, keywords, numResults, sort, contentFilter, searchsource);
		}
		else if (e.getSource() == btnPrevious){ // Previous buton actions
			start -= 50;
			if (start < 0){
				start = 0;
			}
			search(start, keywords, numResults, sort, contentFilter, searchsource);
		}
		else if (e.getSource() == btnViewEpisodes){ // User has selected an entry
			int selectedResult = resultsTable.getSelectedRow();
			if (selectedResult >= 0){
				
				viewEpisode(selectedResult);
			}
		}
		else if (e.getSource() == btnDownload){
			downloadEpisode();
		}

	}

	
	//////////////////////////////////////////////////////////////////////////KEY PRESSED LISTENER////////////////////////////////////////////////////////////////
	/**
	 * 
	 */
	public void keyPressed(KeyEvent e) {
		
		if(DEBUG) System.out.println(e.getKeyCode());
		//if (e.getKeyCode() == 32 || e.getKeyCode() == 10){ // Check for space bar or enter key - does same as btnViewEpisodes
			//int selectedResult = resultsTable.getSelectedRow();
			//if (selectedResult >= 0){
			//	viewEpisode(selectedResult);
			//}
		//}
	}
	public void keyReleased(KeyEvent e) {
		
	}
	public void keyTyped(KeyEvent e) {
	}
}

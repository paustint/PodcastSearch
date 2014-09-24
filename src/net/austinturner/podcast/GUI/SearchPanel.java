package net.austinturner.podcast.GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

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
import javax.swing.JLayeredPane;

import java.awt.CardLayout;

import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchPanel extends JPanel implements ActionListener {
	private JLabel lblKeywordSearch;
	private JLabel lblNumberOfResults;
	private JLabel lblSort;
	private JLabel lblSearchOptions;
	private JLabel lblFilterOptions;
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
	private JButton btnSearch;
	
	private String APIKey;
	private JPanel panel;
	private ResultPanel resultPanel;
	private GUIconnector GUIcon;
	
	/**
	 * Create the panel.
	 */
	public SearchPanel(String APIKey) {
		this.APIKey = APIKey;
		setBounds(10, 50, 1331, 591);
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
		txtSearchQuery.setFont(new Font("Aharoni", Font.PLAIN, 14));
		txtSearchQuery.setBounds(176, 11, 320, 32);
		txtSearchQuery.setColumns(10);
		add(txtSearchQuery);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		btnSearch.setFont(new Font("Aharoni", Font.PLAIN, 16));
		btnSearch.setBounds(365, 353, 131, 40);
		add(btnSearch);
		
		resultPanel = new ResultPanel();
		resultPanel.setBounds(506, 11, 815, 569);
		add(resultPanel);
	}
	/**
	 * Perform search query and instantiate the result panel
	 * @param keywords
	 * @param numResults
	 * @param sort
	 * @param contentFilter
	 * @param searchsource
	 */
	public void search(String keywords, int numResults, int sort, int contentFilter, int searchsource){
		try {
			int start = 0;
			GUIcon = new GUIconnector(APIKey);
			GUIcon.setParameters(keywords, numResults, sort, contentFilter, searchsource, start);
			resultPanel.setSearchResults(GUIcon.getFeed(), GUIcon.getMessages(), GUIcon);
			resultPanel.setBounds(506, 11, 815, 569);
			resultPanel.repaint();
			repaint();
		} catch (Exception e) {
			System.out.println("EXCEPTION resultspanel search()");
			e.printStackTrace();
		}

	}
	//Action listener for search button
	public void actionPerformed(ActionEvent e) {
		String keywords = txtSearchQuery.getText();
		int numResults = Integer.parseInt(btnGroupNumResults.getSelection().getActionCommand());
		int searchsource = Integer.parseInt(btnGroupSearch.getSelection().getActionCommand());
		int sort = Integer.parseInt(btnGroupSort.getSelection().getActionCommand());
		int contentFilter = Integer.parseInt(btnGroupFilter.getSelection().getActionCommand());
		this.search(keywords, numResults, sort, contentFilter, searchsource);
		
		
	}
}

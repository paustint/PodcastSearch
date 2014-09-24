/**
 * This panel is dynamically generated with the results from a search query
 * 
 */
package net.austinturner.podcast.GUI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;

import net.austinturner.podcast.RSS.RSSFeed;
import net.austinturner.podcast.RSS.RSSFeedMessage;

import javax.swing.JList;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

public class ResultPanel extends JPanel implements ActionListener {
	private JLabel lblNewLabel;
	private BufferedImage image; // Consider creating array of images from result
	private BufferedImage image2;
	private int x = 0;
	private int y = 0;
	private JScrollPane scrollPane;
	private JList resultsList; // for some reason methods in class were not available from outside class
	private JPanel pnlPrevNext;
	private JButton btnSelect;
	private JScrollPane searchPane;
	private JButton btnPrev;
	private JLabel lblResultInformation;
	private JButton btnNext;
	
	private int totalResults;
	private int startIndex;
	private int itemsPerPage;
	private GUIconnector GUIcon;
	
	/**
	 * Create the panel.
	 * isInitialSearch represents a search that does not have all the components, as it links to other queries
	 * 
	 * @param isInitialSearch
	 * @param resultNum
	 * @throws IOException 
	 */
	public ResultPanel()  {
		setLayout(new BorderLayout(0, 0));
		
		resultsList = new JList();
		//resultsList.setCellRenderer(new RSSFeedRenderer(feed));
		resultsList.setVisibleRowCount(4);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setFont(new Font("Aharoni", Font.PLAIN, 13));
		resultsList.setBorder(new LineBorder(new Color(0, 0, 0)));
		searchPane = new JScrollPane(resultsList);
		add(searchPane, BorderLayout.CENTER);
		
		
		pnlPrevNext = new JPanel();
		add(pnlPrevNext, BorderLayout.NORTH);
		GridBagLayout gbl_pnlPrevNext = new GridBagLayout();
		gbl_pnlPrevNext.columnWidths = new int[] {0, 100, 590, 100};
		gbl_pnlPrevNext.rowHeights = new int[]{23, 0};
		gbl_pnlPrevNext.columnWeights = new double[]{0.0};
		gbl_pnlPrevNext.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlPrevNext.setLayout(gbl_pnlPrevNext);
		
		btnPrev = new JButton("Prev");
		btnPrev.setFont(new Font("Aharoni", Font.PLAIN, 14));
		btnPrev.setEnabled(false);
		btnPrev.addActionListener(this);
		GridBagConstraints gbc_btnPrev = new GridBagConstraints();
		gbc_btnPrev.insets = new Insets(0, 0, 0, 5);
		gbc_btnPrev.gridx = 1;
		gbc_btnPrev.gridy = 0;
		pnlPrevNext.add(btnPrev, gbc_btnPrev);
		
		lblResultInformation = new JLabel("");
		lblResultInformation.setFont(new Font("Aharoni", Font.PLAIN, 14));
		GridBagConstraints gbc_lblResultInformation = new GridBagConstraints();
		gbc_lblResultInformation.insets = new Insets(0, 0, 0, 5);
		gbc_lblResultInformation.gridx = 2;
		gbc_lblResultInformation.gridy = 0;
		pnlPrevNext.add(lblResultInformation, gbc_lblResultInformation);
		
		btnNext = new JButton("Next");
		btnNext.setFont(new Font("Aharoni", Font.PLAIN, 14));
		btnNext.setEnabled(false);
		btnNext.addActionListener(this);
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.anchor = GridBagConstraints.WEST;
		gbc_btnNext.gridx = 3;
		gbc_btnNext.gridy = 0;
		pnlPrevNext.add(btnNext, gbc_btnNext);
		
		btnSelect = new JButton("Select");
		btnSelect.addActionListener(this);
		add(btnSelect, BorderLayout.SOUTH);
		
		//lblNewLabel = new JLabel();
		//lblNewLabel.setBounds(128, 5, 321, 206);
		//lblNewLabel.setIcon((Icon) image);
		//add(lblNewLabel);
		
	}
	
	public JList getJList(){
		return resultsList;
	}

	/**
	 * This is called to update search results and RSS query after initial search<br>
	 * This method does not handle scrolling through results that do not appear on one page, use setSearchResults for pagination<Br>
	 * @param feed
	 * @param messages
	 */
	public void setResults(RSSFeed feed, List<RSSFeedMessage> messages){
		//System.out.println(feed);
		DefaultListModel model = new DefaultListModel();
		
		for (RSSFeedMessage m : messages){
			model.addElement(m);
		}
		resultsList.setModel(model);
		resultsList.setCellRenderer(new RSSFeedRenderer(feed));
		resultsList.setVisibleRowCount(4);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setFont(new Font("Aharoni", Font.PLAIN, 13));
		resultsList.setBorder(new LineBorder(new Color(0, 0, 0)));
		resultsList.repaint();
		searchPane.repaint();
		add(searchPane, BorderLayout.CENTER);
	}
	/**
	 * If Search from Digital Podcast Service result, send in number of results, results per page, and start index.<br>
	 * @param totalResults
	 * @param startIndex
	 * @param itemsPerPage
	 * @param feed
	 * @param messages
	 */
	public void setSearchResults(RSSFeed feed, List<RSSFeedMessage> messages, GUIconnector GUIcon){
		this.GUIcon = GUIcon; //set GUIcon for use in class because it will need to update start parameter 
	    totalResults = Integer.parseInt(feed.getTotalResults());
	    startIndex = Integer.parseInt(feed.getStartIndex());
	    itemsPerPage = Integer.parseInt(feed.getItemsPerPage());
		updatePrevNextPanel();
        this.setResults(feed, messages);
	}
	/**
	 * Update the previous/next buttons and text
	 * @param totalResults
	 * @param startIndex
	 * @param itemsPerPage
	 */
	private void updatePrevNextPanel(){
		updateResultText(); // update text
		//See if there are more results than can fit on one page
		if(totalResults > itemsPerPage){
			if(startIndex == 0){//see if first page, if so disable prev button
				btnPrev.setEnabled(false);
				btnNext.setEnabled(true);
			}
			else if((startIndex + itemsPerPage) >= totalResults){ //see if on last page, if so disable next button
				btnPrev.setEnabled(true);
				btnNext.setEnabled(false);
			}
			else{ // not first or last page, enable both buttons
				btnPrev.setEnabled(true);
				btnNext.setEnabled(true);
			}
		}
		else{ // only one page of results, fit on one page
			btnPrev.setEnabled(false);
			btnNext.setEnabled(false);
		}
	}
	
	/**
	 * Update result text
	 */
	private void updateResultText(){
		String resultText = "Results:"+ (startIndex) +" - "+ (startIndex + itemsPerPage) +" out of " + totalResults;
		lblResultInformation.setText(resultText);
	}

	/**
	 * Action Listenr for all three buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnPrev){//User clicked previous, get new query and update list
			GUIcon.setStart(startIndex-itemsPerPage);
			setSearchResults(GUIcon.getFeed(), GUIcon.getMessages(), GUIcon);
		} else if (e.getSource() == btnNext){// user clicked next, get new query and update list
			GUIcon.setStart(startIndex+itemsPerPage);
			setSearchResults(GUIcon.getFeed(), GUIcon.getMessages(), GUIcon);
		}
		else if (e.getSource() == btnSelect){
			
		}
		
	}
	

}







/**
 * Create ListCellRenderer to display each result properly<br>
 * Reference:<br>
 * http://www.java2s.com/Code/Java/Swing-JFC/UseJListcomponenttodisplaycustomobjectswithListCellRenderer.htm<br>
 * @author Austin
 *
 */
class RSSFeedRenderer extends JLabel implements ListCellRenderer{
	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
	RSSFeed feed;
	public RSSFeedRenderer(RSSFeed feed) {
		setOpaque(true);
		setIconTextGap(15);
		this.feed = feed;
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		RSSFeedMessage message = (RSSFeedMessage) value;
		setText(message.getMessageText());
		ImageIcon icon = getImage();
		if (icon != null){
			setIcon(icon);
		}
	    if (isSelected) {
	        setBackground(HIGHLIGHT_COLOR);
	        setForeground(Color.white);
	      } else {
	        setBackground(Color.white);
	        setForeground(Color.black);
	      }
	      return this;
	}
	
	private ImageIcon getImage(){
		try {
			if (feed.getImage().length() > 5){
				ImageIcon image = new ImageIcon(new URL(feed.getImage()));
				Image scaledImage = image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
				return new ImageIcon(scaledImage);
			}
			else{
				return null;
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}

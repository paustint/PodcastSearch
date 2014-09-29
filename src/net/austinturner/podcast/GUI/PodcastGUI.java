package net.austinturner.podcast.GUI;

import java.awt.EventQueue;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;

import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Font;
import java.awt.Component;

import javax.swing.Box;

import net.austinturner.podcast.GUI.helper.GUIconnector;

import java.awt.Dimension;


public class PodcastGUI {

	private JFrame frame;
	private JFrame errorFrame;
	
	private SearchPanel searchPanel;
	private DownloadPanel downloadPanel;
	private LibraryPanel libraryPanel;
	private SubscriptionPanel subscriptionPanel;
	
	private JTabbedPane tabbedPane;
	private GUIconnector GUIcon;
	private ExecutorService  pool = Executors.newFixedThreadPool(3);
	
	private boolean DEBUG = true;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu meuTools;
	private JMenuItem menuItemPrefs;
	private JMenuItem menuItemExit;
	private Component rigidArea;
	private Component rigidArea_1;
	private JMenuItem menuItemSearchHistory;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		String APIkey = args[0];
		
		
		final String APIKey = APIkey;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PodcastGUI window = new PodcastGUI(APIKey);
					window.frame.setVisible(true);
					//TODO fire off to tell subscriptions to update (based on settings) and update library tree to update
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PodcastGUI(String APIKey) {
		initialize(APIKey);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String APIKey) {
		//TODO - should each panel have it's own thread?
		try {
			GUIcon = new GUIconnector(APIKey, pool); // This is the class that throws the exception
			frame = new JFrame();
			frame.setBounds(100, 100, 1377, 810);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);

			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setSelectedIndex(-1);
			tabbedPane.setBounds(0, 11, 1358, 740);
			frame.getContentPane().add(tabbedPane);
			
			searchPanel = new SearchPanel(GUIcon);
			tabbedPane.addTab("Search", null, searchPanel, null);
			frame.getRootPane().setDefaultButton(searchPanel.getSearchButton()); // allows enter button to be mapped to search button
			
			subscriptionPanel = new SubscriptionPanel(GUIcon);
			tabbedPane.addTab("Subscriptions", null, subscriptionPanel, null);
			
			downloadPanel = new DownloadPanel(GUIcon);
			tabbedPane.addTab("Downloads", null, downloadPanel, null);
			
			libraryPanel = new LibraryPanel(GUIcon);
			tabbedPane.addTab("Library", null, libraryPanel, null);
			
			menuBar = new JMenuBar();
			menuBar.setFont(new Font("Aharoni", Font.PLAIN, 14));
			frame.setJMenuBar(menuBar);
			
			menuFile = new JMenu("File");
			menuFile.setFont(new Font("Aharoni", Font.PLAIN, 14));
			menuBar.add(menuFile);
			
			menuItemExit = new JMenuItem("Exit");
			menuItemExit.setFont(new Font("Aharoni", Font.PLAIN, 14));
			menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
			menuFile.add(menuItemExit);
			
			rigidArea = Box.createRigidArea(new Dimension(8, 12));
			menuBar.add(rigidArea);
			
			meuTools = new JMenu("Tools");
			meuTools.setFont(new Font("Aharoni", Font.PLAIN, 14));
			menuBar.add(meuTools);
			
			menuItemPrefs = new JMenuItem("Preferences");
			menuItemPrefs.setFont(new Font("Aharoni", Font.PLAIN, 14));
			menuItemPrefs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
			meuTools.add(menuItemPrefs);
			
			menuItemSearchHistory = new JMenuItem("Search History");
			menuItemSearchHistory.setFont(new Font("Aharoni", Font.PLAIN, 14));
			menuItemSearchHistory.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
			meuTools.add(menuItemSearchHistory);
			
			rigidArea_1 = Box.createRigidArea(new Dimension(8, 12));
			menuBar.add(rigidArea_1);
			
			
		} catch (Exception e) {
			errorFrame = new JFrame();
			JOptionPane.showMessageDialog(errorFrame, "API could not be connected to.", "Could not connect", JOptionPane.ERROR_MESSAGE, null);
			if(DEBUG) e.printStackTrace();
		}
	}
}

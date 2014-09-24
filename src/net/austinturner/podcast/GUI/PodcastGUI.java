package net.austinturner.podcast.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class PodcastGUI {

	private JFrame frame;
	private JPanel searchPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		String APIkey = "";
		
		try {
			FileReader fr = new FileReader("api.key");
			BufferedReader APIreader = new BufferedReader(fr);
			APIkey = APIreader.readLine();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final String APIKey = APIkey;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PodcastGUI window = new PodcastGUI(APIKey);
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 1367, 691);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		searchPanel = new SearchPanel(APIKey);
		frame.getContentPane().add(searchPanel);

	}
}

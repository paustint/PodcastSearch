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
		final String APIKey = args[0];
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

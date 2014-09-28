package net.austinturner.podcast.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import net.austinturner.podcast.GUI.helper.GUIconnector;

public class DownloadPanel extends JPanel {

	private JPanel contentPane;
	private GUIconnector GUIcon;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel lblDownloadHistory;


	public DownloadPanel(GUIconnector GUIcon) {
		this.GUIcon = GUIcon;
		setBounds(10, 50, 1361, 711);
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 50, 1341, 650);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		lblDownloadHistory = new JLabel("Download History");
		lblDownloadHistory.setHorizontalAlignment(SwingConstants.CENTER);
		lblDownloadHistory.setFont(new Font("Aharoni", Font.PLAIN, 18));
		lblDownloadHistory.setBounds(10, 11, 1341, 28);
		add(lblDownloadHistory);
		
		//TODO run DB query to get download history and update table model
	}
	
	public void addDownload(long id){
		
	}
	public void updateDownloadStatus(long id, String status){
		
	}
	
}

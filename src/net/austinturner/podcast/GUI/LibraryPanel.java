package net.austinturner.podcast.GUI;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.LineBorder;

import net.austinturner.podcast.GUI.helper.GUIconnector;

import java.awt.Color;

public class LibraryPanel extends JPanel {

	private GUIconnector GUIcon;
	private JLabel lblLibrary;
	private JTree tree;

	public LibraryPanel(GUIconnector GUIcon) {
		this.GUIcon = GUIcon;
		setBounds(10, 50, 1361, 711);
		setLayout(null);
		
		lblLibrary = new JLabel("Library");
		lblLibrary.setHorizontalAlignment(SwingConstants.CENTER);
		lblLibrary.setFont(new Font("Aharoni", Font.PLAIN, 18));
		lblLibrary.setBounds(10, 11, 698, 28);
		add(lblLibrary);
		
		tree = new JTree();
		tree.setBorder(new LineBorder(new Color(0, 0, 0)));
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("PodCastSearch") {
				{
				}
			}
		));
		tree.setBounds(20, 42, 688, 658);
		add(tree);
	}
}

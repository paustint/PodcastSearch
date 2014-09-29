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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;

import net.austinturner.podcast.GUI.helper.AudioPlayer;
import net.austinturner.podcast.GUI.helper.GUIconnector;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class LibraryPanel extends JPanel implements ActionListener{

	private GUIconnector GUIcon;
	private JLabel lblLibrary;
	private JTree tree;
	private JScrollPane scrollPane;
	private FileSystemView fileSystemView;
	private final boolean DEBUG = false;
	private File selectedFile;
	private JButton btnListen;
	private AudioPlayer audio;
	
	public LibraryPanel(GUIconnector GUIcon) {
		this.GUIcon = GUIcon;
		setBounds(10, 50, 1361, 711);
		setLayout(null);
		
		lblLibrary = new JLabel("Library");
		lblLibrary.setHorizontalAlignment(SwingConstants.CENTER);
		lblLibrary.setFont(new Font("Aharoni", Font.PLAIN, 18));
		lblLibrary.setBounds(10, 11, 698, 28);
		add(lblLibrary);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 42, 688, 658);
		add(scrollPane);
		
		File rootDir = new File("PodcastLibrary");
		tree = new JTree(addNodes(null, rootDir));
		scrollPane.setViewportView(tree);
		tree.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		btnListen = new JButton("Listen");
		btnListen.addActionListener(this);
		btnListen.setFont(new Font("Aharoni", Font.PLAIN, 14));
		btnListen.setBounds(718, 41, 89, 23);
		add(btnListen);
		
		audio = new AudioPlayer();

	    // Add a listener
	    tree.addTreeSelectionListener(new TreeSelectionListener() {
	      public void valueChanged(TreeSelectionEvent e) {
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getPath()[0];
	        TreePath selectedItem = e.getPath();
	        String filePath = "";
	        boolean firstPass = true;
	        for (int i = 0; i<selectedItem.getPathCount(); i++){
	        	if (firstPass){
	        		firstPass = false;
	        		filePath += selectedItem.getPathComponent(i);
	        	} else{
	        		filePath += "/" + selectedItem.getPathComponent(i);
	        	}	
	        }
	        if(DEBUG) System.out.println(filePath);
	        selectedFile = new File(filePath);
	      }
	    });
	    
	    
	}
	


	////////////////////////////Crete table structure//////////////////////////////////////////////////////////////
	  /** Add nodes from under "dir" into curTop. Highly recursive. 
	   * http://www.java2s.com/Code/Java/Swing-JFC/DisplayafilesysteminaJTreeview.htm
	   * 
	   * */
	  DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
	    String curPath = dir.getName();
	    DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
	    if (curTop != null) { // should only be null at root
	      curTop.add(curDir);
	    }
	    Vector ol = new Vector();
	    String[] tmp = dir.list();
	    for (int i = 0; i < tmp.length; i++)
	      ol.addElement(tmp[i]);
	    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
	    File f;
	    Vector files = new Vector();
	    // Make two passes, one for Dirs and one for Files. This is #1.
	    for (int i = 0; i < ol.size(); i++) {
	      String thisObject = (String) ol.elementAt(i);
	      String newPath;
	      if (curPath.equals("."))
	        newPath = thisObject;
	      else
	        newPath = curPath + File.separator + thisObject;
	      if ((f = new File(newPath)).isDirectory())
	        addNodes(curDir, f);
	      else
	        files.addElement(thisObject);
	    }
	    // Pass two: for files.
	    for (int fnum = 0; fnum < files.size(); fnum++)
	      curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
	    return curDir;
	  }
	  
		///////////////////////////////////////////////////ACTION LISTENER//////////////////////////////////////////////////////////////////////
		/**
		* Action Listener for all buttons on search panel<br>
		*/
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnListen){
				if(selectedFile != null){
					audio.listen(selectedFile);
				}
				
			}
		}
}


// I was using this previously, but node always showed the entire path in the name - which I did not want
///**
//* 
//* @author Austin
//* Source code obtained from: http://java.dzone.com/news/taking-new-swing-tree-table-a-
//*
//*/
//private static class FileTreeModel implements TreeModel {
//	 
//	private File root;
//	 
//	public FileTreeModel(File root) {
//	this.root = root;
//	}
//	 
//	@Override
//	public void addTreeModelListener(javax.swing.event.TreeModelListener l) {
//		//do nothing
//	}
//
//	@Override
//	public Object getChild(Object parent, int index) {
//		File f = (File) parent;
//		return f.listFiles()[index];
//	}
//
//	@Override
//	public int getChildCount(Object parent) {
//		File f = (File) parent;
//		if (!f.isDirectory()) {
//			return 0;
//		} else {
//			return f.list().length;
//		}
//	}
//
//	@Override
//	public int getIndexOfChild(Object parent, Object child) {
//		File par = (File) parent;
//		File ch = (File) child;
//		return Arrays.asList(par.listFiles()).indexOf(ch);
//	}
//
//	@Override
//	public Object getRoot() {
//		return root;
//	}
//
//	@Override
//	public boolean isLeaf(Object node) {
//		File f = (File) node;
//		return !f.isDirectory();
//	}
//
//	@Override
//	public void removeTreeModelListener(javax.swing.event.TreeModelListener l) {
//		//do nothing
//	}
//
//	@Override
//	public void valueForPathChanged(javax.swing.tree.TreePath path, Object newValue) {
//		//do nothing
//	}
//
//}


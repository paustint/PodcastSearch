package net.austinturner.podcast.GUI.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadTask implements Runnable{

	private String threadName;
	private Thread t;
	private boolean activeTask = true;
	private final String[] CURR_STATE = {"Initialized", "Download Pending", "Download Started", "Download Finished", "Download Failed"};
	private String currentState = CURR_STATE[0];
	private URL website;
	private String feedName;
	private String fileName;
	private String fileSize;
	
	private final boolean DEBUG = true;
	
	public DownloadTask(URL website, String feedName, String fileName, String fileSize) {
		this.website = website;
		this.feedName = feedName;
		this.fileName = fileName;
		this.fileSize = fileSize;
	}

	@Override
	public void run() {
		startDownload();
	}
	
	public boolean startDownload() {
		Boolean newFolder = (new File("PodcastLibrary/" + feedName)).mkdir();
			
			try {
				if(DEBUG) System.out.println("Starting download ID: " + threadName);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream("PodcastLibrary/" + feedName + "/"+ fileName);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				if(DEBUG) System.out.println("Success");
				return true;
			} catch (IOException e) {
				if(DEBUG) System.out.println("fail");
				if(DEBUG) e.printStackTrace();
				return false;
			}

	}

}

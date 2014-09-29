package net.austinturner.podcast.GUI.helper;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
	
	private Clip clip;
	private final boolean DEBUG = true;
	
	public AudioPlayer(){
		
	}
	
	public void listen(File file){
		try{
			if(DEBUG) System.out.println("File Exists?: " + file.exists());
		      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		     clip = AudioSystem.getClip();
		     clip.open(audioInputStream);
		     clip.start( );
		    }
		   catch(Exception ex)
		   {  
			   if(DEBUG) ex.printStackTrace();
		   }
	}
	
	public void stop(){
		clip.stop();
	}

}

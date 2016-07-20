package audio;

import jaco.mp3.player.MP3Player;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

public class AudioPlayerTester {
	
	public void playAudio() {
		
	}
	

	public static void main(String[] args) {
		Audio.setMasterOutputVolume(0.1f);
		MP3Player player = new MP3Player(new File("res/test.mp3"));
		setSoundVolume(0.1f);
		player.play();
	}
	
	public static void setSoundVolume(float volume) {
		Mixer.Info [] mixers = AudioSystem.getMixerInfo();  
		System.out.println(mixers.length + " Mixer Info Objekte");  
		for (Mixer.Info mixerInfo : mixers)  
		{  
		    System.out.println("Mixer name: " + mixerInfo.getName());  
		    Mixer mixer = AudioSystem.getMixer(mixerInfo);  
		    Line.Info [] lineInfos = mixer.getTargetLineInfo();
		    for (Line.Info lineInfo : lineInfos)  
		    {  
		        System.out.println("  Line.Info: " + lineInfo);  
		        Line line = null;  
		        boolean opened = true;  
		        try  
		        {  
		            line = mixer.getLine(lineInfo);  
		            opened = line.isOpen() || line instanceof Clip;  
		            if (!opened)  
		            {  
		                line.open();  
		            }  
		            FloatControl volCtrl = (FloatControl)line.getControl(FloatControl.Type.VOLUME);  
		            volCtrl.setValue(volume);
		            System.out.println("    volCtrl.getValue() = " + volCtrl.getValue());  
		        }  
		        catch (LineUnavailableException e)  
		        {  
		            e.printStackTrace();  
		        }  
		        catch (IllegalArgumentException iaEx)  
		        {  
		            System.out.println("    " + iaEx);  
		        }  
		        finally  
		        {  
		            if (line != null && !opened)  
		            {  
		                line.close();  
		            }  
		        }  
		    }  
		} 
	}

}

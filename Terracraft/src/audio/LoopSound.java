package audio;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class LoopSound {

	public static void main(String[] args) throws Exception {
		System.out.println(AudioSystem.getAudioFileTypes());
		URL url = new URL("http://jalako.tk/stressed_out.wav");
		File file = new File("res/stressed_out.wav");
		Clip clip = AudioSystem.getClip();
		// getAudioInputStream() also accepts a File or InputStream
		AudioInputStream ais = AudioSystem.getAudioInputStream(file);
		clip.open(ais);
		clip.loop(-1);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// A GUI element to prevent the Clip's daemon Thread
				// from terminating at the end of the main()
				JOptionPane.showMessageDialog(null, "Close to exit!");
			}
		});
	}
}
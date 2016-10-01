package audio;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import terracraft.Game;

public class SoundManager {

	private Clip[] clipList = new Clip[10];
	private Sound[] soundList = new Sound[10];
	private FloatControl[] gainControl = new FloatControl[10];

	public void playSound(int id) {
		try {
			soundList[id] = new Sound(id);
			clipList[id] = AudioSystem.getClip();
			clipList[id].open(AudioSystem.getAudioInputStream(new File(soundList[id].getPath())));
			gainControl[id] = (FloatControl) clipList[id].getControl(FloatControl.Type.MASTER_GAIN);
			// gainControl[id].setValue(soundList[id].getVolume());
			if (soundList[id].volid == 0) {
				gainControl[id].setValue(((Game.menu.settings.musicvol_slider.ex / 2) - 140 - Game.menu.settings.musicvol_slider.ex / 8 - 40));
			}
			if (soundList[id].volid == 1) {
				gainControl[id].setValue(((Game.menu.settings.soundvol_slider.ex / 2) - 140 - Game.menu.settings.soundvol_slider.ex / 8 - 40));
			}
			clipList[id].start();
			for (SoundID s : SoundID.values()) {
				if (id == s.getID()) {
					s.setRunning();
				}
			}

		} catch (Exception e) {
		}
	}

	public void stopSound(int id) {
		try {
			clipList[id].stop();
			for (SoundID s : SoundID.values()) {
				if (id == s.getID()) {
					s.stopRunning();
				}
			}
		} catch (Exception ex) {
		}
	}

	private class Sound {

		private String path;
		private int vol, volid;

		public Sound(int id) {
			for (SoundID s : SoundID.values()) {
				if (id == s.getID()) {
					this.path = s.getPath();
					this.vol = s.getVolume();
					this.volid = s.getVolId();

				}
			}
		}

		public String getPath() {
			return this.path;
		}

		public int getVolume() {
			return this.vol;
		}

		public void setVolume(int vol) {
			this.vol = vol;
		}

		public int getVolId() {
			return this.volid;
		}
	}

	public void setVolume(int volid, float volume) {
		for (SoundID s : SoundID.values()) {
			if (volid == s.getVolId() && s.isRunning()) {
				gainControl[s.getID()].setValue(volume);
			}
		}
	}

}

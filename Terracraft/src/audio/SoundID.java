package audio;

public enum SoundID {
	// template ( id, name, vol),
	door_open(0, "Door_Opened", 1),
	door_closed(1, "Door_Closed", 1),
	trade(2, "trade", 1);

	private int id;
	private String prefix = "res/Sound/";
	private String suffix = ".wav";
	private String name;
	// musicvol // effectvol // mastervol//
	private int[] volList = { -20, 6, -20 };
	private int vol;

	SoundID(int id, String name, int vol) {
		this.id = id;
		this.name = name;

		for (int i = 0; i < volList.length; i++) {
			if (vol == i) {
				this.vol = volList[i];
			}
		}
	}

	public int getID() {
		return this.id;
	}

	public String getPath() {
		return this.prefix + this.name + this.suffix;
	}

	public int getVolume() {
		return this.vol;
	}
}

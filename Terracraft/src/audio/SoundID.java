package audio;

public enum SoundID {
	// template ( id, name, vol),
	door_open(0, "Door_Opened", 1),
	door_closed(1, "Door_Closed", 1),
	trade(2, "trade", 0);


	private int id;
	private String prefix = "res/Sound/";
	private String suffix = ".wav";
	private String name;
	// musicvol // effectvol
	public int[] volList = { -20, -30};
	private int vol;
	private int volid;
	private boolean running;

	SoundID(int id, String name, int vol) {
		this.id = id;
		this.name = name;

		for (int i = 0; i < volList.length; i++) {
			if (vol == i) {
				this.vol = volList[i];
			}
		}
		volid = vol;
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
	
	public int getVolId(){
		return this.volid;
	}
	
	public boolean isRunning(){
		return this.running;
	}
	public void setRunning(){
		this.running = true;
	}

	public void stopRunning() {
		this.running = false;
		
	}
}

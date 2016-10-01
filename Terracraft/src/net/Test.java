package net;

import terracraft.Utils;

public class Test {

	public static void main(String args[]){
		Utils.startTimerMillis();
		MySQL mysql = new MySQL();
		System.out.println("dauer : " + Utils.getTimerMillis());
		
	}

}

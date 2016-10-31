package net;

import java.io.IOException;

public class MainServer {
	public static void main(String[] args) throws IOException{
		ServerConnection conn = new ServerConnection();
		ServerGui gui = new ServerGui(conn);
	}
}

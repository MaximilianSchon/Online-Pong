package server;

import java.io.IOException;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		
		PongMonitor mon = new PongMonitor();
		PongServer server = new PongServer(mon);
		PongThread th = new PongThread(mon);
		
		th.start();
		System.out.println("Starting Pong server!");
		server.start(7777);
		
	}
	
	
	
}

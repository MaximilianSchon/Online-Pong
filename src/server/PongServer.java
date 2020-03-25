package server;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Optional;
import java.util.Scanner;

public class PongServer extends Observable {
	private ServerSocket serverSocket;
	private boolean p1Present = false;
	public static boolean running = true;

	public PongMonitor mon;

	public PongServer(PongMonitor mon) {

		this.mon = mon;

	}

	public void start(int port) throws IOException {

		serverSocket = new ServerSocket(port);
		while (running) {
			Scanner scan = new Scanner(System.in);
			String s = scan.nextLine();
			if (s.equals("accept")) {
				Socket socket = serverSocket.accept();
				System.out.println("Accepting connection from " + socket.getInetAddress());


				if (p1Present) {
					new PongClientHandler(socket, 2).start();
				} else if (!p1Present) {
					new PongClientHandler(socket, 1).start();
					p1Present = true;
				}
				System.out.println("started new thread");
			} else if (s.equals("stop")) {
				stop();
				running = false;

			}

		}
	}

	public void stop() throws IOException {
		serverSocket.close();
	}

	private class PongClientHandler extends Thread {

		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;
		private int player;

		public PongClientHandler(Socket socket, int player) {
			this.clientSocket = socket;
			this.player = player;

		}

		public void run() {
			try {

				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				while (true) {
					int v = Integer.parseInt(in.readLine());
					if (player == 1) {
						mon.setP1Vel(v);
					} else if (player == 2) {
						mon.setP2Vel(v);
					}
					StringBuilder sb = new StringBuilder();
					sb.append(((int) mon.getBallX()) + ";" + ((int) mon.getBallY()) + ";" + ((int) mon.getP1Y()) + ";" + ((int) mon.getP2Y()));
					out.println(sb.toString()); // send ball x + ball y
				}

			



			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
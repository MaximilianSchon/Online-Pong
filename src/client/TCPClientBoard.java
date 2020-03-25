package client;

//import Pong.Pong;
//import Pong.Paddle;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class TCPClientBoard extends Applet implements Runnable, ImageObserver, KeyListener {
	
	// private Pong pong;
	private Graphics gfx;
	private Image img;
	boolean upPressed = false;
	private int velX, velY = 0;
	private final int WIDTH = 500;
	private final int HEIGHT = 300;
	private final int PACKET_PER_SECOND = 60;

	private TCPTestClient client;

	private int ballX;
	private int ballY;
	private int p1Y;
	private int p2Y;

	public void updateBall(String s) {
		String[] v = new String[4];
		v = s.split(";");
		ballX = Integer.parseInt(v[0]);
		ballY = Integer.parseInt(v[1]);
		p1Y = Integer.parseInt(v[2]);
		p2Y = Integer.parseInt(v[3]);

	}

	public static void main(String[] args) {
		new TCPClientBoard().init();
	}


	public void init() {


		this.resize(WIDTH, HEIGHT);
		img = createImage(WIDTH, HEIGHT);
		gfx = img.getGraphics();
		addKeyListener(this);
		setFocusable(true);
		// pong = new Pong();
		Thread thread = new Thread(this);
		thread.start();
	}

	public void paint(Graphics g) {
		gfx.setColor(Color.BLACK);
		gfx.fillRect(0, 0, WIDTH, HEIGHT);
		gfx.setColor(Color.WHITE);
		

		 gfx.fillRect((int) 20, p1Y,
		 10, 100);
		gfx.fillRect((int) 470, p2Y,
				10, 100);
		// gfx.fillRect((int) pong.getPlayer2X(), (int) pong.getPlayer2Y(),
		// Paddle.WIDTH, Paddle.HEIGHT);

		gfx.fillOval(ballX, ballY, 20, 20);
		// gfx.fillOval((int) pong.getBallX(), (int) pong.getBallY(),
		// Pong.RADIUS * 2, Pong.RADIUS * 2);

		gfx.setColor(Color.RED);

		// gfx.fillRect((int) pong.getPlayer1X(), (int) pong.getPlayer1Y(), 5,
		// 5);
		// gfx.fillRect((int) pong.getPlayer2X(), (int) pong.getPlayer2Y(), 5,
		// 5);
		// gfx.fillOval((int) pong.getBallX(), (int) pong.getBallY(), 5, 5);

		g.drawImage(img, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void run() {
		client = new TCPTestClient();
		System.out.println("starting connection");
		try {
			client.startConnection("127.0.0.1", 7777);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String s;
			while (true) {

				s = client.receive(velX);
				updateBall(s);

				repaint();

				Thread.sleep(1000/PACKET_PER_SECOND);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
			velX = -5;
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			velX = 5;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			velX = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
			velY = 0;
		}
	}
}

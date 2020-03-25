package client;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class UDPClientBoard extends Applet implements Runnable, ImageObserver, KeyListener {
    private Graphics gfx;
    private Image img;
    boolean upPressed = false;
    private final int WIDTH = 500;
    private final int HEIGHT = 300;
    private final int PACKET_PER_SECOND = 60;

    private UDPTestClient client;
    private String velY = "0";
    private int ballX;
    private int ballY;
    private int p1Y;
    private int p2Y;

//    UDPClientBoard(Paddle p1, Paddle p2, Ball ball, String player, String ip) {
//        setDoubleBuffered(true);
//        this.p1 = p1;
//        this.p2 = p2;
//        this.ball = ball;
//        this.player = player;
//        try {
//            client.startConnection(ip, 7777);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Timer time = new Timer(20, this);
//        time.start();
//    }

    private void updateBall(String s) {
        String[] v = s.split(";");
        if (v.length == 4) {
            ballX = Integer.parseInt(v[0]);
            ballY = Integer.parseInt(v[1]);
            p1Y = Integer.parseInt(v[2]);
            p2Y = Integer.parseInt(v[3]);
        }
    }
    public static void main(String[] args) {
        new UDPClientBoard().init();
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
        client = new UDPTestClient();
        System.out.println("starting connection");
        try {
            client.startConnection("81.228.57.76", 7777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String s;
            while (true) {

                s = client.receive("P2" + velY);
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
            velY = "-5";
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            velY = "5";
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            velY = "0";
        }
    }
}

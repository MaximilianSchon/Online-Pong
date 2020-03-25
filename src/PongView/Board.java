package PongView;

import Pong.Pong;
import Pong.Paddle;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;

public class Board extends Applet implements Runnable, ImageObserver, KeyListener {
private Pong pong;
private Graphics gfx;
private Image img;
boolean upPressed = false;
private static final int VELCONST = 2;
private int velX,velY = 0;

    public static void main(String[] args) {
        new Board().init();
    }
    public void init() {
        pong = new Pong();
        this.resize(Pong.WIDTH, Pong.HEIGHT);
        img = createImage(Pong.WIDTH, Pong.HEIGHT);
        gfx = img.getGraphics();
        addKeyListener(this);
        Thread thread = new Thread(this);
        thread.start();
    }
    public void paint(Graphics g) {
        gfx.setColor(Color.BLACK);
        gfx.fillRect(0,0, Pong.WIDTH, Pong.HEIGHT);
        gfx.setColor(Color.WHITE);
        gfx.fillRect((int) pong.getPlayer1X(), (int) pong.getPlayer1Y(), Paddle.WIDTH, Paddle.HEIGHT);
        gfx.fillRect((int) pong.getPlayer2X(), (int) pong.getPlayer2Y(), Paddle.WIDTH, Paddle.HEIGHT);
        gfx.fillOval((int) pong.getBallX(), (int) pong.getBallY(), Pong.RADIUS*2, Pong.RADIUS*2);
        /** debugging **/
        gfx.setColor(Color.RED);
        gfx.fillRect((int) pong.getPlayer1X(), (int) pong.getPlayer1Y(), 5,5);
        gfx.fillRect((int) pong.getPlayer2X(), (int) pong.getPlayer2Y(), 5,5 );
        gfx.fillOval((int) pong.getBallX(), (int) pong.getBallY(), 5, 5);
        gfx.fillOval((int) pong.deadX, (int) pong.deadY, Pong.RADIUS*2, Pong.RADIUS*2);
        g.drawImage(img, 0,0,this);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void run() {
       for (;;) {
           pong.step(velX,velY);
           repaint();
           try {
               Thread.sleep(10);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            velX = -VELCONST;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            velX = VELCONST;
        else if (e.getKeyCode() == KeyEvent.VK_W)
            velY = -VELCONST;
        else if (e.getKeyCode() == KeyEvent.VK_S)
            velY = VELCONST;


    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            velX = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
            velY = 0;
        }
    }
}

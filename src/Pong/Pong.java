package Pong;

import java.awt.*;
import java.util.Observable;

public class Pong extends Observable {
    public static final int HEIGHT = 300;
    public static final int WIDTH = 500;
    static final int GOALSIZE = 20;
    public static final int RADIUS = 10;
    private static final double GRAVITY = 0.5;
    private Paddle player1 = new Paddle(GOALSIZE, HEIGHT / 2 - Paddle.HEIGHT/2);
    private Paddle player2 = new Paddle(WIDTH - GOALSIZE - Paddle.WIDTH, HEIGHT / 2 - Paddle.HEIGHT/2);
    private Ball ball = new Ball(RADIUS, WIDTH / 2, HEIGHT / 2, GRAVITY);
    private int score1 = 0;
    private int score2 = 0;
    public double deadX,deadY;


    public void step(int p1Vel, int p2Vel) {
            if (ball.paddleCollision(player1, player2)) {
                ball.bounceX();
                deadX = ball.x();
                deadY = ball.y();
            } else if (ball.inGoal())  {
                deadX = ball.x();
                deadY = ball.y();
                switch (ball.getScorer()) {
                    case 1:
                        score1++;
                        ball.reset();
                        break;
                    case 2:
                        score2++;
                        ball.reset();
                        break;
                }
            }
        ball.move();
        player1.move(p1Vel);
        player2.move(p2Vel);
    }

    private boolean rectPaddleCollision() {
        Rectangle ballRect = new Rectangle((int) ball.x(), (int) ball.y(), RADIUS*2, RADIUS*2);
        Rectangle paddle1 = new Rectangle((int) player1.x(), (int) player1.y(), Paddle.WIDTH, Paddle.HEIGHT);
        Rectangle paddle2 = new Rectangle((int) player2.x(), (int) player2.y(), Paddle.WIDTH, Paddle.HEIGHT);
        return (ballRect.intersects(paddle1) || ballRect.intersects(paddle2));
    }

    public double getBallX() {
        return ball.x();
    }

    public double getBallY() {
        return ball.y();
    }

    public double getPlayer1X() {
        return player1.x();
    }

    public double getPlayer1Y() {
        return player1.y();
    }

     public double getPlayer2X() {
        return player2.x();
    }

    public double getPlayer2Y() {
        return player2.y();
    }
}
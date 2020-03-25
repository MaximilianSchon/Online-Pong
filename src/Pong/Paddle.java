package Pong;

public class Paddle {
    private int moveY = 1;
    private double x;
    private double y;
    public static int HEIGHT = Pong.HEIGHT/3;
    public static int WIDTH = 10;
    Paddle(int x, int y) {
        this.y = y;
        this.x = x;
    }

    double x() {
        return x;
    }

    double y() {
        return y;
    }
    void move() {
        y += moveY;
        if (y + moveY + HEIGHT == Pong.HEIGHT || y + moveY == 0) {
            moveY = -moveY;
        }
    }
    void move(int vel) {
        if (!(y + vel + HEIGHT > Pong.HEIGHT || y + vel < 0))
            y += vel;
    }
}

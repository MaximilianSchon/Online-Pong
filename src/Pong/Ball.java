package Pong;

class Ball {
    private final int radius;
    private final static int VELCONST = 2;
    private final int startX;
    private final int startY;
    private double x,y,velX,velY,gravity;

    Ball(int rad, int startX, int startY, double gravity) {
        radius = rad;
        x = startX;
        y = startY;
        this.gravity = gravity;
        this.startX = startX;
        this.startY = startY;
        velX = (Math.random() >= 0.5) ? VELCONST:-VELCONST;
        velY = (Math.random() >= 0.5) ? VELCONST:-VELCONST;
    }
    void reset() {
        x = startX;
        y = startY;
        velX =(Math.random() >= 0.5) ? VELCONST:-VELCONST;
        velY =(Math.random() >= 0.5) ? VELCONST:-VELCONST;
    }

    void move() {
        x += velX;
        y += velY;
        if (onBorder()) {
            bounceY();
        }
    }


    private boolean onBorder() {
        return ((y <= 0) || (y + radius*2 >= Pong.HEIGHT));
    }

    boolean paddleCollision(Paddle p1, Paddle p2) {
        if (x <= Pong.GOALSIZE + Paddle.WIDTH) {
            if (y >= p1.y() - 2.5*radius && y <= p1.y() + Paddle.HEIGHT + radius) {
                return true;
            }
        }
        else if (x >= Pong.WIDTH - Pong.GOALSIZE - Paddle.WIDTH - 2*radius) {
                if (y >= p2.y() - 2.5*radius && y <= p2.y() + Paddle.HEIGHT + radius) {
                    return true;
                }
            }
        return false;
    }

    int getScorer() {
        if (velX > 0)
            return 1;
        else
            return 2;
    }


    boolean inGoal() {
            return (x <= Pong.GOALSIZE + Paddle.WIDTH || x >= Pong.WIDTH - Pong.GOALSIZE - Paddle.WIDTH - 2*radius);
    }

    void bounceX() {
        if (Math.abs(velX) < 5) {
            if (velX < 0)
                velX = -velX + gravity;
            else
                velX = -velX - gravity;
        }
        else {
            velX = -velX;
        }
    }
    private void bounceY() {
        if (Math.abs(velY) < 5) {
            if (velY < 0)
                velY = -velY + gravity;
            else
                velY = -velY - gravity;
        } else {
            velY = -velY;
        }
    }

    double x() {
        return x;
    }

    double y() {
        return y;
    }

}

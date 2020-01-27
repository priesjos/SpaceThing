import java.awt.*;

public class Player {

    int x, y;
    final int WIDTH = 20, HEIGHT = 40;
    double speed, dx = 5, dy;

    public Player(Board board){
        x = board.getWidth()/2;
        y = board.getHeight() - HEIGHT;
    }

    public void paint(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(x,y,WIDTH,HEIGHT);
    }

    public void moveRight(){
        x += dx;
    }

    public void moveLeft(){
        x -= dx;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWIDTH(){
        return WIDTH;
    }

}

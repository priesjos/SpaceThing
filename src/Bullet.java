import java.awt.*;

public class Bullet {

    int x, y, diameter = 15;
    int dy = 10;
    boolean remove;

    public Bullet(Player player){
        x = player.getX() + (player.getWIDTH()/2) - (diameter/2);
        y = player.getY() - diameter;
    }

    public void move(){
        y -= dy;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, diameter, diameter);
    }

    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x, y, diameter, diameter);
    }

    public int getY(){
        return y;
    }


    public boolean getRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
}

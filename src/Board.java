import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    Game game;
    Player player;
    Enemy enemy;
    Enemy[][] enemies = new Enemy[5][10];
    Bullet bullet;
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    Timer timer;
    Long timeDelay;
    Long bulletDelay;

    public Board(Game game){
        this.game = game;
        setPreferredSize(new Dimension(1000, 700));
        setBackground(Color.BLACK);
        timer = new Timer(1000/60, this);
    }

    //Gives objs starting positions
    public void setup(){
        player = new Player(this);
        for (int row = 0; row < 5; row++){
            for (int col = 0; col < 10; col++){
                enemies[row][col] = new Enemy(getWidth()/4 + col*50, row*50);
            }
        }

        timeDelay = System.currentTimeMillis();
        bulletDelay = System.currentTimeMillis();
    }

    public void checkCollisions(){

        for(int i = bullets.size()-1; i >= 0; i--){
            for(int j = 0; j < enemies.length; j++){
                for (int k = 0; k < enemies[0].length; k++){
                    if (enemies[j][k] != null) {
                        if (bullets.get(i).getBounds().intersects(enemies[j][k].getBounds())) {
                            bullets.get(i).setRemove(true);
                            enemies[j][k] = null;
                            break;
                        }
                    }
                }
            }

            //if (bullets.get(i).getRemove()){
            //    bullets.remove(bullets.get(i));
            //}
        }

    }

    public void paintComponent(Graphics g){
        timer.start();
        super.paintComponent(g);

        if(Gamestates.isMENU()) {
            //Paint the Menu
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("Times New Roman", Font.PLAIN, 48));
            printSimpleString("SPACE THING", getWidth(), 0, 100, g);
            g.setFont(new Font("Times New Roman", Font.ITALIC, 28));
            printSimpleString("Press ENTER to play", getWidth(), 0, 180, g);
        }

        if (Gamestates.isPLAY()) {
            player.paint(g);
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 10; col++) {
                    if (enemies[row][col] != null)
                        enemies[row][col].paint(g);
                }
            }
            for (Bullet bullet : bullets) {
                bullet.paint(g);
            }
        }

    }

    private void printSimpleString(String s, int width, int xPos, int yPos, Graphics g){
        int stringLen = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
        int start = width/2 - stringLen/2;
        g.drawString(s, start + xPos, yPos);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.currentTimeMillis();

        if (game.isEnterPressed()){
            Gamestates.setPLAY(true);
            Gamestates.setMENU(false);
        }

        if (Gamestates.isPLAY() && !timer.isRunning()){
            timer.start();
        }
        if (game.ispPressed()){
            if (Gamestates.isPAUSE()){
                Gamestates.setPAUSE(false);
            }
            else
                Gamestates.setPAUSE(true);
        }

        if (Gamestates.isPLAY() && !Gamestates.isPAUSE()){

            checkCollisions();
            if (game.isSpacePressed() && currentTime - bulletDelay >= 250) {
                bullets.add(new Bullet(player));
                bulletDelay = System.currentTimeMillis();
            }

            if (game.isLeftPressed() && player.getX() > 0){
                player.moveLeft();
            }

            if (game.isRightPressed() && player.getX() < getWidth() - player.getWIDTH()) {
                player.moveRight();
            }

            for (int i = bullets.size()-1; i >= 0; i--){
                if(bullets.get(i).getY() < 0){
                    bullets.remove(bullets.get(i));
                }
                else
                    bullets.get(i).move();
            }

            if (currentTime - timeDelay >= 1000) {
                for (int row = 0; row < 5; row++) {
                    for (int col = 0; col < 10; col++) {
                        if (enemies[row][col] != null)
                            enemies[row][col].move();
                    }
                }
                timeDelay = System.currentTimeMillis();
            }
        }

        repaint();
    }
}

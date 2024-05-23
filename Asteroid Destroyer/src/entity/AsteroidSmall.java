package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class AsteroidSmall extends Entity{

    private int velX;
    private int velY;
    private int factor;
    private int life;

    BufferedImage asteroid;
    GamePanel gp;

    public AsteroidSmall(GamePanel gp, int posX, int posY, int velX, int velY, int factor) {
        this.gp = gp;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.factor = factor;
        setLife();
        setImage();
    }

    private void setLife(){
        life = factor;
    }

    public void removeLife(){
        life--;
    }

    public int getLife(){
        return life;
    }

    private void setImage(){
        try{

            asteroid = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroids/Assteroid1.png")));

        }catch(IOException e){

            e.printStackTrace();

        }
    }
    public void update() {
        posX += velX;
        posY += velY;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public int getFactor(){return factor;}

    public void draw(Graphics2D g2) {

        g2.drawImage(asteroid, posX, posY, gp.tileSize * factor, gp.tileSize * factor, null);
    }

}

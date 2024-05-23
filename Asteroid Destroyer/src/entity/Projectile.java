package entity;

import main.GamePanel;

import java.awt.*;

public class Projectile extends Entity{

    public int velX;
    public int velY;
    private int projectileWidth;
    private int projectileHeight;
    private GamePanel gp = new GamePanel();

    public Projectile(int posX, int posY, int velX, int velY) {
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
    }

    public void update() {
        posX += velX;
        posY += velY;
    }

    public boolean collidesWith(AsteroidSmall asteroidSmall){
        boolean collision = false;

        Rectangle projectile = new Rectangle(posX, posY, projectileWidth, projectileHeight);
        Rectangle asteroid = new Rectangle(asteroidSmall.getPosX(), asteroidSmall.getPosY(), gp.tileSize * asteroidSmall.getFactor(), gp.tileSize * asteroidSmall.getFactor());

        if (projectile.intersects(asteroid)){
            collision = true;
        }
        return collision;
    }

    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }

    public void draw(Graphics2D g2) {
        if(velX != 0) {
            projectileWidth = 13;
            projectileHeight = 2;
            g2.fillRect(posX, posY, projectileWidth, projectileHeight);
        }
        if (velY != 0){
            projectileWidth = 2;
            projectileHeight = 13;
            g2.fillRect(posX, posY, projectileWidth, projectileHeight);
        }
    }

}
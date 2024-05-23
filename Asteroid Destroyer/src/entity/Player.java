package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class Player extends Entity{

    private static final int MOVE_INTERVAL = 150;

    GamePanel gp;
    KeyHandler keyH;
    private long lastMoveTime = 0;
//    int plusVel = 20;
    int upMid = 0;
//    private static final int SHOOT_INTERVAL = 150;
//    private long lastShootTime = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImages();

        direction = "up";
        upMid = gp.tileSize / 2;

    }

    private void getPlayerImages(){

        // Get player images
        try{

            up = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/SpaceShip_up.png")));
            right = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/SpaceShip_right.png")));
            left = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/SpaceShip_left.png")));
            down = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/SpaceShip_down.png")));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setDefaultValues(){
        posX = 469;
        posY = 290;
    }

    public void update(){

        
        // Update player position

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed ||
                keyH.upArrow || keyH.downArrow || keyH.leftArrow || keyH.rightArrow) {

            if (keyH.upPressed) {
                posY -= speed;
            } else if (keyH.downPressed) {
                posY += speed;
            } else if (keyH.leftPressed) {
                posX -= speed;
            } else if (keyH.rightPressed) {
                posX += speed;
            }

            if (keyH.upArrow || keyH.rightArrow || keyH.downArrow || keyH.leftArrow){
                if (System.currentTimeMillis() - lastMoveTime >= MOVE_INTERVAL) {
                    lastMoveTime = System.currentTimeMillis();
                    if (keyH.upArrow) {
                        direction = "up";
                    } else if (keyH.downArrow) {
                        direction = "down";
                    } else if (keyH.rightArrow) {
                        direction = "right";
                    } else if (keyH.leftArrow) {
                        direction = "left";
                    }
                }
            }
        }

//        int minusVel = -1 * plusVel;
//
//        if (keyH.spacePressed){
//
//            if (System.currentTimeMillis() - lastShootTime >= SHOOT_INTERVAL){
//
//                lastShootTime = System.currentTimeMillis();
//
//                switch (direction) {
//                    case "up":
//                        gp.projectiles.add(new Projectile(posX + upMid, posY, 0, minusVel));
//                        break;
//                    case "down":
//                        gp.projectiles.add(new Projectile(posX + upMid, posY + gp.tileSize, 0, plusVel));
//                        break;
//                    case "left":
//                        gp.projectiles.add(new Projectile(posX, posY + upMid, minusVel, 0));
//                        break;
//                    case "right":
//                        gp.projectiles.add(new Projectile(posX + gp.tileSize, posY + upMid, plusVel, 0));
//                        break;
//                }
//
//            }
//
//        }
//
//
//        //HA?!!?!?!?!? Hier is the lag problem
//
//        Iterator<Projectile> projectileIterator = gp.projectiles.iterator();
//        // Update projectiles
//        while(projectileIterator.hasNext()){
//            Projectile projectile = projectileIterator.next();
//            projectile.update();
//
//            // Collect projectiles that are out of bounds
//            if (projectile.getPosX() < 0 || projectile.getPosX() > gp.screenWidth ||
//                    projectile.getPosY() < 0 || projectile.getPosY() > gp.screenHeight) {
//                projectileIterator.remove();
//            }
//        }

    }

    public boolean collidesWith(AsteroidSmall asteroidSmall){

        boolean collision = false;

        Rectangle spaceshipHitbox = new Rectangle(posX+1, posY+1, gp.tileSize-2, gp.tileSize-2);
        Rectangle asteroidHitbox = new Rectangle(asteroidSmall.getPosX(), asteroidSmall.getPosY(), gp.tileSize * asteroidSmall.getFactor(), gp.tileSize * asteroidSmall.getFactor());

        if (spaceshipHitbox.intersects(asteroidHitbox)){
            collision = true;
        }

        return collision;

    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction){
            case "up":
                image = up;
                break;
            case "down":
                image = down;
                break;
            case "left":
                image = left;
                break;
            case "right":
                image = right;
                break;
        }


        g2.drawImage(image, posX,posY, gp.tileSize , gp.tileSize , null);

    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public String getDirection(){
        return direction;
    }
}

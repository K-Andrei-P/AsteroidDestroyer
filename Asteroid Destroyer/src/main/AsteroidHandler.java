package main;

import entity.AsteroidSmall;

import java.util.Iterator;
import  java.util.Random;

public class AsteroidHandler {

    GamePanel gp;
    Random random;

    String direction;

    AsteroidHandler(GamePanel gp) {
        random = new Random();
        this.gp = gp;
    }


    public String setDirection(){
        int high = 5;
        int low = 1;
        int zufallDirection = random.nextInt(high - low) + low;

        switch (zufallDirection){
            case 1:
                direction = "up";
                break;
            case 2:
                direction = "down";
                break;
            case 3:
                direction = "left";
                break;
            case 4:
                direction = "right";
                break;
        }

        return direction;
    }


    public int setVelocity(){
        return random.nextInt(7 - 3) + 3;
    }

    public int setPosX(){
        int low = 40;
        int high = gp.screenWidth - 40;

        return random.nextInt(high-low) + low;
    }

    public int setPosY(){
        int low = 30;
        int high = gp.screenHeight - gp.tileSize - 20;

        return random.nextInt(high - low) + low;
    }

    public int setFactor(){

        // Range is 4 until 1
        return random.nextInt(4-1) + 1;
    }

    public void spawnAsteroid(){

        int velocity = setVelocity();
        String directionAs = setDirection();
        int posX = setPosX();
        int posY = setPosY();
        int factor = setFactor();

        switch (directionAs) {
            case "up":
                gp.asteroidSmalls.add(new AsteroidSmall(gp, posX, gp.screenHeight, 0, -1 * velocity, factor));
                break;
            case "down":
                gp.asteroidSmalls.add(new AsteroidSmall(gp, posX, 0, 0, velocity, factor));
                break;
            case "left":
                gp.asteroidSmalls.add(new AsteroidSmall(gp, gp.screenWidth, posY, -1 * velocity, 0, factor));
                break;
            case "right":
                gp.asteroidSmalls.add(new AsteroidSmall(gp, 0, posY, velocity, 0, factor));
                break;
        }
    }

    public void update(){

        Iterator<AsteroidSmall> iterator = gp.asteroidSmalls.iterator();
        while (iterator.hasNext()) {
            AsteroidSmall asteroidSmall = iterator.next();
            asteroidSmall.update();
            // Remove after OffScreen
            if (asteroidSmall.getPosX() < 0 || asteroidSmall.getPosX() > gp.screenWidth ||
                    asteroidSmall.getPosY() < 0 || asteroidSmall.getPosY() > gp.screenHeight) {
                iterator.remove();
            }
        }
    }
}

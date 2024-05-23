package main;


import entity.Player;
import entity.Projectile;

import java.util.Iterator;

public class ProjectileHandler {

    private GamePanel gp;
    private KeyHandler keyH;
    private static final int SHOOT_INTERVAL = 250;
    private int plusVel = 20;
    private int upMid;
    private Player player;
    private long lastShootTime = 0;
    String direction;
    int posX;
    int posY;

    public ProjectileHandler(GamePanel gp, KeyHandler kh, Player player){

        this.gp = gp;
        this.keyH = kh;
        this.player = player;
        upMid = gp.tileSize / 2;
        direction = this.player.getDirection();
        posX = this.player.getPosX();
        posY = this.player.getPosY();
    }

    public void setEverythingToPlayerInformation(){

        posX = player.getPosX();
        posY = player.getPosY();
        direction = player.getDirection();
    }

    public void update() {

        int minusVel = -1 * plusVel;

        if (System.currentTimeMillis() - lastShootTime >= SHOOT_INTERVAL) {
            if (keyH.spacePressed) {
                lastShootTime = System.currentTimeMillis();
                switch (direction) {
                    case "up":
                        gp.projectiles.add(new Projectile(posX + upMid, posY, 0, minusVel));
                        break;
                    case "down":
                        gp.projectiles.add(new Projectile(posX + upMid, posY + gp.tileSize, 0, plusVel));
                        break;
                    case "left":
                        gp.projectiles.add(new Projectile(posX, posY + upMid, minusVel, 0));
                        break;
                    case "right":
                        gp.projectiles.add(new Projectile(posX + gp.tileSize, posY + upMid, plusVel, 0));
                        break;
                }
            }
        }

    }

    public void updatingProjectiles(){

        // Collect projectiles that are out of bounds
        Iterator<Projectile> projectileIterator = gp.projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if (projectile != null) { // Check if projectile is not null
                projectile.update();
                if (projectile.getPosX() < 0 || projectile.getPosX() > gp.screenWidth ||
                        projectile.getPosY() < 0 || projectile.getPosY() > gp.screenHeight) {
                    projectileIterator.remove();
                }
            }
        }

    }
}

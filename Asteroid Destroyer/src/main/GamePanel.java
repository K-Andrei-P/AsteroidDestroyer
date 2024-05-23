package main;

import entity.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends JPanel {

    final int originalTitleSize = 17;
    final int scale = 3;
    public final int tileSize = originalTitleSize * scale;
    final int macScreenCol = 20;
    final int maxScreenRow = 12;
    public final int screenWidth = tileSize * macScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    private boolean startGame = false;
    private boolean restartGame = false;
    int points = 0;

    Player player;

    AsteroidHandler asteroidH;
    KeyHandler keyH = new KeyHandler();
    Background background;
    PlayScreen playScreen;
    RestartScreen restartScreen;
    ProjectileHandler projectileH;

    // Threads
    Thread updateThread;
    Thread drawThread;
    Thread projectileThread;

    // Gameloop testing
    int roundCounter = 0;


    // GameObjects ==> Collisions
    public final List<AsteroidSmall> asteroidSmalls = new ArrayList<>();
    public final List<Projectile> projectiles = new ArrayList<>();

    int Level = 1;

    int minutes = 0;
    int seconds = 0;

    public GamePanel(){
        //default. Setting Game panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        //recognise KeyListener
        this.addKeyListener(keyH);
        this.setFocusable(true);

        playScreen = new PlayScreen(this);
        restartScreen = new RestartScreen(this);
        player = new Player(this, keyH);
        background = new Background(this);
        asteroidH = new AsteroidHandler(this);
        projectileH = new ProjectileHandler(this, keyH, player);

    }

    public void startGameThread(){

        projectileThread = new Thread(this::projectileLoop);
        updateThread = new Thread(this::updateLoop);
        drawThread = new Thread(this::drawLoop);

        projectileThread.start();
        updateThread.start();
        drawThread.start();


    }

    private void projectileLoop(){

        while(projectileThread != null){

            projectileH.setEverythingToPlayerInformation();
            projectileH.update();

            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void updateLoop(){

        // CPU: 15% - 25%

        long asteroidlastTimeLevel1 = System.currentTimeMillis();
        long asteroidlastTimeLevel2 = System.currentTimeMillis();
        long asteroidlastTimeLevel3 = System.currentTimeMillis();
        long asteroidlastTimeLevel4 = System.currentTimeMillis();
        long asteroidlastTimeLevel5 = System.currentTimeMillis();

        long gamestart = 0;
        long elapsedTime;
        roundCounter = 1;

        int asteroidDeltaLevel1 = 2000;         //3 seconds
        int asteroidDeltaLevel2 = 1500;         //4 seconds
        int asteroidDeltaLevel3 = 1000;         //2 seconds
        int asteroidDeltaLevel4 = 500;          //
        int asteroidDeltaLevel5 = 300;


        long currentTime;

        while (updateThread != null) {

            if (startGame) {
                update();

                if (roundCounter == 1) {
                    roundCounter++;
                    gamestart = System.currentTimeMillis();
                }


                currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - gamestart;

                // Level 2 starts at 10 seconds
                if (Level == 1 && elapsedTime >= 10000) {
                    Level++;
                    asteroidlastTimeLevel2 = System.currentTimeMillis();
                }

                // Level 3 starts at 30 seconds
                if (Level == 2 && elapsedTime >= 30000) {
                    Level++;
                    asteroidlastTimeLevel3 = System.currentTimeMillis();
                }

                // Level 4 starts at 40 seconds
                if (Level == 3 && elapsedTime >= 40000) {
                    Level++;
                    asteroidlastTimeLevel4 = System.currentTimeMillis();
                }

                // Level 5 starts at 60 seconds
                if (Level == 4 && elapsedTime >= 60000) {
                    Level++;
                    asteroidlastTimeLevel5 = System.currentTimeMillis();
                }

                int elapsedSeconds = (int) (elapsedTime / 1000);
                minutes = elapsedSeconds / 60;
                seconds = elapsedSeconds % 60;

                // Spawn asteroids based on the current level and elapsed time
                if (Level == 1 && currentTime - asteroidlastTimeLevel1 >= asteroidDeltaLevel1) {
                    asteroidH.spawnAsteroid();
                    asteroidlastTimeLevel1 = System.currentTimeMillis();
                } else if (Level == 2 && currentTime - asteroidlastTimeLevel2 >= asteroidDeltaLevel2) {
                    asteroidH.spawnAsteroid();
                    asteroidlastTimeLevel2 = System.currentTimeMillis();
                } else if (Level == 3 && currentTime - asteroidlastTimeLevel3 >= asteroidDeltaLevel3) {
                    asteroidH.spawnAsteroid();
                    asteroidlastTimeLevel3 = System.currentTimeMillis();
                } else if (Level == 4 && currentTime - asteroidlastTimeLevel4 >= asteroidDeltaLevel4) {
                    asteroidH.spawnAsteroid();
                    asteroidlastTimeLevel4 = System.currentTimeMillis();
                } else if (Level == 5 && currentTime - asteroidlastTimeLevel5 >= asteroidDeltaLevel5) {
                    asteroidH.spawnAsteroid();
                    asteroidlastTimeLevel5 = System.currentTimeMillis();
                }


                // End Gamestart
            } else if (keyH.enterPressed && !restartGame) {
                startGame = true;
            } else if (restartGame) {
                if (keyH.enterPressed){
                    roundCounter++;
                    restartGame = false;
                    gamestart = 0;
                    Level = 1;
                    points = 0;
                    startGame = true;
                }
            }

            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }

    }


    private void drawLoop(){

        while (drawThread != null){
            repaint();

            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void update(){
        player.update();
        asteroidH.update();
        projectileH.setEverythingToPlayerInformation();

        projectileH.updatingProjectiles();

        checkCollisions();
    }

    private void resetAll(){
        roundCounter = 0;
        asteroidSmalls.clear();
        projectiles.clear();
        restartGame = true;
        startGame = false;
    }
    private void resetGameState(){
        Level = 1;
        player.setDefaultValues();
    }

    private void checkCollisions() {
        // Check player-asteroid collisions
        synchronized (asteroidSmalls) {
            List<AsteroidSmall> asteroidsCopy = new ArrayList<>(asteroidSmalls);
            for (AsteroidSmall asteroidSmall : asteroidsCopy) {
                if (player.collidesWith(asteroidSmall)) {
                    // Handle collision
                    resetAll();
                    return; // Exit the method after resetting
                }
            }
        }

        // Check projectile-asteroid collisions
        List<Projectile> projectilesCopy = new ArrayList<>(projectiles);
        for (Projectile projectile : projectilesCopy) {
            List<AsteroidSmall> asteroidsCopy = new ArrayList<>(asteroidSmalls);
            for (AsteroidSmall asteroidSmall : asteroidsCopy) {
                if (projectile != null && projectile.collidesWith(asteroidSmall)) {
                    // Collect projectiles and asteroids to be removed
                    projectiles.remove(projectile);
                    asteroidSmall.removeLife();

                    if (asteroidSmall.getLife() == 0) {
                        // Handle collision
                        switch (asteroidSmall.getFactor()) {
                            case 1:
                                points += 100;
                                break;
                            case 2:
                                points += 200;
                                break;
                            case 3:
                                points += 300;
                                break;
                            case 4:
                                points += 400;
                                break;
                        }
                        asteroidSmalls.remove(asteroidSmall);
                    }
                    break;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (!startGame && !restartGame) {
            playScreen.draw(g2);
        } else if (restartGame) {

            restartScreen.draw(g2);
            resetGameState();
        } else {
            background.draw(g2);
            player.draw(g2);

            g2.setColor(new Color(48,213,200));

            // Projectiles
            Iterator<Projectile> projectileIterator = projectiles.iterator();
            while (projectileIterator.hasNext()){
                Projectile projectile = projectileIterator.next();
                projectile.draw(g2);
            }

            // Asteroids
            Iterator<AsteroidSmall> iterator = asteroidSmalls.iterator();
            while (iterator.hasNext()) {
                AsteroidSmall asteroidSmall = iterator.next();
                asteroidSmall.draw(g2);
            }

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Score: " + points, 20, 30);
            g2.drawString("Level: " + getCurrentLevel(), 20, 60);

            // Timer
            String timerText = String.format("Time: %02d:%02d", minutes, seconds);
            FontMetrics fontMetrics = g2.getFontMetrics();
            int timerWidth = fontMetrics.stringWidth(timerText);
            int x = screenWidth - timerWidth - 5;
            int y = 20;
            g2.drawString(timerText, x, y);

        }

        g2.dispose();
    }

    private int getCurrentLevel() {
        return Level;
    }
}



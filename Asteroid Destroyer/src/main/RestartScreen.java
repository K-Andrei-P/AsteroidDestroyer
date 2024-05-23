package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class RestartScreen {

    BufferedImage restartScreen;
    GamePanel gp;

    RestartScreen(GamePanel gp){
        this.gp = gp;
        getBackground();
    }

    private void getBackground(){
        try{
            restartScreen = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/RestartScreen.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        g2.drawImage(restartScreen, 0, 0, gp.screenWidth, gp.screenHeight, null);

        g2.setFont(new Font("Impact", Font.BOLD, 40));
        g2.setColor(Color.WHITE);
        String scoreText = "Score: " + gp.points;
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(scoreText);
        int x = (gp.screenWidth - textWidth) / 2;
        int y = gp.screenHeight - 60;

        g2.setFont(new Font("Origin", Font.BOLD, 40));
        String timeText = String.format("Your Time: %02d:%02d", gp.minutes, gp.seconds);
        int timeWidth = fontMetrics.stringWidth(timeText);
        int xT = ((gp.screenWidth - timeWidth) / 2) - 13;
        int yT = gp.screenHeight - 20;

        g2.drawString(timeText, xT, yT);
        g2.drawString(scoreText, x, y);
    }
}

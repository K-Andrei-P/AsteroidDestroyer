package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PlayScreen {

    BufferedImage background;
    GamePanel gp;

    PlayScreen(GamePanel gp){
        this.gp = gp;
        getPlayScreen();
    }

    private void getPlayScreen(){

        try{
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/PlayScreen.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);
    }
}

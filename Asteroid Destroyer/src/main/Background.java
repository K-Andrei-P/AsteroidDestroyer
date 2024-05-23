package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Background {

    BufferedImage background;
    GamePanel gp;

    Background(GamePanel gp){
        this.gp = gp;
        getBackground();
    }

    private void getBackground(){

        try{
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/SpaceBackground.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);
    }
}

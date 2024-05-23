package main;

import javax.swing.*;
import java.util.Objects;

public class Main {

    public static void main(String[] args){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //Customization
        window.setTitle("Asteroid Destroyer");
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/background/GalagaSpaceShip2.png")));
        window.setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        gamePanel.startGameThread();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}

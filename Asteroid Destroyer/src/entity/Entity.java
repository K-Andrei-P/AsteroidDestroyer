package entity;

import java.awt.image.BufferedImage;

public class Entity {

    protected int posX;
    protected int posY;
    int speed = 4;

    BufferedImage up, down, left, right;
    String direction;
}

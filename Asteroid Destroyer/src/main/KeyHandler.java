package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, upArrow, leftArrow, downArrow, rightArrow, spacePressed, enterPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_UP){
            upArrow = true;
        }
        if(code == KeyEvent.VK_DOWN){
            downArrow = true;
        }
        if (code == KeyEvent.VK_LEFT){
            leftArrow = true;
        }
        if (code == KeyEvent.VK_RIGHT){
            rightArrow = true;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_UP){
            upArrow = false;
        }
        if(code == KeyEvent.VK_DOWN){
            downArrow = false;
        }
        if (code == KeyEvent.VK_LEFT){
            leftArrow = false;
        }
        if (code == KeyEvent.VK_RIGHT){
            rightArrow = false;
        }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
    }
}

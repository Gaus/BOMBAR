package com.gaustank.Player.State;

import com.gaustank.main.GamePanel;
import com.gaustank.main.State;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Leaving extends PlayerState {
    private int finalSpeed;
    
    public Leaving(){
        finalSpeed = (int) player.getSpeed();
        player.resetPlane();
    }
    
    @Override
    public void update() {
        player.increaseX(finalSpeed);
        if (player.getX() > 1280){
            player.changeState(new Coming(player));
            GamePanel.changeState(State.MENU);
        }
    }   

    @Override
    public void render(Graphics2D g) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

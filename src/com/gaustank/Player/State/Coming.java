package com.gaustank.Player.State;

import com.gaustank.Player.Player;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Coming extends PlayerState {

    private static final int CENTER_PLAYER_POSITION = 565;
     
    public Coming(Player player) {
        this.player = player;
        player.init();
    }
    
    @Override
    public void update(){
        player.increaseX((int)START_SPEED);
        if (player.getX() > CENTER_PLAYER_POSITION)
            player.changeState(new Stable());
    }

    @Override
    public void render(Graphics2D g) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

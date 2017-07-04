package com.gaustank.Player.State;

import com.gaustank.Player.Player;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public abstract class PlayerState {
    protected static Player player;
    protected static final double START_SPEED = 10.0;
    
    public abstract void render(Graphics2D g);
    public abstract void update();
    public abstract void keyReleased(KeyEvent e);
}

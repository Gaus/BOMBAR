package com.gaustank.main;

import com.gaustank.Player.Player;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameState {     
    protected static Player player; 
    
    public void update(){};
    public void render(Graphics2D g){};

    public abstract void keyReleased(KeyEvent e);
    public abstract void keyPressed(KeyEvent e);   
    public abstract void mousePressed(MouseEvent e);    
}

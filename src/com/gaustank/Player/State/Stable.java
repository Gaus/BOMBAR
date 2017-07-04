 package com.gaustank.Player.State;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Stable extends PlayerState {
    private Font font;
    private Color color;

    private static final int    FONT_SIZE = 16;
    private static final int    FUEL_X_POSITION = 1050;
    private static final int    SPEED_X_POSITION = 800;
    private static final int    BOMB_X_POSITION = 50;
    private static final int    INFO_Y_POSITION = 50;
    private static final int    WARNING_X_POSITION = 500;
    private static final int    WARNING_Y_POSITION = 150;
    private static final int    SHIFT = 100;
    private static final double RATIO = 20;
    
    public Stable() {
        player.setSpeed(START_SPEED);
        font = new Font("Verdana", Font.BOLD, FONT_SIZE);
        color = Color.BLACK;
    }
    
    @Override
    public void update() {
        player.updateActions();
        player.updatePlane();
    }    

    @Override
    public void render(Graphics2D g) {
        g.setFont(font); 
        g.setColor(color);
        g.drawString("Топливо", FUEL_X_POSITION, INFO_Y_POSITION);    
        g.drawString("Скорость", SPEED_X_POSITION, INFO_Y_POSITION);
        g.drawString("Бомбы", BOMB_X_POSITION, INFO_Y_POSITION);
        g.drawString(String.format("%d ед", player.getFuel()), FUEL_X_POSITION + SHIFT, INFO_Y_POSITION);
        g.drawString(String.format("%.1f км/ч", player.getSpeed() * RATIO), SPEED_X_POSITION + SHIFT, INFO_Y_POSITION);
        g.drawString(String.format("%d", player.getCurrentCountBombs()), BOMB_X_POSITION + SHIFT, INFO_Y_POSITION);
        
        if(player.getSpeed() < 7){
            g.setColor(Color.RED);
                g.drawString("Внимание!!! Опасность сваливания!", WARNING_X_POSITION, WARNING_Y_POSITION);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if(k == KeyEvent.VK_ESCAPE)
            player.changeState(new Leaving());
    }
}

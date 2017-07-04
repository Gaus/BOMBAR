package com.gaustank.main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Menu extends GameState {
    private BufferedImage       logo;
    private BufferedImage       background;
    private BufferedImage       start_button;
    private BufferedImage       options_button;
    private BufferedImage       exit_button;
    
    private Color[]             buttonsColor;
    private Font                font;

    private static final int    FONT_SIZE = 40;

    private static final int    LOGO_WIDTH = 538;
    private static final int    LOGO_HEIGHT = 118;
    private static final int    BUTTONS_WIDTH = 211;
    private static final int    BUTTONS_HEIGHT = 33;
    
    private static final int    LOGO_X_POSITION = 373;
    private static final int    LOGO_Y_POSITION = 100;
    private static final int    BUTTONS_X_POSITION = 535;
    
    private static final int    LOGO_X_IMAGE = 509;
    private static final int    LOGO_Y_IMAGE = 640;
    
    private static final int    START_Y_IMAGE = 640;
    private static final int    OPTIONS_Y_IMAGE = 673;
    private static final int    EXIT_Y_IMAGE = 706;
    
    private static final int    START_Y_POSITION = 400;
    private static final int    OPTIONS_Y_POSITION = 440;
    private static final int    EXIT_Y_POSITION = 480; 
            
    public Menu(){
        try {
            background = ImageIO.read(getClass().getResource("/Menu_Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logo = GamePanel.getTextureAtlas().getImage(LOGO_X_IMAGE, LOGO_Y_IMAGE, LOGO_WIDTH, LOGO_HEIGHT);
        
        start_button = GamePanel.getTextureAtlas().getImage(0, START_Y_IMAGE, BUTTONS_WIDTH, BUTTONS_HEIGHT);
        options_button = GamePanel.getTextureAtlas().getImage(0, OPTIONS_Y_IMAGE, BUTTONS_WIDTH, BUTTONS_HEIGHT);
        exit_button = GamePanel.getTextureAtlas().getImage(0, EXIT_Y_IMAGE, BUTTONS_WIDTH, BUTTONS_HEIGHT);
                
        buttonsColor = new Color[3];
        font = new Font("Verdana", Font.BOLD, FONT_SIZE);    
    }

    @Override
    public void render(Graphics2D g)
    {
        g.drawImage(background, 0, 0, null);
        g.drawImage(logo, LOGO_X_POSITION, LOGO_Y_POSITION, null);
        
        g.drawImage(start_button, BUTTONS_X_POSITION, START_Y_POSITION, null);
        g.drawImage(options_button, BUTTONS_X_POSITION, OPTIONS_Y_POSITION, null);
        g.drawImage(exit_button, BUTTONS_X_POSITION, EXIT_Y_POSITION, null);
        
        g.setColor(Color.WHITE);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() < BUTTONS_X_POSITION + BUTTONS_WIDTH &&
                e.getX() > BUTTONS_X_POSITION && 
                e.getY() < START_Y_POSITION  + BUTTONS_HEIGHT &&
                e.getY() > START_Y_POSITION)
            GamePanel.changeState(State.GAME);
        if(e.getX() < BUTTONS_X_POSITION + BUTTONS_WIDTH &&
                e.getX() > BUTTONS_X_POSITION &&
                e.getY() < OPTIONS_Y_POSITION  + BUTTONS_HEIGHT &&
                e.getY() > OPTIONS_Y_POSITION)
            GamePanel.changeState(State.OPTIONS);
        if(e.getX() < BUTTONS_X_POSITION + BUTTONS_WIDTH &&
                e.getX() > BUTTONS_X_POSITION &&
                e.getY() < EXIT_Y_POSITION  + BUTTONS_HEIGHT &&
                e.getY() > EXIT_Y_POSITION)
            System.exit(0);         
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
}

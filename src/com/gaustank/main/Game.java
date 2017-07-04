package com.gaustank.main;

import com.gaustank.entity.*;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Game extends GameState {
    private BufferedImage       background;
    private Environment[]       objects;
    private int                 backgroundPosition;
      
    private static final int    COUNT_OBJECTS = 10000;
    private static final int    COUNT_TYPE_OBJECTS = 4;
    private static final int    OUT_OBJECT_POSITION = -256;
   
    
    public Game(){
        try {
            background = ImageIO.read(getClass().getResource("/Game_Background.png"));
            backgroundPosition = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random generator = new Random(System.currentTimeMillis());
        objects = new Environment[COUNT_OBJECTS];
        for(int numObject = 0; numObject < COUNT_OBJECTS; numObject++)
        {
            objects[numObject] = new Environment(generator.nextInt(COUNT_TYPE_OBJECTS));
            objects[numObject].setPosition(numObject * 256);
        }
    }  

    @Override
    public void update() {
        int position;
        int delta;
        int speed = (int)player.getSpeed();
        
        if(backgroundPosition > 0)
            backgroundPosition -= speed / 5;
        else
            backgroundPosition = GamePanel.WIDTH;  
                
        for(int numObject = 0; numObject < COUNT_OBJECTS; numObject++)
        {
            position = objects[numObject].getPosition() - speed;
            delta = OUT_OBJECT_POSITION - position;
            if(position <= OUT_OBJECT_POSITION)
                objects[numObject].setPosition((COUNT_OBJECTS-1)*256 - delta);
            else
                objects[numObject].setPosition(position);
            
            if(position > 0 || position < 1280)
                objects[numObject].checkCollision(player);
        }

        player.update();
    }

    @Override
    public void render(Graphics2D g) {
        
        g.drawImage(background, backgroundPosition, 0, null);
        if(backgroundPosition > 0)
            g.drawImage(background, backgroundPosition - GamePanel.WIDTH, 0, null);
                
        for(int numObject = 0; numObject < COUNT_OBJECTS; numObject++)
        {
            g.drawImage(objects[numObject].getImage(),
                        objects[numObject].getPosition(),
                        GamePanel.HEIGHT - objects[numObject].getHeight(),
                        null);
        }
        player.render(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {}
}

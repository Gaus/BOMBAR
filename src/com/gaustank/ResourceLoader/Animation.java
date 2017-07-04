package com.gaustank.ResourceLoader;

import com.gaustank.main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Animation {

    private int currentFrame;
    private int countFrame;
    private TypeAnimation type;
    private BufferedImage[] images;
    
    private static final int COUNT_FRAME_PLANE = 2;
    private static final int COUNT_FRAME_BOMB_EXPLOSION = 15;
    
    public Animation(int x, int y, int width, int height, TypeAnimation type) {
        this.type = type;
        switch(type){
            case PLANE:
                countFrame = COUNT_FRAME_PLANE;
                break;
            case EXPLOSION:
                countFrame = COUNT_FRAME_BOMB_EXPLOSION;
                break;
            default:
                countFrame = COUNT_FRAME_BOMB_EXPLOSION;
                break;
        }
        images = new BufferedImage[countFrame];
        for(int i = 0; i < countFrame; i++)
        {
            images[i] = GamePanel.getTextureAtlas().getImage((int)(x + (i%5)*width), (int)(y + (i/5)*height), width, height);
        }
    }
        
    public boolean render(Graphics2D g, int x, int y){
        int i = currentFrame / 2;
        g.drawImage(images[i], x, y, null);
        if(i < (countFrame - 1))
            currentFrame++;
        else{
            currentFrame = 0;
            if(type == TypeAnimation.EXPLOSION)
                return true;
        }
        return false;
    }
}
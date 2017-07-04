package com.gaustank.ResourceLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {
    private BufferedImage texture;
    public Sprite(){
        try {
            texture = ImageIO.read(getClass().getResource("/game_textures.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(int x, int y, int width, int height){     
        return texture.getSubimage(x, y, width, height);      
    }
}

package com.gaustank.entity;

import com.gaustank.Player.Player;
import com.gaustank.main.*;
import java.awt.image.BufferedImage;

public class Environment {

    private int id;
    private int position;
    private BufferedImage tiles;

    public static final int TILES_WIDTH = 256;
    public static final int TILES_HEIGHT = GamePanel.HEIGHT - 16;

    public Environment(int id) {
        this.id = id;
        tiles = GamePanel.getTextureAtlas().getImage(0, id * 128, 256, 128);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getHeight() {
        return tiles.getHeight();
    }

    public BufferedImage getImage() {
        return tiles;
    }

    public void destroy() {
        tiles = GamePanel.getTextureAtlas().getImage(256, id * 128, 256, 128);
    }

    public void checkCollision(Player player) {  
        Bomb bombs[] = player.getBombs();
        int countBombs = player.getCountBombs();
        int x_player_position = player.getX();
        int y_player_position = player.getY() + 100;
        if (y_player_position > TILES_HEIGHT
                && x_player_position >= position
                && x_player_position <= position + TILES_WIDTH
                && player.getActive() == true) {
            player.destroy();
            this.destroy();
        }

        for (int i = 0; i < countBombs; i++) {
            int x_bomb_positon = bombs[i].getX();
            int y_bomb_positon = bombs[i].getY();
            if (y_bomb_positon > TILES_HEIGHT
                    && x_bomb_positon >= position
                    && x_bomb_positon <= position + TILES_WIDTH) {
                bombs[i].destroy();
                this.destroy();
            }
        }
    }
}

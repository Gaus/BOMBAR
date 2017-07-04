package com.gaustank.entity;

import com.gaustank.main.GamePanel;
import java.awt.image.BufferedImage;

public enum TypeBombs {
    FRAG() {
        @Override
        public Bomb getType() {
            return new Bomb.Type("FRAG")
                    .image(0, 803, 26, 7)
                    .explosion(0, 902, 96, 96)
                    .build();
        }

        @Override
        public BufferedImage getImage() {
            return GamePanel.getTextureAtlas().getImage(0, 803, 26, 7); 
        }
    },
    DEMOLITION() {
        @Override
        public Bomb getType() {
            return new Bomb.Type("DEMOLITION")
                    .image(26, 803, 27, 9)
                    .explosion(509, 809, 112, 127)
                    .build();
        }

        @Override
        public BufferedImage getImage() {
            return GamePanel.getTextureAtlas().getImage(26, 803, 27, 9);
        }
    },
    CONCRETE() {
        @Override
        public Bomb getType() {
            return new Bomb.Type("CONCRETE")
                    .image(53, 803, 27, 11)
                    .explosion(0, 1190, 68, 171)
                    .build();
        }

        @Override
        public BufferedImage getImage() {
            return GamePanel.getTextureAtlas().getImage(53, 803, 27, 11);
        }
    };
    
    public abstract BufferedImage getImage();
    public abstract Bomb getType();
}

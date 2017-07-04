package com.gaustank.entity;

import com.gaustank.ResourceLoader.Animation;
import com.gaustank.ResourceLoader.TypeAnimation;
import com.gaustank.main.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bomb {
    private BufferedImage image;
    private Animation explosion;
    private State state;
    private Type type;
        
    private double x;
    private double y;
    private double ySpeed;
    private double xSpeed;
    private int height;
    private int width;
    private int heightExplosion;

    private static final int SHIFT_X = 74;
    private static final int SHIFT_Y = 40;

    private static final double FREE_ACCELERATION = 0.02;
    private static final double AIR_RESISTANCE = 0.2;
        
    public enum State {
        DEACTIVITY, ACTIVITY, EXPLOSION;
    }
    
    public static class Type {   
        private String name;
        private BufferedImage image;
        private Animation explosion;
        private int heightExplosion;
        private int height;
        private int width;
        private int count;
        
        public Type(String name){
            this.name = name;
        }
        
        public Type image(int x_image, int y_image, int width_image, int height_image){
            this.image = GamePanel.getTextureAtlas().getImage(
                    x_image,
                    y_image,
                    width_image,
                    height_image);
            this.height = height_image;
            this.width = width_image;
            return this;
        }
        
        public Type explosion(int x_animation, int y_animation, int width_animation, int height_animation){
            this.explosion = new Animation(
                    x_animation,
                    y_animation,
                    width_animation,
                    height_animation,
                    TypeAnimation.EXPLOSION);
            heightExplosion = height_animation;
            return this;
        }
        
        public Bomb build(){
            return new Bomb(this);
        }
    }

    public Bomb(Type type){
        state = State.DEACTIVITY;
        this.type = type;
        image = type.image;
        explosion =  type.explosion;
        heightExplosion = type.heightExplosion;
        height = type.height;
        width = type.width;
    }

    public int getX() {
        if (state == State.ACTIVITY)
            return (int)x;
        return 0;
    }

    public int getY() {
        if (state == State.ACTIVITY)
            return (int)y + 24;
        return 0;
    }
    
    public int getWidth(){
        return width;
    }
    
    public BufferedImage getImage() {
        return image;
    }

    public void activate(int x, int y, double speed)
    {
        state = State.ACTIVITY;
        this.x = x + SHIFT_X;
        this.y = y + SHIFT_Y;
        this.xSpeed = speed;
    }
    
    public void destroy()
    {
        state = State.EXPLOSION;
    }
    
    public void update(double speed)
    {
        switch(state){
            case ACTIVITY:
                y += ySpeed;
                ySpeed += FREE_ACCELERATION;
                x -= (AIR_RESISTANCE + speed - xSpeed);
                break;
            default:
                x -= xSpeed;
                break;
        }
    }

    public void render(Graphics2D g)
    { 
        switch(state){
            case ACTIVITY:
                g.drawImage(image, (int)x, (int)y, null);
                break;
            case EXPLOSION:
                if(explosion.render(g, (int)x, (int)y - heightExplosion)){
                    state = state.DEACTIVITY;
                }
                break;
            default:
                break;
        }
    } 
}

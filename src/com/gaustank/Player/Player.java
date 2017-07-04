package com.gaustank.Player;

import com.gaustank.Player.State.*;
import com.gaustank.ResourceLoader.Animation;
import com.gaustank.ResourceLoader.TypeAnimation;
import com.gaustank.entity.Bomb;
import com.gaustank.entity.TypeBombs;
import com.gaustank.main.GamePanel;
import com.gaustank.main.State;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    private boolean actions[];

    private Bomb[] bombs;
    private TypeBombs[] typeBombs;
    private Plane plane;
    private PlayerState state;
    private Animation planeAnimation;

    private String planeName;
    private boolean active;

    private double x;
    private double y;
    private double xSpeed;
    private double ySpeed;
    private double acceleration;
    private int maxSpeed;
    private int countBombs;
    private int currentCountBombs;
    private int maxCountBombs;
    private int mass;
    private int lift;
    private int fuel;
    private int width;
    private int height;
    private int[] countTypeBombs;

    private static final int START_X_POSITION = -100;
    private static final int START_Y_POSITION = 200;
    private static final int COUNT_ACTIONS = 8;
    private static final double FREE_ACCELERATION = 0.03;

    private static final int X_ANIMATION_EXPLOSION = 509;
    private static final int Y_ANIMATION_EXPLOSION = 809;
    private static final int WIDTH_ANIMATION_EXPLOSION = 112;
    private static final int HEIGHT_ANIMATION_EXPLOSION = 127;

    public static class Plane {

        private String name;
        private Animation animation;
        private int width;
        private int height;
        private int mass;
        private int lift;
        private int fuel;
        private int maxSpeed;
        private int maxCountBombs;
        private double acceleration;

        public Plane(String name) {
            this.name = name;
        }

        public Plane animation(int xPosition, int yPosition, int width, int height) {
            this.animation = new Animation(xPosition, yPosition, width, height, TypeAnimation.PLANE);
            this.width = width;
            this.height = height;
            return this;
        }

        public Plane mass(int mass) {
            this.mass = mass;
            return this;
        }

        public Plane lift(int lift) {
            this.lift = lift;
            return this;
        }

        public Plane fuel(int fuel) {
            this.fuel = fuel;
            return this;
        }

        public Plane maxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
            return this;
        }

        public Plane maxCountBombs(int maxCountBombs) {
            this.maxCountBombs = maxCountBombs;
            return this;
        }

        public Plane acceleration(double acceleration) {
            this.acceleration = acceleration;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }

    public Player(Plane plane) {
        actions = new boolean[COUNT_ACTIONS];
        this.plane = plane;
        state = new Coming(this);
        bombs = new Bomb[maxCountBombs];
        typeBombs = TypeBombs.values();
        countTypeBombs = new int[typeBombs.length];
    }

    public void init() {
        planeName = plane.name;
        width = plane.width;
        height = plane.height;
        mass = plane.mass;
        lift = plane.lift;
        fuel = plane.fuel;
        maxSpeed = plane.maxSpeed;
        acceleration = plane.acceleration;
        maxCountBombs = plane.maxCountBombs;
        planeAnimation = plane.animation;
        active = true;
        x = START_X_POSITION;
        y = START_Y_POSITION;
    }

    public void loadBombs() {
        currentCountBombs = 0;
        for (int numBomb = 0; numBomb < maxCountBombs; numBomb++) {
            setBomb(0);
        }
    }

    public void setBomb(int numTypeBomb) {
        if (currentCountBombs < maxCountBombs) {
            bombs[currentCountBombs] = typeBombs[numTypeBomb].getType();
            countTypeBombs[numTypeBomb]++;
            currentCountBombs++;
            countBombs = currentCountBombs;
        }
    }

    public TypeBombs[] getTypeBombs() {
        return typeBombs;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public Plane getPlane() {
        return plane;
    }

    public String getPlaneName() {
        return planeName;
    }

    public int getWidth() {
        return width;
    }

    public void increaseX(int speed) {
        x += speed;
    }

    public double getSpeed() {
        return xSpeed;
    }

    public void setSpeed(double speed) {
        this.xSpeed = speed;
    }

    public int getFuel() {
        return fuel;
    }

    public int getCurrentCountBombs() {
        return currentCountBombs;
    }
    
    public int getCountBombs() {
        return countBombs;
    }

    public int getCountTypeBomb(int numTypeBomb) {
        return countTypeBombs[numTypeBomb];
    }

    public Bomb[] getBombs() {
        return bombs;
    }

    public boolean getActive() {
        return active;
    }

    public void updatePlane() {
        int force = (int) (lift * (xSpeed * xSpeed / 2) - mass * FREE_ACCELERATION);
        if (force < 0 || fuel <= 0) {
            ySpeed -= FREE_ACCELERATION;
            y -= ySpeed;
        } else {
            ySpeed = 0;
            fuel -= xSpeed;
        }
    }

    public void update() {
        state.update();

        for (int numBomb = 0; numBomb < countBombs; numBomb++) {
            bombs[numBomb].update(xSpeed);
        }
    }

    public void render(Graphics2D g) {
        for (int numBomb = 0; numBomb < countBombs; numBomb++) {
            bombs[numBomb].render(g);
        }
        if (planeAnimation.render(g, (int) x, (int) y)) {
            state = new Coming(this);
            GamePanel.changeState(State.MENU);
        }
        state.render(g);
    }

    public void render(Graphics2D g, int x, int y) {
        planeAnimation.render(g, x, y);
    }

    public void changeState(PlayerState state) {
        this.state = state;
    }

    public void resetBomb() {
        if (currentCountBombs > 0) {
            bombs[currentCountBombs - 1].activate((int) x, (int) y, xSpeed);
            currentCountBombs--;
        }
    }

    public void destroy() {
        resetPlane();
        planeAnimation = new Animation(
                X_ANIMATION_EXPLOSION,
                Y_ANIMATION_EXPLOSION,
                WIDTH_ANIMATION_EXPLOSION,
                HEIGHT_ANIMATION_EXPLOSION,
                TypeAnimation.EXPLOSION);
    }

    public void resetPlane() {
        xSpeed = 0;
        ySpeed = 0;
        fuel = 0;
        mass = 0;
        acceleration = 0;
        active = false;
    }

    public void updateActions() {
        if (actions[0] == true && y >= 0) {
            y -= 1;
        }
        if (actions[1] == true && y <= 520) {
            y += 1;
        }
        if (actions[2] == true && x >= 0) {
            x -= 1;
        }
        if (actions[3] == true && x <= 1100) {
            x += 1;
        }
        if (actions[4] == true && xSpeed < maxSpeed) {
            xSpeed += acceleration;
        }
        if (actions[5] == true && xSpeed > 0) {
            xSpeed -= acceleration;
        }
    }

    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        switch (k) {
            case KeyEvent.VK_W:
                actions[0] = true;
                break;
            case KeyEvent.VK_S:
                actions[1] = true;
                break;
            case KeyEvent.VK_A:
                actions[2] = true;
                break;
            case KeyEvent.VK_D:
                actions[3] = true;
                break;
            case KeyEvent.VK_SHIFT:
                actions[4] = true;
                break;
            case KeyEvent.VK_CONTROL:
                actions[5] = true;
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        state.keyReleased(e);
        int k = e.getKeyCode();
        switch (k) {
            case KeyEvent.VK_W:
                actions[0] = false;
                break;
            case KeyEvent.VK_S:
                actions[1] = false;
                break;
            case KeyEvent.VK_A:
                actions[2] = false;
                break;
            case KeyEvent.VK_D:
                actions[3] = false;
                break;
            case KeyEvent.VK_SHIFT:
                actions[4] = false;
                break;
            case KeyEvent.VK_CONTROL:
                actions[5] = false;
                break;
            case KeyEvent.VK_SPACE:
                resetBomb();
            default:
                break;
        }
    }
}

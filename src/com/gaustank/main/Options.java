package com.gaustank.main;

import com.gaustank.Player.*;
import com.gaustank.entity.Bomb;
import com.gaustank.entity.TypeBombs;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Options extends GameState {
    private BufferedImage background;

    private BufferedImage back_button;
    private BufferedImage up_button;
    private BufferedImage down_button;
    private BufferedImage left_button;
    private BufferedImage right_button;

    private Planes[] planes;
    private TypeBombs[] typeBombs;

    private Font font;
    private int num = 0;
    private int plane_x_position;
    private int plane_y_position = 300;
    
    private int button_x_position_plane[] = {0, 0};
    private int button_y_position_plane;
    private int[] button_x_positions_bombs;
    private int[] button_y_positions_bombs;

    private static final int SIZE_FONT = 18;
    private static final int SIZE_BUTTONS = 16;

    private static final int BACK_BUTTON_WIDTH = 211;
    private static final int BACK_BUTTON_HEIGHT = 33;
    private static final int BACK_BUTTON_X_POSITION = 535;
    private static final int BACK_BUTTON_Y_POSITION = 520;

    private static final int BUTTONS_X_IMAGE[] = {0, 0, 16, 32, 48};
    private static final int BUTTONS_Y_IMAGE[] = {739, 772, 772, 772, 772};

    private static final int BUTTONS_SHIFT = 20;

    private static final int BOMBS_X_POSITION = 550;
    private static final int BOMBS_Y_POSITION = 420;
    
    private static final int TITLE_X_POSTIONS = 615;
    private static final int TITLE_Y_POSTIONS = 280;
    
    public Options() {
        planes = Planes.values();
        player = planes[0].getPlane();
        player.loadBombs();
        typeBombs = player.getTypeBombs();

        plane_x_position = (GamePanel.WIDTH - player.getWidth()) / 2;

        back_button = GamePanel.getTextureAtlas().getImage(BUTTONS_X_IMAGE[0], BUTTONS_Y_IMAGE[0], BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        up_button = GamePanel.getTextureAtlas().getImage(BUTTONS_X_IMAGE[1], BUTTONS_Y_IMAGE[1], SIZE_BUTTONS, SIZE_BUTTONS);
        left_button = GamePanel.getTextureAtlas().getImage(BUTTONS_X_IMAGE[3], BUTTONS_Y_IMAGE[3], SIZE_BUTTONS, SIZE_BUTTONS);
        right_button = GamePanel.getTextureAtlas().getImage(BUTTONS_X_IMAGE[4], BUTTONS_Y_IMAGE[4], SIZE_BUTTONS, SIZE_BUTTONS);
        setPostionsButtons();
        
        font = new Font("Verdana", Font.BOLD, SIZE_FONT);
        try {
            background = ImageIO.read(getClass().getResource("/Menu_Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPostionsButtons() {
        button_x_position_plane[0] = plane_x_position - BUTTONS_SHIFT;
        button_x_position_plane[1] = plane_x_position + player.getWidth() + BUTTONS_SHIFT;
        button_y_position_plane = plane_y_position;

        button_x_positions_bombs = new int[typeBombs.length];
        button_y_positions_bombs = new int[typeBombs.length];

        int widthBomb;
        for (int numBomb = 0; numBomb < typeBombs.length; numBomb++) {
            widthBomb = typeBombs[numBomb].getImage().getWidth();
            button_x_positions_bombs[numBomb] = BOMBS_X_POSITION + widthBomb + (widthBomb - SIZE_BUTTONS) / 2 + numBomb * 50;
            button_y_positions_bombs[numBomb] = BOMBS_Y_POSITION - 20;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setFont(font);
        g.drawImage(background, 0, 0, null);
        g.drawImage(back_button, BACK_BUTTON_X_POSITION, BACK_BUTTON_Y_POSITION, null);

        player.render(g, plane_x_position, plane_y_position);
        g.drawImage(left_button, button_x_position_plane[0], button_y_position_plane, null);
        g.drawImage(right_button, button_x_position_plane[1], button_y_position_plane, null);
        g.drawString(player.getPlaneName(),  TITLE_X_POSTIONS, TITLE_Y_POSTIONS);
        
        Bomb typeBomb;
        int widthBomb;
        for (int numBomb = 0; numBomb < typeBombs.length; numBomb++) {
            typeBomb = typeBombs[numBomb].getType();
            widthBomb = typeBomb.getWidth();
            g.drawImage(up_button, button_x_positions_bombs[numBomb], BOMBS_Y_POSITION - SIZE_BUTTONS - 10, null);
            g.drawImage(typeBombs[numBomb].getType().getImage(), BOMBS_X_POSITION + widthBomb + numBomb * 50, BOMBS_Y_POSITION, null);
            g.drawString(String.format("%d", player.getCountTypeBomb(numBomb)), BOMBS_X_POSITION + widthBomb + numBomb * 50, BOMBS_Y_POSITION + 50);
        }
    }

    public void selectPlayer(int i) {
        num += i;
        if (num == planes.length) {
            num = 0;
        }
        if (num < 0) {
            num = planes.length - 1;
        }
        player = planes[num].getPlane();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx > BACK_BUTTON_X_POSITION
                && mx < BACK_BUTTON_X_POSITION + BACK_BUTTON_WIDTH
                && my > BACK_BUTTON_Y_POSITION
                && my < BACK_BUTTON_Y_POSITION + BACK_BUTTON_HEIGHT) {
            GamePanel.changeState(State.MENU);
        }

        if (mx < button_x_position_plane[0] + SIZE_BUTTONS
                && mx > button_x_position_plane[0]
                && my < button_y_position_plane + SIZE_BUTTONS
                && my > button_y_position_plane) {
            selectPlayer(-1);
        }

        if (mx < button_x_position_plane[1] + SIZE_BUTTONS
                && mx > button_x_position_plane[1]
                && my < button_y_position_plane + SIZE_BUTTONS
                && my > button_y_position_plane) {
            selectPlayer(1);
        }

        for (int numBomb = 0; numBomb < typeBombs.length; numBomb++) {
            if (mx < button_x_positions_bombs[numBomb] + SIZE_BUTTONS
                    && mx > button_x_positions_bombs[numBomb]
                    && my < BOMBS_Y_POSITION - 10
                    && my > BOMBS_Y_POSITION - SIZE_BUTTONS - 10) {
                player.setBomb(numBomb);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        switch (k) {
            case KeyEvent.VK_1:
                player.setBomb(0);
                break;
            case KeyEvent.VK_2:
                player.setBomb(1);
                break;
            case KeyEvent.VK_3:
                player.setBomb(2);
                break;
            default:
                break;
        }
    }
}

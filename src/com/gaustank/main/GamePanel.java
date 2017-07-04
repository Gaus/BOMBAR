package com.gaustank.main;
import com.gaustank.ResourceLoader.Sprite;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public final class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener 
{
    public static final int         WIDTH		= 1280;
    public static final int         HEIGHT		= 720;
    public static String            TITLE		= "BOMBAR";
    public static final int         CLEAR_COLOR         = 0xffffffff;
    
    private static final int    SECOND        = 1000000000;
    private static final double UPD_INTERVAL  = SECOND / 60.0D;
    
    private static JFrame           window;
    private static BufferedImage    buffer;
    private static int[]            bufferData;
    private static Graphics         bufferGraphics;
    private static BufferStrategy   bufferStrategy;
       
    private static GameState    currentState;
    private static Menu         menuState;
    private static Game         gameState;
    private static Options      optionsState;
    
    private static Sprite       textureAtlas;
    
    private boolean             running;
    private Thread              gameThread;
    private Graphics2D          graphics;  
        
    public GamePanel(){
        super();    
        graphics = (Graphics2D) bufferGraphics;
        textureAtlas = new Sprite();
        
        stateCreate();
        running = false;
        start();
    }
    
    private void stateCreate(){
        menuState = new Menu();
        gameState = new Game();
        optionsState = new Options();       
        currentState = menuState;
    }
     
    public synchronized void start()
    {
        if (running)
            return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop()
    {
        if (!running)
            return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
        
    @Override
    public void run()
    {
        int fps = 0;
	int upd = 0;
	int updl = 0;
	double delta = 0;
   
        boolean render;

        long now;
        long count = 0;
        long elapsedTime;
        long lastTime = System.nanoTime();
	while (running)                                       
	{
            now = System.nanoTime();
            elapsedTime = now - lastTime;
            delta += ((elapsedTime) / UPD_INTERVAL);
            lastTime = now;
            count += elapsedTime;
            render = false;           
            while (delta > 1)                                
            {
		update();
		upd++;
		delta--;
		if (render) {
                    updl++;
		}
                else {
                    render = true;
		}
            }
            if (render) {                                     
                render();
		fps++;
            }
            else {
		try {
                    Thread.sleep(1);
		} catch (InterruptedException e) {
                    e.printStackTrace();
		}
            }
            if(count >= SECOND){
                window.setTitle("BOMBAR" + "     Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);
                fps = 0;
                upd = 0;
                updl = 0;
                count = 0;
            }
	}
        stop();
    }
    
    private void update()
    {
        currentState.update();
    }

    private void render()
    {
        Arrays.fill(bufferData, CLEAR_COLOR);
        currentState.render(graphics);
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }
    
    public static void changeState(State state) {
        switch(state)
        {
            case GAME:
                currentState = gameState;
                break;
            case MENU:
                currentState = menuState;
                break;
            case OPTIONS:
                currentState = optionsState;
                break;
            default: break;
        }
    }
    
    public static Sprite getTextureAtlas() {
        return textureAtlas;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        currentState.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentState.keyReleased(e);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentState.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            window = new JFrame(TITLE);
            window.setSize(WIDTH, HEIGHT);

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setLocationRelativeTo(null);
            window.setResizable(false);
            
            Canvas content = new Canvas();
            content.setPreferredSize(new Dimension(WIDTH, HEIGHT));

            window.getContentPane().add(content);
            window.pack();
            
            content.createBufferStrategy(2);
            bufferStrategy = content.getBufferStrategy();
            buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
            bufferGraphics = buffer.getGraphics();
            ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            content.createBufferStrategy(2);
            bufferStrategy = content.getBufferStrategy();
            
            GamePanel gamePanel = new GamePanel();
            content.addKeyListener(gamePanel);
            content.addMouseListener(gamePanel);
            content.addMouseMotionListener(gamePanel);
            window.setFocusable(true);
            window.requestFocus();
            
            window.setVisible(true);   
        }
        });
    }   
}

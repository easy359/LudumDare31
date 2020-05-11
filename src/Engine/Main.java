package Engine;

import Game.Game;
import Listener.KeyInput;
import Listener.MouseInput;
import Listener.MouseMotionInput;
import Menu.OptionMenu;
import Menu.PauseMenu;
import Util.HitBox;
import Util.Resources;
import Util.Static;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Main extends JPanel implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 832, HEIGHT = 640;
    public static final String NAME = "All On One Screen";
    public static final HitBox screenBox = new HitBox(0, 0, 832, 640);
    public static KeyInput keyInput;
    public static MouseInput mouseInput;
    public static MouseMotionInput mouseMotionInput;
    public static Resources resources;

    private Game game;
    private JFrame jFrame;
    private PauseMenu pauseMenu;
    private OptionMenu optionMenu;
    private Thread thread;
    private BufferedImage image;
    private int[] pixels;

    private boolean running;
    private boolean pause;
    private boolean options;

    public Main() {
        init();
    }

    public void init() {
        resources = new Resources();
        resources.load();
        pauseMenu = new PauseMenu();
        optionMenu = new OptionMenu();
        game = new Game();
        thread = new Thread(this);

        keyInput = new KeyInput(255);
        mouseInput = new MouseInput(255);
        mouseMotionInput = new MouseMotionInput();

        setFocusable(true);
        requestFocus();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(keyInput);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseMotionInput);

        jFrame = new JFrame(NAME);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(this);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    }

    public synchronized void start() {
        running = true;
        pause = true;
        options = false;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        pause = false;
        setVisible(false);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                render();
                delta--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }

    }

    private void update() {
        keyInput.update();
        mouseInput.update();
        if (keyInput.isCurrentKeyPressed(Static.ESC)) {
            if (game.running()) {
                pause = pause ? false : true;
                options = false;
            }
        }

        if (pause) {
            pauseMenu.update();
            if (pauseMenu.newGame()) {
                game = new Game();
                game.start();
                pause = false;
            }
            if (pauseMenu.options()) {
                pause = false;
                options = true;
            }
        } else if (options) {
            optionMenu.update();
            game.setMode(optionMenu.getMode());
            if (optionMenu.goBack()) {
                pause = true;
                options = false;
            }
        } else if (!pause && !options) {
            game.update();
            if (game.gameOver()) {
                pause = true;
            }
        }
    }

    private void render() {
        if (pause) {
            pauseMenu.render(pixels);
        } else if (options) {
            optionMenu.render(pixels);
        } else if (!pause && !options) {
            game.render(pixels);
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        Arrays.fill(pixels, 0x000000);
        g.dispose();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
}

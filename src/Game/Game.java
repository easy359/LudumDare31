package Game;

import Objects.Zombie;
import Player.Player;
import Util.Mode;
import Util.Resources;
import Util.Static;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private Random rand;
    private Player player;
    private boolean running;
    private boolean gameOver;
    private Mode mode;
    private int[] background;
    private boolean spawn;
    private int zomC = 0;
    private int zomDE = 200;
    private int zomDM = 150;
    private int zomDH = 250;
    private int level = 1;
    private int levelC = 0;
    private int levelDE = 3;
    private int levelDM = 4;
    private int levelDH = 5;

    public Game() {
        init();
    }

    private ArrayList<Zombie> zombies;

    private void init() {
        rand = new Random();
        spawn = true;
        mode = Mode.easy;
        zombies = new ArrayList<Zombie>();
        running = true;
        gameOver = false;
        player = new Player();
        background = Engine.Main.resources.background.getPixels();
    }

    public void update() {
        if (Engine.Main.keyInput.isCurrentKeyPressed(Static.P)) {
            if (zomDE > 1) {
                zomDE -= 10;
            }
            if (zomDM > 1) {
                zomDM -= 10;
            }
            if (zomDH > 1) {
                zomDH -= 10;
            }
        }
        if (Engine.Main.keyInput.isCurrentKeyPressed(Static.O)) {
            if (zomDE < 500) {
                zomDE += 10;
            }
            if (zomDM < 500) {
                zomDM += 10;
            }
            if (zomDH < 500) {
                zomDH += 10;
            }

        }
        player.update(zombies);
        for (Zombie zom : zombies) {
            zom.update(player);
        }

        for (int i = 0; i < zombies.size(); i++) {
            if (zombies.get(i).getHealth() <= 0) {
                zombies.remove(i);
                i--;
            }
        }
        if (player.getHealth() <= 0) {
            running = false;
            gameOver = true;
        }

        if (spawn == true) {
            spawn = false;
            int amount = rand.nextInt(level);
            if (mode == Mode.easy) {
                amount++;
            } else if (mode == Mode.medium) {
                amount += 2;
            } else if (mode == Mode.hard) {
                amount += 3;
            }
            for (int i = 0; i < amount; i++) {
                zombies.add(new Zombie(mode));
            }
        }
        if (spawn == false) {
            zomC++;
            if (mode == Mode.easy) {
                if (zomC > zomDE) {
                    zomC = 0;
                    spawn = true;
                    levelC++;
                    if (levelC > levelDE) {
                        level++;
                        levelC = 0;
                    }
                }
            } else if (mode == Mode.medium) {
                if (zomC > zomDM) {
                    spawn = true;
                    zomC = 0;
                    levelC++;
                    if (levelC > levelDM) {
                        level++;
                        levelC = 0;
                    }
                }
            } else if (mode == Mode.hard) {
                if (zomC > zomDH) {
                    spawn = true;
                    zomC = 0;
                    levelC++;
                    if (levelC > levelDH) {
                        level++;
                        levelC = 0;
                    }
                }
            }
        }
    }

    private int[] pOne;
    private int[] pTwo;
    private int[] heart;

    public void render(int[] pixels) {
        Resources.addPixels(pixels, background, 0, 0, 832, 640);
        player.render(pixels);
        for (Zombie zom : zombies) {
            zom.render(pixels);
        }
        int one = level / 10;
        int two = level % 10;
        pOne = Engine.Main.resources.numbers.get(one).getPixels();
        pTwo = Engine.Main.resources.numbers.get(two).getPixels();
        Resources.addPixels(pixels, pOne, 10, 0, 16, 16);
        Resources.addPixels(pixels, pTwo, 26, 0, 16, 16);
        int health = player.getHealth();
        heart = Engine.Main.resources.heart.getPixels();
        for (int i = 816; i > (816 - (health * 16)); i -= 16) {
            Resources.addPixels(pixels, heart, i, 0, 16, 16);
        }
    }

    public void start() {
        running = true;
    }

    public boolean running() {
        return running;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public boolean gameOver() {
        return gameOver;
    }
}

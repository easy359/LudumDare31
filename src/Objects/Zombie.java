package Objects;

import Player.Player;
import Util.*;

import java.util.ArrayList;
import java.util.Random;

public class Zombie {

    private Random rand;
    private static ArrayList<Location> locations;
    private static boolean first = true;
    private Mode mode;
    private int x, y;
    private int width, height;
    private HitBox hitBox;
    private static int[] left;
    private static int[] right;
    private Direction dir;
    private int speed;
    private int dmg;
    private int health;

    public Zombie(Mode mode) {
        if (first == true) {
            first = false;
            locations = new ArrayList<Location>();
            locations.add(new Location(0, 213 - 32));
            locations.add(new Location(0, 426 - 32));
            locations.add(new Location(832 - 32, 213 - 32));
            locations.add(new Location(832 - 32, 426 - 32));
            locations.add(new Location(277 - 16, 0));
            locations.add(new Location(555 - 16, 0));
            locations.add(new Location(277 - 16, 640 - 64));
            locations.add(new Location(555 - 16, 640 - 64));
            left = Engine.Main.resources.zombieLeft.getPixels();
            right = Engine.Main.resources.zombieRight.getPixels();
        }
        this.mode = mode;
        init();
    }

    private void init() {
        rand = new Random();
        int pos = rand.nextInt(8);
        Location loc = locations.get(pos);
        x = loc.getX();
        y = loc.getY();
        width = 32;
        height = 64;
        hitBox = new HitBox(x + 4, y + 4, 32 - 4, 64 - 4);
        dir = Direction.left;
        if (mode == Mode.easy) {
            speed = 1;
            dmg = 1;
            health = 1;
        } else if (mode == Mode.medium) {
            speed = 2;
            dmg = 2;
            health = 2;
        } else if (mode == Mode.hard) {
            speed = 3;
            dmg = 3;
            health = 3;
        }
    }

    public void update(Player player) {
        if (this.x < player.getX()) {
            dir = Direction.right;
            x += speed;
            hitBox.setX(x);
        } else if (this.x > player.getX()) {
            dir = Direction.left;
            x -= speed;
            hitBox.setX(x);
        }
        if (this.y < player.getY()) {
            y += speed;
            hitBox.setY(y);
        } else if (this.y > player.getY()) {
            y -= speed;
            hitBox.setY(y);
        }

        if (hitBox.intersects(player.getHitBox())) {
            player.hit(dmg);
        }

    }

    public void render(int[] pixels) {
        if (dir == Direction.left) {
            Resources.addPixels(pixels, left, x, y, width, height);
        } else if (dir == Direction.right) {
            Resources.addPixels(pixels, right, x, y, width, height);
        }
    }

    public void shoot(int dmg) {
        health -= dmg;
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public int getHealth() {
        return health;
    }
}

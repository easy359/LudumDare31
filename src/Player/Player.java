package Player;

import java.util.ArrayList;

import Objects.Shoot;
import Objects.Zombie;
import Util.Direction;
import Util.HitBox;
import Util.Resources;
import Util.Static;

public class Player {

	private ArrayList<Shoot> shots;

	public Player() {
		shots = new ArrayList<Shoot>();
		init();
	}

	private int x, y;
	private HitBox hitBox;
	private int[] downStill, downShoot;
	private int[] upStill, upShoot;
	private int[] rightStill, rightShoot;
	private int[] leftStill, leftShoot;
	private int speed;
	private Direction dir;
	private boolean shot;
	private int counter;
	private int delay;
	private int health;
	private boolean hit;
	private int hitC;
	private int hitD;
	private int dmg;

	public void init() {
		x = 400;
		y = 288;
		hitBox = new HitBox(x, y, 32, 64);
		downStill = Engine.Main.resources.playerDownStill.getPixels();
		downShoot = Engine.Main.resources.playerDownShoot.getPixels();
		upStill = Engine.Main.resources.playerUpStill.getPixels();
		upShoot = Engine.Main.resources.playerUpShoot.getPixels();
		rightStill = Engine.Main.resources.playerRightStill.getPixels();
		rightShoot = Engine.Main.resources.playerRightShoot.getPixels();
		leftStill = Engine.Main.resources.playerLeftStill.getPixels();
		leftShoot = Engine.Main.resources.playerLeftShoot.getPixels();
		speed = 3;
		dir = Direction.downStill;
		shot = false;
		counter = 0;
		delay = 20;
		health = 10;
		hit = false;
		hitC = 1;
		hitD = 50;
		dmg = 2;
	}

	public void update(ArrayList<Zombie> zoms) {
		if(Engine.Main.keyInput.isCurrentKeyPressed(Static.P)){
			if(delay > 1){
				delay -= 3;
				if(speed < 10){
					speed++;
				}
			}
		}
		if(Engine.Main.keyInput.isCurrentKeyPressed(Static.O)){
			if(delay < 60){
				delay += 3;
				if(speed > 1){
					speed--;
				}
			}
		}
		if (Engine.Main.keyInput.isKeyPressed(Static.W)) {
			y -= getChange(Engine.Main.screenBox, 0, -speed);
			hitBox.setY(y);
			dir = Direction.upStill;
		}
		if (Engine.Main.keyInput.isKeyPressed(Static.S)) {
			y += getChange(Engine.Main.screenBox, 0, speed);
			hitBox.setY(y);
			dir = Direction.downStill;
		}
		if (Engine.Main.keyInput.isKeyPressed(Static.D)) {
			x += getChange(Engine.Main.screenBox, speed, 0);
			hitBox.setX(x);
			dir = Direction.rightStill;
		}
		if (Engine.Main.keyInput.isKeyPressed(Static.A)) {
			x -= getChange(Engine.Main.screenBox, -speed, 0);
			hitBox.setX(x);
			dir = Direction.leftStill;
		}
		if (Engine.Main.keyInput.isKeyPressed(Static.UP)) {
			dir = Direction.upShoot;
			shoot();
		} else if (Engine.Main.keyInput.isKeyPressed(Static.DOWN)) {
			dir = Direction.downShoot;
			shoot();
		} else if (Engine.Main.keyInput.isKeyPressed(Static.RIGHT)) {
			dir = Direction.rightShoot;
			shoot();
		} else if (Engine.Main.keyInput.isKeyPressed(Static.LEFT)) {
			dir = Direction.leftShoot;
			shoot();
		}
		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).update(zoms);
			if (shots.get(i).getX() < 0 || shots.get(i).getX() + 4 > (Engine.Main.WIDTH - 1) || y < 0 || y > (Engine.Main.HEIGHT - 1) + 2) {
				shots.remove(shots.get(i));
				i--;
				continue;
			}
			if(shots.get(i).hit() == true){
				shots.remove(shots.get(i));
				i--;
			}
		}
		
	}

	public void render(int[] pixels) {
		for (Shoot shot : shots) {
			shot.render(pixels);
		}
		if (hitC % 2 == 1) {
			if (dir == Direction.downStill) {
				Resources.addPixels(pixels, downStill, hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
			} else if (dir == Direction.downShoot) {
				Resources.addPixels(pixels, downShoot, hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
			} else if (dir == Direction.upStill) {
				Resources.addPixels(pixels, upStill, hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
			} else if (dir == Direction.upShoot) {
				Resources.addPixels(pixels, upShoot, hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
			} else if (dir == Direction.rightStill) {
				Resources.addPixels(pixels, rightStill, hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
			} else if (dir == Direction.rightShoot) {
				Resources.addPixels(pixels, rightShoot, hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
			} else if (dir == Direction.leftStill) {
				Resources.addPixels(pixels, leftStill, hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
			} else if (dir == Direction.leftShoot) {
				Resources.addPixels(pixels, leftShoot, hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
			}
		}

		if (shot == true) {
			counter++;
			if (counter > delay) {
				counter = 0;
				shot = false;
			}
		}

		if (hit == true) {
			hitC++;
			if (hitC > hitD) {
				hitC = 1;
				hit = false;
			}
		}
	}

	private void shoot() {
		if (shot != true) {
			if (dir == Direction.upShoot) {
				shots.add(new Shoot(x + 22, y + 34, 5, dmg, Direction.up, Objects.Shoot.Type.bullet));
			} else if (dir == Direction.downShoot) {
				shots.add(new Shoot(x + 22, y + 36, 5, dmg, Direction.down, Objects.Shoot.Type.bullet));
			} else if (dir == Direction.rightShoot) {
				shots.add(new Shoot(x + 28, y + 32, 5, dmg, Direction.right, Objects.Shoot.Type.bullet));
			} else if (dir == Direction.leftShoot) {
				shots.add(new Shoot(x, y + 32, 5, dmg, Direction.left, Objects.Shoot.Type.bullet));
			}
			shot = true;
			counter++;
		}
	}

	private int getChange(HitBox screenBox, int xChange, int yChange) {

		int lowestChange = speed;

		HitBox temp = new HitBox(hitBox);
		temp.setX(temp.getX() + xChange);
		temp.setY(temp.getY() + yChange);

		if (!(hitBox.intersects(screenBox))) {
			if (temp.intersects(screenBox)) {
				if (xChange > 0) {
					lowestChange = hitBox.getDistanceX2toOX1(screenBox) - 1;
				} else if (xChange < 0) {
					lowestChange = hitBox.getDistanceX1toOX2(screenBox) - 1;
				} else if (yChange > 0) {
					lowestChange = hitBox.getDistanceY2toOY1(screenBox) - 1;
				} else if (yChange < 0) {
					lowestChange = hitBox.getDistanceY1toOY2(screenBox) - 1;
				}
			}
		} else if (hitBox.intersects(screenBox)) {
			if (!(temp.fullIntersects(screenBox))) {
				if (xChange > 0) {
					lowestChange = hitBox.getDistanceX2toOX2(screenBox) - 1;
				} else if (xChange < 0) {
					lowestChange = hitBox.getDistanceX1toOX1(screenBox) - 1;
				} else if (yChange > 0) {
					lowestChange = hitBox.getDistanceY2toOY2(screenBox) - 1;
				} else if (yChange < 0) {
					lowestChange = hitBox.getDistanceY1toOY1(screenBox) - 1;
				}
			}
		}

		return lowestChange;
	}

	public void hit(int dmg) {
		if (hit == false) {
			hit = true;
			health -= dmg;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHealth() {
		return health;
	}

	public HitBox getHitBox() {
		return hitBox;
	}
}

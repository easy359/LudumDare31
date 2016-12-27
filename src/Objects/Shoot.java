package Objects;

import java.util.ArrayList;

import Util.Direction;
import Util.HitBox;
import Util.Resources;

public class Shoot {
	
	public enum Type{
		bullet;
	}
	
	private int x, y;
	private int speed;
	private int dmg;
	private Direction dir;
	private Type type;
	private HitBox hitBox;
	private int[] pixels;
	private boolean hit;
	
	public Shoot(int x, int y, int speed, int dmg, Direction dir, Type type){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.dmg = dmg;
		this.dir = dir;
		this.type = type;
		hit = false;
		init();
	}
		
	public void init(){
		if(type == Type.bullet){
			hitBox = new HitBox(x, y, 4, 2);
		}
		pixels = Engine.Main.resources.bullet.getPixels();
	}
		
	public void update(ArrayList<Zombie> zoms){
		if(dir == Direction.up){
			y -= speed;
			hitBox.setY(y);
		} else if(dir == Direction.down){
			y += speed;
			hitBox.setY(y);
		} else if(dir == Direction.right){
			x += speed;
			hitBox.setX(x);
		} else if(dir == Direction.left){
			x -= speed;
			hitBox.setX(x);
		}
		for(Zombie zom : zoms){
			if(hitBox.intersects(zom.getHitBox())){
				zom.shoot(dmg);
				hit = true;
			}
		}
	}
	
	public void render(int[] pixels){
		Resources.addPixels(pixels, this.pixels, x, y, 4, 2);
	}
	
	public boolean hit(){
		return hit;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}

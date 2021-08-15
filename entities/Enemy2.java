package com.steiger.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.steiger.main.Game;
import com.steiger.main.Sound;
import com.steiger.world.AStar;
import com.steiger.world.Camera;
import com.steiger.world.Vector2i;
import com.steiger.world.World;

public class Enemy2 extends Entity {
	
	private double speed = 0.3;
	private double delayAtk = 0;
	private double maxDelayAtk = 100;
	
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;
	
	private int life = 50;
	private boolean agro = false;
	
	private boolean isDamaged = false;
	private int damageFrames = 8, damageCurrent= 0;

	public Enemy2(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 32, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(96, 32, 16, 16);
	}

	public void tick() {
		
		depth = 0;
		
		/*
		
		if(this.cauculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 80) {
		
		if(isColiddingWithPlayer() == false) {
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColidding((int)(x+speed), this.getY())) {
			x+=speed;
		}else if ((int)x >Game.player.getX() && World.isFree((int)(x-speed), this.getY())
				&& !isColidding((int)(x-speed), this.getY())) {
			x-=speed;
		}if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
				&& !isColidding(this.getX(), (int)(y+speed))) {
			y+=speed;
		}else if ((int)y >Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
				&& !isColidding(this.getX(), (int)(y-speed))) {
			y-=speed;
		}
		}else {
			//ESTAMOS CCOLIDINDO
			if(Game.rand.nextInt(100) < 10) {
				Sound.hurtEffect.play();
			Game.player.life-=Game.rand.nextInt(20);
			Game.player.isDamaged = true;
			
			//System.out.println("Life:" + Game.player.life);
			}
		}
		}
		
	*/
		depth = 0;
		
		maskx = 1;
		masky = 3;
		mwidth = 14;
		mheight = 12;
		
		if(this.cauculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 120) {
			agro = true;
		}
		
		if(agro == true) {
			
		if(!isColiddingWithPlayer()) {
		
		if(path == null || path.size() == 0) {
			Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
			Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
			path = AStar.findPath(Game.world, start, end);
			}
		}else {
			if(delayAtk == 0) {
				Sound.hurtEffect.play();
				if(Game.player.shield <= 0) 
					Game.player.life-= 30;
				if(Game.player.shield > 0)
					Game.player.shield-= 30;
				delayAtk = maxDelayAtk;
				Game.player.isDamaged = true;
			}
		}
		if(new Random().nextInt(100) > 60)
			followPath(path);
			
		if(new Random().nextInt(100) > 5) {
			Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
			Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
			path = AStar.findPath(Game.world, start, end);
		}
		}
			
		
		if(delayAtk > 0) {
			delayAtk --;
		}
		
		
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) 
					index = 0;
			
			}
			
			collidingBullet();
			
			if(life <= 0) {
				Sound.enemy2DeathEffect.play();
				destroySelf();
				World.generateParticles(100, (int)x, (int)y);
				Game.mainCoin += 20;
				return;
			}
			
			if(isDamaged) {
				damageCurrent++;
				if(damageCurrent == damageFrames) {
					damageCurrent =0;
					isDamaged = false;
				}
			}
	}
	
	
	
	public void destroySelf() {
		Game.enemies2.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet() {
		
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				
				
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					
					if(Game.player.potion == true) {
						life -= 5;
					} if(Game.silverBullt == true) {
						life -=5;
					}
						life -=5;
					Game.bullets.remove(i);
					return;
				}
			}
		}
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX()+maskx, this.getY()+masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		
		
		return enemyCurrent.intersects(player);
	}

	
	public void render(Graphics g) {
		if(!isDamaged) {
		g.drawImage(sprites[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		}else {
			g.drawImage(Entity.ENEMY2_FEEDBACK, this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
		//DEBUG HITBOX
		//g.setColor(Color.blue);
		//g.fillRect(this.getX()+maskx-Camera.x, this.getY()+masky-Camera.y, mwidth, mheight);
	}
}

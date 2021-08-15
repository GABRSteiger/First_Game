package com.steiger.entities;

import java.awt.Color;
import java.awt.Font;
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

public class EnemyBOSS extends Entity {
	
	private double speed = 0.3;
	public int depth = 1;
	private double delayAtk = 0;
	private double maxDelayAtk = 60;
	private boolean fire = false;
	private double fireDelay = 0;
	private boolean dash = false;
	private double dashDelay = 0;
	
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;
	
	public double maxLife = 200, life = maxLife;
	
	private boolean isDamaged = false;
	private int damageFrames = 8, damageCurrent= 0;

	public EnemyBOSS(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(64, 32, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(48, 32, 16, 16);
	}

	public void tick() {
		
		depth = 1;
		
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
		
		maskx = 3;
		masky = 1;
		mwidth = 10;
		mheight = 14;
		
		if(this.cauculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 200) {
		
		if(!isColiddingWithPlayer()) {
		
		if(path == null || path.size() == 0) {
			Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
			Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
			path = AStar.findPath(Game.world, start, end);
			}
		}else {
			if(delayAtk == 0) {
				Sound.hurtEffect.play();
				if(Game.player.shield <= 0) {
				Game.player.life-= 25;
				}
				Game.player.shield-= 25;
				delayAtk = maxDelayAtk;
				Game.player.isDamaged = true;
			}
		}
		if(dash) {
			followPath(path);
		}
		if(new Random().nextInt(100) > 35)
			followPath(path);
			
		if(new Random().nextInt(100) > 5) {
			Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
			Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
			path = AStar.findPath(Game.world, start, end);
		}
		
		if(fire) {
			
			double angle =Math.atan2(Game.player.getY()-(this.getY()),(Game.player.getX()-(this.getX())));
    		double dx = Math.cos(angle);
    		double dy = Math.sin(angle);
			
    		//BulletShoot bullet = new BulletShoot(this.getX()+8,this.getY()+8, 3, 3, null, dx, dy);
    		//Game.bullets.add(bullet);
    		
    		FireShoot fireshoot = new FireShoot(this.getX()+8,this.getY()+8, 3, 3, null, dx, dy);
    		Game.fireShoots.add(fireshoot);
		}
		
		}
			
		//DELAY DE ATIRAR
			fireDelay ++;
			if(fireDelay == 50){
				fire = true;
				fireDelay = 0;
		}else
			fire = false;
		
		//DELAY DO DASH
		if(dashDelay < 750) {	
			dashDelay ++;
		}
		if(dashDelay == 700) {
			dash = true;
			Sound.bossRoar.play();
		}
			if(dashDelay == 750) {
				dash = false;
				dashDelay = 0;
			}
		
		//DELAY DO ATK
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
				Sound.bossDeathEffect.play();
				destroySelf();
				World.generateParticles(600, (int)x, (int)y);
				Game.mainCoin += 100;
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
		Game.enemiesBoss.remove(this);
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
			g.drawImage(Entity.ENEMYBOSS_FEEDBACK, this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
		
		
		if(Game.enemiesBoss.size() == 1) {
			if(Game.CUR_LEVEL == 3) {
				g.setColor(Color.DARK_GRAY);
				g.fillRect(45, 125, 150, 6);
				g.setColor(Color.BLACK);
				g.fillRect(46, 126, 148, 4);
				g.setColor(Color.RED);
				g.fillRect(46, 126,((int)((life/maxLife)*148)), 4);
				g.setFont(new Font("Arial", Font.BOLD,9));
				g.setColor(Color.WHITE);
				g.drawString("BOOS", 105, 124);
				if(Game.player.potion == true && isDamaged == true && Game.silverBullt == false)
					g.drawString("10", 185, 124);
				if(Game.player.potion == false && isDamaged == true && Game.silverBullt == true)
					g.drawString("10", 185, 124);
				if(Game.player.potion == false && isDamaged == true && Game.silverBullt == false)
					g.drawString("5", 185, 124);
				if(Game.player.potion == true && isDamaged == true && Game.silverBullt == true)
					g.drawString("15", 185, 124);
			}
		}
		//DEBUG HITBOX
		//g.setColor(Color.blue);
		//g.fillRect(this.getX()+maskx-Camera.x, this.getY()+masky-Camera.y, mwidth, mheight);
	}
}

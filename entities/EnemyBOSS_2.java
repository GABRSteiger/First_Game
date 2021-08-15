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

public class EnemyBOSS_2 extends Entity {
	
	private double speed = 0.3;
	private double delayAtk = 0;
	private double maxDelayAtk = 100;
	
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 1;
	private BufferedImage[] sprites, sprites2;
	
	public double maxLife = 500, life = maxLife;
	
	private boolean isDamaged = false;
	private int damageFrames = 8, damageCurrent= 0;
	
	private boolean phase2 = false;
	private int roar = 0;
	
	private boolean fire = false;
	private boolean fire2 = false;
	private int fireDelay = 0;
	private int fireDelay2 = 0;

	public EnemyBOSS_2(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(32, 48, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(32, 64, 16, 16);
		
		sprites2 = new BufferedImage[2];
		sprites2[0] = Game.spritesheet.getSprite(64, 64, 16, 16);
		sprites2[1] = Game.spritesheet.getSprite(80, 64, 16, 16);
		
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
		
		maskx = 1;
		masky = 1;
		mwidth = 14;
		mheight = 15;
		
		if(this.cauculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 600) {
		
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
				Game.player.life-= 60;
				}
				Game.player.shield-= 25;
				delayAtk = maxDelayAtk;
				Game.player.isDamaged = true;
			}
		}
		
		if(new Random().nextInt(100) > 80)
			followPath(path);
			
		if(new Random().nextInt(100) > 5) {
			Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
			Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
			path = AStar.findPath(Game.world, start, end);
		}
		
		//ATAQUES
		
			if(fire) {
			
				double angle =Math.atan2(Game.player.getY()-(this.getY()),(Game.player.getX()-(this.getX())));
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
    		
				double angle2 =Math.atan2(Game.player.getY()-(this.getY()+50),(Game.player.getX()-(this.getX()+50)));
				double dx2 = Math.cos(angle2);
				double dy2 = Math.sin(angle2);
    		
				double angle3 =Math.atan2(Game.player.getY()-(this.getY()-50),(Game.player.getX()-(this.getX()-50)));
				double dx3 = Math.cos(angle3);
				double dy3 = Math.sin(angle3);
    		
				FireShoot fireshoot = new FireShoot(this.getX()+4,this.getY()+4, 3, 3, null, dx, dy);
				Game.fireShoots.add(fireshoot);
				FireShoot fireshoot2 = new FireShoot(this.getX()+8,this.getY()+8, 3, 3, null, dx2, dy2);
				Game.fireShoots.add(fireshoot2);
				FireShoot fireshoot3 = new FireShoot(this.getX()+12,this.getY()+12, 3, 3, null, dx3, dy3);
				Game.fireShoots.add(fireshoot3);
    		
			}
			
			if(phase2 == true) {
				//FASE 2
				if(fire2) {
					double angle4 =Math.atan2(Game.player.getY()-(this.getY()),(Game.player.getX()-(this.getX())));
					double dx4 = Math.cos(angle4);
					double dy4 = Math.sin(angle4);
				
					FireShoot fireshoot4 = new FireShoot(this.getX()+12,this.getY()+12, 3, 3, null, dx4, dy4);
					Game.fireShoots.add(fireshoot4);
				}
			}
			
		}
		

		
		
		
			
		
		//DELAY DO ATK
		if(delayAtk > 0) {
			delayAtk --;
		}
		
			fireDelay ++;
			if(fireDelay < 120)
				fire = false;
			if(fireDelay == 120){
				fire = true;
				fireDelay = 0;
		}
			
			if(phase2 == true)
				fireDelay2 ++;
			if(fireDelay2 < 50)
				fire2 = false;
			if(fireDelay2 == 50){
				fire2 = true;
				fireDelay2 = 0;
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
				Game.mainCoin += 200;
				return;
			}
			
			if(life <= 250) {
				phase2 = true;
			}
			if(phase2 == true) {
				if(roar < 2) {
					roar ++;
					if(roar == 1) {
						Sound.boss2Roar.play();
						}			
					}
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
		Game.enemiesBoss2.remove(this);
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
		if(!phase2) {
			if(!isDamaged) {
				g.drawImage(sprites[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
			}
		}
		if(phase2 == true) {
			if(!isDamaged) {
				g.drawImage(sprites2[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
			}
		}
		if(isDamaged) {
			g.drawImage(Entity.ENEMYBOSS_2_FEEDBACK, this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
		
		
		if(Game.enemiesBoss2.size() == 1) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(45, 125, 150, 6);
			g.setColor(Color.BLACK);
			g.fillRect(46, 126, 148, 4);
			g.setColor(Color.RED);
			g.fillRect(46, 126,((int)((life/maxLife)*148)), 4);
			g.setFont(new Font("Arial", Font.BOLD,9));
			g.setColor(Color.WHITE);
			g.drawString("SMOGU", 105, 124);
			if(Game.player.potion == true && isDamaged == true && Game.silverBullt == false)
				g.drawString("10", 185, 124);
			if(Game.player.potion == false && isDamaged == true && Game.silverBullt == true)
				g.drawString("10", 185, 124);
			if(Game.player.potion == false && isDamaged == true && Game.silverBullt == false)
				g.drawString("5", 185, 124);
			if(Game.player.potion == true && isDamaged == true && Game.silverBullt == true)
				g.drawString("15", 185, 124);
		}
		//DEBUG HITBOX
		//g.setColor(Color.blue);
		//g.fillRect(this.getX()+maskx-Camera.x, this.getY()+masky-Camera.y, mwidth, mheight);
	}
}

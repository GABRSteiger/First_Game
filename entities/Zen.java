package com.steiger.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.steiger.main.Game;
import com.steiger.main.Sound;
import com.steiger.world.AStar;
import com.steiger.world.Camera;
import com.steiger.world.Vector2i;
import com.steiger.world.World;

public class Zen extends Entity {

	public double maxLife = 1500, life = maxLife;
	private boolean phase2 = false;
	public static boolean phaseCut = false;
	
	private double delayAtk = 0;
	private double maxDelayAtk = 100;
	private boolean agro = false;
	public static boolean dead = false;
	private boolean deathAnim = false;
	private int deathIndex = 0;
	private int deathFrames = 0, deathFramesMax = 30;
	public static int deathTimer = 0;
	
	public static boolean cutcene = false;
	private boolean done = false;
	private boolean done2 = false;
	private boolean doneBL = false, BL1 = false, BL2 = false, BL3 = false;
	
	//ATKS
	public static int dashDelay = 0;
	private boolean dash = false;
	
	private boolean fire = false;
	private int fireDelay = 0;
	
	//DIALOG
	private int animation = 0;
	public static int dialog = 1;
	
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 1;
	private BufferedImage death;
	private BufferedImage[] sprites, sprites2, deathSprite;
	
	private boolean isDamaged = false;
	private int damageFrames = 8, damageCurrent= 0;
	
	public Zen(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		sprites = new BufferedImage[2];
		sprites2 = new BufferedImage[2];
		deathSprite = new BufferedImage[2];
		
		sprites[0] = Game.spritesheet.getSprite(128, 128, 17, 18);
		sprites[1] = Game.spritesheet.getSprite(111, 128, 17, 18);
		
		sprites2[0] = Game.spritesheet.getSprite(77, 128, 17, 18);
		sprites2[1] = Game.spritesheet.getSprite(60, 128, 17, 18);
		
		death = Game.spritesheet.getSprite(128, 146, 16, 14);
		deathSprite[0] = Game.spritesheet.getSprite(112, 146, 16, 14);
		deathSprite[1] = Game.spritesheet.getSprite(96, 146, 16, 14);
	}

	
	public void tick() {
		
		depth = 1;
		
		maskx = 1;
		masky = 1;
		mwidth = 14;
		mheight = 15;

		
		if(!phaseCut && !dead) {
		
		//ATAQUES
		if(agro) {
			
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
			if(!dash) {
				if(new Random().nextInt(100) > 30)
					followPath(path);
				if(new Random().nextInt(100) > 5) {
					Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
					Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
					path = AStar.findPath(Game.world, start, end);
				}
			}
			
			if(fire) {
				
					double angle1 =Math.atan2(Game.player.getY()-(this.getY()),(Game.player.getX()-(this.getX())));
		    		double dx1 = Math.cos(angle1);
		    		double dy1 = Math.sin(angle1);
		    		
		    		FireShoot fireshoot1 = new FireShoot(this.getX()+8,this.getY()+8, 3, 3, null, dx1, dy1);
		    		Game.fireShoots.add(fireshoot1);
				
				if(phase2) {
					double angle =Math.atan2(this.getY()+1-(this.getY()),(this.getX()-(this.getX())));
					double dx = Math.cos(angle);
					double dy = Math.sin(angle);
					
					double angle2 =Math.atan2(this.getY()-1-(this.getY()),(this.getX()-(this.getX())));
					double dx2 = Math.cos(angle2);
					double dy2 = Math.sin(angle2);
					
					double angle3 =Math.atan2(this.getY()-(this.getY()),(this.getX()-1-(this.getX())));
					double dx3 = Math.cos(angle3);
					double dy3 = Math.sin(angle3);

					double angle4 =Math.atan2(this.getY()-(this.getY()),(this.getX()+1-(this.getX())));
					double dx4 = Math.cos(angle4);
					double dy4 = Math.sin(angle4);
					
					//ATIRO DE +
					FireShoot fireshoot = new FireShoot(this.getX()+8,this.getY()+8, 3, 3, null, dx, dy);
		    		Game.fireShoots.add(fireshoot);
		    		FireShoot fireshoot2 = new FireShoot(this.getX()+8,this.getY()+8, 3, 3, null, dx2, dy2);
		    		Game.fireShoots.add(fireshoot2);
		    		FireShoot fireshoot3 = new FireShoot(this.getX()+8,this.getY()+8, 3, 3, null, dx3, dy3);
		    		Game.fireShoots.add(fireshoot3);
		    		FireShoot fireshoot4 = new FireShoot(this.getX()+8,this.getY()+8, 3, 3, null, dx4, dy4);
		    		Game.fireShoots.add(fireshoot4);
		    		
				}
			}
			
			
			if(delayAtk > 0) {
				delayAtk --;
			}
			
			
			//DELAY DO DASH
			if(dashDelay < 1000) {	
				dashDelay ++;
			}
			if(dashDelay > 700) {
				dash = true;
			}
			if(dashDelay >= 1000) {
				dash = false;
				dashDelay = 0;
			}
			if(dash) {
				followPath(path);
			}
			
			
			//FIRE
			fireDelay ++;
			if(fireDelay == 100){
				fire = true;
				fireDelay = 0;
		}else
			fire = false;
			
			
			
		}
		
		if(life < maxLife/2)
			phase2 = true;
		
		collidingBullet();
		
		//ANIMAÇÕES
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) 
				index = 0;
		}
		if(isDamaged) {
			damageCurrent++;
			if(damageCurrent == damageFrames) {
				damageCurrent =0;
				isDamaged = false;
			}
		}
		
		//MORTE
		if(life <= 0) {
			dead = true;
		}
		
		
		
		}
		
		//CUTCENE
		
				if(animation < 120)
					animation ++;
				if(animation >= 120)
					animation = 0;
		
		//PHASE 2 CUTCENE
		
		if(dialog >= 3) {
			cutcene = false;
			done = true;
			agro = true;
			if(dialog == 5) {
				phaseCut = false;
				done2 = true;
			}
		}
		
		if(!done2) {
			if(phase2 == true) {
				phaseCut = true;
			}
		}
		
		if(!doneBL) {
			if(phase2) {
				World.generateParticles(200, (int)x, (int)y);
				doneBL = true;
			}
		}
		
		if(!BL1 && life <= 850) {
			World.generateParticles(50, (int)x, (int)y);
			BL1 = true;
		}
		if(!BL2 && life <= 450) {
			World.generateParticles(50, (int)x, (int)y);
			BL2 = true;
		}
		if(!BL3 && life <= 150) {
			World.generateParticles(50, (int)x, (int)y);
			BL3 = true;
		}
			
		
		if(deathTimer < 60*5) {
		
		if(phase2 == true && phaseCut == false)
			Sound.musicZen2.loop();
		else
			Sound.musicZen2.stop();
		
		if(phase2 == false && agro == true)
			Sound.musicZen.loop();
		else
			Sound.musicZen.stop();
		}else {
			Sound.musicZen.stop();
			Sound.musicZen2.stop();
		}
			
		
		if(phaseCut == true || dead == true) {
			isDamaged = false;
		}
		
		//DEAD CUTCENE
		
		if(deathFrames != deathFramesMax && dialog == 7) 
			deathFrames++;
		if(deathIndex != 1 && deathFrames == deathFramesMax)
			deathIndex ++;
		
		if(deathIndex == 1 && deathTimer != 60*5) {
			World.generateParticles(1, (int)x, (int)y);
			deathTimer ++;
		}
		
		if(deathTimer == 60*5) {
			destroySelf();
			dead = false;
		}
		
			
		
		
		if(!done) {
			if(this.cauculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 80){
				cutcene = true;
			}
		}
		
		if(cutcene == true || phaseCut == true || dead) {
		updateCamera();
		}
		
	}
	
	public void destroySelf() {
		Game.entities.remove(this);
		Game.zen.remove(this);
	}
	
	public void updateCamera() {
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);

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
		Graphics2D g2 = (Graphics2D) g;
		
		if(!isDamaged) {
			if(!phase2)
				g.drawImage(sprites[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
			else if(phase2 && !dead)
				g.drawImage(sprites2[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
			else if(dead && deathAnim == false)
				g.drawImage(death, this.getX()-Camera.x, this.getY()-Camera.y, null);
			else if(dead && deathAnim == true)
				g.drawImage(deathSprite[deathIndex], this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
		if(isDamaged) {
			g.drawImage(Entity.ZEN_FEEDBACK, this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
		
		if(Game.zen.size() == 1) {
			if(agro) {
				g.setColor(Color.DARK_GRAY);
				g.fillRect(45, 125, 150, 6);
				g.setColor(Color.BLACK);
				g.fillRect(46, 126, 148, 4);
				g.setColor(Color.RED);
				g.fillRect(46, 126,((int)((life/maxLife)*148)), 4);
				g.setFont(new Font("Arial", Font.BOLD,9));
				g.setColor(Color.WHITE);
				g.drawString("ZEN", 105, 124);
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
		
		//CUTCENES
		
		if(cutcene) {
			if(dialog == 1) {
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial",Font.BOLD, 9));
				g.drawString("Pra que desafiar alguém que você", 45, 30);
				g.drawString("não conhece?", 90, 40);
				if(animation < 60)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.white);
				g.drawString("(E)", 115, 65);
				
			}else if(dialog == 2) {
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial",Font.BOLD, 9));
				g.drawString("Que assim seja", 90, 30);
				if(animation < 60)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.white);
				g.drawString("(E)", 115, 65);
			}
		}
		
		if(phaseCut) {
			if(dialog == 3) {
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial",Font.BOLD, 9));
				g.drawString("Droga... ", 45, 30);
				if(animation < 60)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.white);
				g.drawString("(E)", 115, 65);
			}
			else if(dialog == 4) {
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial",Font.BOLD, 9));
				g.drawString("...", 45, 30);
				if(animation < 60)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.white);
				g.drawString("(E)", 115, 65);
			}
		}
		
		if(dead) {
			if(dialog == 5) {
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial",Font.BOLD, 9));
				g.drawString("oque você fez...", 45, 30);
				if(animation < 60)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.white);
				g.drawString("(E)", 115, 65);
			}
			else if(dialog == 6) {
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial",Font.BOLD, 9));
				g.drawString("... hughh...", 45, 30);
				if(animation < 60)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.white);
				g.drawString("(E)", 115, 65);
			}
			else if(dialog == 7)
				deathAnim = true;
		}
		
		
		
		//DEBUG HITBOX
		//g.setColor(Color.blue);
		//g.fillRect(this.getX()+maskx-Camera.x, this.getY()+masky-Camera.y, mwidth, mheight);
		
	}

}

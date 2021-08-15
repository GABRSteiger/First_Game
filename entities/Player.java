package com.steiger.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.steiger.graficos.Spritesheet;
import com.steiger.main.Game;
import com.steiger.main.Sound;
import com.steiger.world.Camera;
import com.steiger.world.World;

public class Player extends Entity {
	
	public boolean right, left, up, down;
	public int right_dir = 0, left_dir =1;
	public int dir = right_dir;
	public double speed = 1.7;
	public double maxLife = Game.gameMaxLife, life = maxLife;
	public int mx, my;
	public boolean interact = false;
	
	public int coin = Game.mainCoin;
	
	public double shield = 0, maxShield = maxLife/2;
	public boolean iframe = false;
	public int iframeTime = 0;
	
	public boolean curse = false;
	public int curseDelay = 0;
	public int maxCurseDelay = 300;
	public boolean curseText = false;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	public int delay = 0, maxDelay = 20;
	public boolean potion = false;
	public int potionDelay = 900; 
	private boolean canShoot = false;
	private boolean moved = false;
	
	public boolean sprint = false;
	public double stamina = 50;
	public boolean cantSprint = false;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	
	//BLOOD
	private BufferedImage[] rightPlayerBL;
	private BufferedImage[] leftPlayerBL;
	private BufferedImage[] rightPlayerBL2;
	private BufferedImage[] leftPlayerBL2;
	public double bloody = 0;
	
	private BufferedImage playerDamaged;
	private BufferedImage pistolDamagedRight;
	private BufferedImage pistolDamagedLeft;
	private int damageFrames = 0;
	
	public boolean shoot = false, mouseShoot = false;
	
	public int Ammo = 0;
	
	public boolean isDamaged = false;
	
	public boolean hasPistol = false;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamaged = Game.spritesheet.getSprite(32, 32, 16, 16);
		pistolDamagedLeft = Game.spritesheet.getSprite(144, 16, 16, 16);
		pistolDamagedRight = Game.spritesheet.getSprite(144, 32, 16, 16);
		
		for(int i =0; i < 4; i++) {
		rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		for(int i =0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
			}
		
		rightPlayerBL = new BufferedImage[4];
		leftPlayerBL = new BufferedImage[4];
		rightPlayerBL2 = new BufferedImage[4];
		leftPlayerBL2 = new BufferedImage[4];
		
			for(int i =0; i < 4; i++) {
				rightPlayerBL[i] = Game.spritesheet.getSprite(96 + (i*16), 64, 16, 16);
				}
				for(int i =0; i < 4; i++) {
					leftPlayerBL[i] = Game.spritesheet.getSprite(96 + (i*16), 80, 16, 16);
					}
			for(int i =0; i < 4; i++) {
				rightPlayerBL2[i] = Game.spritesheet.getSprite(96 + (i*16), 96, 16, 16);
				}
				for(int i =0; i < 4; i++) {
					leftPlayerBL2[i] = Game.spritesheet.getSprite(96 + (i*16), 112, 16, 16);
					}
		
	}
	
	public void tick() {
		
		if(!Zen.cutcene && !Zen.phaseCut && !Zen.dead && !Npc_2.shop) {
		
		depth = 1;
		
		//IFRAME
		if(isDamaged) {
			iframe = true;
		}
		if(iframe) {
			iframeTime ++;			
		}
		if(iframeTime == 60) {
			iframe = false;
			iframeTime = 0;
		}
		
		
		if(Game.CUR_LEVEL > 1) {
			hasPistol = true;
		}
		
		moved = false;
		if(right && World.isFreeDynamic((int)(x+speed),this.getY(), 15, 15)) {
			moved = true;
			dir = right_dir;
			x+=speed;
			
			
		}
		else if(left && World.isFreeDynamic((int)(x-speed),this.getY(), 15, 15)) {
			moved = true;
			dir = left_dir;
			x-=speed;
			
			
		}
		if(up && World.isFreeDynamic(this.getX(),(int)(y-speed), 15, 15)) {
			moved = true;
			y-=speed;
			
			
		}
		else if(down && World.isFreeDynamic(this.getX(),(int)(y+speed), 15, 16)) {
			moved = true;
			y+=speed;
			
			
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) 
					index = 0;
				
			}
		}
		
		//COLISOES
		    checkCollisionCherry();
		    checkCollisionAmmo();
		    checkCollisionPistol();
		    checkCollisionCarta();
		    checkCollisionFireShoot();
		    checkCollisionCurse();
		    checkCollisionShield();
		    checkCollisionPotion();
		    checkCollisionNPC_1();
		    checkCollisionNPC_2();
		    checkCollisionGoldenApp();
		    checkCollisionSilverBullet();
		    
		    
		    if(isDamaged) {
		    	this.damageFrames++;
		    	if(this.damageFrames == 8) {
		    		this.damageFrames = 0;
		    		isDamaged = false;
		    	}
		    }
		    	
		    	if(shoot) {
		    		shoot = false;
		    		if(hasPistol && Ammo > 0) {
		    		Ammo--;
		    		int dx = 0;
		    		int px = 8;
		    		int py = 7;
		    		
		    		if(dir == right_dir) {
		    			px = 12;
		    			 dx = 1;
		    		}else {
		    			px = 0;
		    			 dx = -1;
		    		}
		    		
		    		
		    		BulletShoot bullet = new BulletShoot(this.getX()+px,this.getY()+py, 3, 3, null, dx, 0);
		    		Game.bullets.add(bullet);
		    		
		    		}
		    	}
		    	
		    	if(shield < 0)
		    		shield = 0;
		    	
		    	//CURSE
		    	
		    	if(curse) {
		    		if(!Game.goldenApp)
		    			maxLife = 50;
		    		if(Game.goldenApp)
		    			maxLife = 60;
		    		shield = 0;
		    		maxShield = 0;
		    		if(curseDelay != maxCurseDelay) {
		    		curseDelay++;
		    		curseText = true;
		    		}
		    		if(!Game.goldenApp)
		    			if(life > 50) 
		    				life = 50;
		    		if(Game.goldenApp)
		    			if(life > 60)
		    				life = 60;
		    		
		    	}
		    	
		    	if(curseDelay >= maxCurseDelay) {
		    		curseText = false;
		    	}
		    	
		    	//POÇÃO
		    	if(Game.player.potion) {
		    		if(potionDelay > 0) {
		    			potionDelay--;
		    		}
		    		if(potionDelay <= 0 && Game.player.potion == true) {
		    			potionDelay = 0;
		    			Game.player.potion = false;
		    		}
		    	
		    	}
		    	
		    	//SPRINTING
		    	
		    	
		    	
		    	
		    	if(sprint == true && stamina > 0) {
		    		if(cantSprint == false) {
		    			if(!Game.boots)
		    				speed = 2.3;
		    			else //BOTAS
		    				speed = 2.5;
		    		stamina -= 2;
		    		//System.out.println("POGGERS");
		    		}
		    	}else if(!Game.boots)
		    		speed = 1.7;
		    	else if(Game.boots)
		    		speed = 2.0;
		    	
		    	//STAMINA
		    	if(sprint == false && stamina < 50) {
		    		stamina += 0.25;
		    	}
		    	
		    	if(sprint == false && stamina < 22.5) {
		    		cantSprint = true;
		    	}else
		    		cantSprint = false;
		    	
		    	
		    	if(delay != maxDelay) {
		    	delay++;
		    	canShoot = false;
		    	//System.out.println(delay);
		    	}
		    	if(delay == maxDelay) {
		    		canShoot = true;
		    	}
		    	
		    	if(mouseShoot) {
		    		//System.out.println("POG");
		    		mouseShoot = false;
		    		
		    		
		    			
		    		if(hasPistol && Ammo > 0 && canShoot) {
		    			
		    			delay = 0;
		    			Sound.gunshootEffect.play();
		    		Ammo--;
		    		double angle =Math.atan2(my-(this.getY()+8-Camera.y), mx-(this.getX()+8-Camera.x));
		    		double dx = Math.cos(angle);
		    		double dy = Math.sin(angle);
		    		int px = 0;
		    		int py = 0;
		    		if(dir == right_dir) {
		    			px = 12;
		    			 py = 8;
		    		}else {
		    			px = 0;
		    			 py = 8;
		    		}
		    			
		    		BulletShoot bullet = new BulletShoot(this.getX()+px,this.getY()+py, 3, 3, null, dx, dy);
		    		Game.bullets.add(bullet);
		    		
		    		}
		    	}
					
					
		    
		    if(life<=0) {
		    	//GAME OVER
		    	Sound.deathEffect.play();
		    	life = 0;
		    	Game.gameState = "GAME_OVER";
		    	}
		    if(!Zen.cutcene)
		    updateCamera();
		}
	}
	
	public void updateCamera() {
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
public void checkCollisionPistol() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Pistol) {
				if(Entity.isColidding(this, atual)) {
					hasPistol = true;
					this.Ammo +=10;
					Sound.pickPistol.play();
					Game.entities.remove(atual);
				}
			}
		}
	}
	
public void checkCollisionAmmo() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					if(Ammo < 99) {
					Ammo +=10;
			    	if(Ammo > 99) {
			    		Ammo = 99;
			    	}
					Game.entities.remove(atual);
					}
					
					//System.out.println("Ammo:"+ Ammo);
				}
			}
		}
	}
	
public void checkCollisionCherry() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Cherry) {
				if(Entity.isColidding(this, atual)) {
					if(life < maxLife) {
						Sound.pickCherry.play();
						if(life > maxLife - 20) {
							life=maxLife;
							Game.entities.remove(atual);
						}else {
					life += 20;
					Game.entities.remove(atual);
						}
					}
				}
			}
		}
		
	}
	
public void checkCollisionCarta() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Carta) {
				if(Entity.isColidding(this, atual)) { 
					Carta.cartaPlayer = true;
					
					}
				if(!Entity.isColidding(this, atual)) {
					Carta.cartaPlayer = false;
				}
			}
		}
}
	
public void checkCollisionFireShoot() {
	
	for(int i = 0; i < Game.fireShoots.size(); i++) {
		Entity e = Game.fireShoots.get(i);
		if(e instanceof FireShoot) {
			
			
			if(Entity.isColidding(this, e)) {
				isDamaged = true;
				if(shield > 0)
					if(Game.CUR_LEVEL == 12)
						shield -=20;
					else
					shield -= 10;
				else if(Game.CUR_LEVEL == 12)
					life -= 20;
				else
					life -= 10;
				Sound.hurtEffect.play();
				Game.fireShoots.remove(i);
				return;
			}
		}
	}
}

public void checkCollisionCurse() {
	
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof Curse) {
			if(Entity.isColidding(this, atual)) {
				curse = true;
				Sound.curseEffect.play();
				Game.entities.remove(atual);
					}
				}
			}
		}
	
public void checkCollisionShield() {
	
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof Shield) {
			if(Entity.isColidding(this, atual)) {
				if(shield < maxLife/2) {
				shield += 25;
				Sound.pickShield.play();
				Game.entities.remove(atual);
						}
				if(shield > maxShield) {
					shield = maxLife/2;
					Sound.pickShield.play();
						}
					}
				}
			}
		}

public void checkCollisionPotion() {
	
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof Potion) {
			if(Entity.isColidding(this, atual)) {
				if(potion == false) {
				potion = true;
				}
				if(potion == true)
					potionDelay = 900;
				Sound.pickPotion.play();
				Game.entities.remove(atual);
					}
				}
			}
		}

public void checkCollisionNPC_1() {
	
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof Npc_1) {
			if(Entity.isColidding(this, atual)) {
				Npc_1.npc_1 = true;
					}
			if(!Entity.isColidding(this, atual)) {
				Npc_1.npc_1 = false;
			}
				}
			}
		}

public void checkCollisionNPC_2() {
	
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof Npc_2) {
			if(Entity.isColidding(this, atual)) {
				Npc_2.npc_1 = true;
					}
			if(!Entity.isColidding(this, atual)) {
				Npc_2.npc_1 = false;
			}
				}
			}
		}

public void checkCollisionGoldenApp() {
	
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof GoldenApple) {
			if(Entity.isColidding(this, atual)) {
					if(Game.mainCoin >= 300 && Game.goldenApp == false) {
						Game.mainCoin -= 300;
						Game.goldenApp = true;
						maxLife = 125;
						Sound.pickCherry.play();
						Game.entities.remove(atual);
						}
					}
				}
			}
		}

public void checkCollisionSilverBullet() {
	
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof SilverBullet) {
			if(Entity.isColidding(this, atual)) {
					if(Game.mainCoin >= 500 && Game.silverBullt == false) {
						Game.mainCoin -= 500;
						Game.silverBullt = true;
						Sound.pickPistol.play();
						Game.entities.remove(atual);
						}
					}
				}
			}
		}

	public void render(Graphics g) {
		if(!isDamaged) {
		if(dir == right_dir) {
		g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		if(hasPistol) {
			//ARMA PARA A DIREITA
			g.drawImage(Entity.HOLD_PISTOL_RIGHT, this.getX()+4-Camera.x, this.getY()-Camera.y+2, null);
		}
		}else if(dir == left_dir) {
		g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		if(hasPistol) {
			//ARMA PARA A ESQUERDA
			g.drawImage(Entity.HOLD_PISTOL_LEFT, this.getX()-4-Camera.x, this.getY()-Camera.y+2, null);
		}
		}
		
		//SANGRE
		if(bloody > 50 && bloody < 150) {
			if(dir == right_dir) {
				g.drawImage(rightPlayerBL[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasPistol) {
					//ARMA PARA A DIREITA
					g.drawImage(Entity.HOLD_PISTOL_RIGHT, this.getX()+4-Camera.x, this.getY()-Camera.y+2, null);
				}
				}else if(dir == left_dir) {
				g.drawImage(leftPlayerBL[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasPistol) {
					//ARMA PARA A ESQUERDA
					g.drawImage(Entity.HOLD_PISTOL_LEFT, this.getX()-4-Camera.x, this.getY()-Camera.y+2, null);
				}
			}
		}else if(bloody >= 150) {
			if(dir == right_dir) {
				g.drawImage(rightPlayerBL2[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasPistol) {
					//ARMA PARA A DIREITA
					g.drawImage(Entity.HOLD_PISTOL_RIGHT, this.getX()+4-Camera.x, this.getY()-Camera.y+2, null);
				}
				}else if(dir == left_dir) {
				g.drawImage(leftPlayerBL2[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasPistol) {
					//ARMA PARA A ESQUERDA
					g.drawImage(Entity.HOLD_PISTOL_LEFT, this.getX()-4-Camera.x, this.getY()-Camera.y+2, null);
					}
				}
			}
			
		}else {	
			g.drawImage(playerDamaged, this.getX()-Camera.x, this.getY()-Camera.y, null);
			if(dir == right_dir && hasPistol) {
					g.drawImage(pistolDamagedRight, this.getX()+4-Camera.x, this.getY()-Camera.y+2, null);
				}
			else if(dir == left_dir && hasPistol) {
				g.drawImage(pistolDamagedLeft, this.getX()-4-Camera.x, this.getY()-Camera.y+2, null);
				}
			}
		}
	}
	



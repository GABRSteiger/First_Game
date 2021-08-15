package com.steiger.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.steiger.main.Game;
import com.steiger.world.Camera;
import com.steiger.world.World;

public class BulletShoot extends Entity {
	
	private double dx;
	private double dy;
	private double spd = 5;
			
    private int life = 60, curlife = 0;

	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		
		this.dx = dx;
		this.dy = dy;
	}
	
	
	
	
	
	public void tick() {
		
		x+= dx*spd;
		y+= dy*spd;
		curlife ++;
		if(curlife == life) {
			Game.bullets.remove(this);
			return;
		}
		
		//COLISÃO COM A PAREDE (MEIO BUGADO)
		/*
		if(!World.isFreeBullet((int)(x+spd),this.getY())) {
		     Game.bullets.remove(this);
			
			
		}
		else if(!World.isFreeBullet((int)(x-spd),this.getY())) {
		 Game.bullets.remove(this);
			
		}
		if(!World.isFreeBullet(this.getX(),(int)(y-spd))) {
	     Game.bullets.remove(this);
			
			
		}
		else if(!World.isFreeBullet(this.getX(),(int)(y+spd))) {
		     Game.bullets.remove(this);
			
			
		}
		*/
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		if(Game.player.potion == true) {
			g2.setColor(new Color(255,100,0));
			g2.fillRect(this.getX()-Camera.x-2, this.getY()-Camera.y-2, 7, 7);
		}
		g.setColor(Color.yellow);
		if(Game.silverBullt == true)
			g.setColor(Color.white);
		g.fillRect(this.getX()-Camera.x, this.getY()-Camera.y, 3, 3);
		
	}
	
	
	
	
	
	
	
	
}

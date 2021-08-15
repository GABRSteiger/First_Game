package com.steiger.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Collection;

import com.steiger.main.Game;
import com.steiger.world.Camera;
import com.steiger.world.World;

public class FireShoot extends Entity {
	
	private double dx;
	private double dy;
	private double spd = 4;
			
    public static int life = 120, curlife = 0;

	public FireShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		
		this.dx = dx;
		this.dy = dy;
	}
	
	
	
	
	
	public void tick() {
		
		x+= dx*spd;
		y+= dy*spd;
		
		if(Game.fireShoots.size() >= 10) {
			Game.fireShoots.remove(this);
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
		
		g.setColor(Color.YELLOW);
		if(Game.zen.size() == 1)
			g.setColor(Color.RED);
		g.fillOval(this.getX()-Camera.x-1, this.getY()-Camera.y-1, 7, 7);
		g.setColor(new Color(255,0,0));
		if(Game.enemiesBoss2.size() == 1)
			g.setColor(new Color(0,255,0));
		if(Game.zen.size() == 1)
			g.setColor(Color.RED);
		g.fillOval(this.getX()-Camera.x, this.getY()-Camera.y, 5, 5);
		
	}
	
	
	
	
	
	
	
	
}

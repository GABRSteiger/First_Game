package com.steiger.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.steiger.main.Game;
import com.steiger.world.Camera;
import com.steiger.world.World;

public class Particle extends Entity {

	public int lifeTime = 20;
	public int curLife = 0;
	
	public int mainLife = 0;
	public int maxMainLife = 60*20;
	
	public int spd = 2;
	public double dx = 0;
	public double dy = 0;
	
	
	
	public Particle(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		dx = Game.rand.nextGaussian();
		dy = Game.rand.nextGaussian();
		
	}
	
	public void tick() {
		
		depth = -2;
		
		curLife++;
		if(World.isFreeDynamic((int)(x+(dx*spd)), (int)(y+(dy*spd)),3,3) && curLife <= lifeTime) {
		x+=dx*spd;
		y+=dy*spd;
		}
		
		if(Entity.isColidding(this, Game.player) && curLife < lifeTime) {
				Game.player.bloody += 1;
				Game.entities.remove(this);
			}
		curLife ++;
		mainLife ++;
		
		if(Game.CUR_LEVEL != 13) {
			if(mainLife >= maxMainLife) {
				Game.entities.remove(this);
			}
		}
		
	}
	
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(255,0,0));
		g.fillRect(this.getX()-Camera.x, this.getY()-Camera.y, 1, 1);
		
	}
	
	

}

package com.steiger.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.steiger.main.Game;
import com.steiger.main.Sound;
import com.steiger.world.Camera;

public class Spikes extends Entity {
	
	private double delayAtk = 0;
	private double maxDelayAtk = 50;
	
	private int delay = 0;
	private int spike = 1;
	
	private int mwidth = 16, mheight = 12;

	public Spikes(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
	
		depth = -3;
		
		if(spike == 2) {
			if(isColiddingWithPlayer()) {
				
				if(!Game.player.iframe) {
					Sound.hurtEffect.play();
					if(Game.player.shield <= 0) 
						Game.player.life-= 5;
					if(Game.player.shield > 0)
						Game.player.shield-=5;
					delayAtk = maxDelayAtk;
					Game.player.isDamaged = true;
				}
				
			}
		}
	
	
	//ANIMAÇÃO
	if(delay <= 120) 
		delay ++;
	if(delay >= 120) 
		delay = 0;
	if(spike == 1) {
		if(delay == 119) {
			spike = 2;
			Sound.spike.play();
		}
	}else if(spike == 2) {
		if(delay == 119)
			spike = 1;
	}
	
	
	//ATAQUE 
	if(delayAtk > 0) {
		delayAtk--;
	}
	
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX()+maskx, this.getY()+masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		
		
		return enemyCurrent.intersects(player);
	}
	
	public void render(Graphics g) {
		if(spike == 2)
			g.drawImage(Entity.SPIKES, this.getX()-Camera.x, this.getY()-Camera.y, null);
		if(spike == 1)
			g.drawImage(Entity.NO_SPIKES, this.getX()-Camera.x, this.getY()-Camera.y, null);
		
		//DEBUG HITBOX
		//g.setColor(Color.blue);
		//g.fillRect(this.getX()+maskx-Camera.x, this.getY()+masky-Camera.y, mwidth, mheight);
	}

}

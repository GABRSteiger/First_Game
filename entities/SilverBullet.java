package com.steiger.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.steiger.world.Camera;

public class SilverBullet extends Entity{

	public SilverBullet(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth = 0;
		
	}

public void render(Graphics g) {
		
		g.drawImage(Entity.SILVER_BULLET, this.getX()-Camera.x, this.getY()-Camera.y,null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 9));
		g.drawString("$500", this.getX()-Camera.x-4, this.getY()-Camera.y+22);
	}
	
}

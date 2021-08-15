package com.steiger.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.steiger.main.Game;
import com.steiger.world.Camera;

public class GoldenApple extends Entity{
	

	public GoldenApple(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth = 0;
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(Entity.GOLDEN_APPLE, this.getX()-Camera.x, this.getY()-Camera.y,null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 9));
		g.drawString("$300", this.getX()-Camera.x-4, this.getY()-Camera.y+22);
	}

}

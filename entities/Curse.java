package com.steiger.entities;

import java.awt.image.BufferedImage;

public class Curse extends Entity{

	public Curse(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth = 0;
		
	}
	
}

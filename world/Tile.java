package com.steiger.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.steiger.main.Game;

public class Tile {

	//NORMAL TILES
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_FLOOR2 = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage TILE_FLOOR_FLOWER = Game.spritesheet.getSprite(0, 32, 16, 16);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 16, 16, 16);
	public static BufferedImage TILE_WALL_2 = Game.spritesheet.getSprite(16, 0, 16, 16);
	//WALL TILE
	public static BufferedImage TILE_WALL_3 = Game.spritesheet.getSprite(0, 96, 16, 16);
	//SAND FLOOR
	public static BufferedImage TILE_FLOOR_SAND = Game.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage TILE_FLOOR_SAND2 = Game.spritesheet.getSprite(0, 64, 16, 16);
	public static BufferedImage TILE_FLOOR_SAND3 = Game.spritesheet.getSprite(16, 48, 16, 16);
	public static BufferedImage TILE_FLOOR_SAND4 = Game.spritesheet.getSprite(16, 64, 16, 16);
	public static BufferedImage TILE_FLOOR_SAND5 = Game.spritesheet.getSprite(16, 80, 16, 16);
	//WOOD FLOOR
	public static BufferedImage TILE_FLOOR_WOOD = Game.spritesheet.getSprite(0, 80, 16, 16);
	public static BufferedImage TILE_FLOOR_WOOD2 = Game.spritesheet.getSprite(32, 112, 16, 16);
	public static BufferedImage TILE_FLOOR_CARPET = Game.spritesheet.getSprite(16, 96, 16, 16);
	public static BufferedImage TILE_FLOOR_CARPET2 = Game.spritesheet.getSprite(16, 144, 16, 16);
	public static BufferedImage TILE_FLOOR_CARPET3 = Game.spritesheet.getSprite(32, 80, 16, 16);
	public static BufferedImage TILE_FLOOR_CARPET4 = Game.spritesheet.getSprite(16, 128, 16, 16);
	public static BufferedImage TILE_FLOOR_CARPET5 = Game.spritesheet.getSprite(16, 112, 16, 16);
	public static BufferedImage TILE_WINDOW = Game.spritesheet.getSprite(32, 96, 16, 16);
	
	//PRETO
	public static BufferedImage TILE_BLACK = Game.spritesheet.getSprite(0, 144, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		
	}
}

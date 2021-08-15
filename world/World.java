package com.steiger.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.imageio.ImageIO;

import com.steiger.entities.Bullet;
import com.steiger.entities.Carta;
import com.steiger.entities.Cherry;
import com.steiger.entities.Curse;
import com.steiger.entities.Enemy;
import com.steiger.entities.Enemy2;
import com.steiger.entities.EnemyBOSS;
import com.steiger.entities.EnemyBOSS_2;
import com.steiger.entities.Entity;
import com.steiger.entities.GoldenApple;
import com.steiger.entities.Npc_1;
import com.steiger.entities.Npc_2;
import com.steiger.entities.Particle;
import com.steiger.entities.Pistol;
import com.steiger.entities.Player;
import com.steiger.entities.Potion;
import com.steiger.entities.Shield;
import com.steiger.entities.SilverBullet;
import com.steiger.entities.Spikes;
import com.steiger.entities.Zen;
import com.steiger.graficos.Spritesheet;
import com.steiger.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
						if(Game.CUR_LEVEL <= 8)
							tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
						else if (Game.CUR_LEVEL > 8)
							tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_WOOD);
					
						//NORMAL
						if(pixelAtual == 0xFFFF00DC) {
							//PRETO
							tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_BLACK);
					}else if(pixelAtual == 0xFF808080) {
						//FLOOR
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFFFFFFFF) {
						//WALL
						tiles[xx +(yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
					}else if(pixelAtual == 0xFFEFEFEF) {
						//WALL2
						tiles[xx +(yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL_2);
					}else if(pixelAtual == 0xFF3D3D3D) {
						//WALL3
						tiles[xx +(yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL_3);
					}else if(pixelAtual == 0xFF7C7C7C) {
						//FLOOR2
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR2);
					}else if(pixelAtual == 0xFF6B6B6B) {
						//FLOOR_FLOWER
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_FLOWER);
					}
					//SAND
					else if(pixelAtual == 0xFF808000) {
						//FLOOR_SAND
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_SAND);
					}else if(pixelAtual == 0xFF878743) {
						//FLOOR_SAND2
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_SAND2);
					}else if(pixelAtual == 0xFF879B43) {
						//FLOOR_SAND3
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_SAND3);
					}else if(pixelAtual == 0xFFADAD2B) {
						//FLOOR_SAND4
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_SAND4);
					}else if(pixelAtual == 0xFFAFAF57) {
						//FLOOR_SAND5
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_SAND5);
					}
					//WOOD
					else if(pixelAtual == 0xFF7F6F64) {
						//FLOOR_WOOD
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_WOOD);
					
			        }else if(pixelAtual == 0xFF77685E) {
						//FLOOR_WOOD_2
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_WOOD2);
					
			        }else if(pixelAtual == 0xFFFF6660) {
						//FLOOR_CARPET
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_CARPET);
					
			        }else if(pixelAtual == 0xFFFF544F) {
						//FLOOR_CARPET
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_CARPET2);
					
			        }else if(pixelAtual == 0xFFFF7C77) {
						//FLOOR_CARPET
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_CARPET3);
					
			        }else if(pixelAtual == 0xFFFF9996) {
						//FLOOR_CARPET
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_CARPET4);
					
			        }else if(pixelAtual == 0xFFFFBCBA) {
						//FLOOR_CARPET
						tiles[xx +(yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR_CARPET5);
					
			        }else if(pixelAtual == 0xFF494949) {
						//WINDOW
						tiles[xx +(yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WINDOW);
					
			        }
					
					
					
					//ENTIDADES
					else if(pixelAtual == 0xFF0026FF) {
						//PLAYER
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						
					}else if(pixelAtual == 0xFFFF0000) {
						//ENEMY
						Enemy en = new Enemy(xx*16, yy*16, 16, 16,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						
						
					}else if(pixelAtual == 0xFF680000) {
						//ENEMY2
						Enemy2 en2 = new Enemy2(xx*16, yy*16, 16, 16,Entity.ENEMY_EN2);
						Game.entities.add(en2);
						Game.enemies2.add(en2);
						
						
					}else if(pixelAtual == 0xFFE900FF) {
						//ENEMYBOSS
						EnemyBOSS enBoss = new EnemyBOSS(xx*16, yy*16, 16, 16,Entity.ENEMYBOSS_EN3);
						Game.entities.add(enBoss);
						Game.enemiesBoss.add(enBoss);
						
						
					}else if(pixelAtual == 0xFFD1FF77) {
						//ENEMYBOSS_2
						EnemyBOSS_2 enBoss2 = new EnemyBOSS_2 (xx*16, yy*16, 16, 16,Entity.ENEMYBOSS_2_EN4);
						Game.entities.add(enBoss2);
						Game.enemiesBoss2.add(enBoss2);
  
					}else if(pixelAtual == 0xFF000547) {
						//ZEN
						Zen zen = new Zen (xx*16, yy*16, 16, 16,Entity.ZEN);
						Game.entities.add(zen);
						Game.zen.add(zen);
  
					}else if(pixelAtual == 0xFF307E7F) {
						//SPIKES
						Spikes spikes = new Spikes (xx*16, yy*16, 16, 16,Entity.SPIKES);
						Game.entities.add(spikes);
						Game.spikes.add(spikes);
  
					}else if(pixelAtual == 0xFF9A3F91) {
						//PISTOL
						Game.entities.add(new Pistol(xx*16, yy*16, 16, 16,Entity.PISTOL_EN ));
						
						
					}else if(pixelAtual == 0xFF4CFF00) {
						//CHERRY
						Cherry cherry = new Cherry(xx*16, yy*16, 16, 16,Entity.CHERRY_EN );
						cherry.setMask(5, 7, 5, 5);
						Game.entities.add(cherry);
						
						
					}else if(pixelAtual == 0xFFFFD800) {
						//BULLET
						Game.entities.add(new Bullet(xx*16, yy*16, 16, 16,Entity.BULLET_EN ));
						
						
					}else if(pixelAtual == 0xFF7F3300) {
						//CARTA
						Carta carta = new Carta (xx*16, yy*16, 16, 16,Entity.CARTA);
						carta.setMask(5, 7, 5, 5);
						Game.entities.add(carta);

					}else if(pixelAtual == 0xFF160000) {
						//CURSE
						Curse curse = new Curse (xx*16, yy*16, 16, 16,Entity.CURSE);
						curse.setMask(5, 7, 5, 5);
						Game.entities.add(curse);
  
					}else if(pixelAtual == 0xFF52FFFF) {
						//SHIELD
						Shield shield = new Shield (xx*16, yy*16, 16, 16,Entity.SHIELD);
						shield.setMask(5, 7, 5, 5);
						Game.entities.add(shield);
  
					}else if(pixelAtual == 0xFFFF6A00) {
						//POTION
						Potion potion = new Potion (xx*16, yy*16, 16, 16,Entity.POTION);
						potion.setMask(5, 7, 5, 5);
						Game.entities.add(potion);
  
					}else if(pixelAtual == 0xFF593FFF) {
						//NPC_1
						Npc_1 npc_1 = new Npc_1 (xx*16, yy*16, 16, 16,Entity.NPC_1);
						npc_1.setMask(5, 7, 5, 5);
						Game.entities.add(npc_1);
  
					}else if(pixelAtual == 0xFF5B32FF) {
						//NPC_2
						Npc_2 npc_2 = new Npc_2 (xx*16, yy*16, 16, 16,Entity.NPC_1);
						npc_2.setMask(5, 7, 5, 5);
						Game.entities.add(npc_2);
  
					}else if(pixelAtual == 0xFFFFEEA5) {
						//GOLDEN_APPLE
						GoldenApple goldenApple = new GoldenApple (xx*16, yy*16, 16, 16,Entity.GOLDEN_APPLE);
						goldenApple.setMask(5, 7, 5, 5);
						Game.entities.add(goldenApple);
  
					}else if(pixelAtual == 0xFFD8F1FF) {
						//GOLDEN_APPLE
						SilverBullet silverBullet = new SilverBullet (xx*16, yy*16, 16, 16,Entity.SILVER_BULLET);
						silverBullet.setMask(5, 7, 5, 5);
						Game.entities.add(silverBullet);
  
					}
						
				}
			}
			

			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void generateParticles(int amount, int x, int y) {
		
		for(int i = 0; i < amount; i++) {
			
			Game.entities.add(new Particle(x,y,1,1,null));
			
		}
		
		
	}
	
public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0, Entity n1) {
			if(n1.depth < n0.depth)
				return + 1;
			if(n1.depth > n0.depth)
				return - 1;
			return 0;
		}
		
	};
	
	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+width-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+height -1) / TILE_SIZE;
		
		int x4 = (xnext+width -1) / TILE_SIZE;
		int y4 = (ynext+height -1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)]instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)]instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)]instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)]instanceof WallTile));
		
		
	}
	
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE -1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE -1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)]instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)]instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)]instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)]instanceof WallTile));
		
		
	}
	
	//COLISÃO DA BALA (CRASHANDO)
	/*
	public static boolean isFreeBullet(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+width-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+height -1) / TILE_SIZE;
		
		int x4 = (xnext+width -1) / TILE_SIZE;
		int y4 = (ynext+height -1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)]instanceof WallTile3) ||
				(tiles[x2 + (y2*World.WIDTH)]instanceof WallTile3) ||
				(tiles[x3 + (y3*World.WIDTH)]instanceof WallTile3) ||
				(tiles[x4 + (y4*World.WIDTH)]instanceof WallTile3));
		
	}
	*/
	
	public static void restartGame(String level){
		//LIMPANDO AS ENTIDADES
		Game.entities.clear();
		Game.enemies.clear();
		Game.enemies2.clear();
		Game.enemiesBoss.clear();
		Game.enemiesBoss2.clear();
		Game.zen.clear();
		Game.spikes.clear();
		Game.bullets.clear();
		Game.fireShoots.clear();
		Game.particle.clear();
		//COLOCANDO AS ENTIDADES
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.enemies2 = new ArrayList<Enemy2>();
		Game.enemiesBoss = new ArrayList<EnemyBOSS>();
		Game.enemiesBoss2 = new ArrayList<EnemyBOSS_2>();
		Game.zen = new ArrayList<Zen>();
		Game.spikes = new ArrayList<Spikes>();
		//COLOCANDO OUTRAS COIZINHAS
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
		return;
	}
	
	public void render(Graphics g) {
		
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
		
	}
	
	/*MINIMAPA (BUGADO)
	
	public static void renderMiniMap() {
		
		for(int i = 0; i < Game.miniMapPixels.length; i++) {
			
			Game.miniMapPixels[i] = 0x000000;
		}
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(tiles[xx + (yy * WIDTH)] instanceof WallTile) {
					Game.miniMapPixels[xx + (yy * WIDTH)] = 0xf0f0f0;
				}
			}
		}
		
		int xplayer = Game.player.getX()/16;
		int yplayer = Game.player.getY()/16;
		
		Game.miniMapPixels[xplayer + (yplayer * WIDTH)] = 0x0000ff;
		
	}
	
	*/
}

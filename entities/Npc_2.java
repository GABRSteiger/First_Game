package com.steiger.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.steiger.main.Game;
import com.steiger.world.Camera;

public class Npc_2 extends Entity {
	
	public static boolean npc_1 = false;

	public static int dialogeAnimationTimer = 0;
	public static boolean dialogeAnimation = false;
	public static boolean dialoge = false;
	public static int dialogeNumber = 1;
	public static int maxDialogeNumber = 3;
	
	public static boolean shop = false;
	
	//LOJA
	public String[] options = {"apple", "silver","boots","exit"};
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public static boolean down = false, up = false, enter = false;
	
	//GRAFICO
	public boolean apple = false, silver = false, boots = false;
	
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;

	public Npc_2(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(32, 0, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(64, 0, 16, 16);
		
		
		}
	
	public void tick() {
		
		depth = 1;
		
		if(!dialoge) 
			frames++;
		
		if(dialoge) 
			index = 0;
		
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) 
				index = 0;
		}
		
		dialogeAnimationTimer++;
		if(dialogeAnimationTimer == 100 ) {
			dialogeAnimationTimer = 0;
		}
			if(dialogeAnimationTimer > 50) {
				dialogeAnimation = false;
			}
			if(dialogeAnimationTimer < 50)
				dialogeAnimation = true;
		
			if(npc_1 == false) {
				dialoge = false;
				dialogeNumber = 1;
			}
			if(dialogeNumber >= Npc_2.maxDialogeNumber) {
				shop = true;
			}
			
			
			
			//SHOP
			
			if(up) {
				up = false;
				currentOption--;
				if(currentOption < 0) 
					currentOption = maxOption;
			}
			if(down) {
				down = false;
				currentOption++;
				if(currentOption > maxOption) 
					currentOption = 0;
			}
			
			if(enter) {
				enter = false;
				if(options[currentOption] == "apple") {
					if(Game.mainCoin >= 300 && Game.goldenApp == false) {
						//COMPROU APPLE
						Game.mainCoin -= 300;
						apple = true;
						Game.goldenApp = true;
					}
				}
				else if(options[currentOption] == "silver") {
					if(Game.mainCoin >= 500 && Game.silverBullt == false) {
						//COMPROU SILVER
						Game.mainCoin -= 500;
						silver = true;
						Game.silverBullt = true;
					}
				}
				else if(options[currentOption] == "boots") {
					if(Game.mainCoin >= 400 && Game.boots == false) {
						//COMPROU BOOTS
						Game.mainCoin -= 400;
						boots = true;
						Game.boots = true;
					}
				}else if(options[currentOption] == "exit") {
					shop = false;
					dialogeNumber = 1;
					Game.player.interact = false;
					currentOption = 0;
				}
			}
			
		
		
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g.drawImage(sprites[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		
		if(Npc_2.npc_1 == true) {
			if(Game.player.interact != true) {
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.player.getX()-Camera.x-20, Game.player.getY()-Camera.y-20, 60, 15);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD,9));
				g.drawString("Interagir (E)", Game.player.getX()- Camera.x-17, Game.player.getY()-Camera.y-10);
				}
			if(Game.player.interact == true && Npc_2.dialogeNumber == 1) {
				Npc_2.dialoge = true;
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 9));
				
				g.drawString("Quer ver as coisas que eu achei", Game.WIDTH/2/2-20, 30);
				g.drawString("nesse armazém?", Game.WIDTH/2/2-20, 40);
				
				if(Npc_2.dialogeAnimation == false)
					g.setColor(Color.WHITE);
				if(Npc_2.dialogeAnimation == true)
					g.setColor(Color.YELLOW);
				g.drawString("(E)", Game.WIDTH/2/2+55, 67);
				
			}else if(Game.player.interact == true && Npc_2.dialogeNumber == 2) {
				Npc_2.dialoge = true;
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 9));
				
				g.drawString("Só se você me pagar antes", Game.WIDTH/2/2-20, 30);
				g.drawString(":)", Game.WIDTH/2/2-20, 40);
				
				if(Npc_2.dialogeAnimation == false)
					g.setColor(Color.WHITE);
				if(Npc_2.dialogeAnimation == true)
					g.setColor(Color.YELLOW);
				g.drawString("(E)", Game.WIDTH/2/2+55, 67);
				
			}else if (Game.player.interact == true && shop == true) {
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 130);
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 9));
				
				g.drawString("Loja do Jorge", Game.WIDTH/2/2-18, 30);
				
				if(apple)
					g.setColor(Color.black);
				else g.setColor(Color.white);
				g.drawString("Maçã Dourada", Game.WIDTH/2/2, 50);
				if(silver)
					g.setColor(Color.black);
				else g.setColor(Color.white);
				g.drawString("Munição de Prata", Game.WIDTH/2/2, 70);
				if(boots)
					g.setColor(Color.black);
				else g.setColor(Color.white);
				g.drawString("Botas de Velocidade", Game.WIDTH/2/2, 90);
				g.setColor(Color.white);
				g.drawString("Sair", Game.WIDTH/2/2+100, 120);
				
				//SETAS
				if(options[currentOption] == "apple") {
					if(apple)
						g.setColor(Color.black);
					else g.setColor(Color.white);
					g.drawString(">", Game.WIDTH/2/2-10, 50);
				}
				else if(options[currentOption] == "silver") {
					if(silver)
						g.setColor(Color.black);
					else g.setColor(Color.white);
					g.drawString(">", Game.WIDTH/2/2-10, 70);
				}
				else if(options[currentOption] == "boots") {
					if(boots)
						g.setColor(Color.black);
					else g.setColor(Color.white);
					g.drawString(">", Game.WIDTH/2/2-10, 90);
				}
				else if(options[currentOption] == "exit") 
					g.drawString(">", Game.WIDTH/2/2+90, 120);
							
			}
			//System.out.println(Game.player.interact);
		}
		
		//DEBUG HITBOX
		//g.setColor(Color.blue);
		//g.fillRect(this.getX()+maskx-Camera.x, this.getY()+masky-Camera.y, mwidth, mheight);
		
	}
	
	}	

package com.steiger.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.steiger.main.Game;
import com.steiger.world.Camera;

public class Npc_1 extends Entity {
	
	public static boolean npc_1 = false;

	public static int dialogeAnimationTimer = 0;
	public static boolean dialogeAnimation = false;
	public static boolean dialoge = false;
	public static int dialogeNumber = 1;
	public static int maxDialogeNumber = 3;
	
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;

	public Npc_1(int x, int y, int width, int height, BufferedImage sprite) {
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
			if(dialogeNumber >= Npc_1.maxDialogeNumber) {
				dialogeNumber = 1;
				dialoge = false;
				Game.player.interact = false;
			}
		
		
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g.drawImage(sprites[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		
		if(Npc_1.npc_1 == true) {
			if(Game.player.interact != true) {
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.player.getX()-Camera.x-20, Game.player.getY()-Camera.y-20, 60, 15);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD,9));
				g.drawString("Interagir (E)", Game.player.getX()- Camera.x-17, Game.player.getY()-Camera.y-10);
				}
			if(Game.player.interact == true && Npc_1.dialogeNumber == 1) {
				Npc_1.dialoge = true;
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 9));
				if(Game.CUR_LEVEL == 5) {
				g.drawString("Ola como você esta?", Game.WIDTH/2/2-20, 30);
				g.drawString("Meu nome é Jorge, qual é o seu?", Game.WIDTH/2/2-20, 40);
				}else if(Game.CUR_LEVEL == 9) {
					g.drawString("Esse castelo é bem estranho", Game.WIDTH/2/2-20, 30);
					g.drawString("Eu vou dar meu fora daqui", Game.WIDTH/2/2-20, 40);
				}
				if(Npc_1.dialogeAnimation == false)
					g.setColor(Color.WHITE);
				if(Npc_1.dialogeAnimation == true)
					g.setColor(Color.YELLOW);
				g.drawString("(E)", Game.WIDTH/2/2+55, 67);
			}if(Game.player.interact == true && Npc_1.dialogeNumber == 2) {
				Npc_1.dialoge = true;
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(Game.WIDTH/2/2-20, 20, 165, 50);
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 9));
				if(Game.CUR_LEVEL == 5) {
					g.drawString("Não gosta de conversar?", Game.WIDTH/2/2-20, 30);
					g.drawString("Ok, não queria mesmo ;-;", Game.WIDTH/2/2-20, 40);
				}else if(Game.CUR_LEVEL== 9) {
					g.drawString("Tem alguém nesse castelo...",  Game.WIDTH/2/2-20, 30);
					g.drawString("E eu não tenho uma arma, então...", Game.WIDTH/2/2-20, 40);
				}
				if(Npc_1.dialogeAnimation == false)
					g.setColor(Color.WHITE);
				if(Npc_1.dialogeAnimation == true)
					g.setColor(Color.YELLOW);
				g.drawString("(E)", Game.WIDTH/2/2+55, 67);
				
			}
			//System.out.println(Game.player.interact);
		}
		
		//DEBUG HITBOX
		//g.setColor(Color.blue);
		//g.fillRect(this.getX()+maskx-Camera.x, this.getY()+masky-Camera.y, mwidth, mheight);
		
	}
	
	}	

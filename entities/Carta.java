package com.steiger.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.steiger.main.Game;
import com.steiger.world.Camera;

public class Carta extends Entity {

		public Carta(int x, int y, int width, int height, BufferedImage sprite) {
			super(x, y, width, height, sprite);
			
			depth = 0;
			
		}
		
		private boolean exit = false;
		public int detph = 0;
		public static boolean cartaPlayer = false;
		public static boolean carta = false;
		
	public void render(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			g.drawImage(Entity.CARTA, this.getX()-Camera.x, this.getY()-Camera.y, null);
			
			if(Carta.cartaPlayer) {
				if(Game.player.interact != true) {
					g2.setColor(new Color(0,0,0,100));
					g.fillRect(Game.player.getX()-Camera.x-20, Game.player.getY()-Camera.y-20, 60, 15);
					g.setColor(Color.WHITE);
					g.setFont(new Font("Arial", Font.BOLD,9));
					g.drawString("Interagir (E)", Game.player.getX()- Camera.x-17, Game.player.getY()-Camera.y-10);
				}
				if(Game.player.interact && exit == false) {
					carta = true;
					g2.setColor(new Color(0,0,0,150));
					g.fillRect(74, 14, 102, 52);
					g2.setColor(new Color(150,100,20));
					g.fillRect(75, 15, 100, 50);
					g.setColor(Color.white);
					g.drawString("GORDO", 100, 27);
					g.drawString("KKKKKK", 100, 40);
					g.drawString("KKKKKK", 100, 50);
					g.drawString("KKKKKK", 100, 60);
				//System.out.println("CARTA");
				}else
					carta = false;
				
				if(carta = false)
					Game.player.interact = false;
			}
			
		}
		
		
	}


	
	
	
	


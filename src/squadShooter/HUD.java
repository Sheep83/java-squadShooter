package squadShooter;

import java.awt.Color;
//import java.awt.Color;
//import java.awt.Font;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Rectangle;

import squadShooter.Game.TURN;
//import java.awt.image.BufferedImage;

public class HUD {
	
	public static int health = 100;
	public static int targetHealth;
	private int turnCount;
	private int greenValue = 0;
	@SuppressWarnings("unused")
	private int targetGreenValue = 0;
	@SuppressWarnings("unused")
	private Player player;
	@SuppressWarnings("unused")
	private Handler handler;
	@SuppressWarnings("unused")
	private Textures textures;
	private boolean inRange, enufAP;
	GameObject actor;
	
	public HUD(Handler handler, Textures textures) {
		this.handler = handler;
		this.player = (Player)handler.getPlayer();
		this.textures = textures;
	}
	

	public void tick() {
		if(Game.turn == TURN.Player) {
			
		}else {
			
		}
		
		health = Game.clamp(health, 0, 100);
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = health *2;
		health = Game.clamp(health, 0, 100);
		turnCount = Game.getTurnCount();
		enufAP = player.isEnufAP();
	}
	
	
	public Rectangle getPlayBoxBounds() {
		return new Rectangle(0, 0, 1280, 768);
	
	}
	
	
	public void render(Graphics g) {
//		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.white);
		g.drawString("turnCount: " + turnCount, 50, 50);
		g.drawString("Action Points: " + player.getActionPoints(), 50, 100);
		if(player.selectedTarget != null) {
		g.drawString("Target Health: " + player.selectedTarget.getHealth(), 50, 150);
		}
		if(!enufAP) {
			g.setColor(Color.red);
			g.drawString("Not Enough Action Points!", 500, 50);
		}
//		//HUD Window
//		g.drawRect(0, 768, 1280, 256);
		
//		//draw health bar
//		g.setColor(new Color(75, greenValue, 0));
//		g.fillRect(540, 864, health * 2, 32);
//		g.drawRect(540, 864, 200, 32);
		

	}


	public boolean isEnufAP() {
		return enufAP;
	}


	public void setEnufAP(boolean enufAP) {
		this.enufAP = enufAP;
	}


}



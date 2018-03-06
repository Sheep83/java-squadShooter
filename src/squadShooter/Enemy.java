package squadShooter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
//import java.util.LinkedList;

import squadShooter.Game.TURN;



public class Enemy extends GameObject implements Killable{
	
	private Handler handler;
	@SuppressWarnings("unused")
	private Textures textures;
//	private boolean spooked;
//	private boolean angry;
	private boolean dead;
	private int health = 100;
	private long attackTimer, timeOfDeath;
	private Animation moveLeft, moveRight, moveUp, moveDown;
	private BufferedImage idleSprite;
	private String name;
	private boolean unitTurn;
	private int actionPoints;
	private Path targetPath = null;


	public Enemy(String name, int x, int y, ID id, Handler handler, Textures textures) {
		super(x, y, id);
		this.handler = handler;
		this.textures = textures;
		this.name = name;
		dead = false;
		moveLeft = new Animation(5, textures.enemy[4], textures.enemy[5], textures.enemy[6], textures.enemy[7]);
		moveRight = new Animation(5, textures.enemy[8], textures.enemy[9], textures.enemy[10], textures.enemy[11]);
		moveDown = new Animation(5, textures.enemy[0], textures.enemy[1], textures.enemy[2], textures.enemy[3]);
		moveUp = new Animation(5, textures.enemy[12], textures.enemy[13], textures.enemy[14], textures.enemy[15]);
		idleSprite = textures.enemy[0];
	}

	@Override
	public void tick() {
		if(Game.turn == TURN.Enemy) {
//			Game.camera.setOffsetX(getX()-(Game.WIDTH/2));
//			Game.camera.setOffsetY(getY()-(Game.HEIGHT/2));
			if(targetPath != null && targetPath.getLength() > 0) {
				int targetX = targetPath.getX(0)*64;
				int targetY = targetPath.getY(0)*64;
				if(x < targetX) {
					velX = 2;
				}else if(x > targetX){
					velX = -2;
				}else if(y < targetY){
					velY = 2;
				}else if(y > targetY){
					velY = -2;
				}else{
					velX = 0;
					velY = 0;
					x = targetX;
					y = targetY;
					
					targetPath.steps.remove(0);
					if(targetPath.getLength() == 0) {
						targetPath = null;
						Game.setTurnCount(Game.getTurnCount() + 1);
						for(int i = 0; i < handler.tiles.size(); i++) {
							GameTile tile = (GameTile) handler.tiles.get(i);
							tile.setPath(false);
						}
						Game.turn = TURN.Player;
					}
				}
				
			}
//				else if(targetPath != null && targetPath.getLength() == 0) {
//				
//			}
			else if(targetPath == null || targetPath.getLength() == 0) {
				Player player = (Player)handler.getPlayer();
				targetPath = Game.finder.findPath(player, getMapX(), getMapY(), player.getMapX(), player.getMapY());
			}
			
			
			
			
			else if(dead) {
				if (despawnTimer()) {
					handler.removeObject(this);
			}	
			
			
		}
			
			
		
		moveLeft.runAnimation();
		moveRight.runAnimation();
		moveDown.runAnimation();
		moveUp.runAnimation();
		x += velX;
		y += velY;
		x = Game.clamp((int)x, Game.WIDTH - Game.WIDTH+64, Game.WIDTH-112);
		y = Game.clamp((int)y, Game.HEIGHT - Game.HEIGHT+64, Game.HEIGHT-112);
		
		}
	}
	
	

	public int getMapY() {
		int mapY = (int)y/64;
		return mapY;
	}

	public int getMapX() {
		int mapX = (int)x/64;
		return mapX;
	}

	@Override
	public void render(Graphics g) {
		if(Game.turn == TURN.Enemy) {
			Game.camera.offsetX = (Game.WIDTH/2 - x);
			Game.camera.offsetY = (Game.HEIGHT/2 - y);
		}
		Graphics2D g2d = (Graphics2D) g;
		if(velX < 0) {
			moveLeft.drawAnimation(g, x + Game.camera.offsetX, y + Game.camera.offsetY, 0);
			}else if(velX > 0) {
				moveRight.drawAnimation(g, x + Game.camera.offsetX, y + Game.camera.offsetY, 0);
			}else if(velY > 0) {
				moveDown.drawAnimation(g, x + Game.camera.offsetX, y + Game.camera.offsetY, 0);
			}else if(velY < 0) {
				moveUp.drawAnimation(g, x + Game.camera.offsetX, y + Game.camera.offsetY, 0);
			}else {
				g.drawImage(idleSprite, (int)(x + Game.camera.offsetX), (int)(y + Game.camera.offsetY), null);
			}
			
		if (dead) {
			g2d.setColor(Color.gray);
			g2d.fillRect((int)x, (int)y, 32, 32);
			
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public Point getMidPoint() {
		return new Point((int)x + 16, (int)y + 16);
	}
	
	public boolean checkCooldown(int secs){
		if(System.currentTimeMillis() - attackTimer < (secs * 1000) ) {
			return false;
		}else {
			return true;
		}
		
	}
	
	public boolean isDead() {
		return this.dead;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	private boolean despawnTimer() {
		if(System.currentTimeMillis() - timeOfDeath < 2000 ) {
			return false;
		}else {
			return true;
		}
		
	}
	
	public void decHealth(int delta) {
		this.health = this.health - delta;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public String getName() {
		return this.name;
	}

	public boolean isUnitTurn() {
		return unitTurn;
	}

	public void setUnitTurn(boolean unitTurn) {
		this.unitTurn = unitTurn;
	}

	public int getActionPoints() {
		return actionPoints;
	}

	public void setActionPoints(int actionPoints) {
		this.actionPoints = actionPoints;
	}

	public Path getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(Path targetPath) {
		this.targetPath = targetPath;
	}

	@Override
	public void incHealth(int value) {
		this.health += value;
		
	}



}

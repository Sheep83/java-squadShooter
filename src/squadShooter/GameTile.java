package squadShooter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import squadShooter.Game.TURN;

public class GameTile extends GameObject{
	
	public boolean passable, selected;
	private Handler handler;
	private boolean path, target, hover;
	private Animation mouseHover;
	private Textures textures;

	public GameTile(float x, float y, ID id, boolean passable, Handler handler, Textures textures) {
		super(x, y, id);
		this.passable = passable;
		this.handler = handler;
		this.textures = textures;
		mouseHover = new Animation(2, textures.icon[0], textures.icon[1], textures.icon[2], textures.icon[1]);
	}

	@Override
	public void tick() {
		GameObject player;
		mouseHover.runAnimation();
		if(Game.turn == TURN.Player) {
			player = handler.getPlayer();
		}else {
			player = handler.getEnemy();
		}
			if(player.getTargetPath() != null) {
			for(int i=0; i < player.getTargetPath().getLength(); i++) {
				int pathX = player.getTargetPath().getStep(i).getX();
				int pathY = player.getTargetPath().getStep(i).getY();
				if(getMapX() == pathX && getMapY() == pathY) {
					this.path = true;
					}
				}
			}
//		}else if(Game.turn == TURN.Enemy) {
//			Enemy enemy = (Enemy)handler.getEnemy();
//			if(enemy.getTargetPath() != null) {
//			for(int i=0; i < enemy.getTargetPath().getLength(); i++) {
//				int pathX = enemy.getTargetPath().getStep(i).getX();
//				int pathY = enemy.getTargetPath().getStep(i).getY();
//				if(getMapX() == pathX && getMapY() == pathY) {
//					this.path = true;
//					}
//				}
//			}
//		}
		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(this.passable) {
			g2d.setColor(Color.black);
			g2d.fillRect((int)(x + Game.camera.offsetX), (int)(y + Game.camera.offsetY), 64, 64);
			g2d.setColor(Color.green);
			g2d.fillRect((int)(x + Game.camera.offsetX) +1, (int)(y + Game.camera.offsetY) +1, 62, 62);
		}else {
			g2d.setColor(Color.black);
			g2d.fillRect((int)(x + Game.camera.offsetX), (int)(y + Game.camera.offsetY), 64, 64);
			g2d.setColor(Color.gray);
			g2d.fillRect((int)(x + Game.camera.offsetX) +1, (int)(y + Game.camera.offsetY) +1, 62, 62);
		}
		
		if(this.path) {
			g2d.setColor(Color.black);
			g2d.fillRect((int)(x + Game.camera.offsetX), (int)(y + Game.camera.offsetY), 64, 64);
			g2d.setColor(Color.blue);
			g2d.fillRect((int)(x + Game.camera.offsetX) +1, (int)(y + Game.camera.offsetY) +1, 62, 62);
		}
		if(this.hover) {
			mouseHover.drawAnimation(g, x + Game.camera.offsetX, y + Game.camera.offsetY, 0);
		}
//		else {
//			g2d.setColor(Color.black);
//			g2d.fillRect((int)(x + Game.camera.offsetX), (int)(y + Game.camera.offsetY), 64, 64);
//			g2d.setColor(Color.green);
//			g2d.fillRect((int)(x + Game.camera.offsetX) +1, (int)(y + Game.camera.offsetY) +1, 62, 62);
//			
//		}
			
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)(x + Game.camera.offsetX), (int)(y + Game.camera.offsetY), 64, 64);
	}
	
	public Rectangle getTopBounds() {
		return new Rectangle((int)(x + Game.camera.offsetX), (int)(y + Game.camera.offsetY), 64, 2);
	}
	
	public Rectangle getBottomBounds() {
		return new Rectangle((int)(x + Game.camera.offsetX), (int)(y + Game.camera.offsetY)+64, 64, 2);
	}
	
	public Rectangle getLeftBounds() {
		return new Rectangle((int)(x + Game.camera.offsetX), (int)(y + Game.camera.offsetY), 2, 64);
	}
	
	public Rectangle getRightBounds() {
		return new Rectangle((int)(x + Game.camera.offsetX)+64, (int)(y + Game.camera.offsetY), 2, 64);
	}
	
	public int getMapX() {
		return (int)x/64;
	}
	
	public int getMapY() {
		return (int)y/64;
	}
	
	public void setTarget(boolean value) {
		this.target = value;
	}
	
	
	public void setPath(boolean value) {
		this.path = value;
	}
	
	public boolean isPath() {
		return path;
	}

	@Override
	public Path getTargetPath() {
		return null;
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}
}


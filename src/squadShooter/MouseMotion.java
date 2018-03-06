package squadShooter;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import squadShooter.Game.STATE;

public class MouseMotion implements MouseMotionListener{
	
	private Handler handler;
	private GameTile targetTile;
	private long pathTimer;
	
	public MouseMotion(Handler handler) {
		this.handler = handler;
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mapX = (int)(e.getX() - Game.camera.offsetX)/64;
		int mapY = (int)(e.getY() - Game.camera.offsetY)/64;
//		System.out.println("Mouse moved! -- mapX: " + mapX + "  , mapY:  " + mapY);
		for(int i = 0; i < handler.tiles.size(); i++) {
			GameTile tile = (GameTile) handler.tiles.get(i);
			tile.setHover(false);
		}
		for(int i = 0; i < handler.tiles.size(); i++) {
			GameTile tile = (GameTile) handler.tiles.get(i);
			if(tile.getMapX() == mapX && tile.getMapY() == mapY)
			tile.setHover(true);
		}
		
		int mouseX = e.getX();
		int mouseY = e.getY();
		Point point = new Point(mouseX, mouseY);
		if(Game.gameState == STATE.Game) {
			HUD hud = Game.getHUD();
			Player player = (Player)handler.getPlayer();
			if(!player.isMoving())
			for(int i = 0; i < handler.tiles.size(); i++) {
				GameTile tile = (GameTile) handler.tiles.get(i);
				
				if(tile.getBounds().contains(point)) {
					targetTile = tile;
					tile.setTarget(true);
				}
				tile.setPath(false);
			}
			if(targetTile != null) {
				pathTimer = System.currentTimeMillis();
				if(cooldownTimer(1000)) {
				Path path = Game.finder.findPath(player, player.getMapX(), player.getMapY(), targetTile.getMapX(), targetTile.getMapY());
				
				if(path != null) {
					path.steps.remove(0);
				if(path.getLength() > player.getActionPoints()) {
					player.setEnufAP(false);
				}
				else {
					player.setEnufAP(true);
					player.setTargetPath(path);
				}
				}
//				Game.setPath(path);
//				player.moveToMap(path);
			}		
		}
		}
		
		
	}
	
	public boolean cooldownTimer(long cooldown) {
		if(System.currentTimeMillis() - pathTimer < cooldown) {
			return true;
		}else {
			return false;
		}
	}

}

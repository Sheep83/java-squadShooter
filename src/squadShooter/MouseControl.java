package squadShooter;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import squadShooter.Game.STATE;

public class MouseControl implements MouseListener, MouseMotionListener{
	
	private Handler handler;
	private GameTile targetTile;
	private long pathTimer;
	Point currPoint, prevPoint;
	
	public MouseControl(Handler handler) {
		this.handler = handler;
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		System.out.println(event);
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;
		if(prevPoint == null) {
			prevPoint = event.getPoint();
		}
		if(event.getX() > prevPoint.getX()) {
			right = true;
		}
		if(event.getPoint().getX() < prevPoint.getX()) {
			left = true;
		}
		if(event.getPoint().getY() > prevPoint.getY()) {
			down = true;
		}
		if(event.getPoint().getY() < prevPoint.getY()) {
			up = true;
		}
		if(up) {
			Game.camera.offsetY -= 2;
		}
		if(down) {
			Game.camera.offsetY += 2;
		}
		if(left) {
			Game.camera.offsetX -= 2;
		}
		if(right) {
			Game.camera.offsetX +=2;
		}
		
//		prevPoint = event.getPoint();
		

		
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		int mouseX = e.getX();
		int mouseY = e.getY();
		Point point = new Point(mouseX, mouseY);
		if(Game.gameState == STATE.Menu) {
		if (mouseX >= Game.WIDTH / 2 - 50 && mouseX <= Game.WIDTH/2 + 50) {
			if(mouseY >= 800 && mouseY <= 850) {
				Game.setState(STATE.Game);
			}
		}
		}else if(Game.gameState == STATE.Game) {
			Player player = (Player)handler.getPlayer();
			for(int i = 0; i < handler.tiles.size(); i++) {
				GameTile tile = (GameTile) handler.tiles.get(i);
				
				if(tile.getBounds().contains(point)) {
					targetTile = tile;
					Enemy target = checkForEnemy(tile);
					if(target != null) {
						player.selectedTarget = target;
						player.attack(target, player.getAbilities().get(0));
					}else {
						player.setMoving(true);
					}
//					tile.setTarget(true);
				}
				tile.setPath(false);
			}	
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	private Enemy checkForEnemy(GameTile tile) {
		Enemy enemy = null;
		for(int i = 0; i < handler.objects.size(); i++) {
			if(handler.objects.get(i).getId() == ID.Enemy) {
				Enemy object = (Enemy)handler.objects.get(i);
				if(object.getMapX() == tile.getMapX() && object.getMapY() == tile.getMapY()) {
					enemy = object;
				}
			}
		}
		return enemy;
		
	}
	
	public boolean cooldownTimer(long cooldown) {
		if(System.currentTimeMillis() - pathTimer < cooldown) {
			return true;
		}else {
			return false;
		}
	}

}

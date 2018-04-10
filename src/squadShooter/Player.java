package squadShooter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
//import java.util.LinkedList;
import java.util.Random;

import squadShooter.Game.TURN;

public class Player extends GameObject implements Killable{
	
	Random r = new Random();
	Handler handler;
	@SuppressWarnings("unused")
	private Textures textures;
	private BufferedImage idleSprite;
	private Animation moveLeft, moveRight, moveUp, moveDown;
//	private LinkedList<GameObject> threats;
	private boolean alive;
//	private long timeOfDeath;
	private long attackTimer;
	public Killable target, selectedTarget;
	private String name;
	private Path targetPath = null;
	private boolean unitTurn, enufAP;
	private int health, actionPoints;
	private boolean moving = false;
	private LinkedList<Ability> abilities;


	public Player(String name, float x, float y, ID id, Handler handler, Textures textures) {
		super(x, y, id);
		this.handler = handler;
		this.textures = textures;
		this.name = name;
		idleSprite = textures.player[0];
		moveLeft = new Animation(5, textures.player[4], textures.player[5], textures.player[6], textures.player[7]);
		moveRight = new Animation(5, textures.player[8], textures.player[9], textures.player[10], textures.player[11]);
		moveDown = new Animation(5, textures.player[0], textures.player[1], textures.player[2], textures.player[3]);
		moveUp = new Animation(5, textures.player[12], textures.player[13], textures.player[14], textures.player[15]);
		alive = true;
		actionPoints = 5;
		abilities = new LinkedList<Ability>();
		abilities.add(new Ability("Fire", 20));
		health = 100;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 48, 48);
	}
	
	public float getVelX() {
		return velX;
	}

	public void tick() {
		if(moving) {
		if(Game.turn == TURN.Player) {
			
		if(targetPath != null && targetPath.getLength() > 0) {
			int targetX = (int)((targetPath.getX(0)*64));
			int targetY = (int)((targetPath.getY(0)*64));
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
				actionPoints -= 1;
				targetPath.steps.remove(0);
				if(targetPath.getLength() == 0) {
					if(actionPoints == 0) {
					actionPoints = 5;
					targetPath = null;
					Game.setTurnCount(Game.getTurnCount() + 1);
					clearTiles();
					Game.turn = TURN.Enemy;
					}
				}
			}
			
		}
		else if(targetPath == null || targetPath.getLength() == 0) {
			moving = false;
		}		
			x += velX;
			y += velY;
			x = Game.clamp((int)x, Game.WIDTH - Game.WIDTH+64, Game.WIDTH-112);
			y = Game.clamp((int)y, Game.HEIGHT - Game.HEIGHT+64, Game.HEIGHT-112);
			moveLeft.runAnimation();
			moveRight.runAnimation();
			moveDown.runAnimation();
			moveUp.runAnimation();
			}
		}else {
			if (actionPoints != 0) {
				checkLOS();

			}
		}
	}
	

	public void render(Graphics g) {
		if(Game.turn == TURN.Player) {
			Game.camera.offsetX = (Game.WIDTH/2 - x);
			Game.camera.offsetY = (Game.HEIGHT/2 - y);
		}
		Graphics2D g2d = (Graphics2D) g;
		if(alive) {
			if(velX < 0) {
				moveLeft.drawAnimation(g, x + Game.camera.offsetX, y + Game.camera.offsetY, 0);
				}else if(velX > 0) {
					moveRight.drawAnimation(g, x + Game.camera.offsetX, y + Game.camera.offsetY, 0);
				}else if(velY > 0) {
					moveDown.drawAnimation(g, x + Game.camera.offsetX, y + Game.camera.offsetY, 0);
				}else if(velY < 0) {
					moveUp.drawAnimation(g, x + Game.camera.offsetX, y + Game.camera.offsetY, 0);
				}else {
					g.drawImage(idleSprite, (int)(x+Game.camera.offsetX), (int)(y+Game.camera.offsetY), null);
				}
		}else {
			g2d.setColor(Color.white);
			g2d.fillRect((int)x, (int)y, 48, 48);
		}
//		g2d.setColor(Color.red);
//		g2d.draw(getBounds());
	}
	
	@SuppressWarnings("unused")
	private boolean checkCooldown(int secs){
		if(System.currentTimeMillis() - attackTimer < (secs * 1000) ) {
			return false;
		}else {
			return true;
		}
		
	}
	
	@SuppressWarnings("unused")
	public float getDistance(GameObject subject, GameObject threat) {
		float diffX = subject.getX() - threat.getX();
		float diffY = subject.getY() - threat.getY();
		float distance = (float) Math.sqrt( (subject.getX() - threat.getX()) * (subject.getX() - threat.getX()) + (subject.getY() - threat.getY()) * (subject.getY() - threat.getY()));
		return distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getMapX() {
		int mapX = (int)x/64;
		return mapX;
	}

	public int getMapY() {
		int mapY = (int)y/64;
		return mapY;
	}


	public Path getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(Path targetPath) {
		this.targetPath = targetPath;
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
	
	public void clearTiles() {
		for(int i = 0; i < handler.tiles.size(); i++) {
			GameTile tile = (GameTile) handler.tiles.get(i);
			tile.setPath(false);
		}
	}

	public boolean isEnufAP() {
		return enufAP;
	}

	public void setEnufAP(boolean enufAP) {
		this.enufAP = enufAP;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public LinkedList<Ability> getAbilities() {
		return abilities;
	}

	public void setAbilities(LinkedList<Ability> abilities) {
		this.abilities = abilities;
	}
	
	public void attack(Killable target, Ability ability) {
		target.decHealth(ability.getDamage());
	}

	@Override
	public void decHealth(int value) {
		health =- value;
	}

	@Override
	public void incHealth(int value) {
		health += value;
	}

	@Override
	public int getHealth() {
		return health;
	}
	
	public void checkLOS() {
		Bresenham b = new Bresenham();
//		Point[][] grid = new Point[1280][1088];
//		for (int i = 0; i < 1280; i++)
//            for (int j = 0; j < 1088; j++)
//                grid[i][j] = new Point(i, j);
		for(int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject =  handler.objects.get(i);
			if(tempObject.getId() == ID.Enemy) {
				tempObject.setVisible(true);
				int enemyX = (int)tempObject.getX();
				int enemyY = (int)tempObject.getY();
				List<Point> line = b.findLine(Game.getGrid(), (int)(this.getX()), (int)(this.getY()), enemyX, enemyY);
//				System.out.println(line);
				
				for(int x = 0; x < handler.tiles.size(); x++) {
					GameTile tempTile = (GameTile)handler.tiles.get(x);
					if(tempTile.passable == false) {
						for(int y = 0; y < line.size(); y++) {
							Point linePoint = line.get(y);
							Rectangle tileBounds = tempTile.getRealBounds();
							if(tileBounds.contains(linePoint)) {
								tempObject.setVisible(false);
							}
						}
					}
				}
			}	
		}
	}
	
//	public GameObject getClosestThreat(LinkedList<GameObject> threats) {
//		float distToThreat;
//		float distToCurrentThreat;
//		GameObject closestThreat;
//		if(threats.size() > 0) {
//			distToThreat = getDistance(this, threats.get(0));
//			closestThreat =  threats.get(0);
//		for(int i = 0; i < threats.size(); i++) {
//			
//			distToCurrentThreat = getDistance(this, threats.get(i));
//			
//			if(distToCurrentThreat < distToThreat) {
//				closestThreat =  threats.get(i);
//				targetIndex = i;
//				distToThreat = getDistance(this, threats.get(i));
//				}
//			}
//		return closestThreat;
//		}else {
//			return null;
//		}
//		
//	}
	
//	public void moveToMap(Path path) {
//	for(int i = 0; i < path.getLength(); i++) {
//		int pathX = path.getStep(i).getX();
//		int pathY = path.getStep(i).getY();
//		if(getMapX() != pathX) {
//			if(getMapX() < pathX) {
//				velX = 3;
//			}else {
//				velX = -3;
//			}
//		}else if(getMapY() != pathY) {
//			if(getMapY() < pathY) {
//				velY = 3;
//			}else {
//				velY = -3;
//			}
//		}
////		setX(path.getStep(i).getX()*64);
////		setY(path.getStep(i).getY()*64);
//	}
//}
	
}


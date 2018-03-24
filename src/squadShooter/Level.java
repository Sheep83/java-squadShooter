package squadShooter;

import java.util.ArrayList;

import squadShooter.GameTile;
import squadShooter.Handler;
import squadShooter.ID;
import squadShooter.Textures;

public class Level implements TileBasedMap{
	
	private ArrayList<Integer> arr = new ArrayList<Integer>();
	Textures textures;
//	private boolean[][] visited = new boolean[WIDTH][HEIGHT];
	
	public Level(Textures textures) {
//		super(x, y, id);
//		this.handler = handler;
//		this.velX = (r.nextInt(4) + 1);
//		this.velY = -4;
		this.textures = textures;
	}
	
	public void addTile(int amount) {
		for(int i = 0; i < amount; i++) {
			this.getArr().add(1);
			}
		
	}
	
	public void addSolidTile(int amount) {
		for(int i = 0; i < amount; i++) {
			this.getArr().add(2);
			}
		
	}
	
	public void clearLevel() {
		for(int i = 0; i < getArr().size(); i++) {
			getArr().remove(i);
			}
	}
	
	public void build(Handler handler, int level) {
		int tileX = 0;
		int tileY = 0;
		this.setArr(new ArrayList<Integer>());
		
		if (level == 1) {
			addSolidTile(20);
			for(int i = 0; i < 6; i++) {
				addSolidTile(1);
				addTile(18);
				addSolidTile(1);
			}
			addSolidTile(9);
			addTile(2);
			addSolidTile(9);
			addSolidTile(9);
			addTile(2);
			addSolidTile(9);
			addTile(20);
			addTile(20);
			addSolidTile(3);
			addTile(2);
			addSolidTile(15);
			for(int i = 0; i < 4; i++) {
				addSolidTile(1);
				addTile(18);
				addSolidTile(1);
			}
			addSolidTile(20);
		}
//		}else if (level == 2) {
//			addTile(16, 2);
//			addTile(16, 1);
//			addTile(16, 2);
//			addTile(16, 1);
//		}else if (level == 3) {
//			addTile(16, 2);
//			addTile(16, 3);
//			addTile(16, 2);
//			addTile(16, 1);		
//		}else if (level == 99) {
//			addTile(16, 2);
//			addTile(16, 3);
//			addTile(16, 2);
//			addTile(16, 1);		
//		}
		
		for(int i = 0; i < getArr().size(); i++) {
				if(getArr().get(i) == 1) {
				handler.addTile(new GameTile(tileX, tileY, ID.Tile, true, handler, textures));
				tileX += 64;
				if((i + 1) % 20 == 0) {
				tileX = 0;
				tileY += 64;
				}
			}else if(getArr().get(i) == 2) {
				handler.addTile(new GameTile(tileX, tileY, ID.Tile, false, handler, textures));
				tileX += 64;
				if((i + 1) % 20 == 0) {
				tileX = 0;
				tileY += 64;
				}
			}else if(getArr().get(i) == 3) {
//				handler.addObject(new Brick(brickX, brickY, ID.Brick, Bricktype.Metal));
				tileX += 64;
				if((i + 1) % 16 == 0) {
				tileX = 64;
				tileY += 32;
				}
			}else if(getArr().get(i) == 0) {
//				handler.addObject(new Brick(brickX, brickY, ID.Brick, Bricktype.Metal, handler));
				tileX += 72;
				if((i + 1) % 16 == 0) {
				tileX = 64;
				tileY += 48;
				}
			}
		
	}
	
	}

	@Override
	public int getWidthInTiles() {
		int width = Game.WIDTH / 64;
		return width;
	}

	@Override
	public int getHeightInTiles() {
		int height = Game.HEIGHT / 64;
		return height;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
//		visited[x][y] = true;
		
	}

	@Override
	public boolean blocked(GameObject mover, int x, int y) {
		// if theres a unit at the location, then it's blocked
		///get the tile at mapx, mapy
		int index = ((y*getWidthInTiles()) + x);
		if(getArr().get(index) == 2) {
			return true;
		}else {
			return false;
		}

//				if (getUnit(x,y) != 0) {
//					return true;
//				}
//				
//				int unit = ((UnitMover) mover).getType();
//				
//				// planes can move anywhere
//
//				if (unit == PLANE) {
//					return false;
//				}
//				// tanks can only move across grass
//
//				if (unit == TANK) {
//					return terrain[x][y] != GRASS;
//				}
//				// boats can only move across water
//
//				if (unit == BOAT) {
//					return terrain[x][y] != WATER;
//				}
//				
//				// unknown unit so everything blocks
//
//				return true;
//		// TODO Auto-generated method stub
		
	}

	@Override
	public float getCost(GameObject mover, int sx, int sy, int tx, int ty) {
		// TODO Auto-generated method stub
		return 1;
	}

	public ArrayList<Integer> getArr() {
		return arr;
	}

	public void setArr(ArrayList<Integer> arr) {
		this.arr = arr;
	}
	

}



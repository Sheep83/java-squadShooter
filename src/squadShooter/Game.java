package squadShooter;

import java.awt.Canvas;
import java.awt.Color;
//import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
//import java.io.File;
import java.io.IOException;
//import java.util.Random;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 7436840021209078721L;
	public static final int WIDTH = 1280, HEIGHT = 1024;
	private Thread thread;
	private static BufferedImage playerSpriteSheet, enemySpriteSheet, iconSpriteSheet = null;
	private BufferedImage background = null;
	private boolean running = false;
//	private Random r = new Random();
	private static Handler handler;
	private static HUD hud;
	public static  Level level;
	private static Textures textures;
	private static int levelCount, turnCount;
	public static PathFinder finder;
//	public static Path path;
	private static GameTile targetTile = null;
	public static enum STATE {
		Menu,
		Game
	};
	public static STATE gameState;
	public static enum TURN {
		Player,
		Enemy
	};
	public static TURN turn;
	private Menu menu;
	private static Point[][] grid;
	public static Camera camera;
	public static float offsetX, offsetY;
	
	
	public Game() {
		
		
		new Window(WIDTH, HEIGHT, "squadShooter", this);
		camera = new Camera(0, 0);
		handler = new Handler();
		menu = new Menu(this, handler);
		gameState = STATE.Menu;
		this.addKeyListener(new KeyInput(handler));
		MouseControl mouseInput = new MouseControl(handler);
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);
//		this.addMouseListener(new MouseInput(handler));
		setupSprites();
//		initGame();
		level = new Level(textures);
		level.build(handler, 1);
		Player player = new Player("Brian", 256, 128, ID.Player, handler, textures);
		Enemy enemy = new Enemy("BadGuy", 512, 576, ID.Enemy, handler, textures);
		handler.addObject(player);
		handler.addObject(enemy);
		hud = new HUD(handler, textures);
		finder = new AStarPathFinder(level, 100, false);
//		path = finder.findPath(player, player.getMapX(), player.getMapY(), 13, 8);
		System.out.println("x " + player.getMapX() + "   y  " + player.getMapY());
		levelCount = 1;
		turnCount = 0;
		setGrid();
		turn = TURN.Player;
	}
	
	private void setGrid() {
		Point[][] grid = new Point[1280][1088];
		for (int i = 0; i < 1280; i++)
            for (int j = 0; j < 1088; j++)
                grid[i][j] = new Point(i, j);
		Game.grid = grid;
		
	}
	
	public static Point[][] getGrid(){
		return grid;
	}

	private void setupSprites() {
		
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			playerSpriteSheet = loader.loadImage("/hansolo.png");
			enemySpriteSheet = loader.loadImage("/stormtrooper.png");
			iconSpriteSheet = loader.loadImage("/icon.png");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		textures = new Textures(this);
		
	}
	
	public static BufferedImage getPlayerSpriteSheet() {
		return playerSpriteSheet;
	}
	
	public static BufferedImage getEnemySpriteSheet() {
		return enemySpriteSheet;
	}
	
	public static BufferedImage getIconSpriteSheet() {
		return iconSpriteSheet;
	}
///////////////////////////////
//	private void initGame() {
//		gameState = STATE.Menu;
//		setLevel(1);
//		handler = new Handler();
//		this.addKeyListener(new KeyInput(handler));
//		this.addMouseListener(new MouseInput(handler));
//		handler.addObject(new Player("Brian", WIDTH/2 - 32, HEIGHT - 32, ID.Player, handler, textures));
//		hud = new HUD(handler, textures);
//		level = new Level(textures);
//		level.build(handler, levelCount);
//		menu.tick();
//	}
	
//	private void gameOverCheck() {
//		Player player = (Player) handler.getPlayer();
//		if(player.getLives() <= 0) {
//			gameState = STATE.Menu;
//			initGame();
//		}
//	}
	///////////////////////////
	
	public static int getLevel() {
		return levelCount;
	}
	
	public static void setLevel(int level) {
		levelCount = level;
	}
	
	public static void setState(STATE state) {
		gameState = state;
	}
	
	public static Textures getTextures() {
		return textures;
	}
	
	public static Handler getHandler() {
		return handler;
	}
	
	public synchronized void start() {
	
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
	
		try {
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run()
    {
		this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        while(running){
        	long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1){
            	tick();
            	updates ++;
                delta--;
                }
            
            if(running)
            	render();
                frames++;
                            
                if(System.currentTimeMillis() - timer > 1000){
                	timer += 1000;
                	System.out.println("FPS: "+ frames + "   Ticks: " + updates);
                	updates = 0;
                	frames = 0;
                 }
        }
        stop();	
	}
	
	private void tick() {
		
		if(gameState == STATE.Menu) {
			menu.tick();
		}else if(gameState == STATE.Game) {
//			soundPlayer.stopSound();
//			soundPlayer.currentClip = null;
//			gameOverCheck();
			if(Game.turn == TURN.Player) {
				Player player = (Player)handler.getPlayer();
//				Game.camera.offsetX = (player.getX() + Game.WIDTH/2)*-1;
//				Game.camera.offsetY = (player.getY() + Game.HEIGHT/2)*-1;
//				System.out.println("offsetx: " + Game.camera.offsetX);
//				System.out.println("offsety: " + Game.camera.offsetY);
				hud.tick();	
				handler.tick();	
			}else if(Game.turn == TURN.Enemy) {
				Enemy enemy = (Enemy)handler.getEnemy();
//				Game.camera.setOffsetX(enemy.getX() + Game.WIDTH/2);
//				Game.camera.setOffsetY(enemy.getY() + Game.HEIGHT/2);
				hud.tick();	
				handler.tick();
			}
//			hud.tick();	
//			handler.tick();
//			Player player = (Player)handler.getPlayer();
//			if(targetTile != null) {
////				for(int i = 0; i < handler.tiles.size(); i++) {
////					GameTile tile = (GameTile)handler.tiles.get(i);
//////					tile.setPath(false);
////				}
////				path = finder.findPath(player, player.getMapX(), player.getMapY(), targetTile.getMapX(), targetTile.getMapY());
//			}
//			path = finder.findPath(player, player.getMapX(), player.getMapY(), 16, 3);
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if(gameState == STATE.Game) {
			g.drawImage(background, 0, 0, this);
			handler.render(g);
			hud.render(g);	
		}else if (gameState == STATE.Menu) {
			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static int clamp(int var, int min, int max) {
		if (var >= max) {
			return var = max;
		}else if (var <= min) {
			return var = min;
		}
		else {
			return var;
		}
	}
	
//	public Path getPath() {
//		return path;
//	}
//	

	public static void main(String[] args) {
		new Game();
	}
	
	public static HUD getHUD() {
		return hud;
	}
	
	public static void setTargetTile(GameTile tile) {
		targetTile = tile;
	}
	
	public static GameTile getTargetTile() {
		return targetTile;
	}
	
//	public static void setPath(Path newPath) {
//		path = newPath;
//	}
//
//	public static Path getPath() {
//		// TODO Auto-generated method stub
//		return path;
//	}

	public static int getTurnCount() {
		return turnCount;
	}

	public static void setTurnCount(int turnCount) {
		Game.turnCount = turnCount;
	}
	

}




package squadShooter;

import java.awt.image.BufferedImage;

import squadShooter.Game;
import squadShooter.SpriteSheet;

public class Textures {
	
	public BufferedImage[] player = new BufferedImage[16];
	public BufferedImage[] enemy = new BufferedImage[16];
	public BufferedImage[] icon = new BufferedImage[3];
	
	private SpriteSheet playerSprites, enemySprites, iconSprites;
	
	public Textures(Game game) {
		playerSprites = new SpriteSheet(Game.getPlayerSpriteSheet());
		enemySprites = new SpriteSheet(Game.getEnemySpriteSheet());
		iconSprites = new SpriteSheet(Game.getIconSpriteSheet());
		getTextures();
	}
	
	public void getTextures() {
		
		player[0] = playerSprites.grabCustomImage(1, 1, 32, 48);
		player[1] = playerSprites.grabCustomImage(2, 1, 32, 48);
		player[2] = playerSprites.grabCustomImage(3, 1, 32, 48);
		player[3] = playerSprites.grabCustomImage(4, 1, 32, 48);
		player[4] = playerSprites.grabCustomImage(1, 2, 32, 48);
		player[5] = playerSprites.grabCustomImage(2, 2, 32, 48);
		player[6] = playerSprites.grabCustomImage(3, 2, 32, 48);
		player[7] = playerSprites.grabCustomImage(4, 2, 32, 48);
		player[8] = playerSprites.grabCustomImage(1, 3, 32, 48);
		player[9] = playerSprites.grabCustomImage(2, 3, 32, 48);
		player[10] = playerSprites.grabCustomImage(3, 3, 32, 48);
		player[11] = playerSprites.grabCustomImage(4, 3, 32, 48);
		player[12] = playerSprites.grabCustomImage(1, 4, 32, 48);
		player[13] = playerSprites.grabCustomImage(2, 4, 32, 48);
		player[14] = playerSprites.grabCustomImage(3, 4, 32, 48);
		player[15] = playerSprites.grabCustomImage(4, 4, 32, 48);
		enemy[0] = enemySprites.grabCustomImage(1, 1, 32, 48);
		enemy[1] = enemySprites.grabCustomImage(2, 1, 32, 48);
		enemy[2] = enemySprites.grabCustomImage(3, 1, 32, 48);
		enemy[3] = enemySprites.grabCustomImage(4, 1, 32, 48);
		enemy[4] = enemySprites.grabCustomImage(1, 2, 32, 48);
		enemy[5] = enemySprites.grabCustomImage(2, 2, 32, 48);
		enemy[6] = enemySprites.grabCustomImage(3, 2, 32, 48);
		enemy[7] = enemySprites.grabCustomImage(4, 2, 32, 48);
		enemy[8] = enemySprites.grabCustomImage(1, 3, 32, 48);
		enemy[9] = enemySprites.grabCustomImage(2, 3, 32, 48);
		enemy[10] = enemySprites.grabCustomImage(3, 3, 32, 48);
		enemy[11] = enemySprites.grabCustomImage(4, 3, 32, 48);
		enemy[12] = enemySprites.grabCustomImage(1, 4, 32, 48);
		enemy[13] = enemySprites.grabCustomImage(2, 4, 32, 48);
		enemy[14] = enemySprites.grabCustomImage(3, 4, 32, 48);
		enemy[15] = enemySprites.grabCustomImage(4, 4, 32, 48);
		icon[0] = iconSprites.grabImage64(1, 1, 64, 64);
		icon[1] = iconSprites.grabImage64(2, 1, 64, 64);
		icon[2] = iconSprites.grabImage64(1, 2, 64, 64);
		
		
	}

}


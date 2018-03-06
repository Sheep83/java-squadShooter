package squadShooter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import squadShooter.Game.STATE;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	
	public KeyInput(Handler handler) {
		this.handler = handler;
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
//		keyDown[4] = false;
	}
	
	public void keyPressed(KeyEvent e) {
		if(Game.gameState == STATE.Menu ) {
			int key = e.getKeyCode();
			if (key == 32) {
			 Game.gameState = STATE.Game;
//			 Ball ball = (Ball)handler.getBall();
//			 ball.setVelY(3);
			}
		}
		if(Game.gameState == STATE.Game ) {
		int key = e.getKeyCode();
		System.out.println(key);
		
//		if (Game.getState() == GameState.Menu){
//			if (key == KeyEvent.VK_W) {Game.setState(GameState.Game);}
//			
//		}
		
//		for(int i = 0; i < handler.objects.size(); i++) {
//			GameObject tempObject =  handler.objects.get(i);
//			if(tempObject.getId() == ID.Player) {
//				Player player = (Player)tempObject;
//				
//				if (key == KeyEvent.VK_W) {
////					Game.camera.offsetY += 5;
////					tempObject.setVelY(-5); 
//					keyDown[0] = true;
//					}
//				if (key == KeyEvent.VK_S) {
////					Game.camera.offsetY -= 5;
////					tempObject.setVelY(5); 
//					keyDown[1] = true;
//					}
//				if (key == KeyEvent.VK_A) {
////					tempObject.setVelX(-5); 
//					keyDown[2] = true;
////					Game.camera.offsetX += 5;
//					}
//				if (key == KeyEvent.VK_D) {
////					tempObject.setVelX(5);
////					Game.camera.offsetX -= 5;
//					keyDown[3] = true;
//					}
//			}else {
//				return;
//			}
////			
//		}
		// save map //
		if(key == KeyEvent.VK_S) {
			SaveGame save  = new SaveGame("test", Game.level.getArr());
			save.writeToFile();
		}
		//pause//
		if(key == KeyEvent.VK_P) Game.setState(STATE.Menu);
		//Quit
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_W) {
			
		}
		}
		
		
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject =  handler.objects.get(i);
			if(tempObject.getId() == ID.Player) {
				//key events for player1
				if (key == KeyEvent.VK_W) keyDown[0] = false;//tempObject.setVelY(0);
				if (key == KeyEvent.VK_S) keyDown[1] = false;//tempObject.setVelY(0);
				if (key == KeyEvent.VK_A) keyDown[2] = false;//tempObject.setVelX(0);
				if (key == KeyEvent.VK_D) keyDown[3] = false;//tempObject.setVelX(0);
				if (key == KeyEvent.VK_Q) keyDown[4] = false;//tempObject.setVelX(0);
				if(!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
				if(!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
				
//				if (keyDown[4]) handler.addObject(new Ammo (tempObject.getX(), tempObject.getY(), ID.Ammo, handler));
					
				
				
			}
			
		}	
		
	}
}



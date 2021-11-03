package GameTemplate;


import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Graphics.GameObject;
import Input.KeyInput;
import WorldVariables.EnvironmentVariables;

public class ProcessGameInput {

	public static void processKeyInput() {
		//if user presses "R" reset world to initial state
		if(KeyInput.getKey(KeyEvent.VK_R)) {
			
			EnvironmentVariables.resetGameWorld();
		}
		if(KeyInput.getKey(KeyEvent.VK_SPACE)) {
			if(EnvironmentVariables.getMainPlayer() != null) {
				EnvironmentVariables.getMainPlayer().setDy(100 * EnvironmentVariables.getMeter() / GameLoop.getFPS());
				EnvironmentVariables.getMainPlayer().setDx(40 * EnvironmentVariables.getMeter() / GameLoop.getFPS());
			}
			
		}
		
		if(KeyInput.getKey(KeyEvent.VK_LEFT)) {
			if(EnvironmentVariables.getMainPlayer() != null) {
				EnvironmentVariables.getMainPlayer().setDx(-1 * (60 * EnvironmentVariables.getMeter() / GameLoop.getFPS()));
			}
		}
		if(KeyInput.getKey(KeyEvent.VK_RIGHT)) {
			if(EnvironmentVariables.getMainPlayer() != null) {
				EnvironmentVariables.getMainPlayer().setDx(60 * EnvironmentVariables.getMeter() / GameLoop.getFPS());
			}
		}
	}
	
	public static void processMouseInput() {
		
	}
}

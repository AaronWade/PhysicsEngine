package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Graphics.HomePage;
import Graphics.Window;


public class KeyInput implements KeyListener{
	//create a boolean array the length of 256(number of key codes)
	public static boolean[] keys = new boolean[256];

	@Override
	public void keyTyped(KeyEvent event) {
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		//set index that equals keycode to be true
		keys[event.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		//set index that equals keycode to be false
		keys[event.getKeyCode()] = false;
		
	}
	
	public static boolean getKey(int keyCode) {
		//get the key at this key code (true or false)
		return keys[keyCode];
	}

}

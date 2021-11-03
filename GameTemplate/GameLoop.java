package GameTemplate;

import Graphics.Window;

public class GameLoop {
	public static boolean running = false;
	
	public static int fps = 60;
	//1 second worth of nanoseconds / frames per second
	static int time = 1000000000 / fps;
	
	public static void start() {
		Thread thread = new Thread() {
			public void run() {
				
				while(running) {
					//the system time as this loop iteration has started
					long startTime = System.nanoTime();
					
					//update game (moving objects and add physics)
					GameWorld.update();
					
					//render game
					GameWindow.render();
					
					//time taken to update and render
					long timeTaken = System.nanoTime() - startTime;
					
					//ensure we run at approx fps
					if (timeTaken < time) {
						try {
							//thread.sleep takes miliseconds rather than nanoseconds
							Thread.sleep((time - timeTaken) / 1000000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		running = true;
		thread.setName("GameLoop");
		thread.start();
	}
	
	public static int getFPS() {
		return fps;
	}
}

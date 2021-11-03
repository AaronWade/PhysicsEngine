package GameTemplate;

import Graphics.GameObject;
import Math.Collision;
import Math.Drag;
import Math.Gravity;
import Math.ObjectCollisionReaction;
import Math.Restitution;
import Math.TerrainCollisionReaction;
import WorldVariables.EnvironmentVariables;

public class GameWorld {
	
	static ProcessGameInput process = new ProcessGameInput();
	
	//equations
	public static boolean world_gravity = true;
	public static boolean restitution = true;
	public static boolean drag = true;
	public static boolean gravity_attraction = true;
	public static boolean friction = true;
	
	public static void update() {
		//MAIN
		//apply gravity to main
		if(EnvironmentVariables.getMainPlayer() != null) {
			if(world_gravity) {
				Gravity.applyEnvironmentGravity(EnvironmentVariables.getMainPlayer());
			}
		
			if(drag) {
				if (Drag.terminalVelocity(EnvironmentVariables.getMainPlayer()) == 0) {
					if(world_gravity) {
						Gravity.applyEnvironmentGravity(EnvironmentVariables.getMainPlayer());
					}
				}
				else if(EnvironmentVariables.getMainPlayer().getDy() > Drag.terminalVelocity(EnvironmentVariables.getMainPlayer())) {
					if(world_gravity) {
						Gravity.applyEnvironmentGravity(EnvironmentVariables.getMainPlayer());
					}
					Drag.applyGravityDrag(EnvironmentVariables.getMainPlayer());
				}
				else {
					Drag.applyGravityDrag(EnvironmentVariables.getMainPlayer());
				}
			}
			else {
				if(world_gravity) {
					Gravity.applyEnvironmentGravity(EnvironmentVariables.getMainPlayer());
				}
			}
			for(GameObject terrain: EnvironmentVariables.getTerrain()) {
				if(Collision.collide(EnvironmentVariables.getMainPlayer(), terrain)) {
					
					TerrainCollisionReaction.applyCollisionReaction(EnvironmentVariables.getMainPlayer(), terrain);
					if(EnvironmentVariables.getMainPlayer().getDy() < 1 && EnvironmentVariables.getMainPlayer().getDy() > -1) {
						EnvironmentVariables.getMainPlayer().setDy(0);
					}
				}
			}
			
		}
	
		
		for(GameObject obj: EnvironmentVariables.getWorldObjects()) {
			System.out.println(obj.getMass());
			if(EnvironmentVariables.getMainPlayer() != null) {
				if(Collision.collide(obj, EnvironmentVariables.getMainPlayer())) {
					ObjectCollisionReaction.applyCollisionReaction(obj, EnvironmentVariables.getMainPlayer());
				}
				if(gravity_attraction == true) {
					Gravity.applyGravitationalAttraction(EnvironmentVariables.getMainPlayer(), obj);
					if(!obj.getLock()) {
						Gravity.applyGravitationalAttraction(obj, EnvironmentVariables.getMainPlayer());
					}
					
				}
			}
			for(GameObject obj2: EnvironmentVariables.getWorldObjects()) {
				
				if(obj.getLock() == false || obj2.getLock() == false) {
					
					//so not to compare object with itself
					if(obj.getID() != obj2.getID()) {
						if(Collision.collide(obj, obj2)) {
							ObjectCollisionReaction.applyCollisionReaction(obj, obj2);
						}
						else {
							if(gravity_attraction == true) {
								Gravity.applyGravitationalAttraction(obj, obj2);
							}
							
						}
					}
				}
			}
			
			
			for(GameObject terrain: EnvironmentVariables.getTerrain()) {
				if(obj.getLock() == false) {
					
					if(Collision.collide(obj, terrain)) {
						
						TerrainCollisionReaction.applyCollisionReaction(obj, terrain);
						if(obj.getDy() < 2 && obj.getDy() > -2) {
							obj.setDy(0);
						}
					}
				}
				
			}
			
			//if drag is active
			if(drag) {
				//if there is no terminal velocity/ no drag, then add gravity
				if (Drag.terminalVelocity(obj) == 0) {
					if(world_gravity) {
						Gravity.applyEnvironmentGravity(obj);
					}
				}
				// if obj hasn't reached terminal velocity the  add gravity
				//both terminal velocity and gravity are - values
				else if(obj.getDy() > Drag.terminalVelocity(obj)) {
					if(world_gravity) {
						Gravity.applyEnvironmentGravity(obj);
					}
					//apply drag
					Drag.applyGravityDrag(obj);
				}
				//if equal to terminal velocity or has exceeded it, apply drag
				else {
					Drag.applyGravityDrag(obj);
				}
			}
			// if drag isnt active and gravity is, apply gravity
			else {
				if(world_gravity) {
					Gravity.applyEnvironmentGravity(obj);
				}
			}
			
			//set the objects new x and y positions according to its velocity
			obj.setY(obj.getDy() + obj.getY());
			obj.setX(obj.getDx() + obj.getX());
			
		}
		
		if(EnvironmentVariables.getMainPlayer() != null) {
			//Set the main players new x and y positions
			EnvironmentVariables.getMainPlayer().setY(EnvironmentVariables.getMainPlayer().getY()
					+ EnvironmentVariables.getMainPlayer().getDy());
			
			for (GameObject object: EnvironmentVariables.getWorldObjects()){
				object.setX(object.getX() - EnvironmentVariables.getMainPlayer().getDx());
			}
			for (GameObject terrain: EnvironmentVariables.getTerrain()){
				terrain.setX(terrain.getX() - EnvironmentVariables.getMainPlayer().getDx());
			}
			
			if(EnvironmentVariables.getMainPlayer().getDy() < 10 && EnvironmentVariables.getMainPlayer().getDy() > -10) {
				EnvironmentVariables.getMainPlayer().setDy(0);
			}
		}
		
		//process input (change main dx and dy)
		process.processKeyInput();
	}
	
	//call the render functions
	public static void render() {
		EnvironmentVariables.renderWorldObjects();
		
		if(EnvironmentVariables.getMainPlayer() != null) {
			EnvironmentVariables.renderMainPlayer();
		}
		
		EnvironmentVariables.renderTerrain();
		
	}

	//Getters and Setters
	public static void setWorldGravity(boolean world_gravity) {
		GameWorld.world_gravity = world_gravity;
	}
	public static void setRestitution(boolean restitution) {
		GameWorld.restitution = restitution;
	}
	public static void setDrag(boolean drag) {
		GameWorld.drag = drag;
	}
	public static void setGravityAttraction(boolean gravity_attraction) {
		GameWorld.gravity_attraction = gravity_attraction;
	}
	public static void setFriction(boolean friction) {
		GameWorld.friction = friction;
	}
	
}

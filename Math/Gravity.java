package Math;

import GameTemplate.GameLoop;
import Graphics.GameObject;
import WorldVariables.EnvironmentVariables;

public class Gravity {

	//gravitational constant
	private static float g_const = (float) (6.673 * (Math.pow(10,  -11)));
	
	//environmentVariables
	static float environment_mass = 0;
	private static float em_power = (float) Math.pow(10, 24);
	private static float environment_radius = 0;
	static float gravity = 0;
	
	/**
	 * apply gravity to the object obj
	 * @param obj
	 */
	public static void applyEnvironmentGravity(GameObject obj) {
		environment_mass = EnvironmentVariables.getMass() * em_power;
		
		if (EnvironmentVariables.getRadius() > 0) {
			if(obj.getLock() == false) {
				//radius * 1000 because gravity is in m/s and radius is km
				gravity = (float) ((g_const * environment_mass) / Math.pow(EnvironmentVariables.getRadius() * 1000, 2));
				
				//gravity is m/s so multiply by meter size and divide by FPS
				gravity = (gravity * EnvironmentVariables.getMeter()) / GameLoop.getFPS();
				
				//set obj velocity to be current velocity + gravity
				obj.setDy(obj.getDy() - gravity);
			}
		}
	}
	
	//returns the current gravity in m/s
	public static float getGravity() {
		if (EnvironmentVariables.getRadius() > 0) {
			//radius * 1000 because gravity is in m/s and radius is km
			gravity = (float) ((g_const * environment_mass) / Math.pow(EnvironmentVariables.getRadius() * 1000, 2));
			
			return gravity;
				
		}
		else {
			return 1;
		}
	}
	
	/**
	 * apply gravitational attraction between the 2 object obj1 and obj2, to obj1
	 * @param obj1
	 * @param obj2
	 */
	public static void applyGravitationalAttraction(GameObject obj1, GameObject obj2) {
		//if obj1 possition isnt locked
		if (!obj1.getLock()) {
			float distance_x = (obj2.getX()) - (obj1.getX());
			float distance_y = (obj2.getY()) - (obj1.getY());
			float distance = ((distance_x * distance_x) + (distance_y * distance_y)) * EnvironmentVariables.getMeter(); 
			
			//Calculate force of attraction
			float force = (g_const * obj1.getMass() * obj2.getMass()) / distance;
			
			//direction of force
			float tangent = (obj1.getX() - obj2.getX()) / (obj1.getY() - obj2.getY());
			double angle = Math.atan(tangent);
			angle = angle * (180 / 3.14159);
			
			//depending on the quadrant of the collision
			if(obj1.getX() > obj2.getX()) {
				angle += 90;
				if(obj1.getY() < obj2.getY()) {
					angle += 90;
				}
			}
			else {
				if(obj1.getY() < obj2.getY()) {
					angle += 270;
				}
			}
			
			//get the force in the x direction
			float force_x = (float) ((Math.cos(angle) * force) / GameLoop.getFPS());
			//get the force in the y direction
			float force_y = (float) ((Math.sin(angle) * force) / GameLoop.getFPS());
			
			//apply the force to the objects a and y velocities
			obj1.setDx(obj1.getDx() + ((force_x /obj1.getMass()) * EnvironmentVariables.getMeter()));
			obj1.setDy(obj1.getDy() + ((force_y /obj1.getMass()) * EnvironmentVariables.getMeter()));
		}
	}
		
}

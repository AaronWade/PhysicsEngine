package Math;

import GameTemplate.GameWindow;
import GameTemplate.GameWorld;
import Graphics.GameObject;
import WorldVariables.EnvironmentVariables;

public class TerrainCollisionReaction {
	static float rest;
	static float fric;
	
	public static void applyCollisionReaction(GameObject obj1, GameObject obj2) {
		//if restitution active, find it
		if(GameWorld.restitution == true) {
			rest = Restitution.averageRestitution(obj1, obj2);
		}else {
			rest = 1;
		}
		//if friction active, find coefficient
		if(GameWorld.friction == true) {
			fric = Friction.getFrictionCD(obj1, obj2);
		}else {
			fric = 1;
		}
		
		//apply reaction based on shape
		if(obj1.getShape().equals("Circle")) {
			if(obj2.getShape().equals("Circle")) {
				circleCircle(obj1, obj2);
			}
			else if(obj2.getShape().equals("Square")) {
				circleRect(obj1, obj2);
			}
		}
		else if(obj2.getShape().equals("Circle")) {
			if(obj1.getShape().equals("Square")) {
				rectCircle(obj1, obj2);
			}
		}
		else {
			rectRect(obj1, obj2);
		}
	}
	
	public static void circleCircle(GameObject obj1, GameObject obj2) {
		//distance between center points
		float distance = (float) Math.sqrt((double)((obj1.getX() - obj2.getX()) * (obj1.getX() - obj2.getX()) + 
				(obj1.getY() - obj2.getY()) * (obj1.getY() - obj2.getY())));
		//find their radii
		float obj1_radius = obj1.getScale() * EnvironmentVariables.getMeter() / 2;
		float obj2_radius = obj2.getScale() * EnvironmentVariables.getMeter() / 2;
		//find the overlap
		float overlap = (float) (distance - obj1_radius - obj2_radius);
		//move obj1 x and y as obj2 position is locked
		obj1.setX(obj1.getX() - ((overlap * (obj1.getX() - obj2.getX())) / distance));
		obj1.setY(obj1.getY() - ((overlap * (obj1.getY() - obj2.getY())) / distance));
		
		//find angle of collision
		float tangent = (obj1.getX() - obj2.getX()) / (obj1.getY() - obj2.getY());
		double angle = Math.atan(tangent);
		angle = angle * (180 / 3.14159);
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
		
		//Get combined velocity
		float velocity = (Math.abs(obj1.getDx()) + Math.abs(obj1.getDy())) * rest;
		
		//set new velocity rekative to collision
		obj1.setDx((float)(velocity * Math.cos(angle)));
		obj1.setDy((float)(velocity * Math.sin(angle)));
		
		
	}
	
	public static void circleRect(GameObject obj1, GameObject obj2) {
		//Points of both centers
				Point obj1_center = new Point(obj1.getX(), obj1.getY());
				Point obj2_center = new Point(obj2.getX(), obj2.getY());
				
				
				float obj1_size = (obj1.getScale() * EnvironmentVariables.getMeter()) / 2;
				float obj2_size = (obj2.getScale() * EnvironmentVariables.getMeter()) / 2;
				
				//check for intersection Point on top side===============================================================
				Point intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
						new Point(obj2.getX() - obj2_size, obj2.getY() + obj2_size), 
						new Point(obj2.getX() + obj2_size, obj2.getY() + obj2_size))));
				//if not parallel
				if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
					if(intersection.getX() > obj2.getX() - obj2_size  && intersection.getX() < obj2.getX() + obj2_size) {
						obj1.setY(obj2.getY() + obj2_size + obj1_size);
						yRestxFrict(obj1, obj2);
						return;
					}
				}
				
				//check for intersection Point bottom side================================================================
				intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
						new Point(obj2.getX() - obj2_size, obj2.getY() - obj2_size), 
						new Point(obj2.getX() + obj2_size, obj2.getY() - obj2_size))));
				if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
					if(intersection.getX() > obj2.getX() - obj2_size && intersection.getX() < obj2.getX() + obj2_size ) {
						obj1.setY(obj2.getY() - obj2_size - obj1_size);
						yRestxFrict(obj1, obj2);
						return;
					}
				}
				
				//check for intersection Point left side====================================================================
				intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
						new Point(obj2.getX() - obj2_size, obj2.getY() + obj2_size), 
						new Point(obj2.getX() - obj2_size, obj2.getY() - obj2_size))));
					
				if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
					if(intersection.getY() > obj2.getY() - obj2_size  && intersection.getY() < obj2.getY() + obj2_size ) {
						obj1.setX(obj2.getX() - obj2_size - obj1_size);
						xRestyFrict(obj1, obj2);
						return;
					}
				}
				
				//check for intersection Point right side====================================================================
				intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
						new Point(obj2.getX() + obj2_size, obj2.getY() + obj2_size), 
						new Point(obj2.getX() + obj2_size, obj2.getY() - obj2_size))));
				
				if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
					if(intersection.getY() > obj2.getY() - obj2_size  && intersection.getY() < obj2.getY() + obj2_size ) {
						obj1.setX(obj2.getX() + obj2_size + obj1_size);
						xRestyFrict(obj1, obj2);
						return;
					}	
				}
				
	}
	
	public static void rectCircle(GameObject obj1, GameObject obj2) {
		//distance between center points
				float distance = (float) Math.sqrt((double)((obj1.getX() - obj2.getX()) * (obj1.getX() - obj2.getX()) + 
						(obj1.getY() - obj2.getY()) * (obj1.getY() - obj2.getY())));
				//find their radii
				float obj1_radius = obj1.getScale() * EnvironmentVariables.getMeter() / 2;
				float obj2_radius = obj2.getScale() * EnvironmentVariables.getMeter() / 2;
				//find the overlap
				float overlap = (float) (distance - obj1_radius - obj2_radius);
				//move obj1 x and y as obj2 position is locked
				obj1.setX(obj1.getX() - ((overlap * (obj1.getX() - obj2.getX())) / distance));
				obj1.setY(obj1.getY() - ((overlap * (obj1.getY() - obj2.getY())) / distance));
				
				//find angle of collision
				float tangent = (obj1.getX() - obj2.getX()) / (obj1.getY() - obj2.getY());
				double angle = Math.atan(tangent);
				angle = angle * (180 / 3.14159);
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
				
				//Get combined velocity
				float velocity = (Math.abs(obj1.getDx()) + Math.abs(obj1.getDy())) * rest;
				
				//set new velocity rekative to collision
				obj1.setDx((float)(velocity * Math.cos(angle)));
				obj1.setDy((float)(velocity * Math.sin(angle)));
	}
	
	//Collision between 2 squares
	public static void rectRect(GameObject obj1, GameObject obj2) {
		//Points of both centers
		Point obj1_center = new Point(obj1.getX(), obj1.getY());
		Point obj2_center = new Point(obj2.getX(), obj2.getY());
		
		
		float obj1_size = (obj1.getScale() * EnvironmentVariables.getMeter()) / 2;
		float obj2_size = (obj2.getScale() * EnvironmentVariables.getMeter()) / 2;
		
		//check for intersection Point on top side===============================================================
		Point intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
				new Point(obj2.getX() - obj2_size, obj2.getY() + obj2_size), 
				new Point(obj2.getX() + obj2_size, obj2.getY() + obj2_size))));
		//if not parallel
		if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
			if(intersection.getX() > obj2.getX() - obj2_size  && intersection.getX() < obj2.getX() + obj2_size ) {
				obj1.setY(obj2.getY() + obj2_size + obj1_size);
				yRestxFrict(obj1, obj2);
				return;
			}
		}
		
		//check for intersection Point bottom side================================================================
		intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
				new Point(obj2.getX() - obj2_size, obj2.getY() - obj2_size), 
				new Point(obj2.getX() + obj2_size, obj2.getY() - obj2_size))));
		if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
			if(intersection.getX() > obj2.getX() - obj2_size  && intersection.getX() < obj2.getX() + obj2_size ) {
				obj1.setY(obj2.getY() - obj2_size - obj1_size);
				yRestxFrict(obj1, obj2);
				return;
			}
		}
		
		//check for intersection Point left side====================================================================
		intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
				new Point(obj2.getX() - obj2_size, obj2.getY() + obj2_size), 
				new Point(obj2.getX() - obj2_size, obj2.getY() - obj2_size))));
			
		if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
			if(intersection.getY() > obj2.getY() - obj2_size  && intersection.getY() < obj2.getY() + obj2_size ) {
				obj1.setX(obj2.getX() - obj2_size - obj1_size);
				xRestyFrict(obj1, obj2);
				return;
			}
		}
		
		//check for intersection Point right side====================================================================
		intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
				new Point(obj2.getX() + obj2_size, obj2.getY() + obj2_size), 
				new Point(obj2.getX() + obj2_size, obj2.getY() - obj2_size))));
		
		if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
			if(intersection.getY() > obj2.getY() - obj2_size && intersection.getY() < obj2.getY() + obj2_size ) {
				obj1.setX(obj2.getX() + obj2_size + obj1_size);
				xRestyFrict(obj1, obj2);
				return;
			}	
		}
		
		//has hit a corner so reverse both
		obj1.setDy(obj1.getDy() * rest);
		obj1.setDy(obj1.getDy() * -1);
		obj1.setDx(obj1.getDx() * rest);
		obj1.setDy(obj1.getDx() * -1);
	}

	public static void yRestxFrict(GameObject obj1, GameObject obj2) {
		//apply restitution, change direction and apply friction
		//obj1.setDy((obj1.getDy() * rest) * -1);
		obj1.setDy((obj1.getDy()) * -1);
		obj1.setDx(obj1.getDx() * fric);
	}
	
	public static void xRestyFrict(GameObject obj1, GameObject obj2) {
		//apply restitution, change direction and apply friction
		obj1.setDx((obj1.getDx() * rest) * -1);
		obj1.setDy(obj1.getDy() * fric);
	}
	
}

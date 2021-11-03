package Math;

import GameTemplate.GameWindow;
import GameTemplate.GameWorld;
import Graphics.GameObject;
import WorldVariables.EnvironmentVariables;

public class ObjectCollisionReaction {
	static float rest;
	static float fric;

	public static void applyCollisionReaction(GameObject obj1, GameObject obj2) {
		//set the restitution value
		if(GameWorld.restitution == true) {
			rest = Restitution.averageRestitution(obj1, obj2);
		}else {
			rest = 1;
		}
		//get the friction value
		if(GameWorld.friction == true) {
			fric = Friction.getFrictionCD(obj1, obj2);
		}else {
			fric = 1;
		}
		
		//determine what calculation to use for reaction to collision
		if(obj1.getShape().equals("Circle")) {
			if(obj2.getShape().equals("Circle")) {
				circleCircle(obj1, obj2);
			}
			else if(obj2.getShape().equals("Square")) {
				circleRect(obj1, obj2);
			}
		}
		else if(obj2.getShape().equals("Circle")) {
			if(obj1.getShape().equals("Circle")) {
				circleCircle(obj1, obj2);
			}
			else if(obj1.getShape().equals("Square")) {
				rectCircle(obj1, obj2);
			}
		}
		else {
			rectRect(obj1, obj2);
		}
	}
	
	private static void circleCircle(GameObject obj1, GameObject obj2) {
		//one of the objects is locked then be can perform the same 
		//calculation as a collision with a terrain object
		if(obj2.getLock()) {
			TerrainCollisionReaction.circleCircle(obj1, obj2);
			return;
		}
		else if(obj1.getLock()) {
			TerrainCollisionReaction.circleCircle(obj2, obj1);
			return;
		}
		
		//find distances between objects
		float distance = (float) Math.sqrt((double)((obj1.getX() - obj2.getX()) * (obj1.getX() - obj2.getX()) + 
				(obj1.getY() - obj2.getY()) * (obj1.getY() - obj2.getY())));
		
		//get radius of both objects
		float obj1_radius = obj1.getScale() * EnvironmentVariables.getMeter() / 2;
		float obj2_radius = obj2.getScale() * EnvironmentVariables.getMeter() / 2;
		
		//find the overlap
		float overlap = (float) (distance - obj1_radius - obj2_radius);
		
		//move both objects relative to overlap
		obj1.setX(obj1.getX() - ((overlap * (obj1.getX() - obj2.getX())) / distance));
		obj1.setY(obj1.getY() - ((overlap * (obj1.getY() - obj2.getY())) / distance));
		obj2.setX(obj2.getX() + ((overlap * (obj1.getX() - obj2.getX())) / distance));
		obj2.setY(obj2.getY() + ((overlap * (obj1.getY() - obj2.getY())) / distance));
		
		//find angle of collision
		float tangent = (obj1.getX() - obj2.getX()) / (obj1.getY() - obj2.getY());
		double angle1 = Math.atan(tangent);
		double angle2 = Math.atan(tangent);
		angle1 = angle1 * (180 / 3.14159);
		angle2 = angle2 * (180 / 3.14159);
		if(obj1.getX() > obj2.getX()) {
			angle1 += 90;
			if(obj1.getY() < obj2.getY()) {
				angle1 += 90;
			}
		}
		else {
			if(obj1.getY() < obj2.getY()) {
				angle1 += 270;
			}
		}
		
		if(obj2.getX() > obj1.getX()) {
			angle2 += 90;
			if(obj2.getY() < obj1.getY()) {
				angle2 += 90;
			}
		}
		else {
			if(obj2.getY() < obj1.getY()) {
				angle2 += 270;
			}
		}
		//get each objects combined velocities and add restitution
		float obj1_velocity = (Math.abs(obj1.getDx()) + Math.abs(obj1.getDy())) * rest;
		float obj2_velocity = (Math.abs(obj2.getDx()) + Math.abs(obj2.getDy())) * rest;
		
		//set their new velocities relative to angle of collision
		obj1.setDx((float)(obj1_velocity * Math.cos(angle1)));
		obj1.setDy((float)(obj1_velocity * Math.sin(angle1)));
		
		obj2.setDx((float)(obj2_velocity * Math.cos(angle2)));
		obj2.setDy((float)(obj2_velocity * Math.sin(angle2)));
		
	}
	
	public static void circleRect(GameObject obj1, GameObject obj2) {
		//one of the objects is locked then be can perform the same 
		//calculation as a collision with a terrain object
		if(obj2.getLock()) {
			TerrainCollisionReaction.circleRect(obj1, obj2);
			return;
		}
		else if(obj1.getLock()) {
			TerrainCollisionReaction.rectCircle(obj2, obj1);
			return;
		}

		//find distances between objects
		float distance = (float) Math.sqrt((double)((obj1.getX() - obj2.getX()) * (obj1.getX() - obj2.getX()) + 
				(obj1.getY() - obj2.getY()) * (obj1.getY() - obj2.getY())));
				
		//get radius of both objects
		float obj1_radius = obj1.getScale() * EnvironmentVariables.getMeter() / 2;
		float obj2_radius = obj2.getScale() * EnvironmentVariables.getMeter() / 2;
				
		//find the overlap
		float overlap = (float) (distance - obj1_radius - obj2_radius);
				
		//move both objects relative to overlap
		obj1.setX(obj1.getX() - ((overlap * (obj1.getX() - obj2.getX())) / distance));
		obj1.setY(obj1.getY() - ((overlap * (obj1.getY() - obj2.getY())) / distance));
		obj2.setX(obj2.getX() + ((overlap * (obj1.getX() - obj2.getX())) / distance));
		obj2.setY(obj2.getY() + ((overlap * (obj1.getY() - obj2.getY())) / distance));
				
		//find angle of collision
		float tangent = (obj1.getX() - obj2.getX()) / (obj1.getY() - obj2.getY());
		double angle1 = Math.atan(tangent);
		double angle2 = Math.atan(tangent);
		angle1 = angle1 * (180 / 3.14159);
		angle2 = angle2 * (180 / 3.14159);
		if(obj1.getX() > obj2.getX()) {
			angle1 += 90;
			if(obj1.getY() < obj2.getY()) {
				angle1 += 90;
			}
		}
		else {
			if(obj1.getY() < obj2.getY()) {
				angle1 += 270;
			}
		}
				
		if(obj2.getX() > obj1.getX()) {
			angle2 += 90;
			if(obj2.getY() < obj1.getY()) {
				angle2 += 90;
			}
		}
		else {
			if(obj2.getY() < obj1.getY()) {
				angle2 += 270;
			}
		}
		//get each objects combined velocities and add restitution
		float obj1_velocity = (Math.abs(obj1.getDx()) + Math.abs(obj1.getDy())) * rest;
		float obj2_velocity = (Math.abs(obj2.getDx()) + Math.abs(obj2.getDy())) * rest;
				
		//set their new velocities relative to angle of collision
		obj1.setDx((float)(obj1_velocity * Math.cos(angle1)));
		obj1.setDy((float)(obj1_velocity * Math.sin(angle1)));
				
		obj2.setDx((float)(obj2_velocity * Math.cos(angle2)));
		obj2.setDy((float)(obj2_velocity * Math.sin(angle2)));
	}
	
	public static void rectCircle(GameObject obj1, GameObject obj2) {
		//one of the objects is locked then be can perform the same 
		//calculation as a collision with a terrain object
		if(obj2.getLock()) {
			TerrainCollisionReaction.rectCircle(obj1, obj2);
			return;
		}
		else if(obj1.getLock()) {
			TerrainCollisionReaction.circleRect(obj2, obj1);
			return;
		}
		
		//find distances between objects
				float distance = (float) Math.sqrt((double)((obj1.getX() - obj2.getX()) * (obj1.getX() - obj2.getX()) + 
						(obj1.getY() - obj2.getY()) * (obj1.getY() - obj2.getY())));
						
				//get radius of both objects
				float obj1_radius = obj1.getScale() * EnvironmentVariables.getMeter() / 2;
				float obj2_radius = obj2.getScale() * EnvironmentVariables.getMeter() / 2;
						
				//find the overlap
				float overlap = (float) (distance - obj1_radius - obj2_radius);
						
				//move both objects relative to overlap
				obj1.setX(obj1.getX() - ((overlap * (obj1.getX() - obj2.getX())) / distance));
				obj1.setY(obj1.getY() - ((overlap * (obj1.getY() - obj2.getY())) / distance));
				obj2.setX(obj2.getX() + ((overlap * (obj1.getX() - obj2.getX())) / distance));
				obj2.setY(obj2.getY() + ((overlap * (obj1.getY() - obj2.getY())) / distance));
						
				//find angle of collision
				float tangent = (obj1.getX() - obj2.getX()) / (obj1.getY() - obj2.getY());
				double angle1 = Math.atan(tangent);
				double angle2 = Math.atan(tangent);
				angle1 = angle1 * (180 / 3.14159);
				angle2 = angle2 * (180 / 3.14159);
				if(obj1.getX() > obj2.getX()) {
					angle1 += 90;
					if(obj1.getY() < obj2.getY()) {
						angle1 += 90;
					}
				}
				else {
					if(obj1.getY() < obj2.getY()) {
						angle1 += 270;
					}
				}
						
				if(obj2.getX() > obj1.getX()) {
					angle2 += 90;
					if(obj2.getY() < obj1.getY()) {
						angle2 += 90;
					}
				}
				else {
					if(obj2.getY() < obj1.getY()) {
						angle2 += 270;
					}
				}
				//get each objects combined velocities and add restitution
				float obj1_velocity = (Math.abs(obj1.getDx()) + Math.abs(obj1.getDy())) * rest;
				float obj2_velocity = (Math.abs(obj2.getDx()) + Math.abs(obj2.getDy())) * rest;
						
				//set their new velocities relative to angle of collision
				obj1.setDx((float)(obj1_velocity * Math.cos(angle1)));
				obj1.setDy((float)(obj1_velocity * Math.sin(angle1)));
						
				obj2.setDx((float)(obj2_velocity * Math.cos(angle2)));
				obj2.setDy((float)(obj2_velocity * Math.sin(angle2)));
	}
	
	public static void rectRect(GameObject obj1, GameObject obj2) {
		if(obj1.getLock()) {
			TerrainCollisionReaction.rectRect(obj2, obj1);
			return;
		}
		else if(obj2.getLock()) {
			TerrainCollisionReaction.rectRect(obj1, obj2);
			return;
		}
		
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
			if(intersection.getX() > obj2.getX() - obj2_size + 5 && intersection.getX() < obj2.getX() + obj2_size - 5) {
				obj1.setY(obj2.getY() + obj2_size + obj1_size);
				topBottom(obj1, obj2);
				return;
			}
		}
				
		//check for intersection Point bottom side================================================================
		intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
				new Point(obj2.getX() - obj2_size, obj2.getY() - obj2_size), 
				new Point(obj2.getX() + obj2_size, obj2.getY() - obj2_size))));
		
		if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
			if(intersection.getX() > obj2.getX() - obj2_size + 5 && intersection.getX() < obj2.getX() + obj2_size - 5) {
				obj1.setY(obj2.getY() - obj2_size - obj1_size);
				topBottom(obj1, obj2);
				return;
			}
		}
				
		//check for intersection Point left side====================================================================
		intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
				new Point(obj2.getX() - obj2_size, obj2.getY() + obj2_size), 
				new Point(obj2.getX() - obj2_size, obj2.getY() - obj2_size))));
					
		if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
			if(intersection.getY() > obj2.getY() - obj2_size + 5 && intersection.getY() < obj2.getY() + obj2_size - 5) {
				obj1.setX(obj2.getX() - obj2_size - obj1_size);
				leftRight(obj1, obj2);
				return;
			}
		}
				
		//check for intersection Point right side====================================================================
		intersection = new Point((Point.pointOfIntersection(obj1_center, obj2_center,
				new Point(obj2.getX() + obj2_size, obj2.getY() + obj2_size), 
				new Point(obj2.getX() + obj2_size, obj2.getY() - obj2_size))));
				
		if(intersection.getX() != GameWindow.getCanvas().getWidth() + 500 && intersection.getY() !=GameWindow.getCanvas().getWidth() + 500) {
			if(intersection.getY() > obj2.getY() - obj2_size + 5 && intersection.getY() < obj2.getY() + obj2_size - 5) {
				obj1.setX(obj2.getX() + obj2_size + obj1_size);
				leftRight(obj1, obj2);
				return;
			}	
		}
			
		//has hit a corner so reverse both
		obj1.setDy(obj1.getDy() * rest);
		obj1.setDy(obj1.getDy() * -1);
		obj1.setDx(obj1.getDx() * rest);
		obj1.setDy(obj1.getDx() * -1);
	}
	
	public static void topBottom(GameObject obj1, GameObject obj2) {
		//apply conservation of energy equation to each object in the y direction
		float obj1_new_dy = ((obj1.getMass() * obj1.getDy()) + (obj2.getMass() * obj2.getDy()) +
				((obj2.getMass() * rest) * (obj2.getDy() - obj1.getDy()))) /
				(obj1.getMass() + obj2.getMass());
		float obj2_new_dy = ((obj1.getMass() * obj1.getDy()) + (obj1.getMass() * obj2.getDy()) +
				((obj2.getMass() * rest) * (obj1.getDy() - obj2.getDy()))) /
				(obj1.getMass() + obj2.getMass());
		
		obj1.setDy(obj1_new_dy);
		obj2.setDy(obj2_new_dy);
		
		//apply friction to x velocity
		obj1.setDx(obj1.getDx() * fric);
		obj2.setDx(obj2.getDx() * fric);
		
	}
	
	public static void leftRight(GameObject obj1, GameObject obj2) {
		//apply conservation of energy equation to each object in the x direction
		float obj1_new_dx = ((obj1.getMass() * obj1.getDx()) + (obj2.getMass() * obj2.getDx()) +
				((obj2.getMass() * rest) * (obj2.getDx() - obj1.getDx()))) /
				(obj1.getMass() + obj2.getMass());
		float obj2_new_dx = ((obj1.getMass() * obj1.getDx()) + (obj1.getMass() * obj2.getDx()) +
				((obj2.getMass() * rest) * (obj1.getDx() - obj2.getDx()))) /
				(obj1.getMass() + obj2.getMass());
		
		obj1.setDx(obj1_new_dx);
		obj2.setDx(obj2_new_dx);
		
		//apply friction to y velocity
		obj1.setDy(obj1.getDy() * fric);
		obj2.setDy(obj2.getDy() * fric);
		return;
	}
}

package Math;

import Graphics.GameObject;
import WorldVariables.EnvironmentVariables;

public class Collision {

	public static boolean collide(GameObject obj1, GameObject obj2) {
		boolean is_collision = false;
		
		//call the function for testing if there is a collision depending on
		//the shapes of the objects
		if(obj1.getShape().equals("Circle") && obj2.getShape().equals("Circle")) {
			is_collision = circleCircle(obj1, obj2);
		}
		if(obj1.getShape().equals("Circle") && obj2.getShape().equals("Square")) {
			is_collision = circleSquare(obj1, obj2);
		}
		else if(obj1.getShape().equals("Square") && obj2.getShape().equals("Circle")) {
			is_collision = circleSquare(obj2, obj1);
		}
		else if(obj1.getShape().equals("Square") && obj2.getShape().equals("Square")) {
			is_collision = squareSquare(obj1, obj2);
		}
		
		return is_collision;
	}
	
	//check for collision between circle and circle
	public static boolean circleCircle(GameObject obj1, GameObject obj2) {
	
		//get both objects radii
		float obj1_radius = obj1.getScale() * EnvironmentVariables.getMeter() / 2;
		float obj2_radius = obj2.getScale() * EnvironmentVariables.getMeter() / 2;
		
		//check if distance between centers is less that combined radii
		if(Math.abs((obj1.getX() - obj2.getX()) * (obj1.getX() - obj2.getX()) + (obj1.getY() - obj2.getY()) * (obj1.getY() - obj2.getY())) <=
				(obj1_radius + obj2_radius) * (obj1_radius + obj2_radius)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Check for collision between circle and square
	public static boolean circleSquare(GameObject obj1, GameObject obj2) {
		//distance from circle center to the square center
		float circleDist_x = Math.abs(obj1.getX() - obj2.getX());
		float circleDist_y = Math.abs(obj1.getY() - obj2.getY());
		
		//get radius of circle and size of square from center
		float circle_radius = obj1.getScale() * EnvironmentVariables.getMeter() /2 ;
		float square_size = obj2.getScale() * EnvironmentVariables.getMeter() / 2;
		
		//if distance is greater than combined sizes then no collision
		if(circleDist_x > (circle_radius + square_size)) {return false;}
		if(circleDist_y > (circle_radius + square_size)) {return false;}
		
		//if overlap, collison
		if(circleDist_x <= (square_size)) {return true;}
		if(circleDist_y <= (square_size)) {return true;}
		
		//check for overlap at corners
		float corner_distance = (float) (Math.pow((circleDist_x - square_size), 2) +
				Math.pow(circleDist_y - square_size, 2));
		
		return (corner_distance <= Math.pow(circle_radius, 2));
	}
	
	//Check for collision between 2 squares
	public static boolean squareSquare(GameObject obj1, GameObject obj2) {
		float obj1_size = (obj1.getScale() * EnvironmentVariables.getMeter()) / 2;
		float obj2_size = (obj2.getScale() * EnvironmentVariables.getMeter()) / 2;
		
		if(obj1.getX() + obj1_size >= obj2.getX() - obj2_size && 	//obj1 right edge past obj2 left edge
			obj1.getX() - obj1_size <= obj2.getX() + obj2_size &&	//obj1 left edge past obj2 right edge
			obj1.getY() + obj1_size >= obj2.getY() - obj2_size &&	//obj1 top edge past obj2 bottom edge
			obj1.getY() - obj1_size <= obj2.getY() + obj2_size		//obj1 top edge past obj2 bottom edge
				) {
			return true;
		}
		else {
			return false;
		}
	}
}

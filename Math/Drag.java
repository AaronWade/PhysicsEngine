package Math;

import java.util.ArrayList;
import java.util.List;

import GameTemplate.GameLoop;
import Graphics.GameObject;
import WorldVariables.EnvironmentVariables;

public class Drag {
	//drag coefficients for different shapes
	static DragCoefficient dragCd[] = new DragCoefficient[]{
			 new DragCoefficient("Circle", 0.47f), 
			 new DragCoefficient("Half Circle", 0.42f), 
			 new DragCoefficient("Triangle", 0.50f), 
			 new DragCoefficient("Square", 1.05f), 
			 new DragCoefficient("Streamlined", 0.04f), 
			 new DragCoefficient("Half Streamlined", 0.09f)};
	
	/**
	 * apply drag to the object obj
	 * @param obj
	 */
	public static void applyGravityDrag(GameObject obj) {
		//Drag Coefficient
		float Cd = 0;
		
		//loop through coefficient list until we find the shape that is equal
		//to the objects chape and set the coefficient 
		for(int i = 0; i < dragCd.length; i ++) {
			if(obj.getShape().equals(dragCd[i].getShape())) {
				Cd = dragCd[i].getCoefficient();
				break;
			}
		}
		
		//get the shadow area of object (meters)
		float area = 0;
		if(obj.getShape().equals("Circle")) {
			area = (float) (3.149526 * Math.pow(obj.getScale(), 2) / EnvironmentVariables.getMeter());
		}
		else if(obj.getShape().equals("Square")) {
			area = (float) Math.pow(obj.getScale(), 2) / EnvironmentVariables.getMeter();
		}
		
		if(obj.getDy() < 0) {
			//Newtons (kg m/s)
			float gravity_force = obj.getMass() * Gravity.getGravity(); 
			float drag_force = (float) (Cd * EnvironmentVariables.getAirDensity() 
					* ((Math.pow(Math.abs(obj.getDy()) / EnvironmentVariables.getMeter(), 2) * area) / 2));
		
			//Seperate the velocity from the mass
			float result = (gravity_force - drag_force) / obj.getMass();
			
			// find the difference from the acceleration of gravity to the applied drag
			float difference = Gravity.getGravity() - result;
			difference = (difference * EnvironmentVariables.getMeter()) / GameLoop.getFPS();
			obj.setDy(obj.getDy() + difference);
		}
		
	}
	/**
	 * finding the terminal velocity of the object obj		 
	 * @param obj
	 * @return
	 */
	public static double terminalVelocity(GameObject obj) {
		float Cd = 0;
		double terminal_vel = 0;
		
		for(int i = 0; i < dragCd.length; i ++) {
			if(obj.getShape().equals(dragCd[i].getShape())) {
				Cd = dragCd[i].getCoefficient();
			}
		}
		
		//get the shadow area of object (meters)
		float area = 0;
		if(obj.getShape().equals("Circle")) {
			area = (float) (3.149526 * Math.pow(obj.getScale(), 2) / EnvironmentVariables.getMeter());
		}
		else if(obj.getShape().equals("Square")) {
			area = (float) Math.pow(obj.getScale(), 2) / EnvironmentVariables.getMeter();
		}
		
		//if there is an air density
		if(EnvironmentVariables.getAirDensity() > 0) {
			//top line of terminal velocity equation
			float t1 = (2 * obj.getMass() * Gravity.getGravity());
			//bottom line of terminal velocity equation
			float t2 = (EnvironmentVariables.getAirDensity() * area * Cd);
			//*-1 so it is towards the ground
			if(t2 > 0) {
				terminal_vel = (Math.sqrt(t1 / t2));
				
				terminal_vel = (terminal_vel * EnvironmentVariables.getMeter()) * -1;
			}
			//return the terminal velocity value of obj
			return terminal_vel;
		}
		else {
			return 0;
		}
	}

}

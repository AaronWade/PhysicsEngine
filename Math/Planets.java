package Math;

import WorldVariables.EnvironmentVariables;

public class Planets {
	
	/**
	 * depending on the planet selected by the user, set the mass and radius 
	 * @param planet
	 */
	public static void setPlanetVariables(String planet){
		if (planet.equals("Mecury")) {
			EnvironmentVariables.setMass((float) 0.330);
			EnvironmentVariables.setRadius((float) 2438);
		}
		else if (planet.equals("Venus")) {
			EnvironmentVariables.setMass((float) 4.87);
			EnvironmentVariables.setRadius((float) 6052);
		}
		else if (planet.equals("Earth")) {
			EnvironmentVariables.setMass((float) 5.97);
			EnvironmentVariables.setRadius((float) 6378);
		}
		else if (planet.equals("Moon")) {
			EnvironmentVariables.setMass((float) 0.073);
			EnvironmentVariables.setRadius((float) 1737.5);
		}
		else if (planet.equals("Mars")) {
			EnvironmentVariables.setMass((float) 0.642);
			EnvironmentVariables.setRadius((float) 3396);
		}
		else if (planet.equals("Jupiter")) {
			EnvironmentVariables.setMass((float) 1898);
			EnvironmentVariables.setRadius((float) 71492);
		}
		else if (planet.equals("Saturen")) {
			EnvironmentVariables.setMass((float) 568);
			EnvironmentVariables.setRadius((float) 60268);
		}
		else if (planet.equals("Uranus")) {
			EnvironmentVariables.setMass((float) 86.8);
			EnvironmentVariables.setRadius((float) 25559);
		}
		else if (planet.equals("Neptune")) {
			EnvironmentVariables.setMass((float) 102);
			EnvironmentVariables.setRadius((float) 24764);
		}
		else if (planet.equals("Pluto")) {
			EnvironmentVariables.setMass((float) 0.0146);
			EnvironmentVariables.setRadius((float) 1185);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

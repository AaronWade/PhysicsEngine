package WorldVariables;

import java.util.ArrayList;


import java.util.List;

import Graphics.GameObject;
import Graphics.Window;
/**
 * this class holds the variables that will be shared throughout the project
 * having a centralized location means i didn't have to keep passing variables
 * from one location to another
 *
 */
public class EnvironmentVariables {
	//the initial values for height, width and title
	public static float width = 600;
	public static float height = 500;
	public static String title = "Game";
	
	public static float getWidth() {
		return width;
	}

	public static void setWidth(float width) {
		EnvironmentVariables.width = width;
	}

	public static float getHeight() {
		return height;
	}

	public static void setHeight(float height) {
		EnvironmentVariables.height = height;
	}

	public static String getTitle() {
		return title;
	}

	public static void setTitle(String title) {
		EnvironmentVariables.title = title;
	}

	//========================================================================
	//initial values of the environments variables
	public static String planet = "Earth";
	private static String reset_planet = "Earth";
	
	//kg
	public static float mass = (float) 5.9736;
	private static float reset_mass = mass;
	
	//km
	public static float radius = (float) 6371;
	private static float reset_radius = radius;
	
	//kg/m^3
	public static float air_density = (float) 1.225;
	private static float reset_air_density = air_density;
	
	//m
	public static float meter = (float) 20;
	private static float reset_meter = meter;
	
	// will reset variables to their starting state
	public static void resetEnvironment() {
		planet = reset_planet;
		mass = reset_mass;
		radius = reset_radius;
		air_density = reset_air_density;
		meter = reset_meter;
	}
	//the default values for a complete reset
	public static void defaultEnvironment() {
		planet = "Earth";
		reset_planet = planet;
		mass = (float) 5.9736;
		reset_mass = mass;
		radius = (float) 6371;
		reset_radius = radius;
		air_density = (float) air_density;
		reset_air_density = air_density;
		meter = 20;
		reset_meter = meter;
	}
	
	//Getters and Setters
	public static String getPlanet() {
		return planet;
	}
	public static void setPlanet(String planet) {
		EnvironmentVariables.planet = planet;
	}
	public static float getMass() {
		return mass;
	}
	public static void setMass(float mass) {
		EnvironmentVariables.mass = mass;
	}
	public static float getRadius() {
		return radius;
	}
	public static void setRadius(float radius) {
		EnvironmentVariables.radius = radius;
	}
	public static float getAirDensity() {
		return air_density;
	}
	public static void setAirDensity(float air_density) {
		EnvironmentVariables.air_density = air_density;
	}
	public static float getMeter() {
		return meter;
	}
	public static void setMeter(float meter) {
		EnvironmentVariables.meter = meter;
	}
	
	//==================================================================
	//the object lists and their reset lists
	public static GameObject main_player = null;
	public static GameObject reset_main_player = null;
	public static List<GameObject> world_objects = new ArrayList<GameObject>();
	public static List<GameObject> reset_world_objects = new ArrayList<GameObject>();
	public static List<GameObject> terrain = new ArrayList<GameObject>();
	public static List<GameObject> reset_terrain = new ArrayList<GameObject>();
	
	//loop through terrain and render each object
	public static void renderTerrain() {
		for (GameObject obj: terrain) {
			obj.render();
		}
	}
	
	//loop through world object and render
	public static void renderWorldObjects() {
		for (GameObject obj: world_objects) {
			obj.render();
		}
	}
	
	//render main
	public static void renderMainPlayer() {
		main_player.render();
	}
	
	public static GameObject getMainPlayer() {
		return main_player;
	}
	public static void setMainPlayer(GameObject main) {
		main_player = main;
	}
	public static List<GameObject> getWorldObjects(){
		return world_objects;
	}
	public static void setWorldObjects(List<GameObject> w) {
		world_objects.clear();
		for(GameObject obj: w) {
			world_objects.add(new GameObject(obj));
		}
	}
	public static void addToWorldObjects(GameObject game_object) {
		world_objects.add(game_object);
	}
	public static List<GameObject> getTerrain(){
		return terrain;
	}
	public static void setTerrain(List<GameObject> t) {
		terrain.clear();
		for(GameObject obj: t) {
			terrain.add(new GameObject(obj));
		}
	}
	public static void addToTerrain(GameObject terrain_object) {
		terrain.add(terrain_object);
	}
	
	//Reset functions==================================================================================================
	public static void clearWorld() {
		main_player = null;
		terrain.clear();
		world_objects.clear();
		setResetTerrain(terrain);
		setResetMainPlayer(main_player);
		setResetWorldObjects(world_objects);
	}
	
	public static void setResetTerrain(List<GameObject> ls) {
		reset_terrain.clear();
		for(GameObject obj: ls) {
			reset_terrain.add(new GameObject(obj));
		}
	}
	public static List<GameObject> getResetTerrain() {
		return reset_terrain;
	}
	
	public static void setResetMainPlayer(GameObject obj) {
		reset_main_player = obj;
	}
	public static GameObject getResetMainPlayer() {
		return reset_main_player;
	}
	
	public static void setResetWorldObjects(List<GameObject> ls) {
		reset_world_objects.clear();
		for(GameObject obj: ls) {
			reset_world_objects.add(new GameObject(obj));
		}
	}
	public static List<GameObject> getResetWorldObjects() {
		return reset_world_objects;
	}
	
	public static void resetGameWorld() {
		if (getResetMainPlayer() != null) {
			setMainPlayer(new GameObject(getResetMainPlayer()));
		}
		if(getResetWorldObjects().size() != 0) {
			setWorldObjects(new ArrayList<GameObject>(EnvironmentVariables.getResetWorldObjects()));
		}
		if(getResetTerrain().size() != 0) {
			setTerrain(new ArrayList<GameObject>(EnvironmentVariables.getResetTerrain()));
		}
		
	}
}

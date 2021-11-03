package GameFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Graphics.GameObject;
import WorldVariables.EnvironmentVariables;

public class SetUpWorld {
	//get the file separator used by the system
	static String separator = System.getProperty("file.separator");
	//create the path to our files
	static String path = System.getProperty("user.dir") + separator + "src" + separator + "GameFiles" + separator;
	
	public static void setUpTerrain(String name) {
		
		//path to our terrain file
		File file= new File(path + name + "Terrain.txt");
		
		try {
			FileInputStream file_in = new FileInputStream(file);
			ObjectInputStream object_in = new ObjectInputStream(file_in);

			//create a new object list to store all of our terrain objects being read in
			List<GameObject> in_terrain = new ArrayList<GameObject>();
			
			//create a new temp Game object for reading in each object from the file
			GameObject obj = null;

		    boolean isExist = true;

		    while(isExist){
		        if(file_in.available() != 0){
		         obj = (GameObject) object_in.readObject();    
		         in_terrain.add(obj);
		        }
		        else{
		        	isExist = false;
		        }
		    }
		    
		    if(in_terrain.size() != 0) {
		    	//make our terrain list equal the file objects we read and create a reset list
		    	EnvironmentVariables.setTerrain(new ArrayList<GameObject>(in_terrain));
			    
			    EnvironmentVariables.setResetTerrain(new ArrayList<GameObject>(in_terrain));
		    }
		    
			object_in.close();
			file_in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//Same steps as terrain set up
	public static void setUpWorldObjects(String name) {
		
		File file= new File(path + name + "WorldObjects.txt");
		
		try {
			FileInputStream file_in = new FileInputStream(file);
			ObjectInputStream object_in = new ObjectInputStream(file_in);

			List<GameObject> in_world_objects = new ArrayList<GameObject>();
			
			GameObject obj = null;

		    boolean isExist = true;

		    while(isExist){
		        if(file_in.available() != 0){
		         obj = (GameObject) object_in.readObject();    
		         in_world_objects.add(obj);
		        }
		        else{
		        	isExist = false;
		        }
		    }
		    
		    if(in_world_objects.size() != 0) {
		    	EnvironmentVariables.setWorldObjects(new ArrayList<GameObject>(in_world_objects));

				EnvironmentVariables.setResetWorldObjects(new ArrayList<GameObject>(in_world_objects));
		    }
		    

			object_in.close();
			file_in.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//Same steps as terrain set up
	public static void setUpMainPlayer(String name) {
		
		File file= new File(path + name + "MainPlayer.txt");
		
		try {
			FileInputStream file_in = new FileInputStream(file);
			ObjectInputStream object_in = new ObjectInputStream(file_in);
			
			GameObject obj = null;

		    boolean isExist = true;

		    while(isExist){
		        if(file_in.available() != 0){
		         obj = (GameObject) object_in.readObject();    
		        }
		        else{
		        	isExist = false;
		        }
		    }
		    if(obj != null) {
		    	EnvironmentVariables.setMainPlayer(new GameObject(obj));
			    
			    EnvironmentVariables.setResetMainPlayer(new GameObject(obj));
		    }
		    
		    
			object_in.close();
			file_in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public static void setUpEnvironment(String name) {
		try {
			
			File env_file = new File(path + name + "EnvVariables.txt");
			
			BufferedReader br = new BufferedReader(new FileReader(env_file));
	        
			//read in each line as a string and convert them where needs be
			EnvironmentVariables.setTitle(br.readLine());
	        EnvironmentVariables.setWidth(Float.parseFloat(br.readLine()));
	        EnvironmentVariables.setHeight(Float.parseFloat(br.readLine()));
	        EnvironmentVariables.setPlanet(br.readLine());
            EnvironmentVariables.setMass(Float.parseFloat(br.readLine()));
            EnvironmentVariables.setRadius(Float.parseFloat(br.readLine()));
            EnvironmentVariables.setAirDensity(Float.parseFloat(br.readLine()));
            EnvironmentVariables.setMeter(Float.parseFloat(br.readLine()));
	        
		}catch (FileNotFoundException e) {
			e.printStackTrace();
	    } catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

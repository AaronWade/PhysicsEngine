package UserInterface;

import java.awt.Color;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Graphics.GameObject;
import Graphics.HomePage;
import Graphics.Window;
import WorldVariables.EnvironmentVariables;

public class SaveObjects {
	JPanel save_objects;
	Font font_header;
	Font font;
	
	//components that are used to create GUI
	JLabel header;
	
	JLabel title_lb;
	JTextField title;
	
	JLabel width_lb;
	JTextField width;
	
	JLabel height_lb;
	JTextField height;
	
	JButton save;
	JButton reset;
	
	public SaveObjects() {
		//panel layout
		GridLayout gl = new GridLayout(5, 2);
		gl.setHgap(5);
		gl.setVgap(5);
		
		save_objects = new JPanel();
		font_header = new Font("Verdana", Font.BOLD, 12);
		font= new Font("Verdana", Font.PLAIN, 10);
		
		init();
		addToJPanel();
		addListeners();
		
		//Make background transparent and add the layout
		//Border border = new LineBorder(Color.DARK_GRAY, 1);
		Border border = new LineBorder(Color.DARK_GRAY, 1);
		Border margin = new EmptyBorder(5,5,5,5);
		save_objects.setBorder(BorderFactory.createCompoundBorder(border, margin));
		save_objects.setBackground(new Color(0,0,0,0));
		save_objects.setLayout(gl);
	}
	
	public void init() {
		header = new JLabel("Save World");
		
		//Title of world
		title_lb = new JLabel("Title");
		title = new JTextField("Sim");
		
		//window width
		width_lb = new JLabel("Width");
		width = new JTextField("600");
		
		//window height
		height_lb = new JLabel("Height");
		height = new JTextField("400");
		
		save = new JButton("Save");
		reset = new JButton("Reset");
	}
	
	/**
	 * add all components to the panel
	 */
	public void addToJPanel() {
		save_objects.add(header);
		JLabel blank1 = new JLabel("");
		save_objects.add(blank1);
		
		save_objects.add(title_lb);
		save_objects.add(title);
		
		save_objects.add(width_lb);
		save_objects.add(width);
		
		save_objects.add(height_lb);
		save_objects.add(height);
		
		save_objects.add(save);
		save_objects.add(reset);
		
		setAllFont();
	}
	
	public void setAllFont() {
		for (Component c : save_objects.getComponents()) {
		    c.setFont(font);
		    
		}
		
		header.setFont(font_header);
	}
	
	public void addListeners() {
		
		//save the current created world
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Set the window variables
				EnvironmentVariables.setWidth(Float.parseFloat(width.getText()));
				EnvironmentVariables.setHeight(Float.parseFloat(height.getText()));
				EnvironmentVariables.setTitle(title.getText());
				updateGameWorld();
				//go to save function
				saveGame();
				
				Window.getFrame().setVisible(false);
				Window.getFrame().dispose();
				
				HomePage.buildHomePage();
			}
			
		});
		
		//reset to empty world
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EnvironmentVariables.clearWorld();
				Window.render();
			}
		});
	}
	
	/**
	 * save the created world to files so that the user can run a simulation 
	 * or game set in it
	 */
	public void saveGame() {
		//separator used by system
		String separator = System.getProperty("file.separator");
		//path to where files will be saved
		String path = System.getProperty("user.dir") + separator + "src" + separator + "GameFiles" + separator;

		try {

			//Save the world objects
			String world_objects_name = EnvironmentVariables.getTitle() + "WorldObjects";
			//create a new file with name given by user with added text for identification
			File world_objects_file = new File(path + world_objects_name + ".txt");
			FileOutputStream f_world_objects = new FileOutputStream(world_objects_file);
			ObjectOutputStream o_world_objects = new ObjectOutputStream(f_world_objects);

			//loop through list and write each object to file
			for(GameObject obj: EnvironmentVariables.getWorldObjects()) {
				o_world_objects.writeObject(obj);
			}

			o_world_objects.flush();
			o_world_objects.close();
			f_world_objects.flush();
			f_world_objects.close();
			
			
			//Save terrain is done the same ass world objects but we create a new file 
			//with different added text
			String terrain_name = EnvironmentVariables.getTitle() + "Terrain";
			File terrain_file = new File(path + terrain_name + ".txt");
			FileOutputStream f_terrain = new FileOutputStream(terrain_file);
			ObjectOutputStream o_terrain = new ObjectOutputStream(f_terrain);

			for(GameObject obj: EnvironmentVariables.getTerrain()) {
				o_terrain.writeObject(obj);
			}

			o_terrain.flush();
			o_terrain.close();
			f_terrain.flush();
			f_terrain.close();
			
			//Save main player, given own file but just a single object
			String main_player_name = EnvironmentVariables.getTitle() + "MainPlayer";
			File main_player_file = new File(path + main_player_name + ".txt");
			FileOutputStream f_main_player = new FileOutputStream(main_player_file);
			ObjectOutputStream o_main_player = new ObjectOutputStream(f_main_player);

			o_main_player.writeObject(EnvironmentVariables.getMainPlayer());

			o_main_player.flush();
			o_main_player.close();
			f_main_player.flush();
			f_main_player.close();
			

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		catch (IOException e) {
			System.out.println("Error initializing stream");
		} 
		
		//saving the environment variables
		try {
			String env_name = EnvironmentVariables.getTitle() + "EnvVariables";
			File env_file = new File(path + env_name + ".txt");
			
			if(!env_file.exists()) {
				env_file.createNewFile();
		    }
			 
			//FileWriter fw = new FileWriter(env_file);
			BufferedWriter bw = new BufferedWriter(new FileWriter(env_file));
			//write each variable to a new line as a string
			bw.write(EnvironmentVariables.getTitle()); bw.newLine();
			bw.write(Float.toString(EnvironmentVariables.getWidth())); bw.newLine();
			bw.write(Float.toString(EnvironmentVariables.getHeight())); bw.newLine();
			bw.write(EnvironmentVariables.getPlanet()); bw.newLine();
			bw.write(Float.toString(EnvironmentVariables.getMass())); bw.newLine();
			bw.write(Float.toString(EnvironmentVariables.getRadius())); bw.newLine();
			bw.write(Float.toString(EnvironmentVariables.getAirDensity())); bw.newLine();
			bw.write(Float.toString(EnvironmentVariables.getMeter())); bw.newLine();

			
			//bw.flush();
			bw.close();
			
		}catch (IOException e) {
			System.out.println("Error initializing stream");
		}
		
		EnvironmentVariables.getTerrain().clear();
		EnvironmentVariables.getWorldObjects().clear();
	}
	
	/**
	 * Ensure ratio of object size and distances in window stay the same. Depending on height change
	 */
	public void updateGameWorld() {
		//divide the user entered height by the height of the canvas to get a ratio
		float canvas_height = Window.getCanvas().getHeight();
		float new_height = EnvironmentVariables.getHeight();
		float ratio = new_height / canvas_height;
		
		//use this ration th=o set the new meter value
		EnvironmentVariables.setMeter(EnvironmentVariables.getMeter() * ratio);
		
		//use the ratio to set all objects new location
		for(GameObject obj: EnvironmentVariables.getWorldObjects()) {
			obj.setX(obj.getX() * ratio);
			obj.setY(obj.getY() * ratio);
		}
		
		for(GameObject obj: EnvironmentVariables.getTerrain()) {
			obj.setX(obj.getX() * ratio);
			obj.setY(obj.getY() * ratio);
		}
		
		if(EnvironmentVariables.getMainPlayer() != null) {
			EnvironmentVariables.getMainPlayer().setX(EnvironmentVariables.getMainPlayer().getX() * ratio);
			EnvironmentVariables.getMainPlayer().setY(EnvironmentVariables.getMainPlayer().getY() * ratio);
		}
		
	}
	
	public JPanel getJPanel() {
		return save_objects;
	}
}
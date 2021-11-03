package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import Input.KeyInput;
import UserInterface.EditEnvironmentVariables;
import UserInterface.EditGameObject;
import UserInterface.EditTerrain;
import UserInterface.SaveObjects;

public class Window {
	private static JFrame frame = null;
	private static GLCanvas canvas = null;
	private static GLProfile profile = null;
	
	public static int width;
	public static int height;
	
	public static JPanel edit_game_objects = null;
	public static JPanel save_world = null;
	public static JPanel edit_env_variables = null;
	public static JPanel edit_terrain = null;
	
	public Window() {
		width = 700;
		height = 400;
		
		frame = new JFrame();
		
		GLProfile.initSingleton();
		
		profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(profile);
		caps.setDoubleBuffered(true);
		canvas = new GLCanvas(caps);
		
		//the JPanels used to create the GUI
		edit_game_objects = new JPanel();
		save_world = new JPanel();
		edit_env_variables = new JPanel();
		edit_terrain = new JPanel();
	}
	
	public void setUpWindow() {
		canvas.setSize(width, height);
		canvas.setVisible(true);
		
		//set the title of the window
		frame.setTitle("Game Engine");
		int frame_width = width + 430;
		int frame_height = height + 200;
		frame.setSize(frame_width,  frame_height);
		
		//dont allow resizing
		frame.setResizable(false);
		
		//if user closes window, destrooy it and go to home page
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				frame.setVisible(false);
				frame.dispose();
				
				HomePage.buildHomePage();
			}
		});
	}
	
	public void setUpUI() {
		//Set the JPanles to be equal to the JPanles created in their separate classes
		
		EditGameObject ego = new EditGameObject();
		edit_game_objects = ego.getJPanel();
		
		SaveObjects so = new SaveObjects();
		save_world = so.getJPanel();
		
		EditEnvironmentVariables eev = new EditEnvironmentVariables();
		edit_env_variables = eev.getJPanel();
		
		EditTerrain et = new EditTerrain();
		edit_terrain = et.getJPanel();
	}
	
	public void buildWindow() {
		JPanel main_layout = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		main_layout.setLayout(gb);
		GridBagConstraints c = new GridBagConstraints();
		//split our main layout into 700 rows and 400 columns
		main_layout.setPreferredSize(new Dimension(canvas.getWidth() / 700, canvas.getHeight()/400));
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.0;
		
		//add edit game object to top left of layout
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 100;
		c.gridheight = 300;
		c.anchor = GridBagConstraints.NORTH;
		main_layout.add(edit_game_objects, c);
		
		//add save world to bottom left of layout
		c.gridy = 300;
		c.gridheight = 100;
		main_layout.add(save_world, c);
		
		//add canvas to center of layout
		c.gridx = 100;
		c.gridy = 0;
		c.gridwidth = 800;
		c.gridheight = 400;
		main_layout.add(canvas, c);
		
		//add edit environment to top right of layout
		c.gridx = 900;
		c.gridy = 0;
		c.gridheight = 250;
		c.gridwidth = 100;
		c.fill = GridBagConstraints.HORIZONTAL;
		main_layout.add(edit_env_variables, c);
		
		//add edit terrain to bottom right of layout
		c.gridy = 250;
		c.gridheight = 300;
		main_layout.add(edit_terrain, c);
		
		//add main layout to frame
		frame.add(main_layout);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas.addGLEventListener(new EventListener());
	}
	
	public static void render() {
		if( canvas == null) {
			return;
		}
		canvas.display();
	}
	

	//Getters and Setters
	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		Window.frame = frame;
	}

	public static GLCanvas getCanvas() {
		return canvas;
	}

	public static void setCanvas(GLCanvas canvas) {
		Window.canvas = canvas;
	}

	public static GLProfile getProfile() {
		return profile;
	}

	public static void setProfile(GLProfile profile) {
		Window.profile = profile;
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		Window.width = width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		Window.height = height;
	}
	
	
}

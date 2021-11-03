package GameTemplate;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import Graphics.HomePage;
import Input.KeyInput;
import UserInterface.EditEnvironmentVariables;
import WorldVariables.EnvironmentVariables;

public class GameWindow {
	private static JFrame frame = null;
	private static GLCanvas canvas = null;
	private static GLProfile profile = null;
	
	public static int width;
	public static int height;
	
	public static JPanel edit_env_variables = null;
	public static JPanel active_equations = null;
	
	//GameWindow constructor
	public GameWindow() {
		width = (int) EnvironmentVariables.getWidth();
		height = (int) EnvironmentVariables.getHeight();
		
		frame = new JFrame();
		
		GLProfile.initSingleton();
		
		profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(profile);
		caps.setDoubleBuffered(true);
		canvas = new GLCanvas(caps);
		
		edit_env_variables = new JPanel();
		active_equations = new JPanel();
	}
	
	public static void setUpGameWindow() {
		//set our canvas to be the width and height entered by user, make it visible
		canvas.setSize(width, height);
		canvas.setVisible(true);
		
		//set title to be the name given by user
		frame.setTitle(EnvironmentVariables.getTitle());
		//make frame bigger than canvas so that we can add GUI
		int frame_width = width + 430;
		int frame_height = height + 55;
		frame.setSize(frame_width,  frame_height);
		
		//dont allow resizing
		frame.setResizable(false);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				//if user closes window, destroy GameWindow
				frame.setVisible(false);
				frame.dispose();
				
				//stop game loop
				GameLoop.running = false;
				
				//rebuild home page
				HomePage.buildHomePage();
			}
		});
	}
	
	public static void setUpUI() {
		//get our edit environment JPanel
		EditEnvironmentVariables eev = new EditEnvironmentVariables();
		//if user is able to change meter size at this stage it could break 
		//the program, so remove it
		eev.removeMeterLb();
		eev.removeMeter();
		edit_env_variables = eev.getJPanel();
		
		//get our active equation JPanel
		ActiveEquations ae = new ActiveEquations();
		active_equations = ae.getJPanel();
	}
	
	public static void buildGameWindow() {
		//create our main JPanel to hold all other JPanels
		JPanel main_layout = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		main_layout.setLayout(gb);
		GridBagConstraints c = new GridBagConstraints();
		//main_layout.setPreferredSize(new Dimension(1, canvas.getHeight()/400));
		
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.0;
		
		//add active equations to the far left of window
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		main_layout.add(active_equations, c);
		
		//add canvas to center 
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		main_layout.add(canvas, c);
		
		//add edit equation to far right
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridheight = 1;
		main_layout.add(edit_env_variables, c);
		
		//add our main JPanel to the frame/window
		frame.add(main_layout);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//add listener to canvas to allow us to render
		canvas.addGLEventListener(new GameEventListener());

		canvas.addKeyListener(new KeyInput());
		
	}
	
	//call display function of our canvas/ event listener
	public static void render() {
		if( canvas == null) {
			return;
		}
		canvas.display();
	}
	
	public static GLCanvas getCanvas() {
		return canvas;
	}
}

package Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import GameFiles.SetUpWorld;
import GameTemplate.GameLoop;
import GameTemplate.GameWindow;
import WorldVariables.EnvironmentVariables;

public class HomePage {
	public static JFrame frame;
	
	public static JButton start_new;
	public static JComboBox files_cb;
	
	public static boolean is_game;

	public static void buildHomePage() {
		//initialise a home page that a user can interact with
		is_game = false;
		frame = new JFrame();
		frame.setSize(500, 400);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
		
        int width = frame.getWidth() - 100;
        int height = 30;
        int position_x = (frame.getWidth() / 2) - (width / 2);
        
        //add a button with to start new project
		start_new = new JButton("Start new Project");
		start_new.setBounds(position_x, 150, width, height);
		frame.add(start_new);
		
		//initialise the meter variable
		EnvironmentVariables.setMeter(20);
		EnvironmentVariables.setMainPlayer(null);
		//reset the entire world
		EnvironmentVariables.clearWorld();
		
		//get the file separator used by system and the path to files
		String separator = System.getProperty("file.separator");
		String path = System.getProperty("user.dir") + separator + "src" + separator + "GameFiles" + separator;
		
		//add all files in the directory/path to an array of files
		File folder = new File(path);
		File[] list_of_files = folder.listFiles();
		String[] files = new String[(list_of_files.length / 4) + 1];
		files[0] = "Unselected";
		int i = 1;
		//loop through array and look for all files that only end in EnvVariables.txt
		//so we dont get duplicates
		for (File file : list_of_files) {
		    if (file.isFile() && file.getName().endsWith("EnvVariables.txt")) {
		        int strip = file.getName().length() - 16;
		        files[i] = file.getName().substring(0, strip);
		        i++;
		    }
		}
		
		//add these files to a combo list 
		files_cb = new JComboBox(files);
		files_cb.setBounds(position_x, 200, width, height);
		frame.add(files_cb);
		
		addListeners();
		
		frame.setVisible(true);
	}
	
	public static void addListeners() {
		start_new.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				is_game = false;
				//Build window to allow user to create a new environment
				EnvironmentVariables.clearWorld();
				//EnvironmentVariables.defaultEnvironment();
				Window window = new Window();
				window.setUpWindow();
				window.setUpUI();
				window.buildWindow();
				
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		files_cb.addItemListener(new ItemListener() {
			@Override
			//if user selects a created world then pass this file name to the SetUpWorld Class
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					if(!files_cb.getSelectedItem().toString().equals("Unselected")) {
						is_game = true;
						String file = (String)files_cb.getSelectedItem();
						
						SetUpWorld.setUpTerrain(file);
						SetUpWorld.setUpWorldObjects(file);
						SetUpWorld.setUpMainPlayer(file);
						SetUpWorld.setUpEnvironment(file);
						
						//build a new game window
						GameWindow g = new GameWindow();
						g.setUpGameWindow();
						g.setUpUI();
						g.buildGameWindow();
						GameLoop.start();
						
						frame.setVisible(false);
						frame.dispose();
						
					}
				}
			}
	   });
	}
	
	public static boolean getGame() {
		return is_game;
	}
}

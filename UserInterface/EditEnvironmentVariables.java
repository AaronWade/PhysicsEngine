package UserInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import GameTemplate.GameLoop;
import Graphics.GameObject;
import Graphics.HomePage;
import Graphics.Window;
import Math.Gravity;
import Math.Planets;
import WorldVariables.EnvironmentVariables;

public class EditEnvironmentVariables {
	JPanel edit_env_variables;
	
	//components to be added to JPanel
	Font font_header;
	Font font;
	
	JLabel header;
	
	JLabel planet_lb;
	String[] planets;
	JComboBox planet_cb;
	
	JLabel mass_lb;
	JTextField mass;
	
	JLabel radius_lb;
	JTextField radius;
	
	JLabel air_density_lb;
	JTextField air_density;
	
	JLabel meter_lb;
	JTextField meter;
	
	JButton apply_env_variables;
	JButton reset_env_variables;
	
	JButton move_left;
	JButton move_right;
	
	public EditEnvironmentVariables() {
		//give JPanel a layout
		GridLayout gl = new GridLayout(8, 2);
		gl.setHgap(5);
		gl.setVgap(5);
		
		edit_env_variables = new JPanel();
		//set font
		font_header = new Font("Verdana", Font.BOLD, 12);
		font = new Font("Verdana", Font.PLAIN, 10);
		
		init();
		addToJPanel();
		addListeners();
		
		//give the JPanel a border
		Border border = new LineBorder(Color.DARK_GRAY, 1);
		Border margin = new EmptyBorder(5, 5, 5, 5);
		edit_env_variables.setBorder(BorderFactory.createCompoundBorder(border, margin));
		edit_env_variables.setLayout(gl);
		
	}
	
	public void init() {
		//Heading of JPanel
		header = new JLabel("Environment");
		
		//Select planet
		planet_lb = new JLabel("Planet");
		planets = new String[] {"Earth", "Mecury", "Venus", "Moon", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto", "Custom"};
		planet_cb = new JComboBox(planets);
		
		//Change mass of environment
		mass_lb = new JLabel("Mass(10^24)");
		mass = new JTextField(Float.toString(EnvironmentVariables.getMass()));
		mass.setEditable(false);
		
		//Change radius of environment
		radius_lb = new JLabel("Radius");
		radius = new JTextField(Float.toString(EnvironmentVariables.getRadius()));
		radius.setEditable(false);
		
		//Change air density of environment
		air_density_lb = new JLabel("Air Density");
		air_density = new JTextField(Float.toString(EnvironmentVariables.getAirDensity()));
		
		//Change meter of environment
		meter_lb = new JLabel("Meter");
		meter = new JTextField(Float.toString(EnvironmentVariables.getMeter()));
		
		//Apply are reset changes
		apply_env_variables = new JButton("Apply");
		reset_env_variables = new JButton("Reset");
		
		//Shift the environment left or right
		move_left = new JButton("<");
		move_right = new JButton(">");
	}
	
	/**
	 * Add all component to the JPanel
	 */
	public void addToJPanel() {
		edit_env_variables.add(header);
		JLabel blank1 = new JLabel("");
		edit_env_variables.add(blank1);
		
		edit_env_variables.add(planet_lb);
		edit_env_variables.add(planet_cb);
		
		edit_env_variables.add(mass_lb);
		edit_env_variables.add(mass);
		
		edit_env_variables.add(radius_lb);
		edit_env_variables.add(radius);
		
		edit_env_variables.add(air_density_lb);
		edit_env_variables.add(air_density);
		
		edit_env_variables.add(meter_lb);
		edit_env_variables.add(meter);
		
		edit_env_variables.add(apply_env_variables);
		edit_env_variables.add(reset_env_variables);
		
		edit_env_variables.add(move_left);
		edit_env_variables.add(move_right);
		
		setAllFont();
	}
	
	public void setAllFont() {
		//all components except header have same font
		for (Component c : edit_env_variables.getComponents()) {
		    c.setFont(font);
		}
		
		header.setFont(font_header);
	}
	
	/**
	 * listeners that will allow usert to make changes to the environment
	 */
	public void addListeners() {
		//listener to change planet
		planet_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					//if custom selected in planet list then allow user to add their own mass and radius
					if(planet_cb.getSelectedItem().toString().equals("Custom")) {
						mass.setEditable(true);
						radius.setEditable(true);
					}
					else {
						//else, set the mass and radius relative to the selected planet
						//make it so user can't edit
						Planets.setPlanetVariables(planet_cb.getSelectedItem().toString());
						mass.setText(Float.toString(EnvironmentVariables.getMass()));
						radius.setText(Float.toString(EnvironmentVariables.getRadius()));
						mass.setEditable(false);
						radius.setEditable(false);
					}
				}
				EnvironmentVariables.setPlanet(planet_cb.getSelectedItem().toString());
			}
		});
		
		//apply changes to the environment
		apply_env_variables.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateVariables();
			}
		});
		
		//reset environment variables
		reset_env_variables.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EnvironmentVariables.resetEnvironment();
				boolean x = false;
				planet_cb.setSelectedIndex(0);
				mass.setText(Float.toString(EnvironmentVariables.getMass()));
				radius.setText(Float.toString(EnvironmentVariables.getRadius()));
				air_density.setText(Float.toString(EnvironmentVariables.getAirDensity()));
			}
		});
		
		
		/**
		 * these listeners allow the user to move the world left or right
		 * so that they arent constrained by the window size
		 */
		//Shift world left
		move_left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(GameObject obj: EnvironmentVariables.getTerrain()) {
					obj.setX(obj.getX() - 10);
				}
				for(GameObject obj: EnvironmentVariables.getWorldObjects()) {
					obj.setX(obj.getX() - 10);
				}
				if(EnvironmentVariables.getMainPlayer() != null) {
					EnvironmentVariables.getMainPlayer().setX(EnvironmentVariables.getMainPlayer().getX() - 10);
				}
				
				Window.render();
			}
		});
		
		//Shift world right
		move_right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(GameObject obj: EnvironmentVariables.getTerrain()) {
					obj.setX(obj.getX() + 10);
				}
				for(GameObject obj: EnvironmentVariables.getWorldObjects()) {
					obj.setX(obj.getX() + 10);
				}
				if(EnvironmentVariables.getMainPlayer() != null) {
					EnvironmentVariables.getMainPlayer().setX(EnvironmentVariables.getMainPlayer().getX() + 10);
				}
				Window.render();
			}
		});
	}
	
	/**
	 * Update the environment variables to match what user has entered
	 */
	public void updateVariables() {
		EnvironmentVariables.setMass(Float.parseFloat((mass.getText())));
		EnvironmentVariables.setRadius(Float.parseFloat((radius.getText())));
		EnvironmentVariables.setAirDensity(Float.parseFloat((air_density.getText())));
		EnvironmentVariables.setMeter(Float.parseFloat((meter.getText())));
	}
	
	public void removeMeterLb() {
		edit_env_variables.remove(meter_lb);
	}
	
	public void removeMeter() {
		edit_env_variables.remove(meter);
	}
	
	public JPanel getJPanel() {
		return edit_env_variables;
	}
}

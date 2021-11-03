package GameTemplate;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Graphics.Window;
import UserInterface.EditGameObject;

public class ActiveEquations {
	// the labels and check boxes that will allow us to turn on
	//and off different physics properties
	
	JPanel active_equations;
	
	Font font_header;
	Font font;
	
	JLabel header;

	JLabel world_gravity;
	JCheckBox world_gravity_cb;
	
	JLabel gravity_attraction;
	JCheckBox gravity_attraction_cb;
	
	JLabel drag;
	JCheckBox drag_cb;
	
	JLabel friction;
	JCheckBox friction_cb;
	
	JLabel restitution;
	JCheckBox restitution_cb;
	
	JLabel all;
	JCheckBox all_cb;
	
	public ActiveEquations() {
		//Build the layout that will be used
		//Grid 7 rows, 2 columns
		GridLayout gl = new GridLayout(7, 2);
		gl.setHgap(5);
		gl.setVgap(5);
		
		//create a JPanel that will be displayed to the user
		active_equations = new JPanel();
		//set our fonts
		font_header = new Font("Verdana", Font.BOLD, 12);
		font = new Font("Verdana", Font.PLAIN, 10);
		
		init();
		addToJPanel();
		addListeners();
		
		//add a border to our JPanel
		Border border = new LineBorder(Color.DARK_GRAY, 1);
		Border margin = new EmptyBorder(5, 5, 5, 5);
		active_equations.setBorder(BorderFactory.createCompoundBorder(border, margin));
		active_equations.setLayout(gl);
	}
	
	public void init() {
		//Heading of JPanel
		header = new JLabel("Physics");
		
		world_gravity = new JLabel("World Gravity");
		//Check box to set world gravity
		world_gravity_cb = new JCheckBox("");
		world_gravity_cb.setSelected(false);
		world_gravity_cb.setActionCommand("set");
		
		gravity_attraction = new JLabel("Gravity Attraction");
		//Check box to set gravity attraction
		gravity_attraction_cb = new JCheckBox("");
		gravity_attraction_cb.setSelected(false);
		gravity_attraction_cb.setActionCommand("set");
		
		drag = new JLabel("Drag");
		//Check box to set drag
		drag_cb = new JCheckBox("");
		drag_cb.setSelected(false);
		drag_cb.setActionCommand("set");
		
		friction = new JLabel("Friction");
		//Check box to set drag
		friction_cb = new JCheckBox("");
		friction_cb.setSelected(false);
		friction_cb.setActionCommand("set");
		
		restitution = new JLabel("Restitution");
		//Check box to set drag
		restitution_cb = new JCheckBox("");
		restitution_cb.setSelected(false);
		restitution_cb.setActionCommand("set");
		
		all = new JLabel("All");
		//Check box to set drag
		all_cb = new JCheckBox("");
		all_cb.setSelected(true);
		all_cb.setActionCommand("set");
	}
	
	//Add all the components to the JPanel
	public void addToJPanel() {
		active_equations.add(header);
		JLabel blank1 = new JLabel("");
		active_equations.add(blank1);
		
		active_equations.add(world_gravity);
		active_equations.add(world_gravity_cb);
		
		active_equations.add(gravity_attraction);
		active_equations.add(gravity_attraction_cb);
		
		active_equations.add(drag);
		active_equations.add(drag_cb);
		
		active_equations.add(friction);
		active_equations.add(friction_cb);
		
		active_equations.add(restitution);
		active_equations.add(restitution_cb);
		
		active_equations.add(all);
		active_equations.add(all_cb);
		
		setAllFont();
	}
	
	public void setAllFont() {
		//Set the font for the componets, give a different font to the header
		for (Component c : active_equations.getComponents()) {
		    c.setFont(font);
		}
		
		header.setFont(font_header);
	}
	
	//Add listeners to our checkboxes that will allow for the activation and
	//deactivation of physics properties
	public void addListeners() {
		world_gravity_cb.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(world_gravity_cb.isSelected()) {
					GameWorld.setWorldGravity(true);
				}
				else {
					GameWorld.setWorldGravity(false);
				}
			} 
		} );
		
		gravity_attraction_cb.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(gravity_attraction_cb.isSelected()) {
					GameWorld.setGravityAttraction(true);
				}
				else {
					GameWorld.setGravityAttraction(false);
				}
			} 
		} );
		
		drag_cb.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(drag_cb.isSelected()) {
					GameWorld.setDrag(true);
				}
				else {
					GameWorld.setDrag(false);
				}
			} 
		} );
		
		friction_cb.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(friction_cb.isSelected()) {
					GameWorld.setFriction(true);
				}
				else {
					GameWorld.setFriction(false);
				}
			} 
		} );
		
		restitution_cb.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(restitution_cb.isSelected()) {
					GameWorld.setRestitution(true);
				}
				else {
					GameWorld.setRestitution(false);
				}
			} 
		} );
		
		//if they check all then activate all physics
		all_cb.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(all_cb.isSelected()) {
					GameWorld.setWorldGravity(true);
					GameWorld.setGravityAttraction(true);
					GameWorld.setDrag(true);
					GameWorld.setFriction(true);
					GameWorld.setRestitution(true);
					world_gravity_cb.setSelected(true);
					gravity_attraction_cb.setSelected(true);
					drag_cb.setSelected(true);
					friction_cb.setSelected(true);
					restitution_cb.setSelected(true);
				}
				else {
					GameWorld.setWorldGravity(false);
					GameWorld.setGravityAttraction(false);
					GameWorld.setDrag(false);
					GameWorld.setFriction(false);
					GameWorld.setRestitution(false);
					world_gravity_cb.setSelected(false);
					gravity_attraction_cb.setSelected(false);
					drag_cb.setSelected(false);
					friction_cb.setSelected(false);
					restitution_cb.setSelected(false);
				}
			} 
		} );
	}
	
	//return our created JPanel
	public JPanel getJPanel() {
		return active_equations;
	}
}



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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Graphics.GameObject;
import Graphics.Window;
import Math.Gravity;
import WorldVariables.EnvironmentVariables;

public class EditTerrain {
	JPanel edit_terrain;
	
	//all components that will be used in this section of the GUI
	Font font_header;
	Font font;
	
	JLabel header;
	
	public static JCheckBox add_terrain;
	
	JLabel shape_lb;
	String[] shapes;
	JComboBox shape_cb;
	
	JLabel material_lb;
	String[] materials;
	JComboBox material_cb;
	
	JLabel colour_lb;
	String[] colours;
	JComboBox colour_cb;
	
	JLabel scale_lb;
	JTextField scale;
	
	JLabel position_x_lb;
	JSpinner position_x;
	JLabel position_y_lb;
	JSpinner position_y;
	
	JButton apply_terrain;
	JButton reset_terrain;
	
	public static GameObject temp_terrain_object;
	
	public EditTerrain() {
		//add layout to the panel
		GridLayout gl = new GridLayout(9, 2);
		gl.setHgap(5);
		gl.setVgap(5);
		
		edit_terrain = new JPanel();
		//set the fonts
		font_header = new Font("Verdana", Font.BOLD, 12);
		font = new Font("Verdana", Font.PLAIN, 10);
		
		init();
		addToJPanel();
		addListeners();
		
		//add a border
		Border border = new LineBorder(Color.DARK_GRAY, 1);
		Border margin = new EmptyBorder(5, 5, 5, 5);
		edit_terrain.setBorder(BorderFactory.createCompoundBorder(border, margin));
		edit_terrain.setLayout(gl);
		
		//initialise the temp object
		temp_terrain_object = new GameObject();
		temp_terrain_object.setLock(true);
		temp_terrain_object.setMass(EnvironmentVariables.getMass());
		temp_terrain_object.setMaterial("Stone");
		
	}
	
	public void init() {
		//Heading of JPanel
		header = new JLabel("Terrain");
		
		//CheckBox to indicate user is interacting with this JPanel
		add_terrain = new JCheckBox("Add Terrain");
		add_terrain.setSelected(true);
		add_terrain.setActionCommand("add");
		
		//Change size of object
		scale_lb = new JLabel("Scale");
		scale = new JTextField("1");
		
		//Select shape
		shape_lb = new JLabel("Shape");
		shapes = new String[] {"Square", "Circle"};
		shape_cb = new JComboBox(shapes);
		
		//Select material
		material_lb = new JLabel("Material");
		materials = new String[]{"Stone", "Grass", "Sand", "Ice"};
		material_cb = new JComboBox(materials);
		
		//Select colour
		colour_lb = new JLabel("Colour");
		colours = new String[] {"Red", "Black", "White", "Blue", "Green", "Yellow", "Grey"};
		colour_cb = new JComboBox(colours);
		
		//Change position of object
		position_x_lb = new JLabel("Position x");
		position_x = new JSpinner(new SpinnerNumberModel(Window.getCanvas().getWidth() / 2, -50, Window.getCanvas().getWidth() + 50, 5));
		position_y_lb = new JLabel("Position y");
		position_y = new JSpinner(new SpinnerNumberModel(Window.getCanvas().getHeight() / 2, -50, Window.getCanvas().getHeight() + 50, 5));
		
		//apply or reset changes
		apply_terrain = new JButton("Apply");
		reset_terrain = new JButton("Reset");
	}
	
	/**
	 * add all components to the panel
	 */
	public void addToJPanel() {
		edit_terrain.add(header);
		JLabel blank1 = new JLabel("");
		edit_terrain.add(blank1);
		
		edit_terrain.add(add_terrain);
		JLabel blank2 = new JLabel("");
		edit_terrain.add(blank2);
		
		edit_terrain.add(scale_lb);
		edit_terrain.add(scale);
		
		edit_terrain.add(shape_lb);
		edit_terrain.add(shape_cb);
		
		edit_terrain.add(material_lb);
		edit_terrain.add(material_cb);
		
		edit_terrain.add(colour_lb);
		edit_terrain.add(colour_cb);
		
		edit_terrain.add(position_x_lb);
		edit_terrain.add(position_x);
		
		edit_terrain.add(position_y_lb);
		edit_terrain.add(position_y);
		
		edit_terrain.add(apply_terrain);
		edit_terrain.add(reset_terrain);
		
		setAllFont();
	}
	
	public void setAllFont() {
		for (Component c : edit_terrain.getComponents()) {
		    c.setFont(font);
		}
		
		header.setFont(font_header);
	}
	
	public void addListeners() {
		//Listener to see is add_terrain is selected, if so, unselect add_game_object
		add_terrain.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(add_terrain.isSelected()) {
					//unselect add_game_object
					EditGameObject.add_game_object.setSelected(false);
					//temp_terrain_object.render();
					Window.render();
				}
			} 
		} );
		
		//listener to change temp_terrain shape
		shape_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					temp_terrain_object.setShape(shape_cb.getSelectedItem().toString());
					Window.render();
					
					Gravity.applyEnvironmentGravity(temp_terrain_object);
				}
			}
	    });
		
		//listener to change temp_terrain material
		material_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					temp_terrain_object.setMaterial(material_cb.getSelectedItem().toString());
					Window.render();
				}
			}
	    });
		
		//listener to change temp_terrain colour
		colour_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					temp_terrain_object.setColour((String)colour_cb.getSelectedItem());
					Window.render();
				}
			}
	    });
		
		//listener to change scale of temp_terrain
		scale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				temp_terrain_object.setScale(Float.parseFloat((scale.getText())));
				Window.render();
			}
		});
		
		//listener to change x position of temp_terrain
		position_x.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Integer x = (Integer) position_x.getValue();
				temp_terrain_object.setX((float)x);
				Window.render();
			}
		});
		
		//listener to change y position of temp_terrain
		position_y.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Integer y = (Integer) position_y.getValue();			
				temp_terrain_object.setY((float)y);
				Window.render();
			}		
		});
		
		//listener to add temp_terrain to terrain list
		apply_terrain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//add object to terrain list
				EnvironmentVariables.addToTerrain(new GameObject (temp_terrain_object));
				//reset to default 
				temp_terrain_object.reset();
				temp_terrain_object.setLock(true);
				resetUI();
				temp_terrain_object.setMaterial(material_cb.getSelectedItem().toString());
				Window.render();
			}
			
		});
		
		//listener to reset temp_terrain 
		reset_terrain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				temp_terrain_object.reset();
				resetUI();
				temp_terrain_object.setMaterial(material_cb.getSelectedItem().toString());
				Window.render();
			}
		});
	}
	
	public void resetUI() {
		shape_cb.setSelectedIndex(0);
		material_cb.setSelectedIndex(0);
		colour_cb.setSelectedIndex(0);
		position_x.getModel().setValue(Window.getCanvas().getWidth() / 2);
		position_y.getModel().setValue(Window.getCanvas().getHeight() / 2);
		scale.setText("1");
	}

	public JPanel getJPanel() {
		return edit_terrain;
	}
	
}

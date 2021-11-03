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
import WorldVariables.EnvironmentVariables;

public class EditGameObject {
	JPanel edit_game_object;
	Font font_header;
	Font font;
	
	//components to be added to panel
	JLabel header;
	
	public static JCheckBox add_game_object;
	
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
	
	JLabel mass_lb;
	JTextField mass;
	
	JLabel position_x_lb;
	JSpinner position_x;
	JLabel position_y_lb;
	JSpinner position_y;
	
	JCheckBox lock;
	JCheckBox main_player;
	
	public static JButton apply_game_object;
	public static JButton reset_game_object;
	
	public static GameObject temp_game_object;
	public static int temp_id;
	
	public EditGameObject() {
		GridLayout gl = new GridLayout(12, 2);
		gl.setHgap(5);
		gl.setVgap(5);
		
		edit_game_object = new JPanel();
		font_header = new Font("Verdana", Font.BOLD, 12);
		font= new Font("Verdana", Font.PLAIN, 10);
		
		init();
		addToJPanel();
		addListeners();
		
		//Make background transparent and add the layout
		Border border = new LineBorder(Color.DARK_GRAY, 1);
		Border margin = new EmptyBorder(5,5,5,5);
		edit_game_object.setBorder(BorderFactory.createCompoundBorder(border, margin));
		edit_game_object.setBackground(new Color(0,0,0,0));
		edit_game_object.setLayout(gl);
		
		temp_game_object = new GameObject();
		temp_id = 1;
	}
	
	public void init() {
		header = new JLabel("Object");
		
		add_game_object = new JCheckBox("Add Object");
		add_game_object.setSelected(false);
		add_game_object.setActionCommand("add");
		
		//Set shape
		shapes = new String[]{"Square", "Circle"};
		shape_lb = new JLabel("Shape");
		shape_cb = new JComboBox(shapes);
		
		//Select material
		material_lb = new JLabel("Material");
		materials = new String[]{"Rubber", "Stone" , "Wood", "Steel"};
		material_cb = new JComboBox(materials);
		
		//Set material
		colours = new String[]{"Red", "Black", "White", "Blue", "Green", "Yellow", "Grey"};
		colour_lb = new JLabel("Colour");
		colour_cb = new JComboBox(colours);
		
		//changeX and Y position of game object
		position_x_lb = new JLabel("Position X");
		position_x = new JSpinner(new SpinnerNumberModel(Window.getCanvas().getWidth() / 2, -200, Window.getCanvas().getWidth() + 200, 5));
		position_y_lb = new JLabel("Position Y");
		position_y = new JSpinner(new SpinnerNumberModel(Window.getCanvas().getHeight() / 2, -200, Window.getCanvas().getHeight() + 200, 5));
		
		//multiplied by meter 
		scale_lb = new JLabel("Scale");
		scale = new JTextField("1");
		
		//mass of the object
		mass_lb = new JLabel("Mass");
		mass = new JTextField("1");
		
		//lock position eg: not effected by gravity
		lock = new JCheckBox("Lock Object");
		lock.setSelected(false);
		lock.setActionCommand("lock");
		
		//set object as main player
		main_player = new JCheckBox("Main Player");
		main_player.setSelected(false);
		main_player.setActionCommand("main");
		
		//apply or reset changes
		apply_game_object = new JButton("Apply");
		reset_game_object = new JButton("Reset");
	
	}
	
	/**
	 * components to be added to the JPanel
	 */
	public void addToJPanel() {
		edit_game_object.add(header);
		JLabel blank1 = new JLabel("");
		edit_game_object.add(blank1);
		
		edit_game_object.add(add_game_object);
		JLabel blank2 = new JLabel("");
		edit_game_object.add(blank2);
		
		edit_game_object.add(scale_lb);
		edit_game_object.add(scale);
		
		edit_game_object.add(shape_lb);
		edit_game_object.add(shape_cb);
		
		edit_game_object.add(material_lb);
		edit_game_object.add(material_cb);
		
		edit_game_object.add(colour_lb);
		edit_game_object.add(colour_cb);
		
		edit_game_object.add(mass_lb);
		edit_game_object.add(mass);
		
		edit_game_object.add(position_x_lb);
		edit_game_object.add(position_x);
		edit_game_object.add(position_y_lb);
		edit_game_object.add(position_y);
		
		edit_game_object.add(lock);
		edit_game_object.add(main_player);
		
		edit_game_object.add(apply_game_object);
		edit_game_object.add(reset_game_object);
		
		setAllFont();
	}
	
	public void setAllFont() {
		for (Component c : edit_game_object.getComponents()) {
		    c.setFont(font);
		}
		
		header.setFont(font_header);
	}
	
	/**
	 * listeners that will allow user to add new object and update the current one
	 */
	public void addListeners() {
		//Listener to see is add_game_object is selected, if so, unselect add_terrain
		add_game_object.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(add_game_object.isSelected()) {
					//unselect add_terrain
					EditTerrain.add_terrain.setSelected(false);
					Window.render();
				}
			} 
		} );
		
		//listener to change temp_game_object shape
		shape_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					temp_game_object.setShape(shape_cb.getSelectedItem().toString());
					Window.render();
				}
			}
		});
				
		//listener to change temp_game_object material
		material_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					temp_game_object.setMaterial(material_cb.getSelectedItem().toString());
					Window.render();
				}
			}
		});
				
		//listener to change temp_terrain colour
		colour_cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					temp_game_object.setColour(colour_cb.getSelectedItem().toString());
					Window.render();
				}
			}
		});
		
		//listener to change scale of temp_game_object
		scale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				temp_game_object.setScale(Float.parseFloat((scale.getText())));
				Window.render();
			}
		});
		
		//listener to change scale of temp_game_object
		mass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				temp_game_object.setMass(Float.parseFloat((mass.getText())));
				Window.render();
			}
		});
				
		//listener to change x position of temp_game_object
		position_x.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Integer x = (Integer) position_x.getValue();
				temp_game_object.setX((float)x);
				Window.render();
			}
		});
				
		//listener to change y position of temp_game_object
		position_y.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Integer y = (Integer) position_y.getValue();			
				temp_game_object.setY((float)y);
				Window.render();
			}		
		});
		
		//Listener to see is lock is selected, if so, unselect main_player
		lock.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(lock.isSelected()) {
					main_player.setSelected(false);
					temp_game_object.setLock(true);
				}
				else if(!lock.isSelected()) {
					temp_game_object.setLock(false);
				}
				Window.render();
			} 
		} );
		
		//Listener to see is lock is selected, if so, unselect main_player
		main_player.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(main_player.isSelected()) {
					lock.setSelected(false);
					temp_game_object.setMainPlayer(true);
				}
				Window.render();
			} 
		} );
				
		//listener to add temp_game_object to game_object list
		apply_game_object.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//if main_player is selected make this object the main player
				temp_game_object.setID(temp_id);
				if(main_player.isSelected()) {
					EnvironmentVariables.setMainPlayer(new GameObject (temp_game_object));
				}
				//else add this object to the world
				else {
					EnvironmentVariables.addToWorldObjects(new GameObject (temp_game_object));
				}
				//increase id by 1 so all ids are different
				temp_id++;
				//reseting doesnt reset id
				temp_game_object.reset();
				resetUI();
				temp_game_object.setMaterial(material_cb.getSelectedItem().toString());
				Window.render();
			}		
		});
				
		//listener to reset	temp_game_object 
		reset_game_object.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				temp_game_object.reset();
				resetUI();
				temp_game_object.setMaterial(material_cb.getSelectedItem().toString());
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
		mass.setText("1");
		lock.setSelected(false);
		main_player.setSelected(false);
	}
	
	public JPanel getJPanel() {
		return edit_game_object;
	}
}

package Graphics;

import java.io.Serializable;

import com.jogamp.opengl.GL2;

import GameTemplate.GameEventListener;
import WorldVariables.EnvironmentVariables;

public class GameObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	//position
	public float x;
	public float y;
	
	//lock position
	public Boolean lock;
	
	//is main_player
	public Boolean main_player;
	
	//velocity
	public float dx;
	public float dy;
	
	//scale (size is scale * meter)
	public float scale;
	
	//mass
	public float mass;
	
	//shape
	public String shape;
	
	//material
	public String material;
	
	//colour
	public String colour;
	public float red = 1.0f;
	public float green = 0.0f;
	public float blue = 0.0f;
	public float alpha = 1.0f;

	public GameObject(){
		id = 0;
		x = Window.getCanvas().getWidth() / 2;
		y = Window.getCanvas().getHeight()/ 2;
		System.out.println(y);
		dx = 0;
		dy = 0;
		lock = false;
		main_player = false;
		scale = 1;
		mass = 1;
		shape = "Square";
		material = "Rubber";
		colour = "Red";
	}
	
	//Constructor that takes in another GameObject
	public GameObject(GameObject go) {
		id = go.getID();
		x = go.getX();
		y = go.getY();
		dx = go.getDx();
		dy = go.getDy();
		lock = go.getLock();
		main_player = go.getMainPlayer();
		scale = go.getScale();
		mass = go.getMass();
		shape = go.getShape();
		material = go.getMaterial();
		colour = go.getColour();
	}
	
	//draw the object
	public void render() {
		setColourValues();
		
		//if square
		if(shape.equals("Square")) {
			renderSquare();
		}
		//else if circle;
		else if(shape.equals("Circle")) {
			renderCircle();
		}
		//else if triangle;
		else if(shape.equals("Triangle")) {
			renderTriangle();
		}
		
	}
	
	public void renderSquare() {
		GL2 gl = null;
		if(HomePage.getGame()) {
			gl = GameEventListener.gl;
		}else {
			gl = EventListener.gl;
		}
		
		float size = scale * (EnvironmentVariables.getMeter()) / 2;
		//from center point, draw four lines from corner to corner
		gl.glBegin(GL2.GL_QUADS);
			gl.glColor4f(red, green, blue, alpha);
			gl.glVertex2f(x - size, y + size);
			gl.glVertex2f(x + size, y + size);
			gl.glVertex2f(x + size, y - size);
			gl.glVertex2f(x - size, y - size);
		gl.glEnd();
		
	}
	
	public void renderCircle() {
		GL2 gl = null;
		if(HomePage.getGame()) {
			gl = GameEventListener.gl;
		}else {
			gl = EventListener.gl;
		}
		
		gl.glBegin( GL2.GL_TRIANGLE_FAN);
		//set the colourValues
		gl.glColor4f(red, green, blue, alpha);
		//Center
		gl.glVertex2f(x, y);
		
		//number of triangle fans used to make circle
		int segmentsInCircle = 100;
		float twicePI = 2.0f * 3.14159f;
		float angle;
		//out radius inm meters
		float radius = scale * EnvironmentVariables.getMeter() / 2;
		
		//loop the number of segments
		for(int i = 0; i <= segmentsInCircle; i++) {
			gl.glColor4f(red, green, blue, alpha);
			if(i == 50) {
				gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
			}
			//find angle relative to current segment being drawn and divide by 
			//number of segments
			angle = (float) (i * twicePI) / segmentsInCircle;
			//draw triangle from center point out the radius relative to angle
			gl.glVertex2f((float)(x + (radius * Math.cos(angle))) , (float)(y + (radius * Math.sin(angle))));
		}
		
		gl.glEnd();
		
	}
	
	public void renderTriangle() {
		GL2 gl = null;
		if(HomePage.getGame()) {
			gl = GameEventListener.gl;
		}else {
			gl = EventListener.gl;
		}
		
		float size = scale * EnvironmentVariables.getMeter() / 2;
		
		//similar to drawing the sqaure
		gl.glBegin(GL2.GL_TRIANGLES);   
			gl.glColor4f(red, green, blue, alpha);
			gl.glVertex2f(x, y + size);      // Top
			gl.glVertex2f(x - size, y - size);  // Bottom Left
			gl.glVertex2f(x + size, y - size);   //Bottom Right
		gl.glEnd();   
	}
	
	//reset to default
	public void reset() {
		x = Window.getCanvas().getWidth() / 2;
		y = Window.getCanvas().getHeight()/ 2;
		dx = 0;
		dy = 0;
		lock = false;
		main_player = false;
		scale = 1;
		mass = 1;
		shape = "Square";
		material = "Concrete";
		colour = "Red";
		
	}
	
	
	//Getters and Setters===================================================
	public int getID() {
		return id;
	}
	public void setID(int temp_id) {
		id = temp_id;
	}
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Boolean getLock() {
		return lock;
	}

	public void setLock(Boolean lock) {
		this.lock = lock;
	}

	public Boolean getMainPlayer() {
		return main_player;
	}

	public void setMainPlayer(Boolean main_player) {
		this.main_player = main_player;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
		setColourValues();
	}
	
	
	//set the values for red, blue, green and alpha
	//depending on the color chosen
	public void setColourValues() {
		if(colour.equals("Red")) {
			red = 1.0f;
			green = 0.0f;
			blue = 0.0f;
			alpha = 1.0f;
		}
		else if(colour.equals("Black")) {
			red = 0.0f;
			green = 0.0f;
			blue = 0.0f;
			alpha = 1.0f;
		}
		else if(colour.equals("White")) {
			red = 1.0f;
			green = 1.0f;
			blue = 1.0f;
			alpha = 1.0f;
		}
		else if(colour.equals("Green")) {
			red = 0.0f;
			green = 1.0f;
			blue = 0.0f;
			alpha = 1.0f;
		}
		else if(colour.equals("Blue")) {
			red = 0.0f;
			green = 0.0f;
			blue = 1.0f;
			alpha = 1.0f;
		}
		else if(colour.equals("Yellow")) {
			red = 1.0f;
			green = 0.972f;
			blue = 0.258f;
			alpha = 1.0f;
		}
		else if(colour.equals("Grey")) {
			red = 0.592f;
			green = 0.592f;
			blue = 0.584f;
			alpha = 1.0f;
		}
	}
	
	@Override
	public String toString() {
		return "Position x: " + x + "\nPosition y: " + y + "\nLock position: " + lock +
				"\nMain player: " + main_player + "\nVelocity x: " + dx + "\nVelocity dy :" + dy +
				"\nScale: " + scale + "\nMass:" + mass + "\nShape: " + shape + "\nMaterial: " + material +
				"\nColour: " + colour;
	}
}

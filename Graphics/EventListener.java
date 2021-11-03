package Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import UserInterface.EditGameObject;
import UserInterface.EditTerrain;
import WorldVariables.EnvironmentVariables;

public class EventListener implements GLEventListener{
	//GL2 allows us to render to the canvas
	public static GL2 gl = null;
	
	
	@Override
	public void display(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		//Clear the last render so we dont experience overlap
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		
		gl.glLoadIdentity();
		
		//render the terrain and world objects
		EnvironmentVariables.renderTerrain();
		EnvironmentVariables.renderWorldObjects();
		
		//render the player
		if(EnvironmentVariables.getMainPlayer() != null) {
			EnvironmentVariables.renderMainPlayer();
		}
		
		//render the temp object so user can see what they are doing
		if(EditTerrain.add_terrain.isSelected()) {
			EditTerrain.temp_terrain_object.render();
		}
		if(EditGameObject.add_game_object.isSelected()) {
			EditGameObject.temp_game_object.render();
		}
		
		
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		
		//set background to be solid blue color
		gl.glClearColor(0.627f, 0.811f, 0.996f, 1.0f);
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		gl = drawable.getGL().getGL2();
		
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glOrtho(0, width, 0, height, -1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		
	}

}

package GameTemplate;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import WorldVariables.EnvironmentVariables;

public class GameEventListener implements GLEventListener{

	//The GL2 allows us to draw to the GLCanvas
	public static GL2 gl = null;
	
	@Override
	public void display(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		
		//clear everything from the last render
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		
		gl.glLoadIdentity();
		
		//our world function that will render all objects
		GameWorld.render();
		
		//flush the GL2 variable
		gl.glFlush();
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		
		//Set our background to a solid blue colour
		gl.glClearColor(0.627f, 0.811f, 0.996f, 1.0f);
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		gl = drawable.getGL().getGL2();
		
		//The matrix used to initialize the window
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		//set the left to be 0
		//the right to be our width
		//bottom to 0
		//top our height
		gl.glOrtho(0, width, 0, height, -1, 1);
		//reset the matrix mode
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

}

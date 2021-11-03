package Math;

import GameTemplate.GameWindow;

/**
 * point class that is used when determining collision reaction
 *
 */
public class Point {
	float x;
	float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public Point(Point point) {
		this.x = point.getX();
		this.y = point.getY();
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
	
	//intersection
	public static Point pointOfIntersection(Point A, Point B, Point C, Point D) {
		
		//line AB 
		float a1 = B.getY() - A.getY();
		float b1 = A.getX() - B.getX();
		float c1 = (a1 * A.getX()) + (b1 * A.getY());
		
		//line CD
		float a2 = D.getY() - C.getY();
		float b2 = C.getX() - D.getX();
		float c2 = (a2 * C.getX()) + (b2 * C.getY());
		
		float determinant = a1 * b2 - a2 * b1;
		
		//Lines are parallel
		if(determinant == 0) {
			return new Point(GameWindow.getCanvas().getWidth() + 500, GameWindow.getCanvas().getWidth() + 500);
		}
		//return the point of intersection
		else {
			float x0 = (b2 * c1 - b1 * c2) / determinant;
			float y0 = (a1 * c2 - a2 * c2) / determinant;
			return new Point(x0, y0);
		}
	}
	
	//closest point of collision
	public static Point closestPoint(float x1, float y1, float next_x1, float next_y1, float x2, float y2) {
		//used when object is moving
		//difference between current point and where it will be
		float A1 = ((next_y1 - y1));
		float B1 = ((x1 - next_x1));
		float C1 = (((next_y1 - y1) * x1) + ((x1 - next_x1) * y1));
		float C2 = (-B1 * x2) + (A1 * y2);
		float determinant = (A1 * A1) - (-B1 * B1);
		float cx = 0;
		float cy = 0;
		
		if(determinant != 0) {
			cx = ((A1 * C1) - (B1 * C2)) / determinant;
			cy = ((A1 * C1) - (B1 * C2)) / determinant;
		}
		else {
			cx = x2;
			cy = y2;
		}
		
		return new Point(cx, cy);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package Math;

/**
 * object class that allows us to build a table of drag coefficients
 *
 */
public class DragCoefficient {
	String shape;
	float coefficient;
	
	public DragCoefficient(String shape, float coefficient) {
		this.shape = shape;
		this.coefficient = coefficient;
	}
	
	public String getShape() {
		return shape;
	}
	
	public float getCoefficient() {
		return coefficient;
	}

}

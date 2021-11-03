package Math;

public class FrictionTable {
	String material1;
	String material2;
	float coefficient;
	
	public FrictionTable(String material1, String material2, float coefficient) {
		this.material1 = material1;
		this.material2 = material2;
		this.coefficient = coefficient;
	}
	
	public String getMaterial1() {
		return material1;
	}
	
	public String getMaterial2() {
		return material2;
	}
	
	public float getCoefficient() {
		return coefficient;
	}
}

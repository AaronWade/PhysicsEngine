package Math;

public class RestitutionTable {
	String material;
	float coefficient;
	
	public RestitutionTable(String material, float coefficient) {
		this.material = material;
		this.coefficient = coefficient;
	}
	
	public String getMaterial() {
		return material;
	}
	
	public float getCoefficient() {
		return coefficient;
	}
}

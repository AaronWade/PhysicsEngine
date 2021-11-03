package Math;

import Graphics.GameObject;

public class Restitution {
	//coefficient of restitution based on objects material
	static RestitutionTable restitutionCr[] = new RestitutionTable[]{
			 new RestitutionTable("Stone", 0.58f), 
			 new RestitutionTable("Rubber", 0.77f), 
			 new RestitutionTable("Wood", 0.23f), 
			 new RestitutionTable("Steel", 0.84f), 
			 new RestitutionTable("Sand", 0.04f),
			 new RestitutionTable("Ice", 0.69f), 
			 new RestitutionTable("Grass", 0.46f)
			 };
	
	/**
	 * find the restitution for both objects and then find the average between tjem
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static float averageRestitution(GameObject obj1, GameObject obj2) {
		float obj1_rest = 0;
		float obj2_rest = 0;
		
		for(int i = 0; i < restitutionCr.length; i ++) {
			if(obj1.getMaterial().equals(restitutionCr[i].getMaterial())) {
				obj1_rest = restitutionCr[i].getCoefficient();
			}
			if(obj2.getMaterial().equals(restitutionCr[i].getMaterial())) {
				obj2_rest = restitutionCr[i].getCoefficient();
			}
		}

		return (obj1_rest + obj2_rest) / 2;
	}
}

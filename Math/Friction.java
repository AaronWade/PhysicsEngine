package Math;

import Graphics.GameObject;

public class Friction {
	
	//a table to hold the friction coefficient
	static FrictionTable frictionCd[] = new FrictionTable[]{
			 new FrictionTable("Stone", "Wood", 0.62f), 
			 new FrictionTable("Stone", "Grass", 0.39f), 
			 new FrictionTable("Stone", "Sand", 0.17f), 
			 new FrictionTable("Stone", "Ice", 0.83f), 
			 new FrictionTable("Stone", "Rubber", 0.23f), 
			 new FrictionTable("Stone", "Steel", 0.57f), 
			 new FrictionTable("Stone", "Stone", 0.41f), 
			 new FrictionTable("Grass", "Wood", 0.38f),
			 new FrictionTable("Grass", "Rubber", 0.21f),
			 new FrictionTable("Grass", "Steel", 0.63f),
			 new FrictionTable("Sand", "Wood", 0.46f),
			 new FrictionTable("Sand", "Rubber", 0.32f),
			 new FrictionTable("Sand", "Steel", 0.70f),
			 new FrictionTable("Ice", "Wood", 0.85f),
			 new FrictionTable("Ice", "Steel", 0.90f),
			 new FrictionTable("Ice", "RUbber", 0.71f),
			 new FrictionTable("Rubber", "Wood", 0.57f),
			 new FrictionTable("Rubber", "Steel", 0.33f),
			 new FrictionTable("Rubber", "Rubber", 0.08f),
			 new FrictionTable("Wood", "Steel", 0.74f),
			 new FrictionTable("Wood", "Wood", 0.66f),
			 new FrictionTable("Steel", "Steel", 0.79f)
			 };
	
	/**
	 * find the friction coefficient between obj1 and obj2
	 * @param obj1
	 * @param obj2
	 * @return friction coefficient
	 */
	public static float getFrictionCD(GameObject obj1, GameObject obj2) {
		float friction_cd = 1.0f;
		
		for(int i = 0; i < frictionCd.length; i++) {
			if(obj1.getMaterial().equals(frictionCd[i].getMaterial1()) && obj2.getMaterial().equals(frictionCd[i].getMaterial2())){
				friction_cd = frictionCd[i].getCoefficient();
				break;
			}
			else if(obj2.getMaterial().equals(frictionCd[i].getMaterial1()) && obj1.getMaterial().equals(frictionCd[i].getMaterial2())){
				friction_cd = frictionCd[i].getCoefficient();
				break;
			}
		}
		
		return friction_cd;
	}
}

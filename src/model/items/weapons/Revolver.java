package model.items.weapons;



public class Revolver extends AmmoWeapon {

	public Revolver() {
		super("Revolver", 0.75, 1.0, true, 1.0, 6);
	}
	
	@Override
	public Revolver clone() {
		return new Revolver();
	}

}

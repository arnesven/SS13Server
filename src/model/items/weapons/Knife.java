package model.items.weapons;


public class Knife extends Weapon {

	public Knife() {
		super("Knife", 0.75, 1.0, false, 0.2);
	}

	@Override
	public Knife clone() {
		return new Knife();
	}
	
	@Override
	protected char getIcon() {
		return 'k';
	}
	
}

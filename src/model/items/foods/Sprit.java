package model.items.foods;

public class Sprit extends Alcohol {

	// TODO Remove this, only for testing
	public Sprit() {
		super("Sprit", 0.0, 10);
	}

	@Override
	public FoodItem clone() {
		return new Sprit();
	}
	
}

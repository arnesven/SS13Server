package model.items.foods;

public class Vodka extends Alcohol {

	public Vodka() {
		super("5", 0.8, 4);
	}

	@Override
	public FoodItem clone() {
		return new Vodka();
	}
	
}

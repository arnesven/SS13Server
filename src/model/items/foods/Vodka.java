package model.items.foods;

public class Vodka extends Alcohol {

	public Vodka() {
		super("Vodka", 0.8, 4);
	}

	@Override
	public FoodItem clone() {
		return new Vodka();
	}
	
}

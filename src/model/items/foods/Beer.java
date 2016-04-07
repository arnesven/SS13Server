package model.items.foods;

public class Beer extends Alcohol {

	public Beer() {
		super("Beer", 0.3, 1);
	}

	@Override
	public FoodItem clone() {
		return new Beer();
	}
	
}

package model.items.foods;

public class Wine extends Alcohol {

	public Wine() {
		super("Wine", 1.0, 2);
	}

	@Override
	public FoodItem clone() {
		return new Wine();
	}
	
}

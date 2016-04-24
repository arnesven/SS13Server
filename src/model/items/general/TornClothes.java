package model.items.general;

public class TornClothes extends GameItem {

	public TornClothes() {
		super("Torn Clothes", 0.5);
	}
	
	@Override
	public GameItem clone() {
		return new TornClothes();
	}

}

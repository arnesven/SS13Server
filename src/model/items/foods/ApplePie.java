package model.items.foods;

public class ApplePie extends HealingFood {

	public ApplePie() {
		super("Apple Pie", 0.5);
	}

	@Override
	public double getFireRisk() {
		return 0.1;
	}

	@Override
	protected char getIcon() {
		return '[';
	}
	
	

	@Override
	public ApplePie clone() {
		return new ApplePie();
	}

}

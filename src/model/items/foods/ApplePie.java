package model.items.foods;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.itemactions.EatAction;
import model.items.GameItem;

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

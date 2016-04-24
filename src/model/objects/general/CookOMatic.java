package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.Sprite;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.CookFoodAction;
import model.actions.objectactions.CookGrenadeIntoFoodAction;
import model.items.general.GameItem;
import model.items.general.Grenade;
import model.items.foods.ApplePie;
import model.items.foods.DoubleFlambeSteakDiane;
import model.items.foods.FoodItem;
import model.items.foods.SpinachSoup;
import model.map.Room;

public class CookOMatic extends ElectricalMachinery {

	public CookOMatic(Room pos) {
		super("Cook-O-Matic", pos);
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("cookomatic", "kitchen.png", 8);
    }

    @Override
	protected void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		at.add(new CookFoodAction(this));
		if (hasGrenade(cl) != null) {
			at.add(new CookGrenadeIntoFoodAction(this, new CookFoodAction(this)));
		}

	}

	public static List<FoodItem> getCookableFood() {
		List<FoodItem> foods = new ArrayList<>();
		foods.add(new ApplePie());
		foods.add(new SpinachSoup());
		foods.add(new DoubleFlambeSteakDiane());
		return foods;
	}


	private GameItem hasGrenade(Player cl) {
		for (GameItem it : cl.getItems()) {
			if (it instanceof Grenade) {
				return it;
			}
		}
		return null;
	}

}

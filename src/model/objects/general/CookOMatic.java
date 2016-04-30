package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.CookFoodAction;
import model.actions.objectactions.CookGrenadeIntoFoodAction;
import model.items.general.ExplodableItem;
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
		if (hasExplosive(cl) != null) {
			at.add(new CookGrenadeIntoFoodAction(this, new CookFoodAction(this)));
		}

	}

	public static List<FoodItem> getCookableFood(Actor maker) {
		List<FoodItem> foods = new ArrayList<>();
		foods.add(new ApplePie(maker));
		foods.add(new SpinachSoup(maker));
		foods.add(new DoubleFlambeSteakDiane(maker));
		return foods;
	}


	private GameItem hasExplosive(Player cl) {
		for (GameItem it : cl.getItems()) {
			if (it instanceof ExplodableItem) {
				return it;
			}
		}
		return null;
	}

}

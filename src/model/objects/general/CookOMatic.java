package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.CookBodyPartIntoFoodAction;
import model.actions.objectactions.CookFoodAction;
import model.actions.objectactions.CookGrenadeIntoFoodAction;
import model.actions.objectactions.WalkUpToElectricalMachineryAction;
import model.characters.general.ChimpCharacter;
import model.fancyframe.CookOMaticFancyFrame;
import model.fancyframe.FancyFrame;
import model.items.BodyPart;
import model.items.SeveredButt;
import model.items.foods.*;
import model.items.general.ExplodableItem;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.items.foods.SliceOfPizza;
import model.npcs.NPC;

public class CookOMatic extends ElectricalMachinery {

	public CookOMatic(Room pos) {
		super("Cook-O-Matic", pos);
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
        	return new Sprite("cookomaticbroken", "kitchen.png", 11, this);
		} else if (!isPowered()) {
        	return new Sprite("cookomaticnopower", "kitchen.png", 7, this);
		}
		return new Sprite("cookomatic", "kitchen.png", 8, this);
    }

    @Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		if (cl instanceof NPC) {
			at.add(new CookFoodAction(this));
			if (hasExplosive(cl) != null) {
				at.add(new CookGrenadeIntoFoodAction(this, new CookFoodAction(this)));
			}

			if (hasBodyPart(cl) != null) {
				at.add(new CookBodyPartIntoFoodAction(this, new CookFoodAction(this)));
			}
		}

        if (cl instanceof Player) {
			at.add(new WalkUpToElectricalMachineryAction(gameData, cl, this) {
				@Override
				protected FancyFrame getFancyFrame(GameData gameData, Actor performingClient) {
					return new CookOMaticFancyFrame((Player)performingClient, gameData, CookOMatic.this);
				}
			});
		}

	}

	public static List<FoodItem> getCookableFood(Actor maker) {
		List<FoodItem> foods = new ArrayList<>();
		foods.add(new ApplePie(maker));
        foods.add(new Doughnut(maker));
        foods.add(new SpaceBurger(maker));
		foods.add(new SpinachSoup(maker));
		foods.add(new SliceOfPizza(maker));
		foods.add(new Pizza(maker));
		foods.add(new DoubleFlambeSteakDiane(maker));
		if (hasAMonkey(maker.getPosition())) {
			foods.add(new GrilledMonkeyDeluxe(maker));
		}
		if (GameItem.hasAnItemOfClass(maker, SeveredButt.class)) {
			foods.add(new ButtBurger(maker));
		}
		return foods;
	}

	private static boolean hasAMonkey(Room position) {
		for (Actor a: position.getActors()) {
			if (a.getInnermostCharacter() instanceof ChimpCharacter) {
				return true;
			}
		}
		return false;
	}


	public static GameItem hasExplosive(Actor cl) {
		for (GameItem it : cl.getItems()) {
			if (it instanceof ExplodableItem) {
				return it;
			}
		}
		return null;
	}

    public static GameItem hasBodyPart(Actor cl) {
        for (GameItem it : cl.getItems()) {
            if (it instanceof BodyPart) {
                return it;
            }
        }
        return null;
    }

}

package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.events.damage.PoisonDamage;
import model.items.chemicals.EthanolChemicals;
import model.items.chemicals.EtherChemicals;
import model.items.chemicals.HydrogenPeroxideChemicals;
import model.items.foods.FoodItem;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public abstract class Chemicals extends FoodItem {

    private final int spriteCol;

    public Chemicals(String name, int spriteMapNumber) {
		super(name, 1.0, 10);
        this.spriteCol = spriteMapNumber;
	}


    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        eatenBy.getAsTarget().beExposedTo(eatenBy, new PoisonDamage(2.0));
        eatenBy.addTolastTurnInfo(HTMLText.makeText("red", "The chemicals burns your intestines!"));

    }


    public static boolean hasNChemicals(Actor performingClient, int num) {
		int i = 0;
		for (GameItem it : performingClient.getItems()) {
			if (it instanceof Chemicals) {
				i++;
				if (i == num) {
					return true;
				}
			}
		}
		return false;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("chemicals", "chemical.png", spriteCol);
    }

    @Override
    public double getFireRisk() {
        return 0;
    }

    public static Chemicals createRandomChemicals() {
        List<Chemicals> list = new ArrayList<>();
        list.add(new EthanolChemicals());
        list.add(new HydrogenPeroxideChemicals());
        list.add(new EtherChemicals());

        return MyRandom.sample(list);
    }
}

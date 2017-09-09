package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.events.damage.PoisonDamage;
import model.items.chemicals.*;
import model.items.foods.FoodItem;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public abstract class Chemicals extends FoodItem {

    private final int spriteCol;

    public Chemicals(String name, int spriteMapNumber) {
		super(name, 0.3, 10);
        this.spriteCol = spriteMapNumber;
	}

    public abstract boolean isFlammable();
    public abstract boolean isToxic();
    public abstract String getFormula();


    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        if (isToxic()) {
            eatenBy.getAsTarget().beExposedTo(eatenBy, new PoisonDamage(2.0));
            eatenBy.addTolastTurnInfo(HTMLText.makeText("red", "The chemicals burns your intestines!"));
        } else {
            eatenBy.addTolastTurnInfo("You consumed the " + getFullName(eatenBy));
        }

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
        list.add(new HydroChloricAcidChemicals());
        list.add(new SulfuricAcidChemicals());
        list.add(new SodiumChloride());
        list.add(new AcetoneChemicals());
        list.add(new AmmoniaChemicals());
        list.add(new BenzeneChemicals());

        return MyRandom.sample(list);
    }


    public GameItem combineWith(Chemicals other) {
        return null;
    }
}

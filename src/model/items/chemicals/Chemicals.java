package model.items.chemicals;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.characters.crew.ChemistCharacter;
import model.characters.general.GameCharacter;
import model.events.damage.PoisonDamage;
import model.items.foods.FoodItem;
import model.items.general.GameItem;
import sounds.Sound;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Chemicals extends FoodItem {

    private final int spriteCol;

    public Chemicals(String name, int spriteMapNumber) {
		super(name, 0.3, 10);
        this.spriteCol = spriteMapNumber;
	}

    public abstract boolean isFlammable();
    public abstract boolean isToxic();
    public abstract boolean isCorrosive();
    public abstract String getFormula();

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("drink");
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
    }

    public abstract Chemicals clone();


    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        if (isToxic()) {
            eatenBy.getAsTarget().beExposedTo(eatenBy, new PoisonDamage(2.0), gameData);
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
        return new Sprite("chemicals"+spriteCol, "chemical.png", spriteCol, this);
    }

    @Override
    public double getFireRisk() {
        return 0;
    }

    public static List<Chemicals> getAllChemicals() {
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
        list.add(new GeneratorStartedFluid());

        return list;
    }

    public static Chemicals createRandomChemicals() {
        return MyRandom.sample(getAllChemicals()).clone();
    }




    public GameItem combineWith(Chemicals other) {
        return null;
    }

    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        if (performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ChemistCharacter)) {
            return (isFlammable()?"<i>Flammable</i>, ":"") + (isToxic()?"<i>Toxic</i>, ":"") +
                    (isCorrosive()?"<i>Corrosive</i>, ":"") + ("<b>Formula:</b> " + getFormula());
        }
        return super.getExtraDescriptionStats(gameData, performingClient);
    }
}

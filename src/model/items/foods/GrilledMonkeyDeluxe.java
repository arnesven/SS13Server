package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.characters.general.ChimpCharacter;
import model.events.damage.FireDamage;
import model.items.NoSuchThingException;

public class GrilledMonkeyDeluxe extends HealingFood {

    public GrilledMonkeyDeluxe(Actor maker) {
        super("Grilled Monkey", 1.5, maker, 1300);
    }

    @Override
    public double getFireRisk() {
        return 0.3;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("grilledmonkeydeluxe", "food.png", 17, 15, this);
    }

    @Override
    public FoodItem clone() {
        return new GrilledMonkeyDeluxe(getMaker());
    }

    @Override
    public boolean canBeCooked(GameData gameData, Actor performingClient) {
        for (Actor a : performingClient.getPosition().getActors()) {
            if (a.getInnermostCharacter() instanceof ChimpCharacter) {
                a.getAsTarget().beExposedTo(performingClient, new FireDamage(300.0), gameData);
                try {
                    a.getPosition().removeActor(a);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        performingClient.addTolastTurnInfo("What, no monkey to use? " + Action.FAILED_STRING);
        return false;
    }
}

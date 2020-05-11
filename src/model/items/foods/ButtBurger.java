package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.items.SeveredButt;
import model.items.general.GameItem;

public class ButtBurger extends SpaceBurger {
    public ButtBurger(Actor maker) {
        super(maker);
        setName("Butt Burger");
    }

    @Override
    public double getFireRisk() {
        return 0.05;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("buttburger", "food.png", 24, 20, this);
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        eatenBy.addTolastTurnInfo("Ugh, tastes kind of shitty...");
    }

    @Override
    public boolean canBeCooked(GameData gameData, Actor performingClient) {
        if (GameItem.hasAnItemOfClass(performingClient, SeveredButt.class)) {
            try {
                GameItem butt = GameItem.getItemFromActor(performingClient, new SeveredButt(performingClient));
                performingClient.getItems().remove(butt);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
            return true;
        }

        return false;
    }
}

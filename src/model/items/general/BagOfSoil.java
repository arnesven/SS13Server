package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.objects.general.SoilPatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 28/11/16.
 */
public class BagOfSoil extends GameItem {
    public BagOfSoil() {
        super("Bag of Soil", 1.0, true, 5);
    }

    @Override
    public GameItem clone() {
        return new BagOfSoil();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("bagofsoil", "seeds.png", 3, 7, this);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        at.add(new SpreadSoilAction());
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A sack of dirt. A good base for planting something in.";
    }

    private class SpreadSoilAction extends Action {

        public SpreadSoilAction() {
            super("Spread Soil", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "spread soil on the floor";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (!performingClient.getItems().contains(BagOfSoil.this)) {
                performingClient.addTolastTurnInfo("What, the bag of soil wasn't there anymore? " + failed(gameData, performingClient));
                return;
            }

            performingClient.getItems().remove(BagOfSoil.this);
            performingClient.getPosition().addObject(new SoilPatch(performingClient.getPosition()));
            performingClient.addTolastTurnInfo("You spread the soil on the floor.");
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

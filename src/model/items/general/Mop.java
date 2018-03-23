package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SearchAction;
import model.objects.general.BloodyMess;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Mop extends GameItem {
    public Mop() {
        super("Mop", 0.4, false, 20);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        Action a = new CleanUpBloodAction();
        at.add(a);

    }

    @Override
    public GameItem clone() {
        return new Mop();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("mop", "janitor.png", 3);
    }

    private class CleanUpBloodAction extends SearchAction {

        public CleanUpBloodAction() {
            setName("Mop Floor");
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Mopped the floors";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (!GameItem.hasAnItemOfClass(performingClient, Mop.class)) {
                performingClient.addTolastTurnInfo("You don't have a mop! " + Action.FAILED_STRING);
                return;
            }

            List<GameObject> toBeRemoved = new ArrayList<>();

            for (GameObject obj : performingClient.getPosition().getObjects()) {
                if (obj instanceof BloodyMess) {
                    toBeRemoved.add(obj);
                }
            }
            performingClient.getPosition().getObjects().removeAll(toBeRemoved);
            if (toBeRemoved.size() > 0) {
                performingClient.addTolastTurnInfo("You cleaned up a bloody mess.");
            }
            super.execute(gameData, performingClient);

        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

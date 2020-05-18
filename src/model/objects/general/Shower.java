package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.map.rooms.DormsRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 10/09/17.
 */
public class Shower extends GameObject {
    private boolean isOn;

    public Shower(DormsRoom dormsRoom) {
        super("Shower", dormsRoom);
        isOn = false;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {

        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("showerhead", "watercloset.png", 7, 2, this));
        String suffix = "";
        if (isOn) {
            list.add(new Sprite("showerwater", "watercloset.png", 7, 3, this));
            list.add(new Sprite("showersteam", "watercloset.png", 0, 5, this));
            suffix = "withwater";
        }

        return new Sprite("totalshower" + suffix, "watercloset.png", 1, list, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (isOn) {
            at.add(new TakeAShower());
        } else {
            if (cl instanceof Player) {
                at.add(new TurnShowerOn());
            }
        }
    }

    private class TurnShowerOn extends Action {
        public TurnShowerOn() {
            super("Turn On Water", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return null;
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {

        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {
            isOn = true;
            ((Player)performingClient).setNextAction(new DoNothingAction());
            for (Player p : performingClient.getPosition().getClients()) {
                p.refreshClientData();
            }
        }

        @Override
        public boolean doesSetPlayerReady() {
            return false;
        }
    }
}

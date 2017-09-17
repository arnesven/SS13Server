package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.items.weapons.PulseRifle;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 04/12/16.
 */
public class PulseRifleDisplayCase extends BreakableObject {
    private boolean empty;

    public PulseRifleDisplayCase(Room r) {
        super("Display Case", 2, r);
        this.empty = false;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (!isBroken()) {
            if (!empty) {
                return new Sprite("displaycase1", "stationobjs.png", 273);
            } else {
                return new Sprite("displaycase2", "stationobjs.png", 272);
            }
        } else {
            if (!empty) {
                return new Sprite("displaycase3", "stationobjs.png", 274);
            } else {
                return new Sprite("displaycase4", "stationobjs.png", 275);
            }
        }
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (!empty) {
            if (GameItem.hasAnItem(cl, new KeyCard()) || isBroken()) {
                at.add(new RetrieveFromDisplayCase());
            }
        }
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean getEmpty() {
        return empty;
    }

    private class RetrieveFromDisplayCase extends Action {

        public RetrieveFromDisplayCase() {
            super("Get Pulse Rifle", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "retrieved a pulse rifle from the display case.";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (PulseRifleDisplayCase.this.getEmpty()) {
                performingClient.addTolastTurnInfo("What, the pulse rifle no longer there? " + Action.FAILED_STRING);
            }

            performingClient.addTolastTurnInfo("You retrieved a pulse rifle from the display case.");
            performingClient.addItem(new PulseRifle(), null);
            PulseRifleDisplayCase.this.setEmpty(true);
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

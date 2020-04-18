package model.map.doors;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.actions.roomactions.AttackDoorAction;
import model.objects.general.BreakableObject;

import java.util.ArrayList;
import java.util.List;

public abstract class ElectricalDoor extends Door {

    private BreakableObject breakableObject;


    public ElectricalDoor(double x, double y, String name, int fromID, int toID) {
        super(x, y, name, fromID, toID);
        breakableObject = new BreakableObject(ElectricalDoor.this.getName(), 2.0, null) {
            @Override
            protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

            }

            @Override
            public void thisJustBroke(GameData gameData) {
                ElectricalDoor.this.thisJustBroke(gameData);
            }

            @Override
            public boolean canBeInteractedBy(Actor performingClient) {
                return performingClient.getPosition().getID() == getFromId() || performingClient.getPosition().getID() == getToId();
            }
        };
    }

    protected boolean isBroken() {
        if (breakableObject == null) {
            return false;
        }
        return breakableObject.isBroken();
    }

    @Override
    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = super.getDoorActions(gameData, forWhom);
        AttackAction act = new AttackDoorAction(forWhom, this);
        act.addTarget(breakableObject);
        act.stripAllTargetsBut(breakableObject);
        act.addClientsItemsToAction(forWhom);
        at.add(act);
        return at;
    }

    protected void thisJustBroke(GameData gameData) {

    }

    protected BreakableObject getBreakableObject() {
        return breakableObject;
    }

    protected void setBreakableObject(BreakableObject breakableObject) {
        this.breakableObject = breakableObject;
    }
}

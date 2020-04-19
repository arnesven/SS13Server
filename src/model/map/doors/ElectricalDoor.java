package model.map.doors;

import comm.chat.AILawChatHandler;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.actions.general.SensoryLevel;
import model.actions.roomactions.AttackDoorAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.events.damage.FireDamage;
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

            @Override
            public void beExposedTo(Actor performingClient, Damager damage, GameData gameData) {
                if (damage instanceof FireDamage) {
                    damage = new FireDamage(0.25);
                }
                super.beExposedTo(performingClient, damage, gameData);
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
        if (!forWhom.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter)) {
            AttackAction act = new AttackDoorAction(forWhom, this);
            act.addTarget(breakableObject);
            act.stripAllTargetsBut(breakableObject);
            act.addClientsItemsToAction(forWhom);
            at.add(act);
        }
        at.add(new Action("Health is " + getBreakableObject().getHealth(), SensoryLevel.NO_SENSE) {
            @Override
            protected String getVerb(Actor whosAsking) {
                return "";
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {

            }

            @Override
            protected void setArguments(List<String> args, Actor performingClient) {

            }
        });
        return at;
    }

    protected void thisJustBroke(GameData gameData) {

    }

    public BreakableObject getBreakableObject() {
        return breakableObject;
    }

    protected void setBreakableObject(BreakableObject breakableObject) {
        this.breakableObject = breakableObject;
    }
}

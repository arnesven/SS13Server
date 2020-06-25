package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.rooms.PirateShipRoom;
import model.npcs.NPC;
import model.npcs.behaviors.CommandedByBehavior;
import model.npcs.behaviors.MeanderingHumanMovement;
import model.npcs.behaviors.MoveTowardsBehavior;

import java.util.List;

public class MoveTowardsPirateShipAction extends Action {

    public MoveTowardsPirateShipAction() {
        super("Move Towards Pirate Ship", SensoryLevel.NO_SENSE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "is moving towards Pirate Ship";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (performingClient instanceof NPC) {
            try {
                PirateShipRoom psr = (PirateShipRoom)gameData.getRoom("Pirate Ship");
                ((NPC) performingClient).setMoveBehavior(new MoveTowardsBehavior(psr,
                        new MeanderingHumanMovement(0.0), new CommandedByBehavior()));
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}

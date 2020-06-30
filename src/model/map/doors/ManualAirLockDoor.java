package model.map.doors;

import graphics.sprites.MoveToSpaceTargetAction;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.MoveAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class ManualAirLockDoor extends AirLockDoor {
    public ManualAirLockDoor(double v, double v1, int i, int i1) {
        super(v, v1, i, i1);
    }

    @Override
    protected List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> acts = super.getDoorActions(gameData, forWhom);

        if (!isFullyOpen()) {
            acts.add(new OpenAndMoveThroughManualAirlockDoor());
        }

        return acts;
    }

    private class OpenAndMoveThroughManualAirlockDoor extends Action {

        public OpenAndMoveThroughManualAirlockDoor() {
            super("Open And Move Through", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "opened and moved through the airlock door";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            openAirlockDoor(gameData);
            for (Actor a : performingClient.getPosition().getActors()) {
                if (!a.isInSpace()) {
                    a.goToSpace(gameData);
                }
            }
            MoveToSpaceTargetAction mstpta = new MoveToSpaceTargetAction(getX(), getY(), getZ());
            mstpta.setActionTreeArguments(new ArrayList<>(), performingClient);
            mstpta.doTheAction(gameData, performingClient);

        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}

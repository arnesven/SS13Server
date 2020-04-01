package model.map.doors;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.MoveAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.Set;

public abstract class StairsObject extends GameObject {
    public StairsObject(String name, Room position) {
        super(name, position);
    }

    @Override
    public boolean shouldBeSeenWhenNotInRoomBy(Player player) {
        return true;
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new StairsMoveAction(cl, this));
    }

    private class StairsMoveAction extends MoveAction {
        private final StairsObject stairs;

        public StairsMoveAction(Actor cl, StairsObject stairs) {
            super(cl);
            this.stairs = stairs;
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            Set<Room> movableTo = findMoveToAblePositions(whosAsking);
            if (stairs instanceof UpgoingStairsDoor) {
                movableTo.removeIf((Room r) -> r.getZ() <= getPosition().getZ());
            } else {
                movableTo.removeIf((Room r) -> r.getZ() >= getPosition().getZ());
            }
            ActionOption opts = new ActionOption(getName());
            for (Room r : movableTo) {
                opts.addOption(r.getName());
            }
            return opts;
        }
    }
}

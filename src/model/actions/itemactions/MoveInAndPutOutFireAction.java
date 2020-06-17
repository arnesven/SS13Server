package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.MoveAction;
import model.actions.general.ActionOption;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.general.FireExtinguisher;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class MoveInAndPutOutFireAction extends MoveAction {
    private final FireExtinguisher fireExtinguisher;
    private final Room targetRoom;

    public MoveInAndPutOutFireAction(GameData gameData, Actor actor, FireExtinguisher fe, Room targetRoom) {
        super(gameData, actor);
        setName("Move In and Put out Fire");
        this.fireExtinguisher = fe;
        this.targetRoom = targetRoom;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        return new ActionOption(getName());
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        List<String> newArgs = new ArrayList<>();
        newArgs.add(targetRoom.getName());
        super.setArguments(newArgs, performingClient);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        super.execute(gameData, performingClient);
        performingClient.setCharacter(new PutOutFireNextTurnDecorator(performingClient.getCharacter(), gameData.getRound()));
    }

    private class PutOutFireNextTurnDecorator extends CharacterDecorator {
        private final int roundSet;

        public PutOutFireNextTurnDecorator(GameCharacter character, int round) {
            super(character, "putoutfirenextturn");
            this.roundSet = round;
        }

        @Override
        public void doAfterMovement(GameData gameData) {
            super.doAfterMovement(gameData);
            if (gameData.getRound() == roundSet + 1) {
                PutOutFireAction pfa = new PutOutFireAction(fireExtinguisher);
                pfa.setArguments(new ArrayList<>(), getActor());
                pfa.doTheAction(gameData, getActor());
                getActor().removeInstance((GameCharacter gc ) -> gc == this);
            }
        }
    }
}

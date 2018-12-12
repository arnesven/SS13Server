package model.actions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.DraggingDecorator;
import model.characters.general.GameCharacter;

import java.util.List;

public class StopDraggingAction extends Action {
    private final Actor beingDragged;

    public StopDraggingAction(Actor beingDragged) {
        super("Stop Dragging " + beingDragged.getPublicName(), SensoryLevel.PHYSICAL_ACTIVITY);
        this.beingDragged = beingDragged;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "stopped dragging " + beingDragged;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof DraggingDecorator)) {
            performingClient.removeInstance((GameCharacter gc) -> gc instanceof DraggingDecorator);
            performingClient.addTolastTurnInfo("You stopped dragging " + beingDragged.getPublicName());
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}

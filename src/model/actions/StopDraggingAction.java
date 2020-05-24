package model.actions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.characters.decorators.DraggingDecorator;
import model.characters.general.GameCharacter;

import java.util.List;

public class StopDraggingAction extends Action {
    private final Actor beingDragged;

    public StopDraggingAction(Actor beingDragged) {
        super("Stop Dragging " + beingDragged.getPublicName() + " (free action)", SensoryLevel.PHYSICAL_ACTIVITY);
        this.beingDragged = beingDragged;
    }

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("stopdraggingabi", "interface.png", 13, 14, null);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "stopped dragging " + beingDragged;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof DraggingDecorator)) {
            performingClient.removeInstance((GameCharacter gc) -> gc instanceof DraggingDecorator);
            performingClient.addTolastTurnInfo("You stopped dragging " + beingDragged.getPublicName());
            if (performingClient instanceof Player) {
                ((Player) performingClient).setNextAction(new DoNothingAction());
                ((Player) performingClient).refreshClientData();
            }
        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}

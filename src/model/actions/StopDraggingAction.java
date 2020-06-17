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

public class StopDraggingAction extends FreeAction {
    private final Actor beingDragged;

    public StopDraggingAction(Actor beingDragged, GameData gameData, Player p) {
        super("Stop Dragging " + beingDragged.getPublicName(), gameData, p);
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
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        if (p.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof DraggingDecorator)) {
            p.removeInstance((GameCharacter gc) -> gc instanceof DraggingDecorator);
            gameData.getChat().serverInSay("You stopped dragging " + beingDragged.getPublicName(), p);
            p.refreshClientData();
        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}

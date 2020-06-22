package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.StopDraggingAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;

import java.util.ArrayList;

public class DraggingDecorator extends CharacterDecorator {
    private final Actor draggedActor;

    public DraggingDecorator(Actor target, GameCharacter character) {
        super(character, "Dragging " + target.getBaseName());
        this.draggedActor = target;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        if (getActor() instanceof Player) {
            at.add(new StopDraggingAction(draggedActor, gameData, (Player)getActor()));
        }
    }

    @Override
    public String getFullName() {
        return super.getFullName() + " (Dragging " + draggedActor.getPublicName() + ")";
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);
        try {
            draggedActor.getPosition().removeActor(draggedActor);
            getActor().getPosition().addActor(draggedActor);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        getActor().addTolastTurnInfo("You dragged " + draggedActor.getPublicName() + " with you.");
        for (Actor a : getActor().getPosition().getActors()) {
            if (a != getActor()) {
                a.addTolastTurnInfo(getActor().getPublicName() + " dragged " + a.getPublicName() + ".");
            }
        }
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        // check to see that the dragged character is still there...

        if (!draggedActorStillThere(gameData)) {
            getActor().removeInstance((GameCharacter gc) -> gc instanceof DraggingDecorator);
            getActor().addTolastTurnInfo("You stopped dragging " + draggedActor.getPublicName());
        }

    }

    private boolean draggedActorStillThere(GameData gameData) {

        for (Actor a : getActor().getPosition().getActors()) {
            if (a == draggedActor) {
                return true;
            }
        }

        return false;
    }
}

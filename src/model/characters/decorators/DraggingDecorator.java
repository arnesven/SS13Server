package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.StopDraggingAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.objects.general.GameObject;

import java.util.ArrayList;

public class DraggingDecorator extends CharacterDecorator {
    private final Target draggedTarget;

    public DraggingDecorator(Target target, GameCharacter character) {
        super(character, "Dragging " + target.getName());
        this.draggedTarget = target;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        if (getActor() instanceof Player) {
            at.add(new StopDraggingAction(draggedTarget, gameData, (Player)getActor()));
        }
    }

    @Override
    public String getFullName() {
        return super.getFullName() + " (Dragging " + draggedTarget.getName() + ")";
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);
        try {
            if (draggedTarget instanceof Actor) {
                draggedTarget.getPosition().removeActor((Actor)draggedTarget);
                getActor().getPosition().addActor((Actor)draggedTarget);
            } else if (draggedTarget instanceof GameObject) {
                draggedTarget.getPosition().removeObject((GameObject)draggedTarget);
                getActor().getPosition().addObject((GameObject)draggedTarget);
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        getActor().addTolastTurnInfo("You dragged " + draggedTarget.getName() + " with you.");
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
            getActor().addTolastTurnInfo("You stopped dragging " + draggedTarget.getName());
        }

    }

    private boolean draggedActorStillThere(GameData gameData) {
        for (Target a : getActor().getPosition().getTargets(gameData)) {
            if (a == draggedTarget) {
                return true;
            }
        }
        return false;
    }
}

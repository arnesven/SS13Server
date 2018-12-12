package model.actions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.DraggingDecorator;
import model.items.general.GameItem;

public class DragAction extends TargetingAction {
    public DragAction(Actor actor, GameData gameData) {
        super("Drag Body", SensoryLevel.PHYSICAL_ACTIVITY, actor);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient,
                                        Target target, GameItem item) {
        performingClient.setCharacter(new DraggingDecorator((Actor)target, performingClient.getCharacter()));
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "started dragging";
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2 instanceof  Actor && target2.isDead();
    }

}

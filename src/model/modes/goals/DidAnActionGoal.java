package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.characters.decorators.CharacterDecorator;

/**
 * Created by erini02 on 03/12/16.
 */
public abstract class DidAnActionGoal extends PersonalGoal {

    private final int timesToDo;
    private final Class<? extends Action> act;
    private int didTimes = 0;

    public DidAnActionGoal(int timesToDo, Class<? extends Action> act) {
        this.timesToDo = timesToDo;
        this.act = act;
    }

    @Override
    public String getText() {
        return getVerb() + " " + timesToDo + " " + getNoun() + ".";
    }

    protected abstract String getNoun();

    protected abstract String getVerb();

    @Override
    public void setBelongsTo(Actor belongingTo) {
        belongingTo.setCharacter(new CharacterDecorator(belongingTo.getCharacter(), "putoutfirecounter") {
            @Override
            public void doAfterActions(GameData gameData) {
                super.doAfterActions(gameData);
                Action a = ((Player)getActor()).getNextAction();
                if (a.getClass().equals(act)) {
                    didTimes += 1;
                }
            }
        });
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return didTimes >= timesToDo;
    }

    
}

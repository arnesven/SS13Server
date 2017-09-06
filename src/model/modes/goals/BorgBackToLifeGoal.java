package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.ReprogramAction;
import model.actions.general.Action;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.programs.BrainBotProgram;

/**
 * Created by erini02 on 25/08/17.
 */
public class BorgBackToLifeGoal extends PersonalGoal {

    private boolean completed;

    public BorgBackToLifeGoal() {
        this.completed = false;
    }

    @Override
    public String getText() {
        return "'Borg someone back to life. (Cut out a dead persons brain, upload it into a ROM in the mainframe and reprogram a bot with the ROM.)";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return completed;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new CharacterDecorator(belongingTo.getCharacter(), "borgchecker") {
            @Override
            public void doAfterActions(GameData gameData) {
                super.doAfterActions(gameData);
                Action a = ((Player)getActor()).getNextAction();
                if ( a instanceof ReprogramAction) {
                    ReprogramAction rep = (ReprogramAction)a;
                    if (rep.getSelectedProgram() instanceof BrainBotProgram) {
                        completed = true;
                    }
                }
            }
        });
    }
}

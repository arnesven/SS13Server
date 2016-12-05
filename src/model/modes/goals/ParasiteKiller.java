package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.ParasiteCharacter;

import java.util.List;

/**
 * Created by erini02 on 03/12/16.
 */
public class ParasiteKiller extends PersonalGoal {

    private final int quant;
    private int killed = 0;

    public ParasiteKiller(int i) {
        this.quant = i;
    }


    @Override
    public String getText() {
        return "Kill " + quant + " parasites";
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new CharacterDecorator(belongingTo.getCharacter(),
                "parasitekillcounter") {

            @Override
            public void doAtEndOfTurn(GameData gameData) {
                super.doAtEndOfTurn(gameData);
                if (getActor() instanceof Player) {
                    Action a = ((Player) getActor()).getNextAction();
                    if (a instanceof AttackAction) {
                        if (((AttackAction) a).getTarget() instanceof ParasiteCharacter) {
                            if (((ParasiteCharacter) ((AttackAction) a).getTarget()).getKiller() == getActor()) {
                                killed += 1;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return killed >= quant;
    }
}

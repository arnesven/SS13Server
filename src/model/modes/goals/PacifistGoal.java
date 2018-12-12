package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.AttackAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 05/12/16.
 */
public class PacifistGoal extends PersonalGoal {

    private int attacks = 0;
    private int quant;

    public PacifistGoal(int quant) {
        this.quant = quant;
    }

    @Override
    public String getText() {
        return "Perform exactly " + quant + " attack(s) this game.";
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new AttackCounterDecorator(belongingTo.getCharacter()));
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return attacks == quant;
    }

    private class AttackCounterDecorator extends CharacterDecorator {
        public AttackCounterDecorator(GameCharacter character) {
            super(character, "attackcounter");
        }

        @Override
        public void doAfterActions(GameData gameData) {
            super.doAfterActions(gameData);
            if (getActor() instanceof Player) {
                if (((Player)getActor()).getNextAction() instanceof AttackAction) {
                    attacks++;
                }
            }
        }
    }
}

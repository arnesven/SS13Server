package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.weapons.StunBaton;

/**
 * Created by erini02 on 25/08/17.
 */
public class BatonABaddieGoal extends PersonalGoal {

    private boolean completed;
    private final GameData gameData;

    public BatonABaddieGoal(GameData gameData) {
        completed = false;
        this.gameData = gameData;
    }

    @Override
    public String getText() {
        return "Use your stun baton on a antagonist.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return completed;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new CharacterDecorator(belongingTo.getCharacter(), "batononantagonistchecker") {
            @Override
            public void doAfterActions(GameData gameData) {
                super.doAfterActions(gameData);
                Action a = ((Player)getActor()).getNextAction();
                if (a instanceof AttackAction) {
                    AttackAction atk = (AttackAction)a;
                    if (atk.getTarget() instanceof Actor && atk.getItem() instanceof StunBaton) {
                        if (gameData.getGameMode().isAntagonist((Actor)atk.getTarget())) {
                            completed = true;
                        }
                    }
                }
            }
        });
    }
}

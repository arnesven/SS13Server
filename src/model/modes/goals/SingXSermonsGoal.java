package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.SermonAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import util.MyRandom;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by erini02 on 25/08/17.
 */
public class SingXSermonsGoal extends PersonalGoal {

    private final int amount;
    private Set<String> people;

    public SingXSermonsGoal() {
        amount = MyRandom.nextInt(3) + 3;
        people = new HashSet<>();
    }

    @Override
    public String getText() {
        return "Sing sermons for " + amount + " people.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return people.size() >= amount;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);

        belongingTo.setCharacter(new CharacterDecorator(belongingTo.getCharacter(), "singsermoncounter") {
            @Override
            public void doAfterActions(GameData gameData) {
                super.doAfterActions(gameData);
                Action a = ((Player)getActor()).getNextAction();
                if (a instanceof SermonAction) {
                    for (Actor act : getPosition().getActors()) {
                        if (!people.contains(act.getBaseName())) {
                            people.add(act.getBaseName());
                        }
                    }
                }
            }
        });
    }
}

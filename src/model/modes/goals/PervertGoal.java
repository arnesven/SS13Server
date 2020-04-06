package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.WatchAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 05/12/16.
 */
public class PervertGoal extends PersonalGoal {

    private boolean man = false;
    private boolean woman = false;

    @Override
    public String getText() {
        return "Watch a naked woman and a naked man.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return man && woman;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new CheckNakedWatcherDecorator(belongingTo.getCharacter()));
    }

    private class CheckNakedWatcherDecorator extends CharacterDecorator {
        public CheckNakedWatcherDecorator(GameCharacter character) {
            super(character, "checknakedwatcher");
        }

        @Override
        public void doAfterActions(GameData gameData) {
            super.doAfterActions(gameData);
            if (getActor() instanceof Player) {
                Player pl = (Player)getActor();
                if (pl.getNextAction() instanceof WatchAction) {
                    Actor actorWatched = (Actor)((WatchAction) pl.getNextAction()).getTarget();

                    if (actorWatched.getCharacter().getEquipment().isNaked()) {
                        String gender = actorWatched.getCharacter().getGender();
                        man = man || gender.equals("man");
                        woman = woman || gender.equals("woman");
                    }
                }
            }
        }
    }
}

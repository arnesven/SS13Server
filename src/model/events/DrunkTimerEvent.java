package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.DrunkDecorator;
import model.characters.decorators.InstanceChecker;
import util.Logger;
import util.MyRandom;

/**
 * @author chrho686
 *         An event which counts how long an Actor should be drunk
 */
public class DrunkTimerEvent extends Event {

    private Actor target;
    private int level;
    private InstanceChecker checker;

    private Boolean firstTime = true;
    private Boolean remove = false;

    public DrunkTimerEvent(Actor target, int level, InstanceChecker checker) {
        this.target = target;
        this.level = level;
        this.checker = checker;

        updatePreviousTimer();
    }

    @Override
    public void apply(GameData gameData) {
        if (remove) {
            return;
        }

        // wait for one round before decreasing drunk level
        if (!firstTime) {
            level--;

            // 25% chance to decrease another drunk level each round
            if (MyRandom.nextDouble() < 0.50) {
                level--;
            }

        } else {
            firstTime = false;
        }

        target.addTolastTurnInfo("You feel " + getDrunkLevelName() + ".");

        if (level <= 0 || target.isDead()) {
            // character is now sober so remove drunk decorator
            target.removeInstance(checker);
            markForRemoval();
        }
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return getDrunkLevelName();
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.NO_SENSE;
    }

    @Override
    public boolean shouldBeRemoved(GameData gameData) {
        return remove;
    }

    public void markForRemoval() {
        remove = true;
    }

    /**
     * @return if the target is drunk return its decorator, else return null
     */
    private DrunkDecorator getDrunkDecorator() {
        GameCharacter ch = target.getCharacter();
        while (ch instanceof CharacterDecorator) {
            if (ch instanceof DrunkDecorator) {
                return ((DrunkDecorator) ch);
            }
            ch = (((CharacterDecorator) ch).getInner());
        }
        return null;
    }

    /**
     * If the target is drunk, remove this and update previous timer
     */
    private void updatePreviousTimer() {
        DrunkDecorator decorator = getDrunkDecorator();

        // if the character wasn't drunk before then make them drunk
        if (decorator == null) {
            target.setCharacter(new DrunkDecorator(target.getCharacter(), this));
            Logger.log(target.getBaseName() + " became drunk.");
            return;
        }

        Logger.log(target.getBaseName() + " became even drunker!");

        decorator.getTimer().addDrunkLevel(level); // update previous timer
        markForRemoval(); // remove "this"
    }

    public void addDrunkLevel(int addition) {
        firstTime = true; // do not decrease the drunk level this round
        level += addition;
    }

    private String getDrunkLevelName() {
        if (level <= 0) {
            return "sober";
        }
        switch (level) {
            case 1:
                return "buzzed";
            case 2:
                return "tipsy";
            case 3:
                return "drunk";
            case 4:
                return "hammered";
            case 5:
                return "smashed";
            case 6:
                return "blackout-drunk";
            default:
                return "out-of-control";
        }
    }

    public int getLevel() {
        return level;
    }
}

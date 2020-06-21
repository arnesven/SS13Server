package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.AttackAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.characters.visitors.ClownCharacter;
import model.items.weapons.UnarmedAttack;
import model.items.weapons.Weapon;

/**
 * Created by erini02 on 05/12/16.
 */
public class BeatUpTheClownGoal extends PersonalGoal {
    private boolean completed = false;

    @Override
    public String getText() {
        return "Beat up the clown.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return completed;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new BeatingUpClownCheckerDecorator(belongingTo.getCharacter()));
    }

    private class BeatingUpClownCheckerDecorator extends CharacterDecorator {
        public BeatingUpClownCheckerDecorator(GameCharacter character) {
            super(character, "beatingupclownchecker");
        }

        @Override
        public void doAfterActions(GameData gameData) {
            super.doAfterActions(gameData);
            if (getActor() instanceof Player) {
                Player pl = (Player)getActor();
                if (pl.getNextAction() instanceof AttackAction) {
                    AttackAction atkAction = (AttackAction) pl.getNextAction();
                    Weapon w = (Weapon)(atkAction.getItem());
                    if (atkAction.getTarget() instanceof Actor) {
                        Actor a = (Actor) (atkAction.getTarget());
                        if (w instanceof UnarmedAttack && a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ClownCharacter)) {
                            completed = true;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isApplicable(GameData gameData, Actor potential) {
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ClownCharacter)) {
                return true;
            }
        }
        return false;
    }
}

package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.characters.decorators.*;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class PolymorphSpellBook extends SpellBook {
    public PolymorphSpellBook() {
        super("Polymorph", 13);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("polymorphspellbook", "library.png", 12, 3, this);
    }


    @Override
    public GameItem clone() {
        return new PolymorphSpellBook();
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        if (target instanceof Actor) {
            String prevName = target.getName();
            Actor victim = (Actor)target;
            int type = MyRandom.nextInt(6);
            if (type == 0) {
                victim.setCharacter(new ChimpAppearanceDecorator(victim));
            } else if (type == 1) {
                victim.setCharacter(new SnakeAppearanceDecorator(victim));
            } else if (type == 2) {
                victim.setCharacter(new CatAppearanceDecorator(victim));
            } else if (type == 3) {
                victim.setCharacter(new BearAppearanceDecorator(victim));
            } else if (type == 4) {
                victim.setCharacter(new DogAppearanceDecorator(victim));
            } else if (type == 5) {
                victim.setCharacter(new MouseAppearanceDecorator(victim));
            }
            victim.setCharacter(new RemoveAnimalAppearanceDecorator(victim.getCharacter(), gameData.getRound()));
            victim.setCharacter(new PoofOfSmokeAnimationDecorator(victim.getCharacter(), gameData));
            performingClient.addTolastTurnInfo("You turned " + prevName + " into a " + target.getName().toLowerCase() + "!");
            ((Actor) target).addTolastTurnInfo("You've been turned into a " + target.getName() + "!");
        }
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastPolymorphAction(this, forWhom);
    }

    @Override
    public String getSpellName() {
        return "Polymorph";
    }

    @Override
    public String getMagicWords() {
        return "Ah-bra-ca-Zham!";
    }

    @Override
    protected String getSpellDescription() {
        return "This spell changes the target's appearance to be more animal-like. The effects last for 5 turns.";
    }

    private class RemoveAnimalAppearanceDecorator extends CharacterDecorator {
        private final int roundSet;

        public RemoveAnimalAppearanceDecorator(GameCharacter character, int round) {
            super(character, "Remove animal appearance");
            this.roundSet = round;
        }

        @Override
        public void doAfterActions(GameData gameData) {
            super.doAfterActions(gameData);
            if (gameData.getRound() > roundSet + 5) {
                if (getActor().getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AnimalAppearanceDecorator)) {
                    getActor().removeInstance((GameCharacter gc) -> gc instanceof AnimalAppearanceDecorator);
                    getActor().removeInstance((GameCharacter gc) -> gc == this);
                }
            }
        }
    }
}

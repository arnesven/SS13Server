package model.items.spellbooks;

import graphics.sprites.EmptySpriteObject;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Target;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.PoofOfSmokeAnimationDecorator;
import model.characters.general.GameCharacter;
import model.events.PoofOfSmokeAnimationEvent;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
import model.items.general.GameItem;
import model.mutations.InvisibilityDecorator;

public class InvisibilitySpellBook extends SpellBook {
    public InvisibilitySpellBook() {
        super("Invisibility", 11);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("invisspellbook", "wizardstuff.png", 9, this);
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        performingClient.setCharacter(new InvisibilityDecorator(performingClient));
        performingClient.getPosition().addEvent(new PoofOfSmokeAnimationEvent(gameData, performingClient.getPosition()));
        performingClient.setCharacter(new RemoveInvisibilityDecorator(performingClient, gameData.getRound(), 5));
        performingClient.addTolastTurnInfo("You turned invisible");
    }

    @Override
    public String getSpellName() {
        return "Invisibility";
    }

    @Override
    public String getMagicWords() {
        return "Cloak, come shroud me!";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastInvisibilityAction(this, forWhom);
    }

    @Override
    protected String getSpellDescription() {
        return "Turns the caster invisible for five turns.";
    }

    @Override
    public GameItem clone() {
        return new InvisibilitySpellBook();
    }

    private class CastInvisibilityAction extends CastSpellAction {
        private final Actor forWhom;

        public CastInvisibilityAction(InvisibilitySpellBook invisibilitySpellBook, Actor forWhom) {
            super(invisibilitySpellBook, forWhom);
            this.forWhom = forWhom;
            addTarget(forWhom.getAsTarget());
        }

        @Override
        protected boolean canBeTargetedBySpell(Target target2) {
            return target2 == forWhom;
        }
    }

    @Override
    public boolean isTargetingSpell() {
        return false;
    }

    private class RemoveInvisibilityDecorator extends CharacterDecorator {
        private final int roundSet;
        private final int duration;

        public RemoveInvisibilityDecorator(Actor performingClient, int roundSet, int duration) {
            super(performingClient.getCharacter(), "Remove Invis");
            this.roundSet = roundSet;
            this.duration = duration;
        }

        @Override
        public void doAfterActions(GameData gameData) {
            super.doAfterActions(gameData);
            if (gameData.getRound() > roundSet + duration) {
                getActor().removeInstance((GameCharacter gc) -> gc instanceof InvisibilityDecorator);
                getActor().removeInstance((GameCharacter gc) -> gc == this);
                getActor().setCharacter(new PoofOfSmokeAnimationDecorator(getActor().getCharacter(), gameData));
            }
        }
    }
}

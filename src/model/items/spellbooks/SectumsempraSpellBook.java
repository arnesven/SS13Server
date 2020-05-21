package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.characters.decorators.BleedingDecorator;
import model.characters.decorators.BloodSplotchAnimationDecorator;
import model.characters.general.AnimalCharacter;
import model.characters.general.GameCharacter;
import model.events.animation.AnimationEvent;
import model.items.general.GameItem;

public class SectumsempraSpellBook extends SpellBook {
    public SectumsempraSpellBook() {
        super("Sectumsempra", 15);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("sectumsempraspellbook", "wizardstuff.png", 3, this);
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        if (target instanceof Actor) {
            ((Actor) target).setCharacter(new BleedingDecorator(((Actor) target).getCharacter(), performingClient));
            ((Actor) target).setCharacter(new BloodSplotchAnimationDecorator(((Actor) target).getCharacter(), gameData));
            performingClient.addTolastTurnInfo("You lacerated " + target.getName() + "!");

        }
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastSpellAction(this, forWhom) {
            @Override
            protected boolean canBeTargetedBySpell(Target target2) {
                return !target2.isDead() && target2 instanceof Actor && (((Actor) target2).isHuman() ||
                        ((Actor) target2).getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AnimalCharacter));
            }
        };
    }

    @Override
    public String getSpellName() {
        return "Sectumsempra";
    }

    @Override
    public String getMagicWords() {
        return "Sectumsempra!";
    }

    @Override
    protected String getSpellDescription() {
        return "This spell lacerates the target.";
    }

    @Override
    public GameItem clone() {
        return new SectumsempraSpellBook();
    }
}

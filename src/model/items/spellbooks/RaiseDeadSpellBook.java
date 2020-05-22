package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.characters.decorators.ZombieDecorator;
import model.characters.general.GameCharacter;
import model.events.ZombifierEvent;
import model.events.damage.ChaosDamage;
import model.items.general.GameItem;

import java.util.HashSet;

public class RaiseDeadSpellBook extends SpellBook {
    public RaiseDeadSpellBook() {
        super("Raise Dead", 9);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("raisedeadspellbook", "wizardstuff.png", 6, this);
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        if (!target.isDead()) {
            target.beExposedTo(performingClient, new ChaosDamage(0.5), gameData);
        }

        if (target instanceof Actor && target.isDead()) {
            Actor victim = (Actor)target;
            if ((victim.isHuman() || victim.isAnimal()) && !isZombie(victim)) {
                ZombifierEvent.turnIntoZombie(victim, gameData, new HashSet<>());
            }
        }
    }

    private boolean isZombie(Actor victim) {
        return victim.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ZombieDecorator);
    }

    @Override
    public String getSpellName() {
        return "Raise Dead";
    }

    @Override
    public String getMagicWords() {
        return "Muharrem-Demirok!";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastSpellAction(this, forWhom) {
            @Override
            protected boolean canBeTargetedBySpell(Target target2) {
                return target2 instanceof Actor;
            }
        };
    }

    @Override
    protected String getSpellDescription() {
        return "Deals 0.5 chaos damage to living targets. Dead targets are instantly resurrected as zombies!";
    }

    @Override
    public GameItem clone() {
        return new RaiseDeadSpellBook();
    }
}

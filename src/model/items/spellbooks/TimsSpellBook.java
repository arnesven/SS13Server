package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.items.MakeShiftBomb;
import model.items.general.BombItem;
import model.items.general.GameItem;

public class TimsSpellBook extends SpellBook {
    public TimsSpellBook() {
        super("Tim's", 35);
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("timsspellbook", "wizardstuff.png", 4, 2, this);
    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        boolean foundDead = false;
        for (Actor a  : performingClient.getPosition().getActors()) {
            if (a.isDead() && (a.isHuman() || a.isAnimal())) {
                a.getCharacter().giveItem(new MagicBomb(gameData, performingClient), performingClient.getAsTarget());
                foundDead = true;
            }
        }
        if (foundDead) {
            performingClient.addTolastTurnInfo("You explode all the corpses!");
        }
        BombItem castersBomb = new MagicBomb(gameData, performingClient);
        performingClient.getCharacter().giveItem(castersBomb, performingClient.getAsTarget());
        castersBomb.explode(gameData, performingClient);
    }

    @Override
    public boolean isTargetingSpell() {
        return false;
    }

    @Override
    public String getSpellName() {
        return "Tim's Spell";
    }

    @Override
    public String getMagicWords() {
        return "BOOOOOOOM!";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastTimsSpellAction(this, forWhom);
    }

    @Override
    protected String getSpellDescription() {
        return "A spell which makes the caster, and all other corpses in the caster's current position explode!";
    }

    @Override
    public GameItem clone() {
        return new TimsSpellBook();
    }

    private class CastTimsSpellAction extends CastSpellAction {
        private final Actor forWhom;

        public CastTimsSpellAction(TimsSpellBook timsSpellBook, Actor forWhom) {
            super(timsSpellBook, forWhom);
            this.forWhom = forWhom;
            addTarget(forWhom.getAsTarget());
        }

        @Override
        protected boolean canBeTargetedBySpell(Target target2) {
            return target2 == forWhom;
        }
    }
}

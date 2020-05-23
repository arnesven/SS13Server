package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.items.general.GameItem;

public class RegenerationSpellBook extends SpellBook {
    public RegenerationSpellBook() {
        super("Regeneration", 8);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("regenspellbook", "wizardstuff.png", 1, 2, this);
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public boolean isTargetingSpell() {
        return false;
    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        performingClient.setCharacter(new HealthRegenerationDecorator(performingClient, gameData.getRound(), 10, 0.25));
    }

    @Override
    public String getSpellName() {
        return "Regeneration";
    }

    @Override
    public String getMagicWords() {
        return "Vulnera Senantur...";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastRegenerationAction(forWhom);
    }

    @Override
    protected String getSpellDescription() {
        return "Gives the caster a weak health regeneration";
    }

    @Override
    public GameItem clone() {
        return new RegenerationSpellBook();
    }

    private class CastRegenerationAction extends CastSpellAction {
        private final Actor forWhom;

        public CastRegenerationAction(Actor forWhom) {
            super(RegenerationSpellBook.this, forWhom);
            this.forWhom = forWhom;
            addTarget(forWhom.getAsTarget());
        }

        @Override
        protected boolean canBeTargetedBySpell(Target target2) {
            return target2 == forWhom;
        }
    }
}

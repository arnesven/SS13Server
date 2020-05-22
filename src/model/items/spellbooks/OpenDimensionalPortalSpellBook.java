package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.ActionOption;
import model.events.AlienDimensionEvent;
import model.items.general.GameItem;

public class OpenDimensionalPortalSpellBook extends SpellBook {
    public OpenDimensionalPortalSpellBook() {
        super("Open Dimension Portal", 15);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("opendimenportalspellbook", "wizardstuff.png", 7, this);
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {
        gameData.addEvent(new AlienDimensionEvent(true, performingClient.getPosition()));
        performingClient.addTolastTurnInfo("You opened a gateway to another dimension!");
    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
    }

    @Override
    public String getSpellName() {
        return "Open Dimension Portal";
    }

    @Override
    public String getMagicWords() {
        return "Whoooa Hiii Gorgo!";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastOpenDimensionalPortal(this, forWhom);
    }

    @Override
    protected String getSpellDescription() {
        return "Opens a portal to an other dimension in your current location.";
    }

    @Override
    public GameItem clone() {
        return new OpenDimensionalPortalSpellBook();
    }

    private class CastOpenDimensionalPortal extends CastSpellAction {
        private final Actor forWhom;

        public CastOpenDimensionalPortal(SpellBook spellBook, Actor forWhom) {
            super(spellBook, forWhom);
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
}

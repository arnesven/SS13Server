package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.items.general.GameItem;
import model.items.suits.SuitItem;

import java.util.List;

public class ClothesSwapSpellBook extends SpellBook {
    public ClothesSwapSpellBook() {
        super("Clothes Swap", 7);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("clotheswapspellbook", "wizardstuff.png", 4, this);
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        if (target instanceof Actor) {
            Actor victim = (Actor)target;
            List<SuitItem> victimsSuits = victim.getCharacter().getEquipment().getSuitsAsList();
            List<SuitItem> castersSuits = performingClient.getCharacter().getEquipment().getSuitsAsList();
            undressAll(victim);
            undressAll(performingClient);
            for (SuitItem sit : victimsSuits) {
                performingClient.getCharacter().putOnEquipment(sit);
            }
            for (SuitItem sit : castersSuits) {
                victim.getCharacter().putOnEquipment(sit);
            }
        }
    }

    private void undressAll(Actor victim) {
        victim.getCharacter().getEquipment().removeEverything();
    }

    @Override
    public String getSpellName() {
        return "Clothes Swap";
    }

    @Override
    public String getMagicWords() {
        return "Booga-di booga-di Boo!";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastSpellAction(this, forWhom) {
            @Override
            protected boolean canBeTargetedBySpell(Target target2) {
                return target2 instanceof Actor && target2.hasInventory();
            }
        };
    }

    @Override
    protected String getSpellDescription() {
        return "A spell which swaps your clothes with the victim's";
    }

    @Override
    public GameItem clone() {
        return new ClothesSwapSpellBook();
    }
}

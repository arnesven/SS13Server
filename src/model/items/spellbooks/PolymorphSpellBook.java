package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.characters.decorators.ChimpAppearanceDecorator;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class PolymorphSpellBook extends SpellBook {
    public PolymorphSpellBook() {
        super("Polymorph", 13);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spellbook", "library.png", 12, 3, this);
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
            Actor victim = (Actor)target;
            victim.setCharacter(new ChimpAppearanceDecorator(victim));
            performingClient.addTolastTurnInfo("You turned " + target.getName() + " into an animal!");
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
        return "This spell changes the target's appearance to be more animal-like.";
    }

}

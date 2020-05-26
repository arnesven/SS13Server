package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.events.damage.FireDamage;
import model.items.MakeShiftBomb;
import model.items.general.BombItem;
import model.items.general.GameItem;
import model.npcs.NPC;
import model.objects.general.BreakableObject;

public class DetonateSpellBook extends SpellBook {
    public DetonateSpellBook() {
        super("Detonate", 15);
    }


    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("detonatespellbook", "wizardstuff.png", 8, this);
    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        if (target instanceof BreakableObject) {
            BreakableObject brob = (BreakableObject)target;
            brob.beExposedTo(performingClient, new FireDamage(), gameData);
            brob.beExposedTo(performingClient, new FireDamage(), gameData);
            BombItem bomb = new MagicBomb(gameData, performingClient);
            brob.getPosition().addItem(bomb);
            bomb.explode(gameData, performingClient);
            brob.getPosition().removeObject(brob);
        } else if (target instanceof Actor) {
            target.beExposedTo(performingClient, new FireDamage(), gameData);
            BombItem bomb = new MagicBomb(gameData, performingClient);
            ((Actor)target).getCharacter().giveItem(bomb, performingClient.getAsTarget());
            bomb.explode(gameData, performingClient);
        }
        performingClient.addTolastTurnInfo("You detonated the " + target.getName());
    }

    @Override
    public String getSpellName() {
        return "Detonate";
    }

    @Override
    public String getMagicWords() {
        return "Detonate!";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastSpellAction(this, forWhom) {
            @Override
            protected boolean canBeTargetedBySpell(Target target2) {
                return target2 instanceof BreakableObject || (target2 instanceof Actor && ((Actor) target2).isRobot());
            }
        };
    }

    @Override
    protected String getSpellDescription() {
        return "Makes target object explode in fiery conflagration damaging everything nearby.";
    }

    @Override
    public GameItem clone() {
        return new DetonateSpellBook();
    }
}

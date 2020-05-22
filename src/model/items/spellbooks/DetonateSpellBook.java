package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.events.damage.FireDamage;
import model.items.MakeShiftBomb;
import model.items.general.BombItem;
import model.items.general.GameItem;
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
          //  CharacterDecorator cd = new ExplosiveProtection(performingClient.getCharacter());
          //  performingClient.setCharacter(cd);
            BreakableObject brob = (BreakableObject)target;
            brob.beExposedTo(performingClient, new FireDamage(), gameData);
            brob.beExposedTo(performingClient, new FireDamage(), gameData);
            BombItem bomb = new MakeShiftBomb(gameData, performingClient);
            brob.getPosition().addItem(bomb);
            bomb.explode(gameData, performingClient);
            brob.getPosition().removeObject(brob);
            performingClient.addTolastTurnInfo("You detonated the " + target.getName());
         //   performingClient.removeInstance((GameCharacter gc) -> gc == cd);
        }
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
                return target2 instanceof BreakableObject;
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

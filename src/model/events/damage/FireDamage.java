package model.events.damage;

import model.Actor;
import model.Target;
import model.characters.decorators.ChilledDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 17/04/16.
 */
public class FireDamage extends DamagerImpl {

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "fire";
    }

    @Override
    public String getText() {
        return "The fire burned you!";
    }

    @Override
    public double getDamage() {
        return 0.5;
    }

    @Override
    public void doDamageOnMe(Target target) {
        if (target instanceof Actor) {
            Actor burned = (Actor)target;
            if (burned.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof ChilledDecorator)) {
                burned.removeInstance((GameCharacter gc) -> gc instanceof ChilledDecorator);
            }
        }
        super.doDamageOnMe(target);
    }
}

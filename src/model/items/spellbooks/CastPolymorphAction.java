package model.items.spellbooks;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.characters.decorators.ChimpAppearanceDecorator;
import model.items.general.GameItem;

import java.util.List;

public class CastPolymorphAction extends CastSpellAction {
    public CastPolymorphAction(PolymorphSpellBook polymorphSpellBook, Actor caster) {
        super(polymorphSpellBook, caster);
    }


    @Override
    protected boolean canBeTargetedBySpell(Target target2) {
        return !target2.isDead() && target2 instanceof Actor && ((Actor) target2).isHuman();
    }
}

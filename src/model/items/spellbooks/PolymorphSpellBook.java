package model.items.spellbooks;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.characters.decorators.ChimpAppearanceDecorator;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class PolymorphSpellBook extends SpellBook {
    public PolymorphSpellBook() {
        super("Polymorph Spell Book", 13);
    }

    @Override
    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        List<Action> list = new ArrayList<>();
        CastSpellAction cast = new CastPolymorphAction(this, forWhom);
        if (cast.isOkToCast(forWhom, gameData)) {
            list.add(cast);
        }

        return list;
    }

    @Override
    public GameItem clone() {
        return new PolymorphSpellBook();
    }

    @Override
    protected void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    protected void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        if (target instanceof Actor) {
            Actor victim = (Actor)target;
            victim.setCharacter(new ChimpAppearanceDecorator(victim));
        }
    }

    @Override
    public String getSpellName() {
        return "Polymorph";
    }

    @Override
    public String getMagicWords() {
        return "Ah-bra-ca-Zham!";
    }
}

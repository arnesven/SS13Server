package model.items.spellbooks;

import model.items.general.GameItem;

public class PolymorphSpellBook extends SpellBook {
    public PolymorphSpellBook() {
        super("Polymorph Spell Book");
    }

    @Override
    public GameItem clone() {
        return new PolymorphSpellBook();
    }
}

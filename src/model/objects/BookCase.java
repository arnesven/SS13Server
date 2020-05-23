package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.items.spellbooks.*;
import model.map.rooms.WizardDinghyRoom;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;

public class BookCase extends ContainerObject {
    public BookCase(WizardDinghyRoom wizardDinghyRoom) {
        super("Book Case", wizardDinghyRoom);
        for (SpellBook sp : SpellBook.getAllSpellBooks()) {
            getInventory().add(sp.clone());
        }

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("bookcase", "library.png", 1, 2, this);
    }
}

package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.items.spellbooks.PolymorphSpellBook;
import model.items.spellbooks.SectumsempraSpellBook;
import model.map.rooms.WizardDinghyRoom;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;

public class BookCase extends ContainerObject {
    public BookCase(WizardDinghyRoom wizardDinghyRoom) {
        super("Book Case", wizardDinghyRoom);
        getInventory().add(new PolymorphSpellBook());
        getInventory().add(new SectumsempraSpellBook());
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("bookcase", "library.png", 1, 2, this);
    }
}

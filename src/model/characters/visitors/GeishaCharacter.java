package model.characters.visitors;

import model.characters.general.GameCharacter;
import model.items.suits.Kimono;

/**
 * Created by erini02 on 17/10/16.
 */
public class GeishaCharacter extends VisitorCharacter {
    public GeishaCharacter() {
        super("Geisha");
        getEquipment().removeEverything();
        putOnEquipment(new Kimono(this));
    }

    @Override
    public GameCharacter clone() {
        return new GeishaCharacter();
    }
}

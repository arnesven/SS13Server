package model.characters.visitors;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.LawyerSuit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public class LawyerCharacter extends VisitorCharacter {
    public LawyerCharacter() {
        super("Lawyer");
        getEquipment().removeEverything();
        new LawyerSuit(this).putYourselfOn(getEquipment());
    }

    @Override
    public int getStartingMoney() {
        return 600;
    }

    @Override
    public GameCharacter clone() {
        return new LawyerCharacter();
    }
}

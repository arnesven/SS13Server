package model.characters.decorators;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;

import java.util.List;

/**
 * Created by erini02 on 29/04/16.
 */
public class FrozenDecorator extends CharacterDecorator {
    public FrozenDecorator(GameCharacter character) {
        super(character, "Frozen");
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public boolean getsActions() {
        return false;
    }
}

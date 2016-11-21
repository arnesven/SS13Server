package model.characters.decorators;

import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 21/11/16.
 */
public class GetsNoActionsDecorator extends CharacterDecorator{

    public GetsNoActionsDecorator(GameCharacter chara, String name) {
        super(chara, name);
    }

    @Override
    public boolean getsActions() {
        return false;
    }
}

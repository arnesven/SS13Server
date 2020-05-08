package model.characters.decorators;

import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 11/11/16.
 */
public class NameChangeDecorator extends CharacterDecorator {

    private final String changeTo;

    public NameChangeDecorator(GameCharacter chara, String changeTo) {
        super(chara, "Name Change");
        this.changeTo = changeTo;
    }

    @Override
    public String getPublicName() {
        String str = changeTo;
        return str;
    }
}

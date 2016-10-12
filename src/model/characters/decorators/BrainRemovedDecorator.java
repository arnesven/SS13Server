package model.characters.decorators;

import model.characters.general.GameCharacter;
import model.items.general.GameItem;

import java.util.List;

/**
 * Created by erini02 on 12/10/16.
 */
public class BrainRemovedDecorator extends CharacterDecorator {

    public BrainRemovedDecorator(GameCharacter chara) {
        super(chara, "Brain removed");
    }
}

package model.items.suits;

import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 01/05/16.
 */
public class CaptainsOutfit extends OutFit {

    public CaptainsOutfit(GameCharacter chara) {
        super(chara);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }
}

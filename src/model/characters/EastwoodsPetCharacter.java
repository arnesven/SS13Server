package model.characters;

import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.general.ParasiteCharacter;
import model.characters.general.PirateCaptainCharacter;
import model.items.weapons.HugeClaws;
import model.items.weapons.Weapon;
import util.Logger;

public class EastwoodsPetCharacter extends ParasiteCharacter {

    @Override
    public Weapon getDefaultWeapon() {
        return new HugeClaws();
    }


}

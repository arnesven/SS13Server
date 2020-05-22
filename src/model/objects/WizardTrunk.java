package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.items.spellbooks.MagickaPotion;
import model.items.weapons.FireStaff;
import model.items.weapons.IceStaff;
import model.items.weapons.LightningWand;
import model.items.weapons.QuickCastWand;
import model.map.rooms.WizardDinghyRoom;
import model.objects.general.ContainerObject;

public class WizardTrunk extends ContainerObject {
    public WizardTrunk(WizardDinghyRoom wizardDinghyRoom) {
        super("Old Trunk", wizardDinghyRoom);
        getInventory().add(new FireStaff());
        getInventory().add(new IceStaff());
        getInventory().add(new QuickCastWand());
        getInventory().add(new LightningWand());
        getInventory().add(new MagickaPotion());
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return  new Sprite("wizstaffcloset", "weapons2.png", 43, 45, this);
    }
}

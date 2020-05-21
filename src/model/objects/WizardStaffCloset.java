package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.items.weapons.FireStaff;
import model.items.weapons.IceStaff;
import model.items.weapons.QuickCastWand;
import model.map.rooms.WizardDinghyRoom;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;

public class WizardStaffCloset extends ContainerObject {
    public WizardStaffCloset(WizardDinghyRoom wizardDinghyRoom) {
        super("Staff Closet", wizardDinghyRoom);
        getInventory().add(new FireStaff());
        getInventory().add(new IceStaff());
        getInventory().add(new QuickCastWand());
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return  new Sprite("wizstaffcloset", "weapons2.png", 43, 45, this);
    }
}

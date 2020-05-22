package model.objects.general;

import graphics.sprites.Sprite;
import model.Player;
import model.items.FlashLight;
import model.items.HandCuffs;
import model.items.suits.SecOffsHelmet;
import model.items.weapons.Baton;
import model.items.weapons.FlashBang;
import model.map.rooms.SecurityStationRoom;

public class SecurityStorage extends ContainerObject {
    public SecurityStorage(SecurityStationRoom securityStationRoom) {
        super("Security Storage", securityStationRoom);
        this.getInventory().add(new Baton());
        this.getInventory().add(new SecOffsHelmet());
        this.getInventory().add(new HandCuffs());
        this.getInventory().add(new FlashBang());
        this.getInventory().add(new FlashLight());
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("securitystorage", "closet.png", 8, this);
    }
}

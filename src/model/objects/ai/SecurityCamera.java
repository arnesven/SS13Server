package model.objects.ai;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.events.damage.Damager;
import model.events.damage.FireDamage;
import model.events.damage.RadiationDamage;
import model.items.weapons.Weapon;
import model.map.rooms.StationRoom;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;

import java.util.ArrayList;

public class SecurityCamera extends ElectricalMachinery {
    public SecurityCamera(StationRoom stationRoom) {
        super("Security Camera", stationRoom);
        setPowerPriority(3);
        this.setMaxHealth(1.0);
        this.setHealth(1.0);
        if (stationRoom != null) {
            this.setAbsolutePosition(stationRoom.getX(), stationRoom.getY());
        }
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }

    @Override
    public boolean canBeOvercharged() {
        return false;
    }

    @Override
    public void beExposedTo(Actor performingClient, Damager damage, GameData gameData) {
       // Do not take damage from effects
    }

    @Override
    public double getPowerConsumption() {
        return 0.000150; // 150 W
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken() || !isPowered()) {
            return new Sprite("securitycameraoffline", "seccamera.png", 0, 1, this);
        }
        return new Sprite("securitycamera", "seccamera.png", 0, 0, this);
    }


}

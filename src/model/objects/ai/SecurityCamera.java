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
        setPowerPriority(2);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }

    @Override
    public void beExposedTo(Actor performingClient, Damager damage, GameData gameData) {
       // Do not take damage from effects
    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken() || !isPowered()) {
            return new Sprite("securitycameraoffline", "monitors.png", 68, this);
        }
        return new Sprite("securitycamera", "monitors.png", 4, this);
    }


}

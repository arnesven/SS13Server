package model.map.doors;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.events.damage.Damager;
import model.events.damage.FireDamage;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.objects.general.ElectricalMachinery;
import util.Logger;

import java.util.ArrayList;

public class DoorMechanism extends ElectricalMachinery {

    private ElectricalDoor electricalDoor;
    private boolean ffVacant;

    public DoorMechanism(ElectricalDoor electricalDoor) {
        super(electricalDoor.getName(), null);
        this.electricalDoor = electricalDoor;
        setPowerPriority(1);
        ffVacant = true;
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }

    @Override
    public void thisJustBroke(GameData gameData) {
        electricalDoor.thisJustBroke(gameData);
    }

    @Override
    public boolean canBeInteractedBy(Actor performingClient) {
        return performingClient.getPosition().getID() == electricalDoor.getFromId() || performingClient.getPosition().getID() == electricalDoor.getToId();
    }

    @Override
    public void beExposedTo(Actor performingClient, Damager damage, GameData gameData) {
        if (damage instanceof FireDamage) {
            damage = new FireDamage(0.25);
        }
        super.beExposedTo(performingClient, damage, gameData);
    }

    @Override
    public void doWhenRepaired(GameData gameData) {

    }


    @Override
    public void onPowerOff(GameData gameData) {
        try {
            electricalDoor.goUnpowered(gameData.getRoomForId(electricalDoor.getFromId()), gameData.getRoomForId(electricalDoor.getToId()));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    public boolean isFancyFrameVacant() {
        return ffVacant;

    }

    public void setFancyFrameOccupied() {
        ffVacant = false;
    }

    public void setFancyFrameVacant() {
        ffVacant = true;
    }
}

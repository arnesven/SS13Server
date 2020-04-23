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

class DoorMechanism extends ElectricalMachinery {

    private ElectricalDoor electricalDoor;

    public DoorMechanism(ElectricalDoor electricalDoor) {
        super(electricalDoor.getName(), null);
        this.electricalDoor = electricalDoor;
        setPowerPriority(1);
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
        //try {
            //unlockRooms(gameData.getRoomForId(getFromId()), gameData.getRoomForId(getToId()));
            //gameData.findObjectOfType(AIConsole.class).informOnStation("Attention, " + getName() + " unlocked because of power failure!", gameData);
        //} catch (NoSuchThingException e) {
        //    e.printStackTrace();
        //}

        //Logger.log(Logger.INTERESTING, " room unlocked because of power failure!");
    }
}

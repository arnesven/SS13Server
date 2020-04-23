package model.map.doors;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.HackDoorToBrokenAction;
import model.actions.objectactions.HackDoorToUnpoweredAction;
import model.events.damage.Damager;
import model.events.damage.ElectricalDamage;
import model.events.damage.FireDamage;
import model.items.NoSuchThingException;
import model.objects.general.ElectricalMachinery;
import util.HTMLText;
import util.MyRandom;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DoorMechanism extends ElectricalMachinery {

    private final List<PowerCord> listOfCords;
    private ElectricalDoor electricalDoor;
    private boolean ffVacant;
    private static Map<String, Color> colorMap = MyRandom.makeRandomPowerCordColorMap();

    private PowerCord linePower = new PowerLinePowerCord(colorMap.get("power"), 1);
    private PowerCord lineBackupPower = new PowerLinePowerCord(colorMap.get("backup"), 1);
    private PowerCord lineGround = new GroundPowerCord(colorMap.get("ground"), 0);
    private PowerCord lineOpen = new OpenPowerCord(colorMap.get("open"), 0);
    private PowerCord lineLock = new LockPowerCord(colorMap.get("lock"), 0);
    private PowerCord lineFire = new FireDoorPowerCord(colorMap.get("fire"), 0);

    public DoorMechanism(ElectricalDoor electricalDoor) {
        super(electricalDoor.getName(), null);
        this.electricalDoor = electricalDoor;
        setPowerPriority(1);
        ffVacant = true;
        this.listOfCords = new ArrayList<PowerCord>();
        listOfCords.add(linePower);
        listOfCords.add(lineBackupPower);
        listOfCords.add(lineGround);
        listOfCords.add(lineOpen);
        listOfCords.add(lineLock);
        listOfCords.add(lineFire);
        //Collections.shuffle(listOfCords);
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
        linePower.repair(this);
        lineBackupPower.repair(this);
        lineGround.repair(this);
        lineOpen.repair(this);
        lineLock.repair(this);
        lineFire.repair(this);
    }


    @Override
    public void onPowerOff(GameData gameData) {
        try {
            electricalDoor.goUnpowered(gameData.getRoomForId(electricalDoor.getFromId()), gameData.getRoomForId(electricalDoor.getToId()));
            linePower.setState(0);
            lineBackupPower.setState(0);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPowerOn(GameData gameData) {
        try {
            electricalDoor.goPowered(gameData.getRoomForId(electricalDoor.getFromId()), gameData.getRoomForId(electricalDoor.getToId()));
            linePower.setState(1);
            lineBackupPower.setState(1);
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

    public boolean hasError() {
        return lineFire.getState() == -1 || lineOpen.getState() == -1 || lineLock.getState() == -1;
    }

    public List<PowerCord> getPowerCords() {
        return listOfCords;
    }

    public Action cutCord(int index, Player player, GameData gameData) {
        listOfCords.get(index).setState(-1);
        return listOfCords.get(index).cut(player, gameData);
    }

    public PowerCord getLockCord() {
        return lineLock;
    }

    public PowerCord getFireCord() {
        return lineFire;
    }

    public PowerCord getOpenCord() {
        return lineOpen;
    }

    private class PowerLinePowerCord extends PowerCord {
        public PowerLinePowerCord(Color color, int i) {
            super(color, i, DoorMechanism.this);
        }

        @Override
        protected Action specificCutAction(Player player, GameData gameData) {
            player.beExposedTo(null, new ElectricalDamage(0.5), gameData);
            gameData.getChat().serverInSay(HTMLText.makeText("red", "You got a shock from a loose wire!"), player);
            if (linePower.isCut() && lineBackupPower.isCut() && !isBroken()) {
                return new HackDoorToUnpoweredAction(electricalDoor);
            }
            return null;
        }

        @Override
        public void repair(DoorMechanism doorMechanism) {
            setCut(false);
            if (isPowered()) {
                setState(1);
            } else {
                setState(0);
            }
        }

    }

    private class GroundPowerCord extends PowerCord {
        public GroundPowerCord(Color color, int i) {
            super(color, i, DoorMechanism.this);
        }

        @Override
        protected Action specificCutAction(Player player, GameData gameData) {
            lineOpen.setState(-1);
            if (lineLock.getState() == 0) {
                lineLock.setState(-1);
            }
            lineFire.setState(-1);
            return null;
        }

        @Override
        public void repair(DoorMechanism doorMechanism) {
            setCut(false);
            setState(0);
        }
    }

    private class OpenPowerCord extends PowerCord {
        public OpenPowerCord(Color color, int i) {
            super(color, i, DoorMechanism.this);
        }

        @Override
        protected Action specificCutAction(Player player, GameData gameData) {
            return new HackDoorToBrokenAction(electricalDoor);
        }
    }

    private class LockPowerCord extends PowerCord {
        public LockPowerCord(Color color, int i) {
            super(color, i, DoorMechanism.this);
        }

        @Override
        protected Action specificCutAction(Player player, GameData gameData) {
            return null;
        }


    }

    private class FireDoorPowerCord extends PowerCord {
        public FireDoorPowerCord(Color fire, int i) {
            super(fire, i, DoorMechanism.this);
        }

        @Override
        protected Action specificCutAction(Player player, GameData gameData) {
            return null;
        }
    }
}

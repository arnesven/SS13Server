package model.map.doors;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.*;
import model.actions.roomactions.LockDoorAction;
import model.events.damage.Damager;
import model.events.damage.ElectricalDamage;
import model.events.damage.FireDamage;
import model.items.NoSuchThingException;
import model.objects.general.ElectricalMachinery;
import util.HTMLText;
import util.Logger;
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
        setHealth(2.5);
        setMaxHealth(2.5);
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

    public boolean isFancyFrameVacant() { return ffVacant; }

    public void setFancyFrameOccupied() {
        ffVacant = false;
    }

    public void setFancyFrameVacant() {
        ffVacant = true;
    }

    public void setDoor(ElectricalDoor electricalDoor) {
        this.electricalDoor = electricalDoor;
    }

    public boolean hasError() {
        return lineFire.getState() == -1 || lineOpen.getState() == -1 || lineLock.getState() == -1;
    }

    public List<PowerCord> getPowerCords() {
        return listOfCords;
    }

    public PowerCord getPowerLine() {
        return linePower;
    }

    public PowerCord getBackupLine() {
        return lineBackupPower;
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

    public boolean permitsUnlock() {
        return getLockCord().isOK() && getOpenCord().isOK();
    }

    public boolean permitsLock() {
        return getLockCord().isOK();
    }


    public boolean powerSupplyOK() {
        return linePower.isOK() || lineBackupPower.isOK();
    }


    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }

    @Override
    public void thisJustBroke(GameData gameData) {
        electricalDoor.breakDoor(gameData);
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
        electricalDoor.gotRepaired(gameData);
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
            if (electricalDoor.getDoorState() instanceof DoorState.Unpowered) {
                electricalDoor.goPowered(gameData.getRoomForId(electricalDoor.getFromId()),
                        gameData.getRoomForId(electricalDoor.getToId()));
            }
            linePower.setState(1);
            lineBackupPower.setState(1);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }


    public Action cutCord(int index, Player player, GameData gameData) {
        return listOfCords.get(index).cut(player, gameData);
    }


    public Action mendCord(int index, Player player, GameData gameData) {
        return listOfCords.get(index).mend(player, gameData);
    }

    public Action pulseCord(int index, Player player, GameData gameData) {
        return listOfCords.get(index).pulse(player, gameData);
    }


    private class PowerLinePowerCord extends PowerCord {
        public PowerLinePowerCord(Color color, int i) {
            super(color, i, DoorMechanism.this);
        }

        @Override
        protected Action specificCutAction(Player player, GameData gameData) {
            if (!powerSupplyOK() && !isBroken() && !(electricalDoor.getDoorState() instanceof DoorState.Unpowered)) {
                return new HackDoorToUnpoweredAction(electricalDoor);
            }
            return null;
        }

        @Override
        protected Action specificMendAction(Player player, GameData gameData) {
            Logger.log("Mending power coord. Power supply is " + powerSupplyOK() + " broken is" + isBroken());
            Logger.log("Instance is " + (electricalDoor.getDoorState() instanceof DoorState.Unpowered));
            if (powerSupplyOK() && !isBroken() && (electricalDoor.getDoorState() instanceof DoorState.Unpowered)) {
                Logger.log("Setting hack door to powered action as next action");
                return new HackDoorToPoweredAction(electricalDoor);
            }
            return null;
        }

        @Override
        protected Action specificPulseAction(Player player, GameData gameData) {
            gameData.getChat().serverInSay("Nothing happens.", player);
            return null;
        }

        @Override
        public void repair(DoorMechanism doorMechanism) {
            Logger.log("Repairing PowerLinePowerCord, power is " + isPowered());
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
        protected Action specificMendAction(Player player, GameData gameData) {
            lineOpen.setState(0);
            if (electricalDoor.getDoorState() instanceof DoorState.Locked) {
                lineLock.setState(1);
            } else {
                lineLock.setState(0);
            }
            lineFire.setState(1);
            return null;
        }

        @Override
        protected Action specificPulseAction(Player player, GameData gameData) {
            gameData.getChat().serverInSay("Nothing happens.", player);
            return null;
        }
    }

    private class OpenPowerCord extends PowerCord {
        public OpenPowerCord(Color color, int i) {
            super(color, i, DoorMechanism.this);
        }

        @Override
        protected Action specificCutAction(Player player, GameData gameData) {
            if (!(electricalDoor.getDoorState() instanceof DoorState.Locked) && !isBroken()) {
                return new HackDoorToBrokenAction(electricalDoor);
            }
            return null;
        }

        @Override
        protected Action specificMendAction(Player player, GameData gameData) {
            return null;
        }

        @Override
        protected Action specificPulseAction(Player player, GameData gameData) {
            if (electricalDoor.getDoorState() instanceof DoorState.Normal) {
                gameData.getChat().serverInSay("The door opens and closes again.", player);
            } else if (electricalDoor.getDoorState() instanceof DoorState.Locked) {
                if (lineLock.getState() != 1 && lineOpen.isOK() && (electricalDoor.getDoorState() instanceof DoorState.Locked)) {
                    return new HackDoorUnlockAction(electricalDoor);
                } else {
                    gameData.getChat().serverInSay("Nothing happens.", player);
                }
            } else {
                gameData.getChat().serverInSay("Nothing happens.", player);
            }
            return null;
        }
    }

    private class LockPowerCord extends PowerCord {
        public LockPowerCord(Color color, int i) {
            super(color, i, DoorMechanism.this);
        }

        @Override
        protected Action specificCutAction(Player player, GameData gameData) { return null; }

        @Override
        protected Action specificMendAction(Player player, GameData gameData) { return null; }

        @Override
        protected Action specificPulseAction(Player player, GameData gameData) {
            if (electricalDoor.getDoorState() instanceof DoorState.Normal) {
                if (permitsLock()) {
                    return new HackDoorToLockAction(electricalDoor);
                }
            }
            gameData.getChat().serverInSay("Nothing happens.", player);
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

        @Override
        protected Action specificMendAction(Player player, GameData gameData) { return null; }

        @Override
        protected Action specificPulseAction(Player player, GameData gameData) {
            if (lineFire.isOK()) {
                return new HackDoorToFireDoor(electricalDoor);
            }
            gameData.getChat().serverInSay("Nothing happens.", player);
            return null;

        }
    }
}

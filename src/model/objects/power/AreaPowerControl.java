package model.objects.power;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.ShowInspectObjectFancyFrame;
import model.map.rooms.KitchenRoom;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class AreaPowerControl extends GameObject {
    private final Room room1;
    private final Room room2;
    private boolean isOpen;

    public AreaPowerControl(Room room1, Room room2) {
        super("APC - " + room1.getName()+"/"+room2.getName(), room1);
        room1.addObject(this);
        room2.addObject(this);
        this.room1 = room1;
        this.room2 = room2;
        isOpen = false;
    }

    public AreaPowerControl(Room single) {
        super("APC - " + single.getName(), single);
        this.room1 = single;
        this.room2 = null;
        isOpen = false;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isOpen) {
            return new Sprite("apcopen", "power.png", 8, 10, this);
        }

        return new Sprite("apc", "power.png", 6, 10, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        if (cl instanceof Player) {
            at.add(new ShowInspectObjectFancyFrame((Player)cl, gameData, this));
        }
    }

    public Room getRoom1() {
        return room1;
    }

    public Room getRoom2() {
        return room2;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean b) {
        this.isOpen = b;
    }

    public List<ElectricalMachinery> getConnectedElectricalObjects() {
        List<ElectricalMachinery> lst = new ArrayList<>();
        for (GameObject obj : getRoom1().getObjects()) {
            if (obj instanceof ElectricalMachinery) {
               lst.add((ElectricalMachinery)obj);
            }
        }
        if (getRoom1().getLighting() != null) {
            lst.add(getRoom1().getLighting());
        }
        if (getRoom1().getLifeSupport() != null) {
            lst.add(getRoom1().getLifeSupport());
        }
        if (room2 != null) {
            for (GameObject obj : getRoom2().getObjects()) {
                if (obj instanceof ElectricalMachinery) {
                    lst.add((ElectricalMachinery) obj);
                }
            }
            if (getRoom2().getLighting() != null) {
                lst.add(getRoom2().getLighting());
            }
            if (getRoom2().getLifeSupport() != null) {
                lst.add(getRoom2().getLifeSupport());
            }
        }
        return lst;
    }
}

package model.objects.power;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.characters.decorators.DarknessShroudDecorator;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.ambient.DarkEvent;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import model.objects.general.PowerConsumer;

import java.util.ArrayList;

public class Lighting extends ElectricalMachinery {
    private final Room room;
    private DarkEvent darkEvent;

    public Lighting(Room room) {
        super(room.getName() + " Lights", room);
        this.room = room;
        setPowerPriority(2);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }

    @Override
    public double getPowerConsumption() {
        return  0.001;          // 1 kW
    }

    @Override
    public String getTypeName() {
        return "Lighting";
    }

    @Override
    public void onPowerOff(GameData gameData) {

    }

    @Override
    public void onPowerOn(GameData gameData) {

    }

    public void addDarkness(GameData gameData) {
        for (Event e : room.getEvents()) {
            if (e instanceof DarkEvent) {
                return; // already dark in here, no need to add one
            }
        }
        this.darkEvent = new DarkEvent();
        this.room.addEvent(darkEvent);
        for (Actor a : room.getActors()) {
            addDarkness(a);
        }
    }

    public void removeDarkness(GameData gameData) {
        if (room.getEvents().contains(this.darkEvent)) {
            room.removeEvent(this.darkEvent);
        }
        for (Actor a : room.getActors()) {
            removeDarkness(a);
        }
    }

    private void addDarkness(Actor a) {
        if (!isDarkened(a)) {
            a.setCharacter(new DarknessShroudDecorator(a.getCharacter()));
        }
    }

    private boolean isDarkened(Actor a) {
        InstanceChecker check = new InstanceChecker(){
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof DarknessShroudDecorator;
            }
        };

        return a.getCharacter().checkInstance(check);
    }

    private void removeDarkness(Actor a) {
        if (isDarkened(a)) {
            a.removeInstance(new InstanceChecker() {

                @Override
                public boolean checkInstanceOf(GameCharacter ch) {
                    return ch instanceof DarknessShroudDecorator;
                }
            });
        }
    }

}

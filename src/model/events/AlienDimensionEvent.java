package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.characters.decorators.DimensionTrappedDecorator;
import model.events.ambient.AmbientEvent;
import model.items.NoSuchThingException;
import model.map.rooms.DecorativeRoom;
import model.map.rooms.Room;
import model.npcs.EyeballAlienNPC;
import model.objects.general.RedDimensionPortal;
import model.objects.consoles.AIConsole;
import model.objects.general.DimensionPortal;
import util.Logger;
import util.MyRandom;

/**
 * Created by erini02 on 18/10/16.
 */
public class AlienDimensionEvent extends AmbientEvent {

    private static final double occurranceChance = AmbientEvent.everyNGames(5);
    private final boolean forceApply;
    private boolean hasHappened = false;
    private Room targetRoom;
    private Room otherDim;
    private int turnsActive;
    private DimensionPortal portal;
    private DimensionPortal portal2;
    private DimensionPortal portal3;
    private DimensionPortal portal4;

    public AlienDimensionEvent(boolean forceApply, Room targetRoom) {
        this.forceApply = forceApply;
        this.targetRoom = targetRoom;
    }

    public AlienDimensionEvent() {
        this(false, null);
    }

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && (MyRandom.nextDouble() < getProbability() || forceApply)) {
            hasHappened = true;

            while (targetRoom == null || targetRoom instanceof DecorativeRoom) {
                targetRoom = MyRandom.sample(gameData.getNonHiddenStationRooms());
            }
            addPortalObject(gameData, targetRoom);
            turnsActive = MyRandom.nextInt(12);
            Logger.log(Logger.INTERESTING, "Portal created in " + targetRoom.getName() + " active for " + turnsActive + " turns.");
            informCrew(gameData);
            gameData.addNPC(new EyeballAlienNPC(targetRoom));
            while (MyRandom.nextDouble() > 0.75) {
                gameData.addNPC(new EyeballAlienNPC(targetRoom));
            }

        } else if (hasHappened) {
            if (turnsActive == 0) {
                informCrewDimensionEnded(gameData);
                removePortalObject();

            }
            turnsActive--;

        }

    }

    private void removePortalObject() {
        portal.remove();
        portal2.remove();
        for (Actor a : otherDim.getActors()) {
            if (!(a instanceof EyeballAlienNPC)) {
                a.setCharacter(new DimensionTrappedDecorator(a.getCharacter()));
            }
        }
        if (portal3 != null) {
            portal3.remove();
        }
        if (portal4 != null) {
            portal4.remove();
        }


    }

    private void addPortalObject(GameData gameData, Room targetRoom) {
        try {
            otherDim = gameData.getRoom("Other Dimension");
            this.portal = new DimensionPortal(gameData, targetRoom, otherDim, "Blue");
            targetRoom.addObject(portal);

            this.portal2 = new DimensionPortal(gameData, otherDim, targetRoom, "Blue");
            otherDim.addObject(portal2);

            setUpDerelictStation(gameData);

        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }


    }

    private void informCrewDimensionEnded(GameData gameData) {
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("Singularity has vanished.", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }

    private void informCrew(GameData gameData) {
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("Warning! Singularity detected in " +
                    gameData.getMap().getAreaForRoom("ss13", targetRoom) + " part of station!", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.FIRE;
    }

    @Override
    protected double getStaticProbability() {
        return occurranceChance;
    }

    public void setUpDerelictStation(GameData gameData) {
        Room derelictBridge = null;
        try {
            derelictBridge = gameData.getRoom("Derelict Bridge");
            this.portal3 = new RedDimensionPortal(gameData, otherDim, derelictBridge, "Red");
            otherDim.addObject(portal3);
            this.portal4 = new RedDimensionPortal(gameData, derelictBridge, otherDim, "Red");
            derelictBridge.addObject(portal4);


            //Room derelictGenerator  = gameData.getRoom("Derelict Generator");


        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }



    }
}

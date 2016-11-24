package model.events;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.characters.decorators.DimensionTrappedDecorator;
import model.events.ambient.AmbientEvent;
import model.items.CosmicArtifact;
import model.items.NoSuchThingException;
import model.map.Room;
import model.npcs.AlienNPC;
import model.objects.RedDimensionPortal;
import model.objects.consoles.AIConsole;
import model.objects.general.DimensionPortal;
import util.Logger;
import util.MyRandom;

import java.util.List;

/**
 * Created by erini02 on 18/10/16.
 */
public class AlienDimensionEvent extends AmbientEvent {

    private static final double occurranceChance = 0.015;
    private boolean hasHappened = false;
    private Room targetRoom;
    private Room otherDim;
    private int turnsActive;
    private DimensionPortal portal;
    private DimensionPortal portal2;
    private DimensionPortal portal3;
    private DimensionPortal portal4;

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < getProbability()) {
            hasHappened = true;

            targetRoom = MyRandom.sample(gameData.getRooms());
            addPortalObject(gameData, targetRoom);
            turnsActive = MyRandom.nextInt(12);
            Logger.log(Logger.INTERESTING, "Portal created in " + targetRoom.getName() + " active for " + turnsActive + " turns.");
            int side = 0;
            for (List<Room> locations : gameData.getMap().getSideLocations()) {
                if (locations.contains(targetRoom)) {
                    break;
                }
                side++;
            }
            informCrew(gameData, side);
            gameData.addNPC(new AlienNPC(targetRoom));
            while (MyRandom.nextDouble() > 0.75) {
                gameData.addNPC(new AlienNPC(targetRoom));
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
            if (!(a instanceof AlienNPC)) {
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

            if (MyRandom.nextDouble() < 0.5) {
                otherDim.addItem(new CosmicArtifact());
            } else {
                Room derelictBridge = gameData.getRoom("Derelict Bridge");
                this.portal3 = new RedDimensionPortal(gameData, otherDim, derelictBridge, "Red");
                otherDim.addObject(portal3);
                this.portal4 = new RedDimensionPortal(gameData, derelictBridge, otherDim, "Red");
                derelictBridge.addObject(portal4);
            }
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

    private void informCrew(GameData gameData, int side) {
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("Warning! Singularity detected in " +
                    gameData.getMap().getSideString(side) + " part of station!", gameData);
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
}

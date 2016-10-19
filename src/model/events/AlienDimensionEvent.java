package model.events;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.characters.decorators.DimensionTrappedDecorator;
import model.events.ambient.AmbientEvent;
import model.map.Room;
import model.npcs.AlienNPC;
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

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < occurranceChance) {
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
    }

    private void addPortalObject(GameData gameData, Room targetRoom) {
        otherDim = gameData.getRoom("Other Dimension");
        this.portal = new DimensionPortal(gameData, targetRoom, otherDim);
        targetRoom.addObject(portal);

        this.portal2 = new DimensionPortal(gameData, otherDim, targetRoom);
        otherDim.addObject(portal2);

    }

    private void informCrewDimensionEnded(GameData gameData) {
        for (Player p : gameData.getPlayersAsList()) {
            p.addTolastTurnInfo("AI; \"Singularity has vanished.\"");
        }
    }

    private void informCrew(GameData gameData, int side) {
        for (Player p : gameData.getPlayersAsList()) {
            p.addTolastTurnInfo("AI; \"Warning! Singularity detected in " + gameData.getMap().getSideString(side) + " part of station!\"");
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

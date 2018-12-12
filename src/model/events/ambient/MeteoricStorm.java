package model.events.ambient;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.damage.MeteorDamage;
import model.events.damage.PhysicalDamage;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.MeanderingAvoidingMovement;
import model.objects.consoles.AIConsole;
import model.objects.mining.RockFactory;
import model.objects.mining.RockObject;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeteoricStorm extends AmbientEvent {


    private int timer = -1;
    private String sideStr;

    private static final double HULL_BREACH_CHANCE       = 0.40;
    private static final double FIRE_CHANCE              = 0.30;
    private static final double ROCK_CHANCE              = 0.30;
    private static final double DESTROY_CHANCE           = 0.03;
    private static final double SHATTER_ON_INPACT_CHANCE = 0.60;
    private Boolean severe = false;
    private Integer[] stormCoordinates;


    @Override
    protected double getStaticProbability() {
        return AmbientEvent.everyNGames(4);
    }

    @Override
    public void apply(GameData gameData) {
        if (timer == -1 && MyRandom.nextDouble() < getProbability()) {
            setupStorm(gameData);
        } else if (timer == 0) {
            if (Arrays.equals(stormCoordinates, gameData.getMap().getPositionForLevel(GameMap.STATION_LEVEL_NAME))) {
                resolveStorm(gameData);
            } else {
                Logger.log("Storm cancelled, station has jumped");
            }
            timer--;
        } else if (timer > 0) {
            timer--;
        }
    }

    private void resolveStorm(GameData gameData) {
        stationInform("Meteoric impact on " + sideStr + " side of station.", gameData);
        int multiplier = severe?2:1;
        Logger.log("Resolving meteor storm...");
        List<Room> allRooms = new ArrayList<>();
        allRooms.addAll(gameData.getMap().getArea(GameMap.STATION_LEVEL_NAME, sideStr));
        for (Room r : allRooms) {
            if (MyRandom.nextDouble() < DESTROY_CHANCE*multiplier) {
                Logger.log("... " + r.getName() + " was destroyed!");
                try {
                    gameData.getMap().removeRoom(r);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
                doDamageOnPeople(gameData, r, 5.0);
            } else {

                if (MyRandom.nextDouble() < HULL_BREACH_CHANCE*multiplier) {
                    HullBreach hull = ((HullBreach) gameData.getGameMode().getEvents().get("hull breaches"));
                    hull.startNewEvent(r);
                }

                if (MyRandom.nextDouble() < FIRE_CHANCE*multiplier) {
                    ElectricalFire fire = ((ElectricalFire) gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(r);
                }

                while (MyRandom.nextDouble() < ROCK_CHANCE*multiplier) {
                    // TODO: sometimes the game hangs around here... why?
                    RockObject rock = (RockObject) RockFactory.randomRock(r);
                    Logger.log("... " + r.getName() + " got a " + rock.getBaseName());
                    if (MyRandom.nextDouble() < SHATTER_ON_INPACT_CHANCE) {
                        Logger.log("...... witch shattered.");
                        rock.shatter();
                    }
                }

                doDamageOnPeople(gameData, r, 1.5);
            }
        }
    }

    private void stationInform(String s, GameData gameData) {
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation(s, gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    private void doDamageOnPeople(GameData gameData, Room r, double v) {
        for (Actor a : r.getActors()) {
            a.addTolastTurnInfo("KABLAM! A meteor hit the station!");
            a.getCharacter().beExposedTo(null, new MeteorDamage(v));
        }
    }

    private void setupStorm(GameData gameData) {
        int side = MyRandom.nextInt(4);
        sideStr = GameMap.getSideString(side);
        timer = MyRandom.nextInt(3) + 1;
        severe = MyRandom.nextBoolean();

        String start = " M";
        if (severe) {
            start = "Severe m";
        }
        stationInform(start + "eteoric storm detected from " + sideStr + ". ETA: " + timer + " minutes.",
                    gameData);

        for (NPC npc : gameData.getNPCs()) {
            if (npc.getCharacter().isCrew() && !npc.isDead()) {
                npc.setMoveBehavior(new MeanderingAvoidingMovement(gameData.getMap().getArea(GameMap.STATION_LEVEL_NAME, sideStr)));
            }
        }

        stormCoordinates = gameData.getMap().getPositionForLevel(GameMap.STATION_LEVEL_NAME);

    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE, SensoryLevel.AudioLevel.VERY_LOUD, SensoryLevel.OlfactoryLevel.UNSMELLABLE);
    }
}

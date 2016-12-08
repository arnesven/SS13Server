package model.events.ambient;

import model.GameData;
import model.events.*;
import model.items.NoSuchThingException;
import model.map.Room;
import model.objects.consoles.PowerSource;
import model.objects.general.GameObject;
import util.Logger;

import java.util.Collection;
import java.util.Map;

/**
 * Created by erini02 on 03/05/16.
 */
public abstract class AmbientEvent extends Event {

    private double prob;

    public AmbientEvent() {
        this.prob = getStaticProbability();
    }



    protected abstract double getStaticProbability();

    @Override
    public final double getProbability() {
        return prob;
    }

    @Override
    public final void setProbability(double v) {
        this.prob = v;
    }

    public static double everyNGames(int n) {
        double d = 1 - Math.pow(1 - 1.0/n, (1.0/20.0));
        Logger.log(Logger.INTERESTING, "Some event hade prob " + d);
        return d;
    }

    public static void setUpAmbients(Map<String,Event> events) {
        events.put("fires",            new ElectricalFire());
        events.put("hull breaches",    new HullBreach());
        events.put("explosion",        new SpontaneousExplosionEvent());
        events.put("crazyness",        new SpontaneousCrazyness());
        events.put("radiation storms", new RadiationStorm());
        events.put("Power flux",       makePowerFluxEvent());
        events.put("random husks",     new RandomHuskEvent());
        events.put("pirate attack",    new PirateAttackEvent());
        events.put("alien dimension",  new AlienDimensionEvent());
        events.put("corrupt ai",       new CorruptAIEvent());
        events.put("merchant",         new TravelingMerchantEvent());
        events.put("marshals",         new GalacticFederalMarshalsEvent());
        events.put("basestars",        new BasestarsAreAttackingStationEvent());
        events.put("simulate power",   new SimulatePower() {
            @Override
            public Collection<Room> getAffactedRooms(GameData gameData) {
                return gameData.getRooms();
            }
        });
        events.put("derelict simpower", new SimulatePower() {
            @Override
            public Collection<Room> getAffactedRooms(GameData gameData) {
                return gameData.getMap().getRoomsForLevel("derelict");
            }
        });
    }

    private static Event makePowerFluxEvent() {
        return new PowerFlux() {
            @Override
            protected PowerSource findePowerSource(GameData gameData) throws NoSuchThingException {
                for (GameObject obj : gameData.getRoom("Generator").getObjects()) {
                    if (obj instanceof PowerSource) {
                        return (PowerSource) obj;
                    }
                }
                throw new NoSuchThingException("Did not find power source");
            }
        };
    }
}

package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.RespawnAsAlienAfterDeathDecorator;
import model.characters.general.GameCharacter;
import model.characters.special.AlienCharacter;
import model.events.Event;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;
import model.items.NoSuchThingException;
import model.items.weapons.Weapon;
import model.map.GameMap;
import model.map.rooms.Room;
import model.modes.HuntGameMode;
import model.npcs.AlienNPC;
import model.npcs.DeadDummyNPC;
import model.npcs.NPC;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;

public class AlienEggObject extends BreakableObject {

    private static final double ALIEN_NPC_SPAWN_CHANCE = 0.01; // 1% per turn and egg object
    private int number;

    public AlienEggObject(int number, Room position) {
        super("Alien Egg", number, position);
        this.number = number;
    }

    public static int getNoOfEggsOnStation(GameData gameData) {
        int num = 0;
        for (Room r  :gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME)) {
            for (GameObject obj : r.getObjects()) {
                if (obj instanceof AlienEggObject) {
                    num += ((AlienEggObject) obj).getNumber();
                }
            }
        }
        return num;
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (number > 1) {
            return "Alien Eggs";
        }
        return "Alien Egg";
    }

    private void decrementNumber() {
        number--;
        if (number == 0) {
            getPosition().removeObject(this);
        }
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon item, GameData gameData) {
        double healthBefore = getHealth();
        boolean result = super.beAttackedBy(performingClient, item, gameData);
        if (result && healthBefore > getHealth()) {
            while (number > 0) {
                decrementNumber();
            }
            performingClient.addTolastTurnInfo("You broke the eggs.");
        }
        return result;
    }

    @Override
    public void beExposedTo(Actor performingClient, Damager damager, GameData gameData) {
        if (damager instanceof RadiationDamage ||
                damager instanceof ColdDamage ||
                damager instanceof AsphyxiationDamage) {
            return;
        }
        double healthBefore = getHealth();
        super.beExposedTo(performingClient, damager, gameData);
        if (healthBefore > getHealth()) {
            while (number > 0) {
                decrementNumber();
            }
        }
    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("alieneggobj", "alien2.png", 1, 8, this);
    }

    public int getNumber() {
        return number;
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }

    public Event createEvent() {
        return new AlienEggEvent();
    }

    public void splitOffIntoEggActor(Actor actor, GameData gameData) {
        decrementNumber();
        try {
            actor.getPosition().removeActor(actor);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        GameCharacter oldChar = actor.getCharacter();
        AlienCharacter ac = new AlienCharacter(getPosition());
        actor.setCharacter(ac);
        oldChar.setActor(new DeadDummyNPC(oldChar));
        gameData.addNPC((NPC)oldChar.getActor());

        actor.addTolastTurnInfo("You respawned as an alien!");
        actor.moveIntoRoom(getPosition());
        actor.setCharacter(new RespawnAsAlienAfterDeathDecorator(actor.getCharacter(), gameData, (HuntGameMode)gameData.getGameMode()));
    }

    public void splitOffIntoEggNPC(GameData gameData) {
        NPC alienNPC = new AlienNPC(AlienEggObject.this.getPosition());
        gameData.addNPC(alienNPC);
        decrementNumber();
        Logger.log("Egg spawned an alien NPC");
    }


    private class AlienEggEvent extends Event {
        @Override
        public void apply(GameData gameData) {
            if (number > 0 && MyRandom.nextDouble() < ALIEN_NPC_SPAWN_CHANCE) {
                AlienEggObject.this.splitOffIntoEggNPC(gameData);

            }
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return null;
        }

        @Override
        public SensoryLevel getSense() {
            return SensoryLevel.NO_SENSE;
        }

        @Override
        public boolean shouldBeRemoved(GameData gameData) {
            return number == 0;
        }
    }


}

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
import model.map.rooms.Room;
import model.modes.HuntGameMode;
import model.npcs.AlienNPC;
import model.npcs.DeadDummyNPC;
import model.npcs.NPC;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
import util.MyRandom;

import java.util.ArrayList;

public class AlienEggObject extends BreakableObject {

    private static final double ALIEN_NPC_SPAWN_CHANCE = 0.05; // 5% per turn and egg object
    private int number;

    public AlienEggObject(int number, Room position) {
        super("Alien Egg", number, position);
        this.number = number;
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
        double before = getHealth();
        boolean result = super.beAttackedBy(performingClient, item, gameData);
        for (int i = (int)Math.ceil(before); i > getHealth(); i--) {
            decrementNumber();
            performingClient.addTolastTurnInfo("You broke some of the egg(s).");
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
        double before = getHealth();
        super.beExposedTo(performingClient, damager, gameData);
        for (int i = (int)Math.ceil(before); i > getHealth(); i--) {
            decrementNumber();
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

    private class AlienEggEvent extends Event {
        @Override
        public void apply(GameData gameData) {
            if (number > 0 && MyRandom.nextDouble() < ALIEN_NPC_SPAWN_CHANCE) {
                NPC alienNPC = new AlienNPC(AlienEggObject.this.getPosition());
                gameData.addNPC(alienNPC);
                decrementNumber();
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

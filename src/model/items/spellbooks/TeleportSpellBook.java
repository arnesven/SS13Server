package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.ActionOption;
import model.actions.roomactions.CanAlsoMoveToForOneTurnDecorator;
import model.characters.decorators.PoofOfSmokeAnimationDecorator;
import model.events.PoofOfSmokeAnimationEvent;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.map.doors.Door;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class TeleportSpellBook extends SpellBook {
    private Room targetRoom;

    public TeleportSpellBook() {
        super("Teleport", 10);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("teleportspellbook", "wizardstuff.png", 5, this);
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
        if (targetRoom != null && performingClient instanceof Player) {
            teleportPlayerToRoom(targetRoom, performingClient, gameData);
            targetRoom = null;
        } else {
            performingClient.addTolastTurnInfo("Room not found!");
        }
    }

    public static void teleportPlayerToRoom(Room targetRoom, Actor performingClient, GameData gameData) {
        performingClient.addTolastTurnInfo("You are teleporting into " + targetRoom.getName() + ".");
        performingClient.setCharacter(new CanAlsoMoveToForOneTurnDecorator(performingClient.getCharacter(), targetRoom));
        performingClient.getPosition().addEvent(new PoofOfSmokeAnimationEvent(gameData, performingClient.getPosition()));
        performingClient.moveIntoRoom(targetRoom);
        performingClient.setCharacter(new PoofOfSmokeAnimationDecorator(performingClient.getCharacter(), gameData));
    }

    @Override
    public String getSpellName() {
        return "Teleport";
    }

    @Override
    public String getMagicWords() {
        return "Open Transport!";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastTeleportAction(this, gameData, forWhom);
    }

    @Override
    public boolean canBeQuickCast() {
        return false;
    }

    @Override
    protected String getSpellDescription() {
        return "Teleport the caster to a nearby location (even through locked doors.) This move is done immediately during the Action Phase. (Players normally move in the Move Phase).";
    }

    @Override
    public GameItem clone() {
        return new TeleportSpellBook();
    }

    public void setTeleportTargetRoom(Room r) {
        targetRoom = r;
    }

    @Override
    public boolean isTargetingSpell() {
        return false;
    }
}

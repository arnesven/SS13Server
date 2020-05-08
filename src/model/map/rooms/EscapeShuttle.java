package model.map.rooms;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.CallEscapeShuttleAction;
import model.characters.decorators.*;
import model.characters.general.GameCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.items.NoSuchThingException;
import model.map.doors.Door;
import model.npcs.NPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.StayBehavior;
import model.objects.consoles.AIConsole;
import model.objects.consoles.EscapeShuttleControl;
import model.objects.decorations.BigThruster;
import model.objects.decorations.ShuttleThruster;
import util.HTMLText;

public class EscapeShuttle extends ShuttleRoom {

    private boolean hasLeft;

    public EscapeShuttle(GameData gameData) {
        super(gameData.getMap().getMaxID()+1, "Escape Shuttle",
                0, 0, 4, 2, new int[]{},
                new Door[]{}, 14);
        hasLeft = false;
        addDecoration(new BigThruster(this, 0.25));
        addDecoration(new BigThruster(this, 1.25));
        addObject(new EscapeShuttleControl(this));
    }

    public boolean hasLeft() {
        return hasLeft;
    }

    public void leaveNow(GameData gameData) {
        undockYourself(gameData);
        gameData.getMap().moveRoomToLevel(this, "centcom", "centcom");
        rotate();
        moveTo(0, 0, 0);
        hasLeft = true;
        for (Actor a : getActors()) {
            escapeOnShuttle(a);
        }
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("The Escape Shuttle has left the station.", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    private void escapeOnShuttle(Actor a) {
        if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator)) {
            a.removeInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator);
        }
        if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof PoisonedDecorator)) {
            a.removeInstance((GameCharacter gc) -> gc instanceof PoisonedDecorator);
        }
        if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ChilledDecorator)) {
            a.removeInstance((GameCharacter gc) -> gc instanceof ChilledDecorator);
        }
        if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof FaceHuggedDecorator)) {
            a.removeInstance((GameCharacter gc) -> gc instanceof FaceHuggedDecorator);
        }
        if (a instanceof Player) {
            a.setCharacter(new EscapedOnShuttleDecorator(a.getCharacter()));
            ((Player) a).setFancyFrame(new SinglePageFancyFrame(((Player) a).getFancyFrame(), "Escape Shuttle",
                    HTMLText.makeCentered(HTMLText.makeText("White", "<br/><br/>You have escaped on the Escape Shuttle.<br/><br/>" +
                            "You may no longer take any actions, but at least you survived."))));
        } else if (a instanceof NPC) {
            ((NPC) a).setActionBehavior(new DoNothingBehavior());
            ((NPC) a).setMoveBehavior(new StayBehavior());
        }
    }

    private class EscapedOnShuttleDecorator extends CharacterDecorator {
        public EscapedOnShuttleDecorator(GameCharacter character) {
            super(character, "EscapedOnShuttle");
        }


        @Override
        public boolean getsActions() {
            return false;
        }
    }
}

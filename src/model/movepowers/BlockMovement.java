package model.movepowers;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.GameMap;
import model.map.rooms.Room;
import model.npcs.behaviors.PathFinding;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class BlockMovement extends MovePower {
    public BlockMovement() {
        super("Block Movement of Others");
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {
        performingClient.setCharacter(new BlockCharacterDecorator(performingClient.getCharacter(), moveData));
    }

    @Override
    public Sprite getButtonSprite() {
        return new Sprite("blockpathbutton", "buttons2.png", 1, 7);
    }

    private class BlockCharacterDecorator extends CharacterDecorator {
        private final MovementData moveData;

        public BlockCharacterDecorator(GameCharacter character, MovementData moveData) {
            super(character, "blockcharacteddecorator");
            this.moveData = moveData;
        }

        @Override
        public void doAfterMovement(GameData gameData) {
            super.doAfterMovement(gameData);
            Actor toBeBlocked = null;
            for (Room r : getActor().getPosition().getNeighborList()) {
                for (Actor a : r.getActors()) {
                    Room actorsLastRoom = moveData.getLastPosition(a);

                    if (getActor().getPosition().getNeighborList().contains(actorsLastRoom) &&
                            GameMap.shortestDistance(actorsLastRoom, a.getPosition()) >= 2) {
                        toBeBlocked = a;
                        Logger.log("Found person to block! " + toBeBlocked.getPublicName());
                        break;
                    }
                }
            }

            if (toBeBlocked == null) {
                for (Actor a : gameData.getActors()) {
                    if (a != getActor()) {
                        if (moveData.getLastPosition(a) == getActor().getPosition() && MyRandom.nextDouble() < 0.67) {
                            toBeBlocked = a;
                            Logger.log("Found person to block! " + toBeBlocked.getPublicName());
                            break;
                        }
                    }
                }
            }

            if (toBeBlocked != null) {
                toBeBlocked.addTolastTurnInfo("Your movement was blocked by " + getActor().getPublicName() + "!");
                getActor().addTolastTurnInfo("You blocked " + toBeBlocked.getPublicName() + "'s movement!");
                toBeBlocked.moveIntoRoom(getActor().getPosition());
            }

            getActor().removeInstance((GameCharacter gc ) -> gc == this);
        }

    }
}

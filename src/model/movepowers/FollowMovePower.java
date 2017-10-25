package model.movepowers;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.GameMap;
import model.map.rooms.Room;
import util.Logger;
import util.MyRandom;

public class FollowMovePower extends MovePower {
    public FollowMovePower() {
        super("Follow Other");
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {
        performingClient.setCharacter(new FollowOtherDecorator(performingClient.getCharacter(), moveData));
    }

    @Override
    public boolean isApplicable(GameData gameData, Actor performingClient) {
        return performingClient.getPosition().getActors().size() > 1;
    }

    @Override
    public Sprite getButtonSprite() {
        return new Sprite("followmovepowerbutton", "buttons2.png", 4, 7);
    }

    private class FollowOtherDecorator extends CharacterDecorator {
        private final MovementData moveData;

        public FollowOtherDecorator(GameCharacter character, MovementData moveData) {
            super(character, "followotherdecorator");
            this.moveData = moveData;
        }

        @Override
        public void doAfterMovement(GameData gameData) {
            super.doAfterMovement(gameData);

            Actor actorToFollow = null;
            for (Actor a : gameData.getActors()) {
                Room actorsLastRoom = moveData.getLastPosition(a);      // FOLLOW a IF:
                if (a != getActor() &&                                  // a isn't me
                        actorsLastRoom == getActor().getPosition() &&   // a was in my room
                        a.getPosition() != actorsLastRoom &&            // a has moved to another pos
                        GameMap.shortestDistance(actorsLastRoom, getActor().getPosition()) <= getMovementSteps() && // I can keep up with a
                        MyRandom.nextDouble() < 0.67) {                 // I am a bit lucky
                    actorToFollow = a;
                    Logger.log("Found actor to follow " + actorToFollow.getPublicName());
                    break;
                }
            }

            if (actorToFollow != null) {
                getActor().addTolastTurnInfo("You followed " + actorToFollow.getPublicName() + "!");
                actorToFollow.addTolastTurnInfo(getActor().getPublicName() + " followed you here!");
                getActor().moveIntoRoom(actorToFollow.getPosition());
            }

            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}

package model.characters.decorators;

import graphics.sprites.SpriteObject;
import model.GameData;
import model.Target;
import model.characters.general.GameCharacter;
import model.map.doors.Door;
import model.map.doors.DoorMechanism;
import model.map.rooms.RelativePositions;

public class InProximityOfTargetOneRoundDecorator extends CharacterDecorator {
    private final SpriteObject target;
    private final int setInRound;

    public InProximityOfTargetOneRoundDecorator(GameCharacter character, SpriteObject target, int round) {
        super(character, "In proximity of " + target.getPublicName(character.getActor()));
        this.target = target;
        this.setInRound = round;
    }

    @Override
    public RelativePositions getPreferredRelativePosition() {
        if (target instanceof DoorMechanism) {
            return new RelativePositions.CloseToDoor(((DoorMechanism)target).getDoor());
        }
        return new RelativePositions.InProximityOf(target);
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        super.doAtEndOfTurn(gameData);
        if (gameData.getRound() > setInRound) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}

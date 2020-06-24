package model.characters.decorators;

import model.characters.general.GameCharacter;
import model.map.rooms.RelativePositions;
import model.objects.general.GameObject;

public class OnTopOfObjectDecorator extends CharacterDecorator {
    private final GameObject sprobj;

    public OnTopOfObjectDecorator(GameCharacter character, GameObject throne) {
        super(character, "On top of " + throne.getBaseName());
        this.sprobj = throne;
    }

    @Override
    public RelativePositions getPreferredRelativePosition() {
        return new RelativePositions.OnTopOf(sprobj);
    }
}

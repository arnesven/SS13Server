package model.combat;

import model.Actor;
import model.GameData;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.DisablingDecorator;
import model.characters.general.GameCharacter;

public class GrabbingSomeoneDecorator extends CharacterDecorator implements DisablingDecorator {
    private final Actor grabbed;
    private final int setInRound;

    public GrabbingSomeoneDecorator(GameCharacter character, Actor opponent, int round) {
        super(character, "Grabbing " + opponent.getPublicName(character.getActor()));
        this.grabbed = opponent;
        this.setInRound = round;
    }

    @Override
    public String getFullName() {
        return super.getFullName() + " (Grabbing " + grabbed.getPublicName(getActor()) + ")";
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        if (gameData.getRound() > setInRound) {
            getActor().addTolastTurnInfo(grabbed.getPublicName() + " broke free from your hold.");
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}

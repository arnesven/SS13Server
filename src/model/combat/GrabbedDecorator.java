package model.combat;

import model.Actor;
import model.GameData;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.DisablingDecorator;
import model.characters.general.GameCharacter;

public class GrabbedDecorator extends AlterMovement implements DisablingDecorator {
    private final Actor grabber;
    private final int setInRound;

    public GrabbedDecorator(GameCharacter character, Actor performingClient, int round) {
        super(character, "Grabbed by " + performingClient.getPublicName(character.getActor()), false, 0);
        this.grabber = performingClient;
        this.setInRound = round;
    }

    @Override
    public String getFullName() {
        return super.getFullName() + " (Grabbed by " + grabber.getPublicName(getActor()) + ")";
    }

    @Override
    public double getCombatBaseDefense() {
        return 0.5;
    }

    @Override
    public boolean getsActions() {
        return false;
    }

    @Override
    public boolean getsObjectActions() {
        return false;
    }

    @Override
    public boolean getsRoomActions() {
        return false;
    }


    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        if (gameData.getRound() > setInRound) {
            getActor().addTolastTurnInfo("You broke free from " + grabber.getPublicName(getActor()) + "'s hold.");
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}

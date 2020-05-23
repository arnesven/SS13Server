package model.items.spellbooks;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;

class HealthRegenerationDecorator extends CharacterDecorator {
    private final int roundSet;
    private final int duration;
    private final double amount;

    public HealthRegenerationDecorator(Actor eatenBy, int round, int duration, double amount) {
        super(eatenBy.getCharacter(), "Health Regen.");
        this.roundSet = round;
        this.duration = duration;
        this.amount = amount;
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        if (gameData.getRound() > roundSet + duration + 1) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        } else if (gameData.getRound() > roundSet){
            getActor().addToHealth(amount);
        }
    }
}

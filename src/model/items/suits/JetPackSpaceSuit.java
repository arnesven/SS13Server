package model.items.suits;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.misc.EVAStrategy;
import model.misc.FreeMoveEVAStrategy;

public class JetPackSpaceSuit extends SpaceSuit {

    @Override
    public void beingPutOn(Actor actionPerformer) {
        super.beingPutOn(actionPerformer);
        actionPerformer.setCharacter(new FreeEVAMoveDecorator(actionPerformer.getCharacter()));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
       super.beingTakenOff(actionPerformer);
       if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof FreeEVAMoveDecorator)) {
           actionPerformer.removeInstance((GameCharacter gc) -> gc instanceof FreeEVAMoveDecorator);
       }
    }


    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return super.getDescription(gameData, performingClient) + ". This kind also" +
                "has an attached jet-pack which lets the wearer move around in space" +
                "freely.";
    }

    private class FreeEVAMoveDecorator extends CharacterDecorator {
        public FreeEVAMoveDecorator(GameCharacter character) {
            super(character, "Free Move EVA");
        }

        @Override
        public EVAStrategy getDefaultEVAStrategy() {
            return new FreeMoveEVAStrategy();
        }
    }
}

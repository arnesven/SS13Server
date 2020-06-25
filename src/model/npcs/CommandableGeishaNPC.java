package model.npcs;

import model.GameData;
import model.actions.characteractions.MoveTowardsPirateShipAction;
import model.actions.general.Action;
import model.characters.crew.GeneticistCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.characters.visitors.GeishaCharacter;
import model.map.rooms.Room;

import java.util.ArrayList;

public class CommandableGeishaNPC extends HumanNPC implements CommandableNPC {
    public CommandableGeishaNPC(Room position) {
        super(new GeishaCharacter(), position);
        setCharacter(new AlsoMoveTowardsPirateShipActionDecorator(getCharacter()));
    }

    @Override
    public int getCommandPointCost() {
        return 10;
    }

    private class AlsoMoveTowardsPirateShipActionDecorator extends CharacterDecorator {
        public AlsoMoveTowardsPirateShipActionDecorator(GameCharacter character) {
            super(character, "Also Move towards pirate ship");
        }

        @Override
        public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
            super.addCharacterSpecificActions(gameData, at);
            if (getActor() instanceof NPC) {
                at.add(new MoveTowardsPirateShipAction());
            }
        }
    }
}

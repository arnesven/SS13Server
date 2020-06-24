package model.npcs;

import model.characters.crew.GeneticistCharacter;
import model.characters.visitors.GeishaCharacter;
import model.map.rooms.Room;

public class CommandableGeishaNPC extends HumanNPC implements CommandableNPC {
    public CommandableGeishaNPC(Room position) {
        super(new GeishaCharacter(), position);
    }

    @Override
    public int getCommandPointCost() {
        return 10;
    }
}

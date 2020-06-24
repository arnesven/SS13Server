package model.npcs;

import model.characters.PleasureBoyCharacter;
import model.characters.general.HumanCharacter;
import model.items.suits.Bikini;
import model.items.suits.Mankini;
import model.map.rooms.Room;

public class PleasureNPC extends HumanNPC implements CommandableNPC {
    public PleasureNPC(HumanCharacter pleasureCharacter, Room room) {
        super(pleasureCharacter, room);
        getCharacter().getPhysicalBody().setFacialHairNumber(0);
       // getCharacter().getPhysicalBody().setWithUnderwear(false);
        if (pleasureCharacter.getGender().equals("man")) {
            getCharacter().putOnEquipment(new Mankini());
        } else {
            getCharacter().putOnEquipment(new Bikini());
        }

    }

    @Override
    public int getCommandPointCost() {
        return 10;
    }
}

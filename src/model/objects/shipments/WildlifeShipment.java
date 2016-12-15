package model.objects.shipments;

import model.GameData;
import model.map.rooms.Room;
import model.npcs.animals.BearNPC;
import model.npcs.animals.ChimpNPC;
import model.npcs.animals.DogNPC;
import util.MyRandom;

/**
 * Created by erini02 on 03/09/16.
 */
public class WildlifeShipment extends Shipment {

    public WildlifeShipment() {
        super("Wildlife");
    }

    @Override
    public int getCost() {
        return 3000;
    }

    @Override
    public Shipment clone() {
        return new WildlifeShipment();
    }

    @Override
    public void hasArrivedIn(Room position, GameData gameData) {
        double rand = MyRandom.nextDouble();
        if (rand < 0.33) {
            for (int i = 3; i > 0 ; --i) {
                position.addNPC(new ChimpNPC(position));
            }
        } else if (rand < 0.66) {
            gameData.addNPC(new BearNPC(position));
        } else {
            gameData.addNPC(new DogNPC(position));
            gameData.addNPC(new DogNPC(position));
            gameData.addNPC(new DogNPC(position));
        }

    }
}

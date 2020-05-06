package model.npcs;

import model.GameData;
import model.characters.MouseCharacter;
import model.map.rooms.NukieShipRoom;
import model.map.rooms.Room;
import model.npcs.animals.AnimalNPC;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.SquakingBehavior;
import util.MyRandom;

public class MouseNPC extends AnimalNPC {

    private final Room starting;

    public MouseNPC(Room starting) {
        super(new MouseCharacter(starting.getID()), new MeanderingMovement(0.5),
                new SquakingBehavior(), starting);
        this.starting = starting;
    }

    @Override
    public NPC clone() {
        return new MouseNPC(starting);
    }

    public static void addAMouseToRandomRoom(GameData gameData) {
        Room mouseRoom;
        do {
            mouseRoom = MyRandom.sample(gameData.getNonHiddenStationRooms());
        } while (mouseRoom instanceof NukieShipRoom);

        NPC mouse = new MouseNPC(mouseRoom);
        gameData.addNPC(mouse);
    }

}

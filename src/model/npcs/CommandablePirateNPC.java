package model.npcs;

import model.Actor;
import model.Player;
import model.characters.general.GameCharacter;
import model.map.rooms.Room;

public class CommandablePirateNPC extends PirateNPC implements CommandableNPC {

    private static int num = 0;
    private final String trueName;
    private final Player pirateCap;

    public CommandablePirateNPC(Room position, String trueName, Player pirateCaptain) {
        super(position, ++num, position);
        this.trueName = trueName;
        this.pirateCap = pirateCaptain;
        setHealth(1.5);
        setMaxHealth(1.5);
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (whosAsking == pirateCap) {
            return trueName;
        }
        return super.getPublicName(whosAsking);
    }

    @Override
    public int getCommandPointCost() {
        return 10;
    }
}

package model.npcs;

import model.Actor;
import model.characters.EastwoodsPetCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.ParasiteCharacter;
import model.characters.general.PirateCaptainCharacter;
import model.map.rooms.Room;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import util.Logger;

public class EastwoodsPetNPC extends NPC implements CommandableNPC {
    private final Room startRoom;

    public EastwoodsPetNPC(Room eastWoodsQuarters) {
        super(new EastwoodsPetCharacter(), new MeanderingMovement(0.5), new DoNothingBehavior(), eastWoodsQuarters);
        this.startRoom = eastWoodsQuarters;
        setHealth(2.0);
        setMaxHealth(2.0);
    }

    @Override
    public NPC clone() {
        return new EastwoodsPetNPC(startRoom);
    }

    @Override
    public int getCommandPointCost() {
        return 100;
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (whosAsking.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof PirateCaptainCharacter)) {
            return "Eastwood's Pet";
        }
        return super.getPublicName(whosAsking);
    }
}

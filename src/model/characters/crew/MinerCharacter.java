package model.characters.crew;

import model.Actor;
import model.characters.general.GameCharacter;
import model.items.FlashLight;
import model.items.mining.MiningTeleporter;
import model.items.general.GameItem;
import model.items.mining.OreShardBag;
import model.items.suits.MinerSpaceSuit;
import model.items.mining.MiningDrill;
import model.map.rooms.MiningStationRoom;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.PutOnSuitBehavior;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class MinerCharacter extends CrewCharacter {

    public MinerCharacter() {
        super("Miner", MiningStationRoom.DEFAULT_ID, 1.5);
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        List<GameItem> arr = new ArrayList<>();
        arr.add(new MiningDrill());
        arr.add(new MiningTeleporter());
        arr.add(new OreShardBag());
        arr.add(new MinerSpaceSuit());
        return arr;
    }

    @Override
    public GameCharacter clone() {
        return new MinerCharacter();
    }

    @Override
    public int getStartingMoney() {
        return 150;
    }


    @Override
    public ActionBehavior getDefaultActionBehavior() {
        return new PutOnSuitBehavior(MinerSpaceSuit.class);
    }
}

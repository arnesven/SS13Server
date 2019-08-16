package model.objects.mining;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.DrillIntoRockAction;
import model.items.general.GameItem;
import model.items.mining.OreShard;
import model.items.mining.RegolithShard;
import model.items.mining.MiningDrill;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public abstract class RockObject extends GameObject {

    private boolean broken = false;

    public RockObject(String name, Room position) {
        super(name, position);
        broken = false;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return new Sprite("regularrockbroken", "meteor.png", 1, 1, this);
        }
        return new Sprite("regularrock", "meteor.png", 1, this);
    }

    public boolean isBroken() {
        return broken;
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        if (GameItem.hasAnItemOfClass(cl, MiningDrill.class)) {
            at.add(new DrillIntoRockAction(this));
        }

    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public OreShard splitOff() {
        return getOre();
    }

    protected abstract OreShard getOre();

    public List<OreShard> shatter() {
        List<OreShard> list = new ArrayList<>();
        for (int i = 0; i < MyRandom.nextInt(3); ++i) {
            list.add(new RegolithShard());
        }
        for (double d = 0.9; d > MyRandom.nextDouble(); d=d/2.0) {
            list.add(splitOff());
        }
        getPosition().removeObject(this);

        return list;
    }
}

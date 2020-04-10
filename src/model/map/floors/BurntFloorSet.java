package model.map.floors;

import graphics.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BurntFloorSet extends FloorSet {
    private final FloorSet old;

    public BurntFloorSet(FloorSet floorSet) {
        super("dirtyfloor" + floorSet.getName(), floorSet.getColumn(), floorSet.getRow());
        this.old = floorSet;
        makeDirty();
    }

    private void makeDirty() {
        setMainSprite(dirtyfy(getMainSprite()));
        List<Sprite> newOthers = new ArrayList<>();
        for (Sprite sp : getOtherSprites()) {
            newOthers.add(dirtyfy(sp));
        }
        super.setOtherSprites(newOthers);
    }

    private Sprite dirtyfy(Sprite sprite) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(sprite);
        Sprite dirtyFloor = new Sprite("dirtyfloor", "floors.png", 27, 28, null);
        sprs.add(dirtyFloor);
        return new Sprite("dirtyfloor" + sprite.getName().replace("0", ""), "human.png", 0, sprs, null);
    }

    public FloorSet getUnburntFloorSet() {
        return old;
    }
}

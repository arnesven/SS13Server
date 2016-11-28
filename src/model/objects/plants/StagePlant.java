package model.objects.plants;

import graphics.sprites.Sprite;
import model.Player;
import model.map.Room;

import java.util.List;

/**
 * Created by erini02 on 28/11/16.
 */
public abstract class StagePlant extends Plant {

    private int stage;
    private final int maxStages;

    public StagePlant(String name, Room position, int maxStages) {
        super(name, position);
        this.stage = 0;
        this.maxStages = maxStages;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return getSpriteStages().get(stage);
    }

    protected abstract List<Sprite> getSpriteStages();

    public void increaseStage() {
        stage = Math.min(maxStages-1, stage + 1);
    }

    public boolean isMaxStage() {
        return stage == maxStages - 1;
    }

}

package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;

import java.util.ArrayList;

public class BarSign extends ElectricalMachinery {
    private final BarSignBis otherHalf;
    private Sprite sprite;
    private GameData gameData;

    public BarSign(Room position) {
        super("Bar Sign 1", position);
        sprite = new Sprite("defaultbarsign1", "barsigns.png", 10, 5, this);
        this.otherHalf = new BarSignBis(position, this);
        position.addObject(otherHalf);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (gameData != null) {
            if (!ElectricalMachinery.isPowered(gameData, this) || !ElectricalMachinery.isPowered(gameData, otherHalf)) {
                return new Sprite("unpoweredbarsign1", "barsigns.png", 4, 5, this);
            }
        }
        return sprite;
    }

    public void setAppearance(Sprite sprite) {
        sprite.setObjectRef(this);
        this.sprite = sprite;
        otherHalf.setAppearance(sprite);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        this.gameData = gameData;
    }

    private static class BarSignBis extends ElectricalMachinery {
        private final BarSign firstHalf;
        private Sprite sprite;
        private GameData gameData;

        public BarSignBis(Room position, BarSign first) {
            super("Bar Sign 2", position);
            this.sprite = new Sprite("defaultbarsign2", "barsigns.png", 11, 5, this);
            this.firstHalf = first;
        }

        @Override
        protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
            this.gameData = gameData;
        }

        public void setAppearance(Sprite sprite) {
            Sprite sp = new Sprite(sprite.getName().substring(0, sprite.getName().length()-2) + "2",
                    "barsigns.png", sprite.getColumn()+1, sprite.getRow(), this);
            this.sprite = sp;
        }

        public Sprite getSprite(Player whosAsking) {
            if (gameData != null) {
                if (!ElectricalMachinery.isPowered(gameData, this) || !ElectricalMachinery.isPowered(gameData, firstHalf)) {
                    return new Sprite("unpoweredbarsign2", "barsigns.png", 5, 5, this);
                }
            }
            return sprite;
        }

    }
}

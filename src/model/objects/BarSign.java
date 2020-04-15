package model.objects;

import graphics.ClientInfo;
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
    //private final BarSignBis otherHalf;
    private Sprite sprite;
    private GameData gameData;

    public BarSign(Room position) {
        super("Bar Sign 1", position);
        sprite = new Sprite("defaultbarsign1", "barsigns.png", 5, 5, 64, 32,this);
    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        if (gameData != null) {
            if (!isPowered()) {
                return new Sprite("unpoweredbarsign1", "barsigns.png", 2, 5, 64, 32, this);
            }
        }
        return sprite;
    }

    public void setAppearance(Sprite sprite) {
        sprite.setObjectRef(this);
        this.sprite = sprite;
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        this.gameData = gameData;
    }

}

package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public interface Wearable {

    public abstract void beingPutOn(Actor actionPerformer);
    public abstract void beingTakenOff(Actor actionPerformer);
    public abstract boolean permitsOver();
    String getPublicName(Actor whosAsking);

   // public String howDoYouAppearEquipped(GameData gameData, Player player);

}

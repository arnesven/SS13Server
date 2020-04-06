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

    void beingPutOn(Actor actionPerformer);
    void beingTakenOff(Actor actionPerformer);
    boolean permitsOver();
    String getPublicName(Actor whosAsking);
    Sprite getSprite(Actor whosAsking);

}

package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.HoldingItemDecorator;

public interface HandheldItem {
    Sprite getHandHeldSprite();
    String getBaseName();


}

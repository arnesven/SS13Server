package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.items.HandheldItem;

import java.util.ArrayList;
import java.util.List;

public class HoldingItemDecorator extends CharacterDecorator {
    private final HandheldItem hhitem;

    public HoldingItemDecorator(GameCharacter gc, HandheldItem w) {
              super(gc, "item holder");
              this.hhitem = w;
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
            if (whosAsking.getItems().contains(hhitem)) {
                List<Sprite> look = new ArrayList<>();
                look.add(super.getSprite(whosAsking));
                look.add(hhitem.getHandHeldSprite());
                return new Sprite(super.getSprite(whosAsking).getName() + "holding" + hhitem.getBaseName().toLowerCase(),
                        "human.png", 0, look, whosAsking);
            }

            return super.getSprite(whosAsking);
        }


    @Override
    public Sprite getMugshotSprite(GameData gameData, Actor player) {
        return getSprite(player);
    }
}

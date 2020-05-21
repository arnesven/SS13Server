package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.actions.general.DropAction;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class AnimalAppearanceDecorator extends CharacterDecorator {
    private final Sprite animalSprite;
    private final String pubName;

    public AnimalAppearanceDecorator(GameCharacter chara, String name, Sprite animalSprite, String pubName) {
        super(chara, name);
        this.animalSprite = animalSprite;
        this.pubName = pubName;
        if (!hasInventory()) {
            for (GameItem it : getActor().getItems()) {
                getActor().getPosition().addItem(it);
                it.setPosition(getActor().getPosition());
                it.setHolder(null);
            }
            while (!getActor().getItems().isEmpty()) {
                getActor().getItems().remove(0);
            }
        }
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite sp = animalSprite;
        sp.setObjectRef(getActor());
        if (getActor().isDead()) {
            sp.setRotation(90);
        }
        return sp;
    }

    @Override
    public String getPublicName() {
        return pubName;
    }

    @Override
    public boolean hasInventory() {
        return false;
    }
}

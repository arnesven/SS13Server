package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;

public class TauntaunCharacter extends AnimalCharacter {
    public TauntaunCharacter(Integer id) {
        super("Tauntaun", id, -5.0);
    }


    @Override
    public GameCharacter clone() {
        return new TauntaunCharacter(getStartingRoom());
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("tauntaun", "tauntaun.png", 0, 0, 82, 82, getActor());
    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return true;
    }

    @Override
    public int getSize() {
        return LARGE_SIZE;
    }
}

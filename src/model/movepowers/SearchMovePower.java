package model.movepowers;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.actions.general.SearchAction;

public class SearchMovePower extends MovePower {
    public SearchMovePower() {
        super("Search Room");
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {
        SearchAction srch = new SearchAction();
        srch.doTheAction(gameData, performingClient);
    }

    @Override
    public Sprite getButtonSprite() {
        return new Sprite("searchmovepower", "buttons1.png", 4, 2);
    }
}

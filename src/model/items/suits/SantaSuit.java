package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.NameChangeDecorator;
import model.characters.general.GameCharacter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by erini02 on 22/11/16.
 */
public class SantaSuit extends TorsoAndHatSuit {
    public SantaSuit() {
        super("Santa suit", 1.0, 100);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(new Sprite("santarobe", "suit2.png", 22, 6, this));
        sprs.add(getExtraSprites().get(Equipment.HEAD_SLOT));
        return new Sprite("santatotalgetup", sprs);
    }

    @Override
    public SuitItem clone() {
        return new SantaSuit();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("santassuit", "uniforms.png", 5, 5, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A red and white suit designed to ward against the frigid cold of the north pole.";
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new NameChangeDecorator(actionPerformer.getCharacter(), "Father Christmas"));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.removeInstance((GameCharacter gc) -> gc instanceof NameChangeDecorator);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    @Override
    protected Map<Integer, Sprite> getExtraSprites() {
        Map<Integer, Sprite> map = new HashMap<>();
        map.put(Equipment.HEAD_SLOT, new Sprite("santahat", "head.png", 0, 5, 32, 32,this));
        return map;
    }
}

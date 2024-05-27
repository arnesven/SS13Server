package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WizardsOutfit extends HeadShoesAndTorsoSuit {

    private static WizardsRobes robes = new WizardsRobes();
    private static WizardsHat hat = new WizardsHat();

    public WizardsOutfit() {
        super("Wizard's Outfit", robes.getWeight() + hat.getWeight(), robes.getCost() + hat.getCost());
    }

    @Override
    protected Map<Integer, Sprite> getExtraSprites() {
        Map<Integer, Sprite> map = new HashMap<>();
        map.put(Equipment.HEAD_SLOT, hat.getSprite(null));
        return map;
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(robes.getWornSprite(whosAsking));
        sprs.add(hat.getWornSprite(whosAsking));

        return new Sprite("wizardsoutfit", "human.png", 0, sprs, null);
    }

    @Override
    public SuitItem clone() {
        return new WizardsOutfit();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "An outfit worn by a wizard, or somebody pretending to be.";
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }
}

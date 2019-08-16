package model.npcs;

import graphics.sprites.Sprite;
import model.GameData;
import model.actions.characteractions.MakeSoilAction;
import model.actions.characteractions.StartCampFire;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.seeds.MushroomSpores;
import model.items.seeds.OrangeSeeds;
import model.items.seeds.SeedsItem;
import model.items.seeds.TomatoSeeds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 16/09/17.
 */
class JungleManCharacter extends GameCharacter {
    public JungleManCharacter(int startRoom) {
        super("Jungle Man", startRoom, -3.5);
    }

    @Override
    public String getPublicName() {
        String res = getBaseName();
        if (isDead()) {
            return res + " (dead)";
        }
        return res;
    }

    @Override
    public Sprite getNakedSprite() {
        if (isDead()) {
            return new Sprite("junglemandead", "jungle.png", 6, 13, this);
        }
        return new Sprite("jungleman", "jungle.png", 11, 12, this);
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> seeds = new ArrayList<>();
        seeds.add(new OrangeSeeds());
        seeds.add(new MushroomSpores());
        seeds.add(new TomatoSeeds());
        return seeds;
    }

    @Override
    public GameCharacter clone() {
        return new JungleManCharacter(getPosition().getID());
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        at.add(new StartCampFire());
        at.add(new MakeSoilAction());
    }
}

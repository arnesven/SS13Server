package model.items.seeds;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.objects.general.SoilPatch;
import model.objects.plants.Plant;
import model.objects.plants.RedMushroom;
import model.objects.plants.WhiteMushroom;
import util.MyRandom;

/**
 * Created by erini02 on 28/11/16.
 */
public class MushroomSpores extends SeedsItem {
    public MushroomSpores() {
        super("Mushroom Spores", 25);
    }

    @Override
    protected Plant getPlant(Room position, GameData gameData, Actor planter, SoilPatch sp) {
        return randomMushroom(position, gameData, planter, sp);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("orangeseeds", "seeds.png", 4, 7, this);
    }


    private Plant randomMushroom(Room position, GameData gameData, Actor planter, SoilPatch sp) {

        if (MyRandom.nextDouble() < 0.5) {
            return new WhiteMushroom(position, sp);
        }

        return new RedMushroom(position, sp);
    }

    @Override
    public GameItem clone() {
        return new MushroomSpores();
    }

    @Override
    protected String plantName() {
        return "mushrooms";
    }
}

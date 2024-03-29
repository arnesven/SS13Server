package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.Player;
import model.fancyframe.FancyFrame;
import model.fancyframe.SeedsAreUsFancyFrame;
import model.items.general.BagOfSoil;
import model.items.seeds.MushroomSpores;
import model.items.seeds.OrangeSeeds;
import model.items.seeds.RedWeedSeeds;
import model.items.seeds.TomatoSeeds;
import model.map.rooms.Room;

/**
 * Created by erini02 on 28/11/16.
 */
public class SeedVendingMachine extends VendingMachine {
    public SeedVendingMachine(Room panorama) {
        super("Seeds 'R' Us", panorama);
        addSelection(new TomatoSeeds());
        addSelection(new RedWeedSeeds());
        addSelection(new OrangeSeeds());
        addSelection(new MushroomSpores());
        addSelection(new BagOfSoil());

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("seedvending", "vending.png", 20, this);
    }

    @Override
    public FancyFrame getFancyFrame(Actor performingClient) {
        return new SeedsAreUsFancyFrame(performingClient, this);
    }
}

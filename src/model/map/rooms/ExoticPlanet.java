package model.map.rooms;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ExplorePlanetAction;
import model.npcs.JungleManNPC;
import model.npcs.NPC;
import model.objects.general.HideableObject;
import model.objects.plants.JunglePlanetPlant;
import util.MyRandom;
import util.MyStrings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by erini02 on 15/09/17.
 */
public class ExoticPlanet extends Room {
    private boolean explored;
    private String realName = "Jungle Planet";
    private Set<HideableObject> hos = new HashSet<>();

    public ExoticPlanet(int id, int x, int y, int w, int h, GameData gameData) {
        super(id, "Exotic Planet", "E X O T I C   P L A N E T", x, y, w, h, new int[]{}, new double[]{}, RoomType.outer);

        for (double d = 0.9; d > MyRandom.nextDouble(); d = d/1.5) {
            HideableObject ho = new JunglePlanetPlant(this);
            addObject(ho);
            hos.add(ho);
        }

        for (double d = 0.4; d > MyRandom.nextDouble(); d= d/1.6) {
            NPC djungleMan = new JungleManNPC(this);
            gameData.addNPC(djungleMan);
        }


    }

    @Override
    public void addActionsFor(GameData gameData, Actor client, ArrayList<Action> at) {
        super.addActionsFor(gameData, client, at);
        at.add(new ExplorePlanetAction(this));
    }

    public String getDescription() {
        return "This world is a hot and steamy jungle planet. The ground is covered with ferns, and tall trees stretch upwards.";
    }

    public void setExplored(boolean explored, GameData gameData) {
        this.explored = explored;
        if (explored) {
            this.setName(realName);
            this.setShortname(MyStrings.capitalize(realName));
            for (HideableObject ho : hos) {
                ho.setFound(true);

            }
            for (double d = 0.9; d > MyRandom.nextDouble(); d= d/1.7) {
                NPC djungleMan = new JungleManNPC(this);
                djungleMan.giveStartingItemsToSelf();
                gameData.addNPC(djungleMan);
            }
        }
    }


}

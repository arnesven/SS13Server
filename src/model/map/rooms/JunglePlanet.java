package model.map.rooms;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import model.GameData;
import model.npcs.JungleManNPC;
import model.npcs.NPC;
import model.objects.general.HideableObject;
import model.objects.plants.JunglePlanetPlant;
import util.MyRandom;

public class JunglePlanet extends ExoticPlanet {
    public JunglePlanet(int i, GameData gameData) {
        super(i, gameData, "Jungle Planet");

        for (double d = 0.9; d > MyRandom.nextDouble(); d = d/1.5) {
            HideableObject ho = new JunglePlanetPlant(this);
            addObject(ho);
            addHiddenObject(ho);
        }

        for (double d = 0.4; d > MyRandom.nextDouble(); d= d/1.6) {
            NPC djungleMan = new JungleManNPC(this);
            gameData.addNPC(djungleMan);
        }
    }


    @Override
    public String getDescription() {
        return "This world is a hot and steamy jungle planet. The ground is covered with ferns, and tall trees stretch upwards.";
    }

    @Override
    public void setExplored(boolean explored, GameData gameData) {
        super.setExplored(explored, gameData);
        if (explored) {
            for (double d = 0.9; d > MyRandom.nextDouble(); d= d/1.7) {
                NPC djungleMan = new JungleManNPC(this);
                djungleMan.giveStartingItemsToSelf();
                gameData.addNPC(djungleMan);
            }
        }
    }

    @Override
    protected int getOrbitSpriteRow() {
        return 0;
    }

    @Override
    protected int getOrbitSpriteCol() {
        return 1;
    }

}

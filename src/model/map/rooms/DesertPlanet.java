package model.map.rooms;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import model.GameData;
import model.npcs.HumanNPC;
import util.MyRandom;

public class DesertPlanet extends ExoticPlanet {
    public DesertPlanet(int i, GameData gameData) {
        super(i, gameData, "Desert Planet");
    }
    
    @Override
    public String getDescription() {
        return "This is a hot, dry planet where rain is rare. It is said that a mysterious race rules this planet with a strange power - desert power!";
    }

    @Override
    public void setExplored(boolean explored, GameData gameData) {
        super.setExplored(explored, gameData);
        if (explored) {
            while (MyRandom.nextDouble() < 0.67) {
                gameData.addNPC(new FremenNPC(this));
            }
        }
    }
}

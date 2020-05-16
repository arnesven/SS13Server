package model.map.rooms;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import model.GameData;
import model.npcs.animals.TauntaunNPC;
import model.npcs.animals.WampaNPC;
import util.MyRandom;

public class IcePlanet extends ExoticPlanet {
    public IcePlanet(int i, GameData gameData) {
        super(i, gameData, "Ice Planet");
    }


    @Override
    public void setExplored(boolean explored, GameData gameData) {
        super.setExplored(explored, gameData);
        if (explored) {
            while (MyRandom.nextDouble() < 0.6) {
                gameData.addNPC(new TauntaunNPC(this));
            }
            if (MyRandom.nextDouble() < 0.4) {
                gameData.addNPC(new WampaNPC(this));
            }
        }
    }


    @Override
    protected int getOrbitSpriteRow() {
        return 0;
    }

    @Override
    protected int getOrbitSpriteCol() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "This planet is cold! The wind is howling and cutting through you like knives.";
    }
}

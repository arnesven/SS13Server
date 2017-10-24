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
    protected int getBackgroundSpriteHeight() {
        return 179;
    }

    @Override
    protected int getBackgroundSpriteWidth() {
        return 270;
    }

    @Override
    protected String getBackgroundSpriteMap() {
        return "iceplanet.png";
    }

    @Override
    protected String getBackroundSpriteName() {
        return "iceplanetbackground";
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
    public String getDescription() {
        return "This planet is cold! The wind is howling and cutting through you like knives.";
    }
}

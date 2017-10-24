package model.map.rooms;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
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
public abstract class ExoticPlanet extends Room {
    private boolean explored;
    private String realName;
    private Set<HideableObject> hos = new HashSet<>();

    public ExoticPlanet(int id, GameData gameData, String realName) {
        super(id, "Exotic Planet", "E X O T I C   P L A N E T", 2, 2, 6, 6, new int[]{}, new double[]{}, RoomType.outer);
        this.realName = realName;
    }

    private Sprite getBackground(ClientInfo clientInfo) {
        Sprite sp = new Sprite(getBackroundSpriteName()+clientInfo.getWidth()+"x"+clientInfo.getHeight(),
                getBackgroundSpriteMap(),
                0, 0, getBackgroundSpriteWidth(), getBackgroundSpriteHeight(),
                (clientInfo.getWidth()*60)/100,
                clientInfo.getHeight());

        return sp;
    }

    protected abstract int getBackgroundSpriteHeight();

    protected abstract int getBackgroundSpriteWidth();

    protected abstract String getBackgroundSpriteMap();

    protected abstract String getBackroundSpriteName();

    public abstract String getDescription();


    protected void addHiddenObject(HideableObject ho) {
        hos.add(ho);
    }

    public boolean isExplored() {
        return explored;
    }

    @Override
    public void addActionsFor(GameData gameData, Actor client, ArrayList<Action> at) {
        super.addActionsFor(gameData, client, at);
        at.add(new ExplorePlanetAction(this));
    }

    public void setExplored(boolean explored, GameData gameData) {
        this.explored = explored;
        if (explored) {
            this.setName(realName);
            this.setShortname(MyStrings.capitalize(realName));
            for (HideableObject ho : hos) {
                ho.setFound(true);
            }
        }
    }

    @Override
    public boolean hasBackgroundSprite() {
        return explored;
    }

    @Override
    public Sprite getBackgroundSprite(ClientInfo clientInfo) {
        return getBackground(clientInfo);
    }


}

package model.map.rooms;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ExplorePlanetAction;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.PlanetFloorSet;
import model.objects.general.HideableObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by erini02 on 15/09/17.
 */
public abstract class ExoticPlanet extends PlanetRoom {
    private boolean explored;
    private String realName;
    private Set<HideableObject> hos = new HashSet<>();

    public ExoticPlanet(int id, GameData gameData, String realName) {
        super(id, "Exotic Planet", "E X O T I C   P L A N E T", 2, 2, 10, 10, new int[]{}, new Door[]{});
        this.realName = realName;
    }

    public abstract String getDescription();

    @Override
    public FloorSet getFloorSet() {
        return new PlanetFloorSet();
    }

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
            for (HideableObject ho : hos) {
                ho.setFound(true);
            }
        }
    }

    public Sprite getOrbitSprite() {
        return new Sprite("orbitplanet-" + getOrbitSpriteCol() + "-" + getOrbitSpriteRow(),
                "planets.png", getOrbitSpriteCol(), getOrbitSpriteRow(), getOrbitSpriteSize(),
                getOrbitSpriteSize(), null);
    }

    protected int getOrbitSpriteSize() {
        return 96;
    }

    protected abstract int getOrbitSpriteRow();

    protected abstract int getOrbitSpriteCol();
}

package model.characters.special;

import graphics.sprites.OverlaySprites;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.characters.general.OperativeCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.map.rooms.Room;
import model.modes.OperativesGameMode;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 20/12/16.
 */
public class SpectatorCharacter extends GhostCharacter {

    private final GameData gameData;

    public SpectatorCharacter(GameData gameData) {
        super("Specter", 999, 0);
        setHealth(1.0);
        this.gameData = gameData;

    }

    @Override
    public boolean getsActions() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public int getMovementSteps() {
        return 0;
    }


    public List<String> getOverlayStrings(Player player, GameData gameData) {
        return OverlaySprites.seeAllOverlay(player, gameData);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spectator", "human.png", 2);
    }

    @Override
    public GameCharacter clone() {
        return new SpectatorCharacter(gameData);
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public List<Room> getVisibleMap(GameData gameData) {
        List<Room> l = super.getVisibleMap(gameData);
        try {
            l.add(gameData.getMap().getRoom("Players"));
            if (gameData.getGameMode() instanceof OperativesGameMode) {
                l.add(gameData.getMap().getRoom("Nuclear Ship"));
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public String getFullName() {
        return "Game Mode: " + gameData.getGameMode().getName();
    }

    @Override
    public SuitItem getSuit() {
        return new SpectatorSuit(gameData.getGameMode().getSpectatorSubInfo(gameData));
    }

    @Override
    public List<GameItem> getItems() {
        List<Pair<Sprite, String>> data = gameData.getGameMode().getSpectatorContent(getActor());
        List<GameItem> its = new ArrayList<>();
        for (Pair<Sprite, String> p : data) {
            its.add(new SpectatorItem(p.second, p.first));
        }
        return its;
    }

    private class SpectatorSuit extends SuitItem {
        public SpectatorSuit(String spectatorSubInfo) {
            super(spectatorSubInfo, 0, 0);
        }

        @Override
        public SuitItem clone() {
            return new SpectatorSuit("");
        }

        @Override
        public void beingPutOn(Actor actionPerformer) {

        }

        @Override
        public void beingTakenOff(Actor actionPerformer) {

        }

        @Override
        public boolean permitsOver() {
            return false;
        }
    }

    private class SpectatorItem extends GameItem {
        private final Sprite sprite;

        public SpectatorItem(String second, Sprite first) {
            super(second, 0, 0);
            this.sprite = first;
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
            return sprite;
        }

        @Override
        public GameItem clone() {
            return new SpectatorItem(getBaseName(), sprite);
        }
    }


}

package model.map.rooms;

import graphics.OverlaySprite;
import graphics.sprites.NormalVision;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.doors.Door;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.consoles.SecurityCameraConsole;
import model.objects.decorations.InnerWindow;
import model.objects.general.EvidenceBox;
import model.objects.general.SecurityStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/12/16.
 */
public class SecurityStationRoom extends SecurityRoom {
    public SecurityStationRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, Door[] doubles, Room releaseRoom) {
        super(id, "Security Station", "SS", x, y, w, h, ints, doubles);
        addObject(new CrimeRecordsConsole(this, gameData, releaseRoom), RelativePositions.MID_TOP);
        addObject(new EvidenceBox(this), RelativePositions.UPPER_RIGHT_CORNER);
        addObject(new SecurityCameraConsole(this), RelativePositions.MID_LEFT);
        addObject(new SecurityStorage(this), RelativePositions.LOWER_RIGHT_CORNER);
        InnerWindow windowToBrig = new InnerWindow(this);
        windowToBrig.setAbsolutePosition(x + 0.5, y + h);
        addObject(windowToBrig);
    }

    @Override
    public void addPlayer(Player client) {
        super.addPlayer(client);
        client.setCharacter(new AlsoSeeBrigVisionDecorator(client.getCharacter()));
    }

    @Override
    public void removePlayer(Player client) throws NoSuchThingException {
        super.removePlayer(client);
        client.removeInstance((GameCharacter gc) -> gc instanceof AlsoSeeBrigVisionDecorator);
    }

    private class AlsoSeeBrigVisionDecorator extends CharacterDecorator {
        public AlsoSeeBrigVisionDecorator(GameCharacter character) {
            super(character, "Also See Brig");
        }

        @Override
        public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
            return new AlsoSeeBrigVision().getOverlaySprites(player, gameData);
        }

        @Override
        public List<Room> getVisibleMap(GameData gameData) {
            List<Room> list = new ArrayList<>();
            list.addAll(super.getVisibleMap(gameData));
            try {
                Room brig = gameData.getRoom("Brig");
                list.add(brig);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }

            return list;
        }
    }

    private class AlsoSeeBrigVision extends NormalVision {

        @Override
        protected void addExtraSensoryPerception(Player player, GameData gameData, ArrayList<OverlaySprite> strs) {
            super.addExtraSensoryPerception(player, gameData, strs);
            Room brig = null;
            try {
                brig = gameData.getRoom("Brig");
                List<Sprite> sprs = new ArrayList<>();
                for (Actor a : brig.getActors()) {
                    sprs.add(a.getSprite(player));
                }
                strs.addAll(getOverlaysForSpritesInRoom(gameData, sprs, brig, player));
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
    }
}

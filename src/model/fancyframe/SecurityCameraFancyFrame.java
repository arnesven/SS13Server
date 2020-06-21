package model.fancyframe;

import graphics.sprites.BlurredCharacter;
import graphics.sprites.CombinedSprite;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.events.Event;
import model.events.ambient.DarkEvent;
import model.map.rooms.Room;
import model.objects.ai.SecurityCamera;
import model.objects.consoles.Console;
import model.objects.consoles.SecurityCameraConsole;
import model.objects.general.GameObject;
import util.HTMLText;
import util.MyRandom;

import java.util.*;

public class SecurityCameraFancyFrame extends ConsoleFancyFrame {
    private final SecurityCameraConsole cons;
    private Room shownRoom;
    private final ArrayList<Room> roomsToShow;
    private static int uid = 1;

    public SecurityCameraFancyFrame(Console console, GameData gameData, Player performingClient) {
        super(performingClient.getFancyFrame(), console, gameData, "black", "#0cff00");
        cons = (SecurityCameraConsole)console;
        this.shownRoom = null;
        this.roomsToShow = new ArrayList<>();
        roomsToShow.addAll(findRoomsWithCameras(gameData));
        Collections.sort(roomsToShow, new Comparator<Room>() {
            @Override
            public int compare(Room room, Room t1) {
                return room.getName().compareTo(t1.getName());
            }
        });
        concreteRebuild(gameData, performingClient);

    }

    private List<Room> findRoomsWithCameras(GameData gameData) {
        List<Room> result = new ArrayList<>();
        for (Room r : gameData.getNonHiddenStationRooms()) {
            for (GameObject obj : r.getObjects()) {
                if (obj instanceof SecurityCamera) {
                    result.add(r);
                }
            }
        }
        return result;
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (shownRoom == null) {
            content.append("<br/><b>Connected Security Cameras:</b><br/>");
            int i = 0;
            for (Room r : roomsToShow) {
                content.append(HTMLText.makeFancyFrameLink("SHOW " + i, "[+]") + " " + r.getName() + "<br/>");
                i++;
            }
            setData(cons.getPublicName(player), false, content.toString());
        } else {
            content.append("___________________________" + HTMLText.makeFancyFrameLink("BACK", "[back]") + "<br/>");
            content.append("<br/><center> " + shownRoom.getName() + "<br/>");
            content.append(HTMLText.makeImage(makeCombinedSprite(gameData, player, shownRoom, true)));


            content.append("</center>");
            setData(cons.getPublicName(player), false, content.toString());
        }


    }

    public static Sprite makeCombinedSprite(GameData gameData, Player player, Room shownRoom, boolean withConnection) {
        List<SpriteObject> stuffToShow = new ArrayList<>();
        Room r = shownRoom;

        stuffToShow.addAll(r.getActors());
        stuffToShow.addAll(r.getObjects());
        stuffToShow.addAll(r.getItems());
        for (Event a : r.getEvents()) {
            if (a.showSpriteInRoom()) {
                stuffToShow.add(a);
            }
        }

        int maxCol = 5;
        if (stuffToShow.size() > 10) {
            maxCol = 7;
        }
        int maxRows = (int)Math.max(3, Math.ceil(stuffToShow.size() / (double)(maxCol)));
        int totalSlots = maxCol * maxRows;
        for (int i = totalSlots - stuffToShow.size(); i > 0; --i) {
            stuffToShow.add(SpriteObject.BLANK);
        }

        Collections.shuffle(stuffToShow);


        int colCount = 1;
        int rowCount = 1;
        List<List<Sprite>> sprs = new ArrayList<>();
        sprs.add(new ArrayList<>());
        for (SpriteObject obj : stuffToShow) {
            Sprite sp;
            if (roomHasDarkness(r)) {
                if (obj instanceof Actor) {
                    sp = new BlurredCharacter(0, null).getSprite(player);
                } else {
                    sp = Sprite.blankSprite();
                }
            } else {
                sp = makeOnFloor(obj, r, player, colCount, maxCol, rowCount, maxRows);
            }
            sprs.get(rowCount - 1).add(sp);
            colCount++;
            if (colCount > maxCol) {
                colCount = 1;
                rowCount++;
                if (rowCount <= maxRows) {
                    sprs.add(new ArrayList<>());
                }
            }
        }

        if (withConnection) {
            checkForNoConnection(gameData, shownRoom, sprs);
        }

        return new CombinedSprite("securitycamerapicnr" + (uid++), sprs);

    }

    private static void checkForNoConnection(GameData gameData, Room shownRoom, List<List<Sprite>> sprs) {
        if (!SecurityCameraConsole.getConnectedCameraRooms(gameData).contains(shownRoom)) {
            for (int j = 0; j < sprs.size(); ++j) {
                for (int i = 0; i < sprs.get(0).size(); ++i) {
                    int randFrame = MyRandom.nextInt(15);
                    sprs.get(j).set(i, new Sprite("staticyconnection"+randFrame, "interface.png",
                            randFrame, 7, null));
                }
            }
        }
    }

    private static boolean roomHasDarkness(Room r) {
        for (Event e : r.getEvents()) {
            if (e instanceof DarkEvent) {
                return true;
            }
        }
        return false;
    }

    private static Sprite makeOnFloor(SpriteObject obj, Room r, Player player, int colCount, int maxCol, int rowCount, int maxRows) {
        List<Sprite> sprs = new ArrayList<>();
        if (colCount == 1 && rowCount == 1) {
            sprs.add(r.getFloorSet().getUpperLeft());
        } else if (colCount == maxCol && rowCount == 1) {
            sprs.add(r.getFloorSet().getUpperRight());
        } else if (colCount == 1 && rowCount == maxRows) {
            sprs.add(r.getFloorSet().getLowerLeft());
        } else if (colCount == maxCol && rowCount == maxRows) {
            sprs.add(r.getFloorSet().getLowerRight());
        } else if (rowCount == 1) {
            sprs.add(r.getFloorSet().getTop());
        } else if (rowCount == maxRows) {
            sprs.add(r.getFloorSet().getBottom());
        } else if (colCount == 1) {
            sprs.add(r.getFloorSet().getLeft());
        } else if (colCount == maxCol) {
            sprs.add(r.getFloorSet().getRight());
        } else {
            sprs.add(r.getFloorSet().getMainSprite());
        }
        if (obj instanceof Actor) {
            sprs.add(((Actor) obj).getCharacter().getUnanimatedSprite(player));
        } else {
            sprs.add(obj.getSprite(player));
        }
        return new Sprite(obj.getSprite(player).getName()+"seccam", "human.png", 0, sprs, null);
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("SHOW")) {
            String rest = event.replace("SHOW ", "");
            Scanner scan = new Scanner(rest);
            int selectedIndex = scan.nextInt();
            shownRoom = roomsToShow.get(selectedIndex);
            rebuildInterface(gameData, player);

            readyThePlayer(gameData, player);
        } else if (event.contains("BACK")) {
            shownRoom = null;
            rebuildInterface(gameData, player);
        }
    }
}

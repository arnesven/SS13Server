package model.fancyframe;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.characteractions.AdvancedBuildNewRoomAction;
import model.actions.characteractions.BuildNewRoomAction;
import model.actions.general.ActionOption;
import model.items.NoSuchThingException;
import model.items.general.RoomPartsStack;
import model.map.Architecture;
import model.map.doors.ElectricalDoor;
import model.map.floors.FloorSet;
import model.map.rooms.Room;
import util.HTMLText;
import util.MyStrings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdvancedBuildingFancyFrame extends FancyFrame {
    private final RoomPartsStack stack;
    private String selectedDoor;
    private String moveThere;
    private boolean showLocation;
    private boolean showNaming;
    private boolean showSummary;
    private String selectedSize;
    private String selectedLocation;
    private String selectedFloors;
    private String selectedWalls;
    private boolean buildButtonPushed;
    private boolean showSize;
    private String selectedName;
    private String locationErrorMessage;
    private String namingError;
    private boolean showFloors;
    private String selectedWindows;
    private boolean showDoors;

    public AdvancedBuildingFancyFrame(Player p, GameData gameData, RoomPartsStack stack) {
        super(p.getFancyFrame());
        this.showLocation = false;
        this.showNaming = false;
        this.showSummary = true;
        this.showSize = false;
        this.showFloors = false;
        this.showDoors = false;
        this.selectedSize = "1x1";
        this.selectedLocation = "Not Set";
        this.selectedFloors = "Hallway";
        this.selectedWalls = "dark";
        this.selectedName = "Not Set";
        this.moveThere = "yes";
        selectedWindows = "yes";
        selectedDoor = "Normal";
        this.buildButtonPushed = false;

        locationErrorMessage = "";
        namingError = "";
        this.stack = stack;

        buildContent(gameData, p);
    }

    private void buildContent(GameData gameData, Player p) {
        StringBuilder content = new StringBuilder();

        if (showSummary) {
            summaryPage(gameData, p, content);
        } else if (showSize) {
            sizePage(gameData, p, content);
        } else if (showLocation) {
            locationPage(gameData, p, content);
        } else if (showNaming) {
            naminPage(gameData, p, content);
        } else if (showFloors) {
            floorPage(gameData, p, content);
        } else if (showDoors) {
            doorPage(gameData, p, content);
        }

        this.setData("Advanced Building", showLocation || showNaming, HTMLText.makeColoredBackground("#cccccc", content.toString()));
    }


    private void summaryPage(GameData gameData, Player p, StringBuilder content) {
        content.append("<center>Building New Room</center>");
        content.append("<b>Size:</b> " + selectedSize + " " +
                HTMLText.makeFancyFrameLink("CHANGESIZE", "[change]") + "<br/>");
        content.append("<b>Location:</b> " + selectedLocation + " " +
                HTMLText.makeFancyFrameLink("CHANGELOCATION", "[change]") + "<br/>");
        content.append("<b>Floor Type:</b> " + selectedFloors + " " +
                HTMLText.makeFancyFrameLink("CHANGEFLOORS", "[change]") + "<br/>");
        content.append("<b>Wall Type:</b> " + selectedWalls + " " +
                HTMLText.makeFancyFrameLink("CHANGEWALLS", "[change]") + "<br/>");
        content.append("<b>Door Type:</b> " + selectedDoor + " " +
                HTMLText.makeFancyFrameLink("CHANGEDOOR", "[change]") + "<br/>");
        content.append("<b>Windows (if able)?</b> " + selectedWindows + " " +
                HTMLText.makeFancyFrameLink("CHANGEWINDOWS", "[change]" + "<br/>"));
        content.append("<b>Room Name:</b> " + selectedName + " " +
                HTMLText.makeFancyFrameLink("CHANGENAME", "[change]" + "<br/>"));
        content.append("<b>Move me there?</b> " + moveThere + " " +
        HTMLText.makeFancyFrameLink("MOVEMETHERE", "[change]") + "<br/>");
        if (!selectedLocation.equals("Not Set") && !selectedName.equals("Not Set")) {
            content.append(HTMLText.makeCentered(HTMLText.makeGrayButton(buildButtonPushed, "FINALIZE", "BUILD")));
        } else {
            content.append(HTMLText.makeCentered("<i>You must select a location and name your room before it can be built.</i>"));
        }
        if (!selectedLocation.equals("Not Set")) {
            content.append(HTMLText.makeCentered(HTMLText.makeText("Black", "sans", 2, "Preview (current room in yellow, new room in blue)")));
            content.append(HTMLText.makeCentered(HTMLText.makeImage(makePreviewSprite(gameData, p))));
            content.append("<br/>");
        }

    }


    private void sizePage(GameData gameData, Player p, StringBuilder content) {
        content.append(HTMLText.makeFancyFrameLink("RETURN", "[back]") + "<br/>");
        BuildNewRoomAction bnr = new BuildNewRoomAction();

        content.append("Select a size (Width x Height): <br/>");
        String lastSize = "1x1";
        for (ActionOption opt : bnr.getOptions(gameData, p).getSuboptions().get(0).getSuboptions()) {
            String size = opt.getName().split("=")[1];
            if (size.charAt(2) != lastSize.charAt(2)) {
                content.append("<br/>");
                lastSize = size;
            }
            content.append(HTMLText.makeFancyFrameLink("SETSIZE " + size, size) + " ");
        }
        content.append("<br/>");
        content.append(HTMLText.makeCentered("<i>The above options are based on the number of parts you have in your inventory.</i>"));
    }


    private void locationPage(GameData gameData, Player p, StringBuilder content) {
        content.append(HTMLText.makeFancyFrameLink("RETURN", "[back]") + "<br/>");
        content.append("Input a location for your new room.<br/>");
        content.append("Location describes upper left corner of room and format must be \"X Y Z\".<br/>");
        content.append("<br/><i>Hint: Your current room is located at position (" +
                p.getPosition().getX() + "," + p.getPosition().getY() + "," + p.getPosition().getZ() +
                ") and is of size " + p.getPosition().getWidth() + "x" + p.getPosition().getHeight() + ".</i><br/>");
        content.append("<br/>");
        content.append(locationErrorMessage);
    }


    private void naminPage(GameData gameData, Player p, StringBuilder content) {
        content.append(HTMLText.makeFancyFrameLink("RETURN", "[back]") + "<br/>");
        content.append("Give your room a good name!");
        content.append("<br/>");
        content.append(namingError);
    }


    private void floorPage(GameData gameData, Player p, StringBuilder content) {
        content.append("Select a floor type:<br/>");
        for (Map.Entry<String, FloorSet> entry : FloorSet.getBuildableFloorSets().entrySet()) {
            content.append(HTMLText.makeFancyFrameLink("SETFLOOR " + entry.getKey(), entry.getKey()) + "<br/>");
        }
    }


    private void doorPage(GameData gameData, Player p, StringBuilder content) {
        content.append("Select a door type:<br/>");
        for (String name : ElectricalDoor.getBuildableDoors().keySet()) {
            content.append(HTMLText.makeFancyFrameLink("SETDOOR " + name, name) + "<br/>");
        }
    }


    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("CHANGESIZE")) {
            this.showSize = true;
            this.showSummary = false;
            buildContent(gameData, player);
        } else if (event.contains("RETURN")) {
            this.showSize = false;
            this.showLocation = false;
            this.showNaming = false;
            this.showSummary = true;
            buildContent(gameData, player);
        } else if (event.contains("SETSIZE")) {
            this.selectedSize = event.replace("SETSIZE ", "");
            this.selectedLocation = "Not Set";
            showSize = false;
            this.showSummary = true;
            buildContent(gameData, player);
        } else if (event.contains("CHANGELOCATION")) {
            this.showLocation = true;
            this.showSummary = false;
            buildContent(gameData, player);
        } else if (event.contains("CHANGENAME")) {
            this.showNaming = true;
            this.showSummary = false;
            buildContent(gameData, player);
        } else if (event.contains("CHANGEFLOORS")) {
            this.showFloors = true;
            this.showSummary = false;
            buildContent(gameData, player);
        } else if (event.contains("SETFLOOR")) {
            this.selectedFloors = event.replace("SETFLOOR ", "");
            this.showFloors = false;
            this.showSummary = true;
            buildContent(gameData, player);
        } else if (event.contains("CHANGEWALLS")) {
            if (selectedWalls.equals("dark")) {
                selectedWalls = "light";
            } else {
                selectedWalls = "dark";
            }
            buildContent(gameData, player);
        } else if (event.contains("MOVEMETHERE")) {
            if (moveThere.equals("yes")) {
                moveThere = "no";
            } else {
                moveThere = "yes";
            }
            buildContent(gameData, player);
        } else if (event.contains("CHANGEWINDOWS")) {
            if (selectedWindows.equals("yes")) {
                selectedWindows = "no";
            } else {
                selectedWindows = "yes";
            }
            buildContent(gameData, player);
        } else if (event.contains("CHANGEDOOR")) {
            this.showDoors = true;
            this.showSummary = false;
            buildContent(gameData, player);
        } else if (event.contains("SETDOOR")) {
            this.selectedDoor = event.replace("SETDOOR ", "");
            this.showDoors = false;
            this.showSummary = true;
            buildContent(gameData, player);
        } else if (event.contains("FINALIZE")) {
            List<String> args = new ArrayList<String>();
            args.add(selectedLocation);
            args.add(selectedSize);
            args.add(selectedName);
            args.add(selectedFloors);
            args.add(selectedWalls);
            args.add(moveThere);
            args.add(selectedWindows);
            args.add(selectedDoor);
            BuildNewRoomAction bnra = new AdvancedBuildNewRoomAction(this);
            bnra.setActionTreeArguments(args, player);
            player.setNextAction(bnra);
            buildButtonPushed = true;
            readyThePlayer(gameData, player);
            buildContent(gameData, player);
        }
    }

    @Override
    public void handleInput(GameData gameData, Player player, String data) {
        super.handleInput(gameData, player, data);
        if (showLocation) {
            if (legalLocation(data, gameData, player)) {
                showLocation = false;
                showSummary = true;
            }
            buildContent(gameData, player);
        } else if (showNaming) {
            if (newNameIsOK(data, gameData, player)) {
                showNaming = false;
                showSummary = true;
            }
            buildContent(gameData, player);
        }
    }

    private boolean newNameIsOK(String data, GameData gameData, Player player) {
        this.selectedName = MyStrings.stripForbiddenCharacters(data);

        for (Room r : gameData.getAllRooms()) {
            if (r.getName().equals(selectedName)) {
                namingError = "ERROR! There is already a room with that name!<br/>";
                return false;
            }
        }
        return true;
    }

    private boolean legalLocation(String data, GameData gameData, Player player) {
        Scanner scan = new Scanner(data);
        int x;
        int y;
        int z;
        if (scan.hasNextInt()) {
            x = scan.nextInt();
            if (scan.hasNextInt()) {
                y = scan.nextInt();
                if (scan.hasNextInt()) {
                    z = scan.nextInt();
                    try {
                        int width = Integer.parseInt(selectedSize.split("x")[0]);
                        int height = Integer.parseInt(selectedSize.split("x")[1]);
                        Architecture arch = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(player.getPosition()).getName(), z);
                        if (arch.canRoomBeBuilt(x, y, z, width, height, player.getPosition())) {
                            selectedLocation = x + " " + y + " " + z;
                            return true;
                        }
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }
                    locationErrorMessage = "ERROR! Not a legal placement for new room!<br/>";
                    return false;
                }
            }
        }
        locationErrorMessage = "ERROR! Could not parse input!<br/>";
        buildContent(gameData, player);
        return false;
    }



    private BufferedImage makePreviewSprite(GameData gameData, Player p) {
        int gridSize = 24;
        Room fromRoom = p.getPosition();
        int newWidth = Integer.parseInt(selectedSize.split("x")[0]);
        int newHeight = Integer.parseInt(selectedSize.split("x")[1]);
        int newX = Integer.parseInt(selectedLocation.split(" ")[0]);
        int newY = Integer.parseInt(selectedLocation.split(" ")[1]);
        int newZ = Integer.parseInt(selectedLocation.split(" ")[2]);

        int minX = Math.min(fromRoom.getX(), newX);
        int minY = Math.min(fromRoom.getY(), newY);

        int totalWidth = (fromRoom.getWidth() + newWidth) * gridSize;
        int totalHeight = (fromRoom.getHeight() + newHeight) * gridSize;
        BufferedImage bimg = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bimg.getGraphics();

        //g.setColor(Color.BLACK);
        //g.fillRect(0, 0, bimg.getWidth(), bimg.getHeight());

        if (newZ > fromRoom.getZ()) {
            drawFromRoom(g, fromRoom, minX, minY, gridSize, false);
            drawNewRoom(g, newX, newY, newWidth, newHeight, minX, minY, gridSize, false);
        } else {
            drawNewRoom(g, newX, newY, newWidth, newHeight, minX, minY, gridSize, false);
            drawFromRoom(g, fromRoom, minX, minY, gridSize, false);
        }

        if (newZ > fromRoom.getZ()) {
            drawFromRoom(g, fromRoom, minX, minY, gridSize, true);
        } else {
            drawNewRoom(g, newX, newY, newWidth, newHeight, minX, minY, gridSize, true);
        }

        return bimg;
    }

    private void drawNewRoom(Graphics g, int newX, int newY, int newWidth, int newHeight, int minX, int minY, int gridSize, boolean frame) {
        g.setColor(Color.BLUE);
        if (frame) {
            g.drawRect((newX - minX) * gridSize, (newY - minY) * gridSize, newWidth * gridSize, newHeight * gridSize);
        } else {
            g.fillRect((newX - minX) * gridSize, (newY - minY) * gridSize, newWidth * gridSize, newHeight * gridSize);
        }
    }

    private void drawFromRoom(Graphics g, Room fromRoom, int minX, int minY, int gridSize, boolean frame) {
        g.setColor(Color.YELLOW);
        if (frame) {
            g.drawRect((fromRoom.getX() - minX) * gridSize,
                    (fromRoom.getY() - minY) * gridSize,
                    fromRoom.getWidth() * gridSize,
                    fromRoom.getHeight() * gridSize);
        } else {
            g.fillRect((fromRoom.getX() - minX) * gridSize,
                    (fromRoom.getY() - minY) * gridSize,
                    fromRoom.getWidth() * gridSize,
                    fromRoom.getHeight() * gridSize);
        }
    }

}

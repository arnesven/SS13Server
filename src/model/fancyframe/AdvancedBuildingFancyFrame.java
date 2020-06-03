package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.characteractions.AdvancedBuildNewRoomAction;
import model.actions.characteractions.BuildNewRoomAction;
import model.actions.general.ActionOption;
import model.items.NoSuchThingException;
import model.items.general.RoomPartsStack;
import model.map.Architecture;
import model.map.rooms.Room;
import util.HTMLText;
import util.MyStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdvancedBuildingFancyFrame extends FancyFrame {
    private boolean showLocation;
    private boolean showNaming;
    private boolean showSummary;
    private String selectedSize;
    private String selectedLocation;
    private final String selectedFloors;
    private final String selectedWalls;
    private boolean buildButtonPushed;
    private boolean showSize;
    private String selectedName;
    private String locationErrorMessage;
    private String namingError;

    public AdvancedBuildingFancyFrame(Player p, GameData gameData, RoomPartsStack stack) {
        super(p.getFancyFrame());
        this.showLocation = false;
        this.showNaming = false;
        this.showSummary = true;
        this.showSize = false;
        this.selectedSize = "1x1";
        this.selectedLocation = "Not Set";
        this.selectedFloors = "Hallway";
        this.selectedWalls = "Dark";
        this.selectedName = "Not Set";
        this.buildButtonPushed = false;
        locationErrorMessage = "";
        namingError = "";

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
        }

        this.setData("Advanced Building", showLocation || showNaming, HTMLText.makeColoredBackground("#e7ecb6", content.toString()));
    }

    private void naminPage(GameData gameData, Player p, StringBuilder content) {
        content.append(HTMLText.makeFancyFrameLink("RETURN", "[back]") + "<br/>");
        content.append("Give your room a good name!");
        content.append("<br/>");
        content.append(namingError);
    }


    private void summaryPage(GameData gameData, Player p, StringBuilder content) {
        content.append("<center>Building New Room</center>");
        content.append("<b>Size:</b> " + selectedSize + " " +
                HTMLText.makeFancyFrameLink("CHANGESIZE", "[change]") + "<br/>");
        content.append("<b>Location:</b> " + selectedLocation + " " +
                HTMLText.makeFancyFrameLink("CHANGELOCATION", "[change]") + "<br/>");
        //content.append("<b>Floor Type:</b> " + selectedFloors + " " +
        //        HTMLText.makeFancyFrameLink("CHANGEFLOORS", "[change]") + "<br/>");
        //content.append("<b>Wall Type:</b> " + selectedWalls + " " +
        //        HTMLText.makeFancyFrameLink("CHANGEWALLS", "[change]" + "<br/>"));
        content.append("<b>Room Name:</b> " + selectedName + " " +
                HTMLText.makeFancyFrameLink("CHANGENAME", "[change]" + "<br/>"));
        if (!selectedLocation.equals("Not Set") && !selectedName.equals("Not Set")) {
            content.append(HTMLText.makeCentered(HTMLText.makeGrayButton(buildButtonPushed, "FINALIZE", "BUILD")));
        } else {
            content.append(HTMLText.makeCentered("<i>You must select a location and name your room before it can be built.</i>"));
        }
    }


    private void sizePage(GameData gameData, Player p, StringBuilder content) {
        content.append(HTMLText.makeFancyFrameLink("RETURN", "[back]") + "<br/>");
        BuildNewRoomAction bnr = new BuildNewRoomAction();

        content.append("Select a size (Width x Height): <br/>");
        for (ActionOption opt : bnr.getOptions(gameData, p).getSuboptions().get(0).getSuboptions()) {
            String size = opt.getName().split("=")[1];
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("SETSIZE " + size, size) + "<br/>"));
        }
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
        } else if (event.contains("FINALIZE")) {
            List<String> args = new ArrayList<String>();
            args.add(selectedLocation);
            args.add(selectedSize);
            args.add(selectedName);
            BuildNewRoomAction bnra = new AdvancedBuildNewRoomAction();
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

}

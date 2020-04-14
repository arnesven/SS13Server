package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.objectactions.MiningShuttleAction;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.ShuttleRoom;
import model.modes.GameMode;
import model.objects.consoles.ShuttleControl;
import util.HTMLText;

public class ShuttleControlFancyFrame extends ConsoleFancyFrame {
    private final ShuttleControl console;

    public ShuttleControlFancyFrame(ShuttleControl console, GameData gameData, Player performingClient) {
        super(performingClient.getFancyFrame(), console, gameData,"#ca9f21");
        this.console = console;
        buildContent(gameData, performingClient);
    }

    private void buildContent(GameData gameData, Player player) {
        ShuttleRoom r = null;
        String docked = "Unknown location!";
        String destination = "Unknown location!";
        try {
            r = (ShuttleRoom)gameData.getMap().getRoom("Mining Shuttle");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        try {
            if (gameData.getMap().getLevelForRoom(r).getName().equals("asteroid field")) {
                docked = "Asteroid Field (docked at Mining station)";
                destination = "SS13 (Cargo Bay)";
            } else if (gameData.getMap().getLevelForRoom(r).getName().equals(GameMap.STATION_LEVEL_NAME)) {
                docked = "SS13 (docked at Cargo Bay)";
                destination = "Asteroid Field (Mining Station)";
            }

            setData(console.getPublicName(player), false,
                    HTMLText.makeCentered("<b>Mining Shuttle Control</b>") + "<br/>" +
                    "Shuttle currently at: " + docked + "<br/>" +
                            "Next stop: "+ destination + "<br/><br/>" +
            HTMLText.makeCentered(HTMLText.makeFancyFrameLink("MOVE", "[Move Mining Shuttle]")));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        buildContent(gameData, player);
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("MOVE")) {
            MiningShuttleAction msa = new MiningShuttleAction(gameData, console);
            player.setNextAction(msa);
            readyThePlayer(gameData, player);
        }
    }
}

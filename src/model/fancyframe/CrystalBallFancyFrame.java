package model.fancyframe;

import model.GameData;
import model.Player;
import model.actions.itemactions.TeleportAction;
import model.items.NoSuchThingException;
import model.items.general.Teleporter;
import model.items.spellbooks.TeleportSpellBook;
import model.map.rooms.Room;
import model.objects.CrystalBall;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class CrystalBallFancyFrame extends FancyFrame {
    private final CrystalBall crystalBall;
    private Room roomToShow;

    public CrystalBallFancyFrame(Player performingClient, GameData gameData, CrystalBall crystalBall) {
        super(performingClient.getFancyFrame());
        this.crystalBall = crystalBall;


        buildContent(gameData, performingClient);
    }

    private void buildContent(GameData gameData, Player performingClient) {
        StringBuilder content = new StringBuilder();

        if (crystalBall.getConnectedRooms().isEmpty()) {
            content.append(HTMLText.makeCentered(HTMLText.makeText("white", "<br/><br/><i>It's just a milky soup...</i>")));
        } else {
            this.roomToShow = crystalBall.getConnectedRooms().iterator().next();

            StringBuilder centerStuff = new StringBuilder();
            centerStuff.append(HTMLText.makeText("white", "The Chapel") + "<br/>");
            centerStuff.append(HTMLText.makeImage(SecurityCameraFancyFrame.makeCombinedSprite(gameData,
                    performingClient, roomToShow, false)) + "<br/>");
            centerStuff.append(HTMLText.makeFancyFrameLink("TELE " + roomToShow.getName(),
                    HTMLText.makeText("white", "<i>Teleport instantly to " + roomToShow.getName() +"</i>")) + "<br/>");
            content.append(HTMLText.makeCentered(centerStuff.toString()));
        }

        setData(crystalBall.getPublicName(performingClient), false, content.toString());
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("TELE")) {
            TeleportSpellBook.teleportPlayerToRoom(roomToShow, player, gameData);

            dispose(player);
            crystalBall.setInUse(true);
            for (Player p : gameData.getPlayersAsList()) {
                if (p.getPosition() == roomToShow || p.isAI()) {
                    p.refreshClientData();
                }
            }
        }
    }
}

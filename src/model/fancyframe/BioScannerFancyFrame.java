package model.fancyframe;

import graphics.sprites.BlurredCharacter;
import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.objectactions.ActivateBioScannerAction;
import model.actions.objectactions.TurnOnBioScannerAction;
import model.items.chemicals.Chemicals;
import model.items.general.GameItem;
import model.objects.general.BioScanner;
import util.HTMLText;

public class BioScannerFancyFrame extends FancyFrame {

    private final BioScanner bioScanner;
    private int result = -1;

    public BioScannerFancyFrame(GameData gameData, Player cl, BioScanner bioScanner) {
        super(cl.getFancyFrame());
        this.bioScanner = bioScanner;


        buildContent(gameData, cl);
    }

    private void buildContent(GameData gameData, Player cl) {
        StringBuffer content = new StringBuffer();

        if (result > -1) {
            makeNiceTable(gameData, cl, content,
                    "Station<br/>scanned!<br/>.",
                    HTMLText.makeImage(Sprite.blankSprite()),
                    makeResult(result));
        } else if (!bioScanner.isLoaded()) {
            if (GameItem.hasAnItemOfClass(cl, Chemicals.class)) {
                makeNiceTable(gameData, cl, content,
                        "Insert organic chemicals to start cycle.",
                        HTMLText.makeFancyFrameLink("LOADCHEMS", HTMLText.makeText("Yellow", "[chems]")), "");
            } else {
                makeNiceTable(gameData, cl, content,
                        "Insert organic chemicals to start cycle.", "", "");
            }

        } else if (gameData.getRound() < BioScanner.CAN_BE_USED_EARLIEST_TURN) {
            makeNiceTable(gameData, cl, content,
                    "...<br/>Warming up<br/>...",
                    HTMLText.makeImage(bioScanner.getLoadedChems().getSprite(cl)), "");
        } else {
            makeNiceTable(gameData, cl, content,
                    "Ready!<br/>.<br/>" + HTMLText.makeFancyFrameLink("ACTIVATE", "[activate]"),
                    HTMLText.makeImage(bioScanner.getLoadedChems().getSprite(cl)), "");
        }

        setData(bioScanner.getName(), false, HTMLText.makeColoredBackground("#293F27", content.toString()));
    }

    private String makeResult(int result) {
        StringBuilder bldr = new StringBuilder();
        for (int i = 0; i < result; i++) {
            bldr.append(HTMLText.makeImage(new BlurredCharacter().getSprite(null)));
            if (i % 2 == 1) {
                bldr.append("<br/>");
            }
        }

        return bldr.toString();
    }

    private void makeNiceTable(GameData gameData, Player cl, StringBuffer content, String leftText, String loadchems, String rightPanel) {
        StringBuffer table = new StringBuffer();
        table.append("<table border=\"1\" bgcolor=\"cyan\" width=\"90%\">");

        table.append("<tr><td width=\"60%\" colspan=\"3\" style=\"text-align:center\">");
        table.append(HTMLText.makeText("Yellow", "courier", 4, leftText));
        table.append("</td>");
        table.append("<td rowspan=\"4\"  style=\"text-align:center\">");
        table.append(rightPanel);
        table.append("</td></tr>");
        table.append("<tr></tr>");
        table.append("<tr><td></td>");
        table.append("<td bgcolor=\"black\" style=\"text-align:center\">");
        table.append(loadchems);
        table.append("</td></tr>");

        table.append("</table>");
        content.append("<br/>" + HTMLText.makeCentered(table.toString()));
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("LOADCHEMS")) {
            TurnOnBioScannerAction tba = new TurnOnBioScannerAction(bioScanner);
            player.setNextAction(tba);
            readyThePlayer(gameData, player);
            buildContent(gameData, player);
        } else if (event.contains("ACTIVATE")) {
            ActivateBioScannerAction aba = new ActivateBioScannerAction(bioScanner, this);
            player.setNextAction(aba);
            readyThePlayer(gameData, player);
            buildContent(gameData, player);
        }
    }

    public void setResult(int infected, GameData gameData, Player player) {
        this.result = infected;
        buildContent(gameData, player);
    }
}

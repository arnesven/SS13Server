package model.fancyframe;

import model.GameData;
import model.Player;
import model.plebOS.ComputerSystem;
import model.plebOS.KlondikeCommand;
import util.HTMLText;

public class KlondikeFancyFrame extends FancyFrame {
    private final FancyFrame innerFancyFrame;

    public KlondikeFancyFrame(GameData gameData, Player sender) {
        super(sender.getFancyFrame());
        innerFancyFrame = sender.getFancyFrame();
        buildContent(sender, gameData);
    }


    private void buildContent(Player performingClient, GameData gameData) {
        StringBuilder content = new StringBuilder();
        KlondikeCommand klondike = gameData.getComputerSystem().getKlondikeGame();
        content.append(HTMLText.makeCentered(HTMLText.makeImage(klondike.makeImageFromContents())));
        setData("Klondike", false, HTMLText.makeColoredBackground("black", content.toString()));
    }
}

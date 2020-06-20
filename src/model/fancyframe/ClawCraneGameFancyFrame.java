package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.objects.ClawCraneGame;
import util.HTMLText;

public class ClawCraneGameFancyFrame extends SinglePersonUseMachineFancyFrame {
    private final ClawCraneGame game;

    public ClawCraneGameFancyFrame(Player performingClient, GameData gameData, ClawCraneGame clawCraneGame) {
        super(performingClient, clawCraneGame);
        this.game = clawCraneGame;
        buildContent(performingClient, gameData);
    }


    private void buildContent(Player performingClient, GameData gameData) {
        StringBuilder content = new StringBuilder();

        if (!game.isPowered()) {
            content.append(HTMLText.makeCentered("<i>No Power...</i>"));
        } else {
            content.append(HTMLText.makeCentered(HTMLText.makeImage(game.makeImageFromContents())));
        }


        setData(game.getPublicName(performingClient), false, HTMLText.makeColoredBackground("gray", content.toString()));
    }
}

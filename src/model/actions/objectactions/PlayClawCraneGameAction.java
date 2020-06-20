package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.fancyframe.ClawCraneGameFancyFrame;
import model.items.general.GameItem;
import model.objects.clawcrane.ClawCraneGame;

import java.util.List;

public class PlayClawCraneGameAction extends Action {
    private final ClawCraneGame clawCraneGame;
    private final ClawCraneGameFancyFrame fancyFrame;

    public PlayClawCraneGameAction(ClawCraneGame clawCraneGame, ClawCraneGameFancyFrame ccgff) {
        super("Play " + clawCraneGame.getBaseName(), SensoryLevel.OPERATE_DEVICE);
        this.clawCraneGame = clawCraneGame;
        this.fancyFrame = ccgff;
    }


    @Override
    protected String getVerb(Actor whosAsking) {
        return "played on the claw crane game";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        GameItem it = clawCraneGame.endGame(gameData, (Player)performingClient);
        fancyFrame.gameOver(gameData, (Player)performingClient, it);
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }

    @Override
    public boolean doesCommitThePlayer() {
        return true;
    }
}

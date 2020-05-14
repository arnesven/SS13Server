package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.fancyframe.ATMFancyFrame;
import model.fancyframe.FancyFrame;
import model.fancyframe.UsingGameObjectFancyFrameDecorator;
import model.objects.general.ATM;

import java.util.List;

public class WalkUpToATMAction extends WalkUpToElectricalMachineryAction {
    private final ATM atm;
    private final ATMFancyFrame ff;
    private final GameData gameData;

    public WalkUpToATMAction(Player cl, GameData gameData, ATM atm) {
        super(gameData, cl, atm);
        this.atm = atm;
        this.ff = new ATMFancyFrame(cl, gameData, atm);
        this.gameData = gameData;
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        super.setArguments(args, performingClient);
        if (performingClient instanceof Player && atm.isFancyFrameVacant()) {
            atm.setFancyFrameOccupied();
            performingClient.setCharacter(new UsingGameObjectFancyFrameDecorator(performingClient.getCharacter(), ff));
        } else {
            gameData.getChat().serverInSay(atm.getPublicName(performingClient) +
                    " is occupied right now, try again later.", (Player)performingClient);
        }
    }

    @Override
    protected FancyFrame getFancyFrame(GameData gameData, Actor performingClient) {
        return ff;
    }
}

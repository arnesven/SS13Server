package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.fancyframe.FancyFrame;
import model.fancyframe.SinglePersonUseMachineFancyFrame;
import model.fancyframe.UsingGameObjectFancyFrameDecorator;
import model.objects.SinglePersonUseMachine;

import java.util.List;

public abstract class WalkUpToSinglePersonUseMachineAction extends WalkUpToElectricalMachineryAction {
    private final SinglePersonUseMachine spm;
    //private final SinglePersonUseMachineFancyFrame ff;
    private final GameData gameData;

    public WalkUpToSinglePersonUseMachineAction(Player cl, GameData gameData, SinglePersonUseMachine atm) {
        super(gameData, cl, atm);
        this.spm = atm;
        //this.ff = spmff;
        this.gameData = gameData;
    }

    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        if (p instanceof Player && spm.isFancyFrameVacant()) {
            FancyFrame ff = getFancyFrame(gameData, p);
            p.setFancyFrame(ff);
            p.setCharacter(new UsingGameObjectFancyFrameDecorator(p.getCharacter(), ff, spm));
            spm.setFancyFrameOccupied();
        } else {
            gameData.getChat().serverInSay(spm.getPublicName(p) +
                    " is occupied right now, try again later.", (Player)p);
        }
    }

    //@Override
    //protected FancyFrame getFancyFrame(GameData gameData, Actor performingClient) {
    //    return ff;
    //}
}

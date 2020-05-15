package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.fancyframe.SlotMachineFancyFrame;
import model.items.NoSuchThingException;
import model.items.general.MoneyStack;
import model.items.general.ItemStackDepletedException;
import model.objects.general.SlotMachine;

import java.util.List;

/**
 * Created by erini02 on 17/11/16.
 */
public class SlotMachineAction extends Action {

    private final SlotMachine slots;
    private final SlotMachineFancyFrame fancyFrame;
    private int bettedAmount = 1;

    public SlotMachineAction(SlotMachine slots, SlotMachineFancyFrame ff) {
        super("Slot Machine", SensoryLevel.OPERATE_DEVICE);
        this.slots = slots;
        this.fancyFrame = ff;
    }

    public SlotMachineAction(SlotMachine slots) {
        this(slots, null);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "played on the slot machine";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        try {
            MoneyStack money = MoneyStack.getActorsMoney(whosAsking);
            for (int i : SlotMachine.getBetSizes()) {
                if (money.getAmount() >= i) {
                    opts.addOption("Bet $$ " + i);
                }
            }
        } catch (NoSuchThingException e) {

        }

        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        slots.play(gameData, performingClient, bettedAmount, fancyFrame);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

        String betStr = args.get(0).replace("Bet $$ ", "");
        this.bettedAmount = Integer.parseInt(betStr);
    }
}

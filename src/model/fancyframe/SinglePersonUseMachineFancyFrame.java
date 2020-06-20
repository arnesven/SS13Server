package model.fancyframe;

import model.GameData;
import model.Player;
import model.objects.SinglePersonUseMachine;

public class SinglePersonUseMachineFancyFrame extends FancyFrame {
    private final SinglePersonUseMachine spm;

    public SinglePersonUseMachineFancyFrame(Player cl, SinglePersonUseMachine spm) {
        super(cl.getFancyFrame());
        this.spm = spm;
    }

    @Override
    protected void beingDisposed() {
        super.beingDisposed();
        spm.setFancyFrameVacant();
    }

}

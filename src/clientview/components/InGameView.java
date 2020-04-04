package clientview.components;

import clientlogic.GameData;
import clientview.PlayersPanel;
import clientview.animation.AnimationHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InGameView extends JSplitPane  {

    private final LastTurnPanel ltp;
    private Box leftPanel = new Box(BoxLayout.X_AXIS);
    private MapPanel mp;
    private boolean inMapMode = true;

    public InGameView(GameUIPanel parent) {
        super(HORIZONTAL_SPLIT);
        mp = new MapPanel(parent);
        leftPanel.add(new JScrollPane(mp));
        this.add(leftPanel, 0);

        JSplitPane lp2 = new JSplitPane(VERTICAL_SPLIT);

        JTabbedPane jtp = new JTabbedPane();
        jtp.add("Map", new MiniMapPanel());
        jtp.add("Players", new PlayersPanel(GameData.getInstance().getClid()));
        lp2.add(new JScrollPane(jtp));
        ltp = new LastTurnPanel();
        lp2.add(ltp);
        lp2.setResizeWeight(1.0);
        lp2.setDividerLocation(250);

        this.add(lp2, 1);

        this.setDividerLocation(0.65);
        this.setResizeWeight(1.0);

        // // Animation timer...
        //Timer t = new Timer(100, new ActionListener() {
        //    @Override
        //    public void actionPerformed(ActionEvent actionEvent) {
        //        repaint();
        //    }
        //});
        //t.start();
    }

    public boolean isInMapMode() {
        return inMapMode;
    }

    public LastTurnPanel getLastTurnPanel() {
        return ltp;
    }

    public MapPanel getMapPanel() {
        return mp;
    }
}

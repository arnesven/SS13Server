package clientview.components;

import clientlogic.GameData;
import clientview.FancyFrame;
import clientview.FancyFrameComponent;
import clientview.PlayersPanel;
import main.SS13Client;

import javax.swing.*;

public class InGameView extends JSplitPane  {

    private final LastTurnPanel ltp;
    private final JTabbedPane jtp;
    private Box leftPanel = new Box(BoxLayout.X_AXIS);
    private MapPanel mp;
    private boolean inMapMode = true;

    public InGameView(GameUIPanel parent) {
        super(HORIZONTAL_SPLIT);
        mp = new MapPanel(parent);
        leftPanel.add(new JScrollPane(mp));
        this.add(leftPanel, 0);

        JSplitPane lp2 = new JSplitPane(VERTICAL_SPLIT);

        jtp = new JTabbedPane();
        jtp.add("Map", new MiniMapPanel());
        jtp.add("Players", new PlayersPanel(GameData.getInstance().getClid(), parent.getParentMain()));
        lp2.add(new JScrollPane(jtp));
        ltp = new LastTurnPanel();
        lp2.add(ltp);
        lp2.setResizeWeight(1.0);
        lp2.setDividerLocation(300);

        this.add(lp2, 1);

        this.setDividerLocation(SS13Client.ingameSize.width - 330);
        this.setResizeWeight(1.0);
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

    public void addDockedSmallWindow() {
        jtp.add("Small Window", new FancyFrameComponent());
        jtp.setSelectedIndex(2);
        revalidate();
        repaint();
    }

    public void removeDockedSmallWindow() {
        if (jtp.getTabCount() == 3) {
            jtp.removeTabAt(jtp.getTabCount() - 1);
            revalidate();
            repaint();
        }
    }
}

package clientview;

import clientlogic.GameData;
import clientview.animation.AnimationHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InGameView extends JSplitPane  {

    public static final int MAP_PERCENTAGE = 60;
    private final LastTurnPanel ltp;
    private Box leftPanel = new Box(BoxLayout.X_AXIS);
    private MapPanel mp;
    //private ActionPanel ap;
    private boolean inMapMode = true;

    public InGameView(GameUIPanel parent) {
        super(HORIZONTAL_SPLIT);
        //this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        mp = new MapPanel(parent);
    //    ap = new ActionPanel();
        leftPanel.add(new JScrollPane(mp));
        this.add(leftPanel, 0);

        JSplitPane lp2 = new JSplitPane(VERTICAL_SPLIT);
//        Box lp3 = new Box(BoxLayout.X_AXIS);
//        lp2.setResizeWeight(1.0);
//
        JTabbedPane jtp = new JTabbedPane();
        jtp.add("Map", new MiniMapPanel());
        jtp.add("Players", new PlayersPanel(GameData.getInstance().getClid()));
        lp2.add(new JScrollPane(jtp));
        ltp = new LastTurnPanel();
        lp2.add(ltp);
        lp2.setResizeWeight(1.0);
        lp2.setDividerLocation(250);
//
//        lp2.add(lp3, 0);
//        JScrollPane jsp = new JScrollPane(new PlayerInfoPanel());
//        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        lp2.add(jsp,1);
//
//        lp2.setDividerLocation(500);

       // lp2.setPreferredSize(new Dimension(400, 0));

        this.add(lp2, 1);

        this.setDividerLocation(0.65);
       // this.setDividerLocation(500);
        this.setResizeWeight(1.0);

        Timer t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AnimationHandler.step();
                repaint();
            }
        });
        t.start();

    }

    public boolean isInMapMode() {
        return inMapMode;
    }

//    public void toggleMapView() {
//        if (inMapMode) {
//            //leftPanel.remove(mp);
//            //leftPanel.add(ap);
//            mp.addActionPanel(ap);
//        } else {
//            //leftPanel.remove(ap);
//            //leftPanel.add(mp);
//            mp.removeActionPanel(ap);
//        }
//        inMapMode = !inMapMode;
//
//    }

    public LastTurnPanel getLastTurnPanel() {
        return ltp;
    }

    public MapPanel getMapPanel() {
        return mp;
    }
}

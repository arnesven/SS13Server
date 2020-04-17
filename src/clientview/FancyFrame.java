package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.FancyFrameHtmlPane;
import model.items.general.GameItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FancyFrame extends JFrame implements Observer {

    private static final int FF_WIDTH = 300;
    private static final int FF_HEIGHT = 250;
    private final JFrame parent;
    private final FancyFrameComponent ffc;
    private int lastState = 0;
    private boolean forceShow = false;

    public FancyFrame(JFrame parent) {
        this.setLocation((int)parent.getLocation().getX() + ((parent.getWidth() - FF_WIDTH)/2),
                (int)parent.getLocation().getY() + ((parent.getHeight() - FF_HEIGHT)/2));
        this.parent = parent;
        this.ffc = new FancyFrameComponent(this);
        this.add(ffc);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(new Dimension(FF_WIDTH, FF_HEIGHT));
        this.setTitle("Unknown");
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        GameData.getInstance().subscribe(this);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                forceShow = false;
                FancyFrame.this.setVisible(false);
                ffc.serverSend("FANCYFRAME EVENT DISMISS");
            }
        });
    }


    @Override
    public void update() {
        String data = GameData.getInstance().getFancyFrameContent();
        this.setTitle(GameData.getInstance().getFancyFrameTitle());
        if (data.startsWith("BLANK")) {
            if (isVisible()) {
                System.out.println("Fancy frame went from some content to blank, hiding it.");
                if (forceShow) {
                    System.out.println("But force show is on");
                } else {
                    this.setVisible(false);
                }
            }
        } else {
            if (!isVisible()) {
                System.out.println("Showing fancy frame.");
                this.setVisible(true);
            }
            repaint();
        }

        if (forceShow) {
            setVisible(true);
        }
    }

}

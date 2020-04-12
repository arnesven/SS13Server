package clientview;

import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.FancyFrameHtmlPane;

import javax.swing.*;
import java.awt.*;

public class FancyFrame extends JFrame implements Observer {

    private static final int FF_WIDTH = 300;
    private static final int FF_HEIGHT = 250;
    private final FancyFrameHtmlPane jed;
    private JTextField inputField;
    private int lastState = 0;
    private boolean forceShow = false;

    public FancyFrame(JFrame parent) {
        this.setLocation((int)parent.getLocation().getX() + ((parent.getWidth() - FF_WIDTH)/2),
                (int)parent.getLocation().getY() + ((parent.getHeight() - FF_HEIGHT)/2));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(new Dimension(FF_WIDTH, FF_HEIGHT));
        this.setTitle("Unknown");
        this.setResizable(false);
        this.jed = new FancyFrameHtmlPane();
        jed.setMargin(new Insets(0,0,0,0));
        this.add(jed);
        inputField = new JTextField();
        GameData.getInstance().subscribe(this);
    }



    @Override
    public void update() {
        String data = GameData.getInstance().getFancyFrameData();
        if (data.startsWith("BLANK")) {
            if (isVisible()) {
                this.setVisible(false);
                lastState = GameData.getInstance().getFancyFrameState();
            }
        } else {
            if (!isVisible() && GameData.getInstance().getFancyFrameState() != lastState) {
                makeContent(data);
                this.setVisible(true);
                repaint();
                lastState = GameData.getInstance().getFancyFrameState();
            }
        }

        if (forceShow) {
            setVisible(true);
        }

    }

    private void makeContent(String data) {
        String[] parts = data.split("<part>");
        this.setTitle(parts[0]);
        if (parts[1].equals("HAS INPUT")) {
            this.add(inputField, BorderLayout.SOUTH);
        }
        jed.setText(parts[2]);
    }
}

package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.FancyFrameHtmlPane;
import clientview.components.MyHtmlPane;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class FancyFrame extends JFrame implements Observer {

    private static final int FF_WIDTH = 300;
    private static final int FF_HEIGHT = 250;
    private final FancyFrameHtmlPane jed;
    private JTextField inputField;
    private int lastState = 0;

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
            }
        } else {
            if (!isVisible() && GameData.getInstance().getFancyFrameState() != lastState) {
                makeContent(data);
                this.setVisible(true);
                repaint();
            }
        }
        lastState = GameData.getInstance().getFancyFrameState();
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

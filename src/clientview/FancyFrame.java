package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;

public class FancyFrame extends JFrame implements Observer {

    private final JEditorPane jed;
    private JTextField inputField;
    private int lastState = 0;

    public FancyFrame(JFrame parent) {
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(new Dimension(300, 250));
        this.setTitle("Unknown");
        this.jed = new JEditorPane();
        jed.setEditable(false);
        jed.setContentType("text/html");
        jed.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    System.out.println("Got URL: " + e.getURL());
                    String command = e.getURL().toString().replace("https://", "");
                    ServerCommunicator.send(GameData.getInstance().getClid() + " FANCYFRAME EVENT " + command, new MyCallback() {
                        @Override
                        public void onSuccess(String result) {

                        }

                        @Override
                        public void onFail() {

                        }
                    });
                }
            }
        });
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

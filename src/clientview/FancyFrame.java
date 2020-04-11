package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;

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
    private final JEditorPane jed;
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
        this.jed = new JEditorPane();
        jed.setEditable(false);
        jed.setMargin(new Insets(0,0,0,0));
        jed.setContentType("text/html");
        jed.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    System.out.println("Got URL: " + e.getURL());
                    gotMouseClick(e.getURL());

                }
            }
        });
        this.add(jed);
        inputField = new JTextField();
        GameData.getInstance().subscribe(this);
    }

    private void gotMouseClick(URL url) {
        if (url.toString().contains("EVENT")) {
            String command = url.toString().replace("https://EVENT.", "");
            ServerCommunicator.send(GameData.getInstance().getClid() + " FANCYFRAME EVENT " + command, new MyCallback() {
                @Override
                public void onSuccess(String result) {
                    GameData.getInstance().deconstructFancyFrameData(result);
                }

                @Override
                public void onFail() {
                    System.out.println("Client: Failed during FANCYFRAME EVENT");
                }
            });
        } else {
            try {
                Desktop.getDesktop().browse(url.toURI());
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
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

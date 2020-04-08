package clientview.components;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class ServerInfoPanel extends JPanel {

    private final JEditorPane jed;

    public ServerInfoPanel() {
        this.setLayout(new BorderLayout());
        jed = new JEditorPane();
        jed.setContentType("text/html");
        jed.setEditable(false);
        jed.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    System.out.println(e.getURL());
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        JScrollPane jsp = new JScrollPane(jed);
        this.add(jsp);
    }

    public void addContent(String result) {
        jed.setText(result);
        jed.setCaretPosition(0);
    }
}

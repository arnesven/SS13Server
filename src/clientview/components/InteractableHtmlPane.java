package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;

import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class InteractableHtmlPane extends MyHtmlPane {

    private final String command;
    private final MyCallback callback;

    public InteractableHtmlPane(String command, MyCallback callback) {
        this.command = command;
        this.callback = callback;
    }

    @Override
    protected void handleHyperLinkEvent(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            URL url = e.getURL();
            if (url.toString().contains("EVENT")) {
                String command = url.toString().replace("https://EVENT.", "");
                ServerCommunicator.send(GameData.getInstance().getClid() + " " + this.command + " EVENT " + command, callback);
            } else {
               super.handleHyperLinkEvent(e);
            }
        }
    }


}

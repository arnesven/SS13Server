package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;

import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class FancyFrameHtmlPane extends MyHtmlPane {

    @Override
    protected void handleHyperLinkEvent(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            URL url = e.getURL();
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
               super.handleHyperLinkEvent(e);
            }
        }
    }


}

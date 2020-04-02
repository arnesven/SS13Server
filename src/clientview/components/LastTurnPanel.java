package clientview.components;

import clientlogic.GameData;
import clientlogic.Observer;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class LastTurnPanel extends JPanel implements Observer {

    //private ScrollPanel scrollPane;
	private static StringBuffer previous = new StringBuffer();
	private static JEditorPane pane;
    private final ChatInputField inputField;
    private String last = "";

	public LastTurnPanel() {
	    this.setLayout(new BorderLayout());
	    //LayoutPanel p = new LayoutPanel();
		//scrollPane = new ScrollPanel();
		//this.getElement().getStyle().setFontSize(12, Unit.PT);
		//scrollPane.setAlwaysShowScrollBars(true);

        pane = new JEditorPane();


        //this.setPreferredSize(new Dimension(300, -1));
        pane.setEditable(false);
        pane.addHyperlinkListener(new HyperlinkListener() {
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
		this.add(new JScrollPane(pane));
		this.inputField = new ChatInputField();
		this.add(inputField, BorderLayout.SOUTH);
		GameData.getInstance().subscribe(this);
	}


    @Override
	public void update() {
	        String res = "";
            for (String line : GameData.getInstance().getLastTurnInfo()) {
                res += line + "<br/>";
            }

            if (!last.equals(res)) {
                pane.setContentType("text/html");
                //content.append(res);
                previous.append(res);
                pane.setText(previous.toString());
                pane.setPreferredSize(new Dimension(300, this.getHeight()-inputField.getHeight()));
               // this.setPreferredSize(new Dimension(300, pane.getHeight()));
                last = res;
            }
            revalidate();
	}

    public static void addToChatMessages(String[] split) {
	    if (pane == null) {
	        return;
        }

        String result = "";
        for (String s : split) {
            result += s + "<br/>";
        }
        //System.out.println("Added chat stuff: " + result);
        previous.append(result);

        pane.setContentType("text/html");
        pane.setText(previous.toString());
        pane.setFont(new Font("Courier New", Font.PLAIN, 12));
        //chatArea.scrollToBottom();
    }
//

	public void clear() {
	    previous = new StringBuffer();
    }

}

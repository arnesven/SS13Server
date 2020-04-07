package clientview.components;

import javax.swing.*;
import java.awt.*;

public class ServerInfoPanel extends JPanel {

    private final JEditorPane jed;

    public ServerInfoPanel() {
        this.setLayout(new BorderLayout());
        jed = new JEditorPane();
        jed.setContentType("text/html");
        jed.setEditable(false);
        JScrollPane jsp = new JScrollPane(jed);
        this.add(jsp);
    }

    public void addContent(String result) {
        jed.setText(result);
    }
}

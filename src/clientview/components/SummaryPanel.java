package clientview.components;

import clientlogic.GameData;
import clientlogic.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SummaryPanel extends JPanel implements Observer {

    private JEditorPane jed;
    private String old = "";

    public SummaryPanel() {
        setLayout(new BorderLayout());
        jed = new MyHtmlPane();
        jed.setContentType("text/html");
        jed.setEditable(false);
        JScrollPane jsp = new JScrollPane(jed);
        this.add(jsp);
        JButton button = new JButton("View In Browser");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeFileAndBrowser();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(button);
        this.add(panel, BorderLayout.SOUTH);
        GameData.getInstance().subscribe(this);
    }

    private void writeFileAndBrowser() {
        File file;
        try {
            file = File.createTempFile("last_summary", ".html");
            FileWriter writer = new FileWriter(file);
            writer.write(GameData.getInstance().getSummaryString());
            writer.close();
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (!GameData.getInstance().getSummaryString().equals(old)) {
            jed.setText(GameData.getInstance().getSummaryString());
            jed.setCaretPosition(0);
            this.old = GameData.getInstance().getSummaryString();
        }
    }

    @Override
    public void unregisterYourself() {
        GameData.getInstance().unsubscribe(this);
    }
}

package clientview;

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

    public SummaryPanel() {
        JButton button = new JButton("See Summary");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeFileAndBrowser();
            }
        });
        this.add(button);
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


    }
}

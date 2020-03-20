package clientview;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReconnectDialog extends MyDialog {

    private final Timer timer;

    public ReconnectDialog(SS13Client owner) {
        super(owner, "Reconnect",
                new Dimension(200, 70), Color.WHITE,false);
        super.setPaneText("Reconnecting in 5...");


        this.timer = new Timer(1000, new ActionListener() {

            int secs = 4;

            @Override
            public void actionPerformed(ActionEvent e) {
                ReconnectDialog.super.setPaneText("Reconnecting in " + secs-- + "...");
                if (secs == -1) {
                    timer.stop();
                    ReconnectDialog.this.dispose();
                }
            }
        });
        timer.start();

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Ran windowadapters windowclosing");
                timer.stop();
            }


        });
    }

}

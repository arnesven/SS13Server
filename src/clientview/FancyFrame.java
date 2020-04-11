package clientview;

import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.FancyFrameHtmlPane;

import javax.swing.*;
import java.awt.*;

public class FancyFrame extends JFrame implements Observer {

    private static final int FF_WIDTH = 300;
    private static final int FF_HEIGHT = 250;
    private final FancyFrameHtmlPane jed;
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
        this.jed = new FancyFrameHtmlPane();
        jed.setMargin(new Insets(0,0,0,0));
        this.add(jed);
        inputField = new JTextField();
        GameData.getInstance().subscribe(this);
    }



    @Override
    public void update() {
      //  System.out.println("Fancy frame update!");
        String data = GameData.getInstance().getFancyFrameData();
        if (data.startsWith("BLANK")) {
         //   System.out.println("Fancy frame is blank");
            if (isVisible()) {
          //      System.out.println("But visible, so hiding it");
                this.setVisible(false);
                lastState = GameData.getInstance().getFancyFrameState();
            }
        } else {
        //    System.out.println("Fancy frame has content, last state is: " + lastState);
        //    System.out.println("...and game data says it is " + GameData.getInstance().getFancyFrameState());
            if (!isVisible() && GameData.getInstance().getFancyFrameState() != lastState) {
         //       System.out.println("Was not visibile and we need to turn it on");
                makeContent(data);
                this.setVisible(true);
                repaint();
                lastState = GameData.getInstance().getFancyFrameState();
            }
        }

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

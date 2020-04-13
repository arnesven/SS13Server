package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.FancyFrameHtmlPane;
import model.items.general.GameItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FancyFrame extends JFrame implements Observer {

    private static final int FF_WIDTH = 300;
    private static final int FF_HEIGHT = 250;
    private final FancyFrameHtmlPane jed;
    private final JFrame parent;
    private JTextField inputField;
    private int lastState = 0;
    private boolean forceShow = false;

    public FancyFrame(JFrame parent) {
        this.setLocation((int)parent.getLocation().getX() + ((parent.getWidth() - FF_WIDTH)/2),
                (int)parent.getLocation().getY() + ((parent.getHeight() - FF_HEIGHT)/2));
        this.parent = parent;
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(new Dimension(FF_WIDTH, FF_HEIGHT));
        this.setTitle("Unknown");
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.jed = new FancyFrameHtmlPane();
        jed.setMargin(new Insets(0,0,0,0));
        this.add(new JScrollPane(jed));
        inputField = new JTextField();
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.YELLOW);
        GameData.getInstance().subscribe(this);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Got action event from input field!");
                String inputData = inputField.getText();
                handleInputEvent(inputData);
                inputField.setText("");
            }
        });

        jed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseEvent(e.getX(), e.getY());
            }
        });

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                forceShow = false;
                FancyFrame.this.setVisible(false);
                serverSend("FANCYFRAME EVENT DISMISS");
            }
        });

    }

    private void serverSend(String mess) {
        ServerCommunicator.send(GameData.getInstance().getClid() + " " + mess, new MyCallback() {
            @Override
            public void onSuccess(String result) {
                GameData.getInstance().deconstructFancyFrameData(result);
            }

            @Override
            public void onFail() {
                System.out.println("Failed during " + mess);
            }
        });
    }

    private void handleMouseEvent(int x, int y) {
        serverSend("FANCYFRAME CLICK " + x + " " + y);
    }

    private void handleInputEvent(String inputData) {
        serverSend("FANCYFRAME INPUT " + inputData);
    }


    @Override
    public void update() {
        String data = GameData.getInstance().getFancyFrameData();
        if (data.startsWith("BLANK")) {
            if (isVisible()) {
                System.out.println("Fancy frame went from some content to blank, hiding it.");
                jed.setText("");
                jed.setBackground(Color.WHITE);
                this.setVisible(false);
                lastState = GameData.getInstance().getFancyFrameState();
            }
        } else {
            if (GameData.getInstance().getFancyFrameState() != lastState) {
                System.out.println("Fancy frame got new content.");
                makeContent(data);
                if (!isVisible()) {
                    System.out.println("Showing fancy frame.");
                    this.setVisible(true);
                }
                repaint();
                lastState = GameData.getInstance().getFancyFrameState();
            }
        }

        if (forceShow) {
            setVisible(true);
        }

    }

    private void makeContent(String data) {
        System.out.println("Making content, data is: " + data);
        String[] parts = data.split("<part>");
        this.setTitle(parts[0]);
        if (parts[1].equals("HAS INPUT")) {
            this.add(inputField, BorderLayout.SOUTH);
        } else {
            this.remove(inputField);
        }
        jed.setText(parts[2]);
        jed.setCaretPosition(0);
        String[] dim = parts[3].split(":");
        this.setSize(Integer.parseInt(dim[0]), Integer.parseInt(dim[1]));
        revalidate();
        repaint();
      //  this.setLocation((int)parent.getLocation().getX() + ((parent.getWidth() - getWidth())/2),
      //          (int)parent.getLocation().getY() + ((parent.getHeight() - getHeight())/2));
    }
}

package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.FancyFrameHtmlPane;
import model.items.general.GameItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FancyFrame extends JFrame implements Observer {

    private static final int FF_WIDTH = 300;
    private static final int FF_HEIGHT = 250;
    private final FancyFrameHtmlPane jed;
    private JTextField inputField;
    private int lastState = 0;
    private boolean forceShow = false;

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
        this.add(new JScrollPane(jed));
        inputField = new JTextField();
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.GREEN);
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
    }

    private void handleMouseEvent(int x, int y) {
        ServerCommunicator.send(GameData.getInstance().getClid() + " FANCYFRAME CLICK " + x + " " + y, new MyCallback() {
            @Override
            public void onSuccess(String result) {
                GameData.getInstance().deconstructFancyFrameData(result);
            }

            @Override
            public void onFail() {
                System.out.println("Failed during FANCYFRAME CLICK");
            }
        });
    }

    private void handleInputEvent(String inputData) {
        ServerCommunicator.send(GameData.getInstance().getClid() + " FANCYFRAME INPUT " + inputData, new MyCallback() {
            @Override
            public void onSuccess(String result) {
                GameData.getInstance().deconstructFancyFrameData(result);
            }

            @Override
            public void onFail() {
                System.out.println("Failed during FANCYFRAME INPUT");
            }
        });
    }


    @Override
    public void update() {
        String data = GameData.getInstance().getFancyFrameData();
        if (data.startsWith("BLANK")) {
            if (isVisible()) {
                this.setVisible(false);
                lastState = GameData.getInstance().getFancyFrameState();
            }
        } else {
            if (!isVisible() && GameData.getInstance().getFancyFrameState() != lastState) {
                makeContent(data);
                this.setVisible(true);
                repaint();
                lastState = GameData.getInstance().getFancyFrameState();
            }
        }

        if (forceShow) {
            setVisible(true);
        }

    }

    private void makeContent(String data) {
        String[] parts = data.split("<part>");
        this.setTitle(parts[0]);
        if (parts[1].equals("HAS INPUT")) {
            this.add(inputField, BorderLayout.SOUTH);
        }
        jed.setText(parts[2]);
        jed.setCaretPosition(0);
    }
}

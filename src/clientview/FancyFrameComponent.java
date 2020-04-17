package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.FancyFrameHtmlPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FancyFrameComponent extends JComponent implements Observer {

    private final FancyFrameHtmlPane jed;
    private JTextField inputField;
    private String oldcontent = "";


    public FancyFrameComponent() {
        this.setLayout(new BorderLayout());
        this.jed = new FancyFrameHtmlPane();
        jed.setMargin(new Insets(0,0,0,0));
        this.add(new JScrollPane(jed));
        inputField = new JTextField();
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.YELLOW);
        setupListeners();
        GameData.getInstance().subscribe(this);
    }

    private void setupListeners() {
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Got action event from input field!");
                String inputData = inputField.getText();
                handleInputEvent(inputData);
                inputField.setText("");
                inputField.grabFocus();
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
        serverSend("FANCYFRAME CLICK " + x + " " + y);
    }

    private void handleInputEvent(String inputData) {
        serverSend("FANCYFRAME INPUT " + inputData);
    }

    protected void serverSend(String mess) {
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

    @Override
    public void update() {
        String content = GameData.getInstance().getFancyFrameContent();

        if (!content.equals(this.oldcontent)) {
            System.out.println("Fancy frame got new content");
            jed.setText(content);
            oldcontent = content;
            jed.setCaretPosition(0);

            if (content.equals("BLANK")) {
                jed.setText(content);
                jed.setBackground(Color.BLACK);
            } else {

            }

            if (GameData.getInstance().getFancyFrameInputField()) {
                this.add(inputField, BorderLayout.SOUTH);
                System.out.println("Added input field");
            } else {
                this.remove(inputField);
                System.out.println("Removed input field");
            }

            this.setPreferredSize(GameData.getInstance().getFancyFrameDimensions());
            this.setMaximumSize(GameData.getInstance().getFancyFrameDimensions());
            revalidate();
            repaint();
        }
    }
}

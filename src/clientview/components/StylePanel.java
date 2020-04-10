package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.Cookies;
import clientlogic.GameData;
import main.SS13Client;
import model.items.general.GameItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

public class StylePanel extends Box {
    private final SS13Client parent;
    private StyleDrawingArea previewArea;
    private boolean loaded;

    public StylePanel(String username, SS13Client parent) {
        super(BoxLayout.X_AXIS);
        setAlignmentX(CENTER_ALIGNMENT);
        this.parent = parent;
        this.loaded = false;
    }


    public void load() {
        if (!loaded) {
            ServerCommunicator.send(GameData.getInstance().getClid() + " STYLE LOAD", new MyCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    GameData.getInstance().getStyle().parseStyleData(result);
                }

                @Override
                public void onFail() {
                    System.out.println("Failed to send STYLE LOAD message to server");
                }
            });
            setStyleFromCookies();
            JPanel controlPanel = makeControlPanel();
            controlPanel.setAlignmentX(CENTER_ALIGNMENT);
            this.add(controlPanel, 0);

            this.previewArea = new StyleDrawingArea();
            this.add(previewArea, 1);
            parent.repaint();
            this.loaded = true;
        }
    }

    private void setStyleFromCookies() {
        if (Cookies.getCookie("selectedgender") != null) {
            String gender = Cookies.getCookie("selectedgender");
            GameData.getInstance().getStyle().setSelectedGender(gender.equals("man"));
            sendToServer("GENDER", gender.toUpperCase());
        }

        if (Cookies.getCookie("selectedhair") != null) {
            int hairNum = Integer.parseInt(Cookies.getCookie("selectedhair"));
            GameData.getInstance().getStyle().setSelectedHair(hairNum);
            sendToServer("HAIR", hairNum+"");
        }

        if (Cookies.getCookie("selectedface") != null) {
            int faceNum = Integer.parseInt(Cookies.getCookie("selectedface"));
            GameData.getInstance().getStyle().setSelectedFacialHair(faceNum);
            sendToServer("FACE", faceNum+"");
        }

        if (Cookies.getCookie("selectedhcolor") != null) {
            Scanner scan = new Scanner(Cookies.getCookie("selectedhcolor"));
            Color col = new Color(scan.nextInt(), scan.nextInt(), scan.nextInt());
            GameData.getInstance().getStyle().setSelectedHairColor(col);
            sendToServer("HCOLOR", col.getRed() + " " + col.getGreen() + " " + col.getBlue());
        }

        if (Cookies.getCookie("selectedfcolor") != null) {
            Scanner scan = new Scanner(Cookies.getCookie("selectedfcolor"));
            Color col = new Color(scan.nextInt(), scan.nextInt(), scan.nextInt());
            GameData.getInstance().getStyle().setSelectedFacialHairColor(col);
            sendToServer("FCOLOR", col.getRed() + " " + col.getGreen() + " " + col.getBlue());
        }
    }

    private JPanel makeControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        {
            Box genderBox = new Box(BoxLayout.X_AXIS);
            genderBox.add(Box.createHorizontalGlue());
            ButtonGroup bg = new ButtonGroup();
            JRadioButton manButton = new GenderButton("Man", bg);
            genderBox.add(manButton);
            JRadioButton womanButton = new GenderButton("Woman", bg);
            genderBox.add(Box.createHorizontalGlue());
            genderBox.add(womanButton);
            genderBox.add(Box.createHorizontalGlue());
            controlPanel.add(genderBox);
            if (GameData.getInstance().getStyle().getSelectedGender()) {
                manButton.setSelected(true);
            } else {
                womanButton.setSelected(true);
            }
        }

        List<String> hairs = GameData.getInstance().getStyle().getHairList();
        JPanel hairPanel = new JPanel(new GridLayout(hairs.size()/10, 11));
        ButtonGroup bg = new ButtonGroup();
        int index = 0;
        for (String hair : hairs) {
            hairPanel.add(new StyleButton("HAIR", index, hair, index == GameData.getInstance().getStyle().getSelectedHair(), bg));
            index++;

        }
        controlPanel.add(hairPanel);

        Box colorBox = new Box(BoxLayout.X_AXIS);
        colorBox.add(Box.createHorizontalGlue());
        StyleColorButton colorButton = new StyleColorButton("Hair Color", "HCOLOR", GameData.getInstance().getStyle().getSelectedHairColor());
        colorBox.add(colorButton);
        colorBox.add(Box.createHorizontalGlue());
        JButton copyColor = new JButton(" => ");
        colorBox.add(copyColor);
        colorBox.add(Box.createHorizontalGlue());
        StyleColorButton scb = new StyleColorButton("Facial Hair Color", "FCOLOR", GameData.getInstance().getStyle().getSelectedFacialHairColor());
        colorBox.add(scb);
        colorBox.add(Box.createHorizontalGlue());
        controlPanel.add(colorBox);

        copyColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scb.setSelectedColor(GameData.getInstance().getStyle().getSelectedHairColor());
            }
        });

        index = 0;
        ButtonGroup bg2 = new ButtonGroup();
        List<String> faces = GameData.getInstance().getStyle().getFacialList();
        JPanel facialPanel = new JPanel(new GridLayout(faces.size() / 10, 11));
        for (String facial : faces) {
            facialPanel.add(new StyleButton("FACE", index, facial, index == GameData.getInstance().getStyle().getSelectedFacialHair(), bg2));
            index++;
        }
        controlPanel.add(facialPanel);
        controlPanel.add(Box.createVerticalGlue());
        return controlPanel;
    }


    public static void sendToServer(String type, String hairNum) {
        ServerCommunicator.send(GameData.getInstance().getClid() + " STYLE SET " + type + " " + hairNum, new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println("Got result " + result);
                GameData.getInstance().getStyle().setPreviewSpriteName(result);
                //repaint();
            }

            @Override
            public void onFail() {
                System.out.println("Failed to send STYLE SET message to server");
            }
        });
    }


}

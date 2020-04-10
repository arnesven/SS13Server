package clientview.components;


import clientlogic.GameData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StyleColorButton extends JButton {
    private final String type;

    public StyleColorButton(String title, String type, Color initial) {
        super(title);
        this.setBackground(initial);
        this.setPreferredSize(new Dimension(300, 50));
        this.setFont(new Font("Arial", Font.BOLD, 16));
        setAntiColor(initial);
        this.type = type;

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color selected = JColorChooser.showDialog(null, title, StyleColorButton.this.getBackground());
                if (selected != null) {
                    setSelectedColor(selected);
                    GameData.getInstance().getStyle().setSelectedHairColor(selected);
                }
            }
        });
    }

    private void setAntiColor(Color selected) {
        if (isDark(selected)) {
            StyleColorButton.this.setForeground(Color.WHITE);
        } else {
            StyleColorButton.this.setForeground(Color.BLACK);
        }
    }

    private boolean isDark(Color c) {
        return c.getRed() + c.getBlue() + c.getGreen() < 300;
    }

    public void setSelectedColor(Color selected) {
        StylePanel.sendToServer(type, selected.getRed() + " " + selected.getGreen() + " " + selected.getBlue());
        StyleColorButton.this.setBackground(selected);
        setAntiColor(selected);
    }
}

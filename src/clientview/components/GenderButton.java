package clientview.components;

import clientlogic.Cookies;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenderButton extends JRadioButton {
    public GenderButton(String man, ButtonGroup bg) {
        super(man);
        bg.add(this);
        this.setFont(new Font("Arial", Font.BOLD, 16));
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StylePanel.sendToServer("GENDER", man.toUpperCase());
                Cookies.setCookie("selectedgender", man.toLowerCase());
            }
        });
    }
}

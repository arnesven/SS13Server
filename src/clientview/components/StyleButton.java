package clientview.components;

import clientview.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StyleButton extends JToggleButton {
    public StyleButton(String type, int index, String spriteName, boolean selected, ButtonGroup bg) {
        this.setIcon(SpriteManager.getSprite(spriteName));
        this.setSelected(selected);
        bg.add(this);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StylePanel.sendToServer(type, index +"");
            }
        });
    }


}

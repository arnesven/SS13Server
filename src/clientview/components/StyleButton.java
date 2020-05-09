package clientview.components;

import clientlogic.Cookies;
import clientview.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StyleButton extends JToggleButton {
    public StyleButton(String type, int index, String spriteName, boolean selected, ButtonGroup bg) {
        ImageIcon copy = new ImageIcon(SpriteManager.getSprite(spriteName, 32).getImage());
        this.setIcon(copy);
        //this.setIcon(SpriteManager.getSprite(spriteName, 64));
        this.setSelected(selected);
        bg.add(this);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StylePanel.sendToServer(type, index +"");
                Cookies.setCookie("selected" + type.toLowerCase(), index+"");
            }
        });
    }


}

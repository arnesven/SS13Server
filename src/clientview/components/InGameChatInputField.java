package clientview.components;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InGameChatInputField extends Box {

    private ExtendedChatInputField cif;

    public InGameChatInputField() {
        super(BoxLayout.Y_AXIS);
        Box innerBox = new Box(BoxLayout.X_AXIS);
        JCheckBox overRadio = new JCheckBox("Over the radio");
        JCheckBox inChar = new JCheckBox("Speak in character");
        inChar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (inChar.isSelected()) {
                    overRadio.setEnabled(true);
                } else {
                    overRadio.setEnabled(false);
                }
            }
        });
        inChar.setSelected(true);
        overRadio.setSelected(true);
        innerBox.add(inChar);
        innerBox.add(Box.createHorizontalStrut(3));
        innerBox.add(overRadio);
        this.add(innerBox);
        cif = new ExtendedChatInputField(inChar, overRadio);
        this.add(cif);
    }
}

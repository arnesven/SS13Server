package clientview.components;

import javax.swing.*;

public class ExtendedChatInputField extends ChatInputField {

    private final JCheckBox inCharCheckBox;
    private final JCheckBox overRadio;

    public ExtendedChatInputField(JCheckBox inChar, JCheckBox overRadio) {
        this.inCharCheckBox = inChar;
        this.overRadio = overRadio;
    }

    @Override
    public void sendToServer(String mess) {
        if (inCharCheckBox.isSelected()) {
            if (overRadio.isSelected()) {
                super.sendToServer("/rinsay " + mess);
            } else {
                super.sendToServer("/insay " + mess);
            }
        } else {
            super.sendToServer(mess);
        }
    }
}

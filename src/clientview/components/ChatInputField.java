package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatInputField extends JTextField {

    public ChatInputField() {
        setFocusable(true);
        //grabFocus();
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent event) {
                if (event.getKeyChar() == '\n') {
                    if (getText() != "") {
                        sendToServer(getText().replaceAll("<.+?>", ""));
                        setText("");
                    }
                }
            }
        });
    }

    public void sendToServer(String mess) {
        ServerCommunicator.send(GameData.getInstance().getClid() + " CHATPUT " +
                mess, new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFail() {
                System.out.println("Failed to send CHATPUT message to server");
            }

        });
    }

}

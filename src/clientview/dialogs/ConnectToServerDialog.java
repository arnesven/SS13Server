package clientview.dialogs;

import clientlogic.Cookies;
import clientlogic.GameData;
import main.SS13Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectToServerDialog extends JDialog {

    private final JCheckBox returnBox;
    private final JTextField nameTextField;
    private final JTextField serverField;

    public ConnectToServerDialog(SS13Client parent) {
        super(parent, "Connect to server", true);
        setSize(new Dimension(300, 130));
        setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());
        {
            Box box = new Box(BoxLayout.X_AXIS);
            JButton button = new JButton("Connect");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (verify()) {
                        parent.cont(returnBox.isSelected(), false, nameTextField.getText());
                    }
                    dispose();
                }
            });
            box.add(Box.createHorizontalGlue());
            JButton cancel = new JButton("Cancel");
            box.add(cancel);
            box.add(Box.createHorizontalStrut(5));
            box.add(button);
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    dispose();
                }
            });
            box.add(Box.createHorizontalGlue());
            this.add(box, BorderLayout.SOUTH);
        }

        {
            Box box = new Box(BoxLayout.Y_AXIS);

            {
                Box fieldBox = new Box(BoxLayout.X_AXIS);
                fieldBox.add(new JLabel("Are you rejoining?"));
                this.returnBox = new JCheckBox("", false);
                fieldBox.add(returnBox);
                box.add(fieldBox);
            }
            box.add(Box.createVerticalStrut(3));


            {
                Box fieldBox = new Box(BoxLayout.X_AXIS);
                fieldBox.add(new JLabel("Player Name: "));
                this.nameTextField = new JTextField(Cookies.getCookie("last_user_name"));
                fieldBox.add(this.nameTextField);
                box.add(fieldBox);
            }
            box.add(Box.createVerticalStrut(3));

            {
                Box fieldBox = new Box(BoxLayout.X_AXIS);
                fieldBox.add(new JLabel("Server (name:port): "));
                this.serverField = new JTextField(Cookies.getCookie("default_server"));
                fieldBox.add(this.serverField);
                box.add(fieldBox);
            }
            box.add(Box.createVerticalStrut(3));

            this.add(box);
        }

        this.setVisible(true);
    }

    private boolean verify() {
        String name = nameTextField.getText();
        if (!name.matches("[a-zA-Z]*?") || name.equals("")) {
            JOptionPane.showMessageDialog(this, "Illegal user name");
            return false;
        }
        Cookies.setCookie("last_user_name", name);
        String result = serverField.getText();
        if (result.contains(":")) {
            String[] parts = result.split(":");
            int port;
            try {
                port = Integer.parseInt(parts[1]);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Illegal server name");
                return false;
            }
            GameData.getInstance().setHost(parts[0]);
            GameData.getInstance().setPort(port);
            Cookies.setCookie("default_server", result);
        } else {
            JOptionPane.showMessageDialog(this, "Illegal server name");
            return false;
        }
        return true;
    }


}

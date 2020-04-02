package clientview.dialogs;

import clientview.components.MyHtmlPane;

import javax.swing.*;
import java.awt.*;

public class MyDialog extends JDialog {

    private JEditorPane pane;

    public MyDialog(JFrame owner, String title, Dimension size, Color background, boolean hasInputField) {
        super(owner, title, ModalityType.MODELESS);
        this.setLayout(new BorderLayout());
        this.setSize(size);
        this.setLocation(new Point(owner.getX() + (owner.getWidth() - (int)size.getWidth()) / 2,
                owner.getY() + (owner.getHeight() - (int)size.getHeight()) / 2));
        setResizable(false);

        pane = new MyHtmlPane();
        pane.setEditable(false);
        pane.setBackground(background);
        pane.setContentType("text/html");
        this.add(pane);

        JTextField input = new JTextField();

        if (hasInputField) {
            this.add(input, BorderLayout.SOUTH);
        }

        setVisible(true);
    }


    protected void setPaneText(String s) {
        pane.setContentType("text/html");
        pane.setFont(new Font("Courier New", Font.PLAIN, 12));
        pane.setText(s);
        repaint();
        revalidate();

    }
}

package clientview.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModePanel extends JComponent {
    private final JTextField propField;
    private final JCheckBox check;
    private final JLabel nameLabel;
    private final JTextArea descr;

    public ModePanel(String name, String description, boolean startsEnabled, ButtonGroup buttonGroup) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        check = new JCheckBox();
        this.add(Box.createHorizontalStrut(5));
        this.add(check);
        buttonGroup.add(check);

        this.add(Box.createHorizontalStrut(5));
        nameLabel = new JLabel(name);
        nameLabel.setMinimumSize(new Dimension(100, 20));
        nameLabel.setPreferredSize(new Dimension(100, 20));
        this.add(nameLabel);
        descr = new JTextArea();
        descr.setText(description);
        descr.setEditable(false);
        descr.setLineWrap(true);
        descr.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(new JScrollPane(descr));

        propField = new JTextField(5);
        propField.setHorizontalAlignment(SwingConstants.RIGHT);
        propField.setMaximumSize(new Dimension(30 , 30));
        this.add(Box.createHorizontalStrut(5));
        this.add(propField);
        this.add(Box.createHorizontalStrut(5));

        if (!startsEnabled) {
            disableAll();
        }
    }

    private void disableAll() {
        propField.setEnabled(false);
    }

    public void setProbability(double v) {
        propField.setText(String.format("%1.1f", v*100) + "%");
    }

    public JCheckBox getCheckBox() {
        return check;

    }

    public void addActionListener(ActionListener actionListener) {
        check.addActionListener(actionListener);
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionListener.actionPerformed(null);
            }
        };
        //nameLabel.addMouseListener(adapter);
        descr.addMouseListener(adapter);
        this.addMouseListener(adapter);
    }
}

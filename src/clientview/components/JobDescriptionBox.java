package clientview.components;

import clientlogic.GameData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JobDescriptionBox extends Box {
    final JCheckBox cb;
    private final int columns;
    private final JobsPanel parent;


    public JobDescriptionBox(String s, String description, int columns, JobsPanel jobsPanel) {
        super(BoxLayout.Y_AXIS);
        this.columns = columns;
        this.parent = jobsPanel;
        setAlignmentX(LEFT_ALIGNMENT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        cb = new JCheckBox(s.substring(1));
        cb.setFont(new Font("Arial", Font.BOLD, 16));
        GameData.getInstance().putJob(s.substring(1), true);

        cb.setSelected(true);

        cb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                GameData.getInstance().putJob(cb.getText(), cb.isSelected());
                GameData.getInstance().sendJobs();
            }
        });

        if (s.charAt(0) == 'a') {
            cb.setForeground(new Color(0xFF4444));
        }
        Box newBox = new Box(BoxLayout.X_AXIS);
        newBox.add(cb);
        newBox.add(Box.createHorizontalGlue());
        this.add(newBox);
        JEditorPane jed = new JEditorPane();
        jed.setContentType("text/html");
        jed.setText(description);
        this.add(new JScrollPane(jed));

    }

    public JCheckBox getCheckBox() {
        return cb;
    }
}

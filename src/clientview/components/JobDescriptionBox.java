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


    public JobDescriptionBox(String s, String description, int columns, JobsPanel jobsPanel, boolean checked) {
        super(BoxLayout.Y_AXIS);
        this.columns = columns;
        this.parent = jobsPanel;
        setAlignmentX(LEFT_ALIGNMENT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        cb = new JCheckBox(s.substring(1));
        cb.setFont(new Font("Arial", Font.BOLD, 16));
        GameData.getInstance().putJob(s.substring(1), checked);

        cb.setSelected(checked);



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
        jed.setCaretPosition(0);
        jed.setEnabled(checked);
        jed.setEditable(false);
        this.add(new JScrollPane(jed));

        cb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                jed.setEnabled(cb.isSelected());
                GameData.getInstance().putJob(cb.getText(), cb.isSelected());
                GameData.getInstance().sendJobs();
            }
        });

    }

    public JCheckBox getCheckBox() {
        return cb;
    }
}

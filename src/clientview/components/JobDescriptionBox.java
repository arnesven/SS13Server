package clientview.components;

import clientlogic.GameData;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class JobDescriptionBox extends Box {
    private static final Font ORIGINAL_FONT = new Font("Arial", Font.BOLD, 16);
    final JCheckBox cb;
    private static final Font STRIKE_THROUGH = makeStrikeThroughFont();


    private final int columns;
    private final JobsPanel parent;
    private static final int PREFERRED_WIDTH = 230;
    private static final int COLLAPSED_HEIGHT = 45;
    private static final int EXPANDED_HEIGHT =  250;
    private final JScrollPane jsp;
    private final JEditorPane jed;
    private final Box newBox;
    private final JButton expand;
    private boolean jedVisible = false;


    public JobDescriptionBox(String s, String description, int columns, JobsPanel jobsPanel, boolean checked) {
        super(BoxLayout.Y_AXIS);
        this.columns = columns;
        this.parent = jobsPanel;

        setAlignmentX(LEFT_ALIGNMENT);
        setAlignmentY(TOP_ALIGNMENT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        cb = new JCheckBox(s.substring(1));
        if (checked) {
            cb.setFont(ORIGINAL_FONT);
        } else {
            cb.setFont(STRIKE_THROUGH);
        }
        GameData.getInstance().putJob(s.substring(1), checked);

        cb.setSelected(checked);

        if (s.charAt(0) == 'a') {
            cb.setForeground(new Color(0xFF4444));
        }
        cb.setBackground(Color.WHITE);
        newBox = new Box(BoxLayout.X_AXIS);
        newBox.setBackground(Color.WHITE);
        newBox.add(cb);
        newBox.add(Box.createHorizontalGlue());
        expand = new JButton("Show");
        expand.setFont(new Font("Arial", Font.PLAIN, 10));
        newBox.add(expand);
        this.add(newBox);
        jed = new JEditorPane();
        jed.setContentType("text/html");
        jed.setText(description);
        jed.setCaretPosition(0);
        jed.setEnabled(checked);
        jed.setEditable(false);
        jsp = new JScrollPane(jed);

        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, COLLAPSED_HEIGHT));
        //this.add(jed);
        //this.setPreferredSize(new Dimension(PREFERRED_WIDTH, 250));

        expand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (jedVisible) {
                    JobDescriptionBox.this.remove(jsp);
                    JobDescriptionBox.this.setPreferredSize(new Dimension(PREFERRED_WIDTH, COLLAPSED_HEIGHT));
                    expand.setText("Show");
                } else {
                    expand.setText("Hide");
                    JobDescriptionBox.this.add(jsp);
                    JobDescriptionBox.this.setPreferredSize(new Dimension(PREFERRED_WIDTH, EXPANDED_HEIGHT));
                }
                jedVisible = !jedVisible;
                JobDescriptionBox.this.revalidate();
                JobDescriptionBox.this.repaint();
            }
        });

        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GameData.getInstance().putJob(cb.getText(), cb.isSelected());
                GameData.getInstance().sendJobs();
            }
        });

        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                jed.setEnabled(cb.isSelected());
                if (!cb.isSelected()) {

                    cb.setFont(STRIKE_THROUGH);
                } else {
                    cb.setFont(ORIGINAL_FONT);
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cb.setSelected(!cb.isSelected());
            }
        });

    }

    private static Font makeStrikeThroughFont() {
        Map attributes = ORIGINAL_FONT.getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        Font font = new Font(attributes);
        return font;
    }



    public JCheckBox getCheckBox() {
        return cb;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}

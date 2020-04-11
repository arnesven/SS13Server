package clientview.components;

import clientlogic.GameData;
import clientview.SpriteManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class JobDescriptionBox extends Box {
    private static final Font ORIGINAL_FONT = new Font("Arial", Font.BOLD, 14);
    final JCheckBox cb;
    private static final Font STRIKE_THROUGH = makeStrikeThroughFont();


    private final int columns;
    private final JobsPanel parent;
    private static final int PREFERRED_WIDTH = 260;
    private static final int COLLAPSED_HEIGHT = 45;
    private static final int EXPANDED_HEIGHT =  230;
    private final JScrollPane jsp;
    private final MyHtmlPane jed;
    private final Box newBox;
    private final JButton expand;
    private final Color color;
    private boolean jedVisible = false;


    public JobDescriptionBox(String s, String description, int columns, JobsPanel jobsPanel, boolean checked) {
        super(BoxLayout.Y_AXIS);
        this.columns = columns;
        this.parent = jobsPanel;

        setAlignmentX(LEFT_ALIGNMENT);
        setAlignmentY(TOP_ALIGNMENT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        cb = new JCheckBox(s.substring(1));

        GameData.getInstance().putJob(s.substring(1), checked);

        cb.setSelected(checked);

        if (s.charAt(0) == 'a') {
            this.color = new Color(0xFF4444);
        } else {
            if (s.charAt(0) == 'u'){
                cb.setSelected(false);
            }
            this.color = Color.BLACK;
        }

        if (checked) {
            cb.setFont(ORIGINAL_FONT);
            cb.setForeground(this.color);
        } else {
            cb.setFont(STRIKE_THROUGH);
            cb.setForeground(Color.GRAY);
        }
        cb.setBackground(Color.WHITE);
        newBox = new Box(BoxLayout.X_AXIS);
        newBox.setBackground(Color.WHITE);
        JLabel icon = new JLabel();
        icon.setIcon(SpriteManager.getSprite(s.substring(1)+"mugshot0"));
        newBox.add(icon);
        newBox.add(cb);
        newBox.add(Box.createHorizontalGlue());
        expand = new JButton();
        expand.setMargin(new Insets(0, 0, 0, 0));
        expand.setPreferredSize(new Dimension(25, 25));
        expand.setFont(new Font("Arial", Font.PLAIN, 9));
        expand.setText("v");
        newBox.add(expand);
        newBox.add(Box.createHorizontalStrut(2));
        this.add(newBox);
        jed = new MyHtmlPane();
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
                    expand.setText("v");
                } else {
                    JobDescriptionBox.this.add(jsp);
                    JobDescriptionBox.this.setPreferredSize(new Dimension(PREFERRED_WIDTH, EXPANDED_HEIGHT));
                    expand.setText("^");
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
                    cb.setForeground(Color.GRAY);
                } else {
                    cb.setFont(ORIGINAL_FONT);
                    cb.setForeground(JobDescriptionBox.this.color);
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

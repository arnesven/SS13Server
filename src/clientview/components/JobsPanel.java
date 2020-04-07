package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JobsPanel extends JPanel implements Observer {

    private final JPanel parent;
    private JPanel gridPanel = new JPanel();
    private ArrayList<JCheckBox> checkboxes = new ArrayList<>();
    boolean allCheck = true;
    private Button tb;

    public JobsPanel(String username, JPanel parent) {
        this.parent = parent;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Please check the jobs you prefer.");
        title.setFont(new Font("Arial", Font.ITALIC, 22));
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(title);
        box.add(Box.createHorizontalGlue());
        this.add(box);
        this.add(gridPanel);

        tb = new Button("Check None");

        tb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                checkAll();
            }
        });
        JPanel buttBox = new JPanel(new FlowLayout());
        buttBox.add(tb);
        this.add(buttBox);

        ServerCommunicator.send(GameData.getInstance().getClid() + " JOBS", new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                fillGrid(result);
            }
        });
    }

    private void checkAll() {
        for (JCheckBox cb : checkboxes) {
            cb.setSelected(!allCheck);
            GameData.getInstance().setAllJobs(!allCheck);

        }
        GameData.getInstance().sendJobs();
        tb.setLabel(allCheck?"Check All":"Check None");
        allCheck = !allCheck;

    }

    private void fillGrid(String result) {

        String[] jobsAndDescriptions = result.split("<player-data-part>");

        int noItems = jobsAndDescriptions.length;// + antagonist.length;
        int columns = 4;
        int rows = (int)Math.ceil(((double)noItems)/columns);
        gridPanel.setLayout(new GridLayout(columns, rows));

        checkboxes = new ArrayList<>();
        for (int i = 0; i < jobsAndDescriptions.length ; i+=2 ) {
            String s = jobsAndDescriptions[i];
            String description = jobsAndDescriptions[i+1];

            JobDescriptionBox jdb = new JobDescriptionBox(s, description, columns, this);
            gridPanel.add(jdb);//, row++, col);
            checkboxes.add(jdb.getCheckBox());
        }

    }

    @Override
    public void update() {

    }
}

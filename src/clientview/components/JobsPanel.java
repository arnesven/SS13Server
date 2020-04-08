package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.Cookies;
import clientlogic.GameData;
import clientlogic.Observer;
import model.characters.general.GameCharacter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobsPanel extends JPanel implements Observer {

    private final JPanel parent;
    private JPanel gridPanel = new JPanel();
    private ArrayList<JCheckBox> checkboxes = new ArrayList<>();
    boolean allCheck = true;
    private Button tb;

    public JobsPanel(String username, JPanel parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout());
        JLabel title = new JLabel("Please check the jobs you prefer.");
        title.setFont(new Font("Arial", Font.ITALIC, 22));
        Box box = new Box(BoxLayout.X_AXIS);

        tb = new Button("Check None");

        tb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                checkAll();
            }
        });

        box.add(title);
        tb.setPreferredSize(new Dimension(80, 0));
        box.add(Box.createHorizontalGlue());
        box.add(tb);
        this.add(box, BorderLayout.NORTH);
        this.add(gridPanel);

        ServerCommunicator.send(GameData.getInstance().getClid() + " JOBS", new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                fillGrid(result);
            }

            @Override
            public void onFail() {
                System.out.println("Failed to send JOBS message to server");
            }
        });

    }

    private void checkAll() {
        for (JCheckBox cb : checkboxes) {
            cb.setSelected(!allCheck);
        }
        GameData.getInstance().setAllJobs(!allCheck);
        GameData.getInstance().sendJobs();
        tb.setLabel(allCheck?"Check All":"Check None");
        allCheck = !allCheck;

    }

    private void fillGrid(String result) {

        String[] jobsAndDescriptions = result.split("<player-data-part>");

        int noItems = jobsAndDescriptions.length;// + antagonist.length;
        int columns = 4;
        int rows = (int)Math.ceil(((double)noItems)/columns);
        gridPanel.setLayout(new FlowLayout());
        gridPanel.setAlignmentY(TOP_ALIGNMENT);
        gridPanel.setAlignmentX(LEFT_ALIGNMENT);


        Map<String, Boolean> oldChoices = new HashMap<>();
        if (Cookies.getCookie("jobselections") != null) {
            String jobSelections = Cookies.getCookie("jobselections");
            jobSelections = jobSelections.substring(1, jobSelections.length()-1);
            String[] jobArr = jobSelections.split(",");
            for (String job : jobArr) {
                String[] parts = job.split("=");
                oldChoices.put(parts[0], Boolean.parseBoolean(parts[1]));
            }
        }

        checkboxes = new ArrayList<>();
        for (int i = 0; i < jobsAndDescriptions.length ; i+=2 ) {
            String s = jobsAndDescriptions[i];
            String description = jobsAndDescriptions[i+1];

            boolean checked = true;
            if (oldChoices.get(s.substring(1)) != null) {
                checked = oldChoices.get(s.substring(1));
            }
            JobDescriptionBox jdb = new JobDescriptionBox(s, description, columns, this, checked);
            gridPanel.add(jdb);//, row++, col);
            checkboxes.add(jdb.getCheckBox());
        }


    }

    @Override
    public void update() {

    }
}

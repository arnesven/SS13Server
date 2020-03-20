package clientview;

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

    private JPanel gridPanel = new JPanel();
    private ArrayList<JCheckBox> checkboxes = new ArrayList<>();
    boolean allCheck = true;
    private Button tb;

    public JobsPanel(String username) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new Label("Please check the jobs you prefer."));

        //fillGrid();
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
        buttBox.add(Box.createHorizontalGlue());
        this.add(buttBox);
        this.add(Box.createVerticalGlue());

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

        String[] jobs = result.split("<player-data-part>");


        int noItems = jobs.length;// + antagonist.length;
        int columns = 4;
        int rows = (int)Math.ceil(((double)noItems)/columns);
        gridPanel.setLayout(new GridLayout(columns, rows));
      //  Grid grid = new Grid(rows, columns);

        //grid.setCellPadding(3);

        int row = 0;
        int col = 0;
        checkboxes = new ArrayList<>();
        for (String s : jobs) {
            final JCheckBox cb = new JCheckBox(s.substring(1));
            checkboxes.add(cb);
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
//                cb.getElement().getStyle().setColor("#FF4444");
            }
            //grid.setWidget(row++, col, cb);

            gridPanel.add(cb);//, row++, col);
            if (row % rows == 0) {
                col += 1;
                row = 0;
            }
        }
        col++;
        row = 0;

    }

    @Override
    public void update() {

    }
}

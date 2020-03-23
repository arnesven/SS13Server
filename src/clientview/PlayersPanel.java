package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayersPanel extends JPanel implements Observer {

    private static final int TIME_INTERVAL = 250;

    private final JTable ft;
    private int selectedIndex = -1;
    private Timer timer;

    public PlayersPanel(final String username) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ft = new JTable(new String[][]{new String[]{"Pelle", "Kalle"}, new String[]{"kjdaf", "soa"}}, new String[]{"kjdf", "gurgle"});

        ft.setFillsViewportHeight(true);
        ft.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedIndex = e.getFirstIndex();
            }
        });
        this.add(ft);


        JButton kickButton = new JButton("Kick Player");
        kickButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ServerCommunicator.send(username + " KICK " + ft.getModel().getValueAt(ft.getSelectedRow(), 0),
                        new MyCallback<String>() {

                            @Override
                            public void onSuccess(String result) {
                                if (result.equals("KICKED")) {
                                    ((DefaultTableModel)ft.getModel()).removeRow(selectedIndex);
                                }
                                selectedIndex = -1;
                            }
                        });
            }
        });

        JPanel buttBox = new JPanel(new FlowLayout());
        buttBox.add(kickButton);
        this.add(buttBox);
        this.add(Box.createVerticalGlue());
        GameData.getInstance().subscribe(this);
        GameUIPanel.pollServerSummary();
        setUpPollingTimer(username);
    }


    private void setUpPollingTimer(final String username) {
        pollServerWithList(username);
        timer = new Timer(TIME_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pollServerWithList(username);
            }
        });
        timer.start();
    }

    private void pollServerWithList(String username) {
        ServerCommunicator.send(username + " LIST", new MyCallback<String>(){
            @Override
            public void onSuccess(String result) {
                if (result.contains("ERROR")) {
                    JOptionPane.showMessageDialog(null, result);
                }
                GameData.getInstance().deconstructReadyListAndStateAndRoundAndSettings(result);
                fillTable();
            }

            @Override
            public void onFail() {
                timer.stop();
            }
        });

    }
    private void fillTable() {

        ArrayList<String> clientArr = GameData.getInstance().getClientList();
        ArrayList<Boolean> boolArr = GameData.getInstance().getReadyList();
        ArrayList<Boolean> specArr = GameData.getInstance().getSpectatorList();

//        while (tableModel.getRowCount() > 0) {
//            tableModel.removeRow(0);
//        }

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Name", "Status"});
        ft.setModel(tableModel);
        ft.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if(table.getValueAt(row, column).equals("READY")){
                    cellComponent.setBackground(Color.GREEN);
                } else if(table.getValueAt(row, column).equals("NOT READY")){
                    cellComponent.setBackground(Color.LIGHT_GRAY);
                }
                return cellComponent;
            }
        });
        for (int i = 0; i < clientArr.size() ; ++i) {
            //tableModel.setValueAt(clientArr.get(i), i, 0);
            //ft.setText(i, 0, clientArr.get(i));
            //System.out.println("Adding " + clientArr.get(i));
            String readyLabel = "";
            Color color;
            if (specArr.get(i)) {
                readyLabel = "SPECTATOR";
                color = new Color(0x5588FF);
            } else {
                if (boolArr.get(i)) {
                    readyLabel = "READY";
                    color = new Color(0x00FF00);
                } else {
                    readyLabel = "NOT READY";
                    color = new Color(0xDDDDDD);
                }
            }
           // tableModel.setValueAt(readyLabel, i, 1);
            tableModel.addRow(new String[]{clientArr.get(i), readyLabel});
            //Component comp = ft.getCellRenderer(i, 1).getTableCellRendererComponent()
            //comp.setBackground(color);

            //ft.getColorModel().
            //ft.setHTML(i, 1, readyLabel);
            if (selectedIndex == i) {
                ft.getSelectionModel().setSelectionInterval(selectedIndex, selectedIndex);
            }
//            } else {
//                ft.getRowFormatter().getElement(i).getStyle().setBackgroundColor("#FFFFFF");
//            }
        }


//        for (int r = clientArr.size(); r < ft.getRowCount(); ++r) {
//            ft.removeRow(r);
//        }




    }


    @Override
    public void update() {
        fillTable();
    }


}

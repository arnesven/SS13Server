package main;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.Cookies;
import clientlogic.GameData;
import clientlogic.Room;
import clientview.*;
import clientview.dialogs.ConnectToServerDialog;
import clientview.dialogs.StartNewServerDialog;
import tests.SimulationClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SS13Client extends JFrame {

    private final ReturningPlayerPanel retPan;
    private static final Dimension originalSize = new Dimension(960, 960);
    private static final Dimension ingameSize = new Dimension(1200, 960);
    private static ConnectData connectData;
    private JMenu view;
    private boolean errorShowing;
    private GameUIPanel guiPanel = null;
    private int lastPortUsed;
    private int botNumber = 1;

    public SS13Client() {
        super("SS13 Client 0.01a");
        errorShowing = false;
        this.setSize(originalSize);
        this.setLocation(new Point(250, 50));
        //this.setLayout(new BorderLayout());
        this.retPan = new ReturningPlayerPanel();
        this.add(retPan);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ServerCommunicator.setFrameReference(this);

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem item = new JMenuItem("Connect");
        view = new JMenu("View");
        makeScaleMenu(view);
        makeBackgroundMenu(view);
        view.setEnabled(false);
        JMenu server = new JMenu("Server");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectToServerDialog ctsd = new ConnectToServerDialog(SS13Client.this);
            }
        });
        file.add(item);
        menubar.add(file);
        menubar.add(view);

        JMenuItem startServer = new JMenuItem("Host Game");
        JMenuItem bot = new JMenuItem("Add Bot");
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StartNewServerDialog snsd = new StartNewServerDialog(SS13Client.this);
                lastPortUsed = snsd.getPort();

                if (snsd.didStart()) {
                    startServer.setEnabled(false);
                    bot.setEnabled(true);

                    if (snsd.alsoConnectMe()) {
                        if (Cookies.getCookie("last_user_name") != null) {
                            GameData.getInstance().setHost("localhost");
                            GameData.getInstance().setPort(snsd.getPort());
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            cont(false, false, Cookies.getCookie("last_user_name"));
                        } else {
                            new ConnectToServerDialog(SS13Client.this);
                        }
                    }

                }
            }
        });
        server.add(startServer);
        bot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SimulationClient sc = new SimulationClient(lastPortUsed, "Botty" + botNumber++);
            }
        });
        bot.setEnabled(false);
        server.add(bot);
        menubar.add(server);

        this.setJMenuBar(menubar);


        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Pressed" + e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_R && e.isControlDown()) {
                    System.out.println("Pressed R");
                    if (guiPanel != null) {
                        guiPanel.toggleReady();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    MapPanel.addXTranslation(1);
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    MapPanel.addXTranslation(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    MapPanel.addYTranslation(1);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    MapPanel.addYTranslation(-1);
                }
            }
        });
        this.requestFocus();

        this.setVisible(true);

    }

    private void makeBackgroundMenu(JMenu view) {
        JMenu menu = new JMenu("Background");
        JMenuItem space = new JRadioButtonMenuItem("Space");
        space.setSelected(true);
        space.addActionListener((ActionEvent e) -> guiPanel.getInGameView().getMapPanel().getDrawingStrategy().setBackgroundDrawingStrategy(new DrawSpaceBackgroundStrategy()));
        JMenuItem black = new JRadioButtonMenuItem("Black");
        black.addActionListener((ActionEvent e) -> guiPanel.getInGameView().getMapPanel().getDrawingStrategy().setBackgroundDrawingStrategy(new BlackBackgroundStrategy()));
        ButtonGroup bg = new ButtonGroup();
        bg.add(space);
        bg.add(black);
        menu.add(space);
        menu.add(black);
        view.add(menu);

    }

    private void makeScaleMenu(JMenu view) {
        JMenu jmenu = new JMenu("Map Scale");


        JMenuItem auto = new JRadioButtonMenuItem("Auto");


        JMenuItem twobytwo = new JRadioButtonMenuItem("2x2");
        JMenuItem threebytwo = new JRadioButtonMenuItem("3x2");
        JMenuItem threebythree = new JRadioButtonMenuItem("3x3");
        JMenuItem fourbythree = new JRadioButtonMenuItem("4x3");
        auto.setSelected(true);
        auto.addActionListener((ActionEvent e) -> Room.setAutomaticScaling(true));
        twobytwo.addActionListener((ActionEvent e) -> {Room.setAutomaticScaling(false); Room.setXScale(2*32); Room.setYScale(2*32);});
        threebytwo.addActionListener((ActionEvent e) -> {Room.setAutomaticScaling(false); Room.setXScale(3*32); Room.setYScale(2*32);});
        threebythree.addActionListener((ActionEvent e) -> {Room.setAutomaticScaling(false); Room.setXScale(3*32); Room.setYScale(3*32);});
        fourbythree.addActionListener((ActionEvent e) -> {Room.setAutomaticScaling(false); Room.setXScale(4*32); Room.setYScale(3*32);});

        ButtonGroup grp = new ButtonGroup();
        grp.add(auto);
        grp.add(twobytwo);
        grp.add(threebythree);
        grp.add(threebytwo);
        grp.add(fourbythree);

        jmenu.add(twobytwo);
        jmenu.add(threebytwo);
        jmenu.add(threebythree);
        jmenu.add(fourbythree);
        jmenu.add(auto);
        view.add(jmenu);
    }

    public static void main(String[] args) {
        new SS13Client();

    }

    protected void switchToGameUI(String username) {
        System.out.println("Switching views");
		getContentPane().remove(retPan);
		guiPanel = new GameUIPanel(username, this);
		enableView();
		getContentPane().add(guiPanel);
		this.setSize(ingameSize);
		this.revalidate();
		this.repaint();
	}

    private void enableView() {
        this.view.setEnabled(true);
    }

    public void cont(boolean returning, Boolean spectator, String clid) {
        String message = "IDENT ME" + clid;
        if (spectator) {
            message += " SPECTATOR";
        }

        if (returning) {
            message = clid + " RETURNING";
        }

        connectData = new ConnectData(returning, spectator, clid, retPan);
        ServerCommunicator.send(message, new MyCallback() {

            @Override
            public void onSuccess(String result) {
                if (result.equals("ID ERROR")) {
                    retPan.setIDError();
                } else if (result.contains("ERROR") || result.contains("error")) {
                    JOptionPane.showMessageDialog(SS13Client.this,
                            result.replace("ERROR", ""));
                } else if (result.contains("fail")) {
                    JOptionPane.showMessageDialog(SS13Client.this, "Could not connect to server.");

                } else {
                    GameData.getInstance().setClid(result);
                    switchToGameUI(result);
                    errorShowing = false;

                }
            }

            @Override
            public void onFail() {
               // tryReconnect();
            }
        });
    }

    public void switchBackToStart() {
        System.out.println("Switching views back");
        GameData.resetAllData();
        getContentPane().remove(0);
        getContentPane().add(retPan);
        this.setSize(originalSize);
        revalidate();
        repaint();
    }

    public void showConnectionError() {
        if (!errorShowing) {
            errorShowing = true;
            JOptionPane.showMessageDialog(this, "Connection to server lost.");
          // tryReconnect();
//            if (res == JOptionPane.OK_OPTION) {
//                tryReconnect();
//            }
        }
    }

//    private void tryReconnect() {
//        ReconnectDialog dialog = new ReconnectDialog(SS13Client.this);
//        while (dialog.isActive()) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        reconnect();
//    }

    public void reconnect() {
        cont(connectData.returning, connectData.spectator, connectData.clid);
    }
}

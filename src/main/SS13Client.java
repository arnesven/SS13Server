package main;

import clientcomm.ConnectData;
import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.Cookies;
import clientlogic.GameData;
import clientlogic.Room;
import clientview.FancyFrame;
import clientview.animation.AnimationHandler;
import clientview.components.GameUIPanel;
import clientview.components.MapPanel;
import clientview.components.ReturningPlayerPanel;
import clientview.dialogs.ConnectToServerDialog;
import clientview.dialogs.StartNewServerDialog;
import clientview.strategies.BackgroundDrawingStrategy;
import tests.SimulationClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SS13Client extends JFrame {

    public static final String CLIENT_VERSION_STRING = "1.232";
    private final ReturningPlayerPanel retPan;
    private static final Dimension originalSize = new Dimension(960, 960);
    private static final Dimension ingameSize = new Dimension(1200, 960);
    private static ConnectData connectData;
    private JMenu view;
    private boolean errorShowing;
    private GameUIPanel guiPanel = null;
    private int lastPortUsed;
    private int botNumber = 1;
    private JFrame fancyFrame;

    public SS13Client() {
        super("SS13 Client " + CLIENT_VERSION_STRING);
        errorShowing = false;
        this.setSize(originalSize);
        this.setLocation(new Point(250, 50));
        this.retPan = new ReturningPlayerPanel();
        this.add(retPan);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ServerCommunicator.setFrameReference(this);
        makeMenuBar();
        setUpKeyListener();
        makeFancyFrame();
    }

    private void makeFancyFrame() {
        this.fancyFrame = new FancyFrame(this);

    }

    public static void main(String[] args) {
        new SS13Client();
    }

    public void LoginToServer(boolean returning, Boolean spectator, String clid) {
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
                SS13Client.this.requestFocus();
            }

            @Override
            public void onFail() {

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
        }
    }

    protected void switchToGameUI(String username) {
        System.out.println("Switching views");
        getContentPane().remove(retPan);
        guiPanel = new GameUIPanel(username, this);
        enableView();
        getContentPane().add(guiPanel);
        guiPanel.setUpPollingTimer(username);
        this.setSize(ingameSize);
        this.revalidate();
        this.repaint();
    }


    private void setUpKeyListener() {
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R && e.isControlDown()) {
                    if (guiPanel != null) {
                        guiPanel.toggleReady();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    MapPanel.addXTranslation(1);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    MapPanel.addXTranslation(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    MapPanel.addYTranslation(1);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    MapPanel.addYTranslation(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                    MapPanel.addZTranslation(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                    MapPanel.addZTranslation(1);
                }
                repaint();
            }
        });
        this.requestFocus();
        this.setVisible(true);
    }

    private void makeMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem item = new JMenuItem("Connect");
        view = new JMenu("View");
        makeCenterOnMe(view);
        makeForeceShowFancyFrame(view);
        makeScaleMenu(view);
     //   makeZoomMenu(view);
        makeBackgroundMenu(view);
        makeHeightMenu(view);
        makeAnimationMenu(view);
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
        JMenuItem tenBots = new JMenuItem("Add 10 Bots");
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StartNewServerDialog snsd = new StartNewServerDialog(SS13Client.this);
                lastPortUsed = snsd.getPort();

                if (snsd.didStart()) {
                    startServer.setEnabled(false);
                    bot.setEnabled(true);
                    tenBots.setEnabled(true);
                    if (snsd.alsoConnectMe()) {
                        if (Cookies.getCookie("last_user_name") != null) {
                            GameData.getInstance().setHost("localhost");
                            GameData.getInstance().setPort(snsd.getPort());
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            LoginToServer(false, false, Cookies.getCookie("last_user_name"));
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

        tenBots.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < 10; ++i) {
                    SimulationClient sc = new SimulationClient(lastPortUsed, "Botty" + botNumber++);
                }
            }
        });
        tenBots.setEnabled(false);
        server.add(tenBots);
        menubar.add(server);

        this.setJMenuBar(menubar);
    }

    private void makeForeceShowFancyFrame(JMenu view) {
        JMenuItem forceShow = new JMenuItem("Show Small Window");
        forceShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fancyFrame.setVisible(true);
            }
        });

        view.add(forceShow);
    }

    private void makeCenterOnMe(JMenu view) {
        JMenuItem center = new JMenuItem("Center Camera");
        center.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MapPanel.setXTranslation(GameData.getInstance().getCurrentRoom().getXPos() / (int)(Room.getXScale() / MapPanel.getZoom()));
                MapPanel.setYTranslation(GameData.getInstance().getCurrentRoom().getYPos() / (int)(Room.getYScale() / MapPanel.getZoom()));
                MapPanel.setZTranslation(GameData.getInstance().getCurrentRoom().getZPos());
                repaint();
            }
        });

        view.add(center);
    }

//    private void makeZoomMenu(JMenu view) {
//        JMenu zoom = new JMenu("Zoom");
//        JMenuItem item32 = new JRadioButtonMenuItem("x1 (32 px)");
//        item32.addActionListener((ActionEvent e) -> MapPanel.setZoom(32));
//        JMenuItem item64 = new JRadioButtonMenuItem("x2 (64 px)");
//        item64.addActionListener((ActionEvent e) -> MapPanel.setZoom(64));
//        ButtonGroup bg = new ButtonGroup();
//        bg.add(item32);
//        bg.add(item64);
//        item32.setSelected(true);
//        zoom.add(item32);
//        zoom.add(item64);
//        view.add(zoom);
//    }

    private void makeAnimationMenu(JMenu view) {
        JMenu ani = new JMenu("Animations");

        final Timer t = new Timer(100, ((ActionEvent e) -> {AnimationHandler.step(); repaint();}));

        JMenuItem slow = new JRadioButtonMenuItem("Slow and laggy");
        slow.addActionListener((ActionEvent e) -> t.stop());
        JMenuItem fast = new JRadioButtonMenuItem("Fast and not so laggy");
        fast.addActionListener((ActionEvent e) -> t.start());
        ButtonGroup bg = new ButtonGroup();
        bg.add(slow);
        bg.add(fast);
        slow.setSelected(true);
        ani.add(slow);
        ani.add(fast);

        view.add(ani);
    }

    private void makeHeightMenu(JMenu view) {
        JMenu height = new JMenu("View Depth");

        JMenuItem stepDown = new JMenuItem("Step Down", KeyEvent.VK_PAGE_DOWN);
        stepDown.addActionListener((ActionEvent e) -> MapPanel.addZTranslation(-1));

        JMenuItem stepUp = new JMenuItem("Step Up", KeyEvent.VK_PAGE_UP);
        stepUp.setMnemonic(KeyEvent.VK_PAGE_UP);
        stepUp.addActionListener((ActionEvent e) -> MapPanel.addZTranslation(1));
        height.add(stepUp);
        height.add(stepDown);

        view.add(height);
    }

    private void makeBackgroundMenu(JMenu view) {
        JMenu menu = new JMenu("Background");
        ButtonGroup bg = new ButtonGroup();
        JMenuItem auto = new JRadioButtonMenuItem("Auto");
        auto.addActionListener((ActionEvent e) -> MapPanel.setAutomaticBackground(true));
        bg.add(auto);
        auto.setSelected(true);
        menu.add(auto);

        for (String backgroundType : BackgroundDrawingStrategy.getAllTypesAsStrings()) {
            JMenuItem it = new JRadioButtonMenuItem(backgroundType);
            it.addActionListener((ActionEvent e) -> {
                guiPanel.getInGameView().getMapPanel().getDrawingStrategy().
                        setBackgroundDrawingStrategy(BackgroundDrawingStrategy.makeStrategy(backgroundType));
                MapPanel.setAutomaticBackground(false);
                    }
            );
            bg.add(it);
            menu.add(it);
        }
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


    private void enableView() {
        this.view.setEnabled(true);
    }



}

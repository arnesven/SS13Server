package graphics.pdf;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.map.AirLockRoom;
import model.map.Room;
import model.map.RoomType;
import model.objects.consoles.KeyCardLock;
import model.objects.general.GameObject;
import util.Logger;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;

/**
 * Created by erini02 on 27/04/16.
 */
public class MapPDFMaker {

    private static final String outFilePath = "resources/pdf/mapout.tex";
    private static final int DESIRED_WIDTH_MM = 620;
    private static final int DESIRED_HEIGHT_MM = 585;

    public static void generate(GameData gameData) {
        File f = makeTexFile(gameData);
        makeTexIntoPdf(f);
    }



    private static File makeTexFile(GameData gameData) {
        Logger.log(Logger.INTERESTING, "Generating map latex file...");
        File header = new File("resources/pdf/header.tex");
        File outFile = new File(outFilePath);

        try {
            copy(header, outFile, false);

            addContent(gameData, outFile);

            copy(new File("resources/pdf/footer.tex"), outFile, true);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Logger.log(Logger.INTERESTING, "Generating..done!");


        return outFile;
    }

    private static void copy(File header, File outFile, boolean append) throws IOException {
        Scanner fileReader;

        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, append));
        try {
            fileReader = new Scanner(header);
            for (String line; fileReader.hasNext() ; ) {
                line = fileReader.nextLine();
                writer.write(line);
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.close();

    }

    private static void addContent(GameData gameData, File outFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));

        writer.write("\\begin{minipage}[t][" + DESIRED_HEIGHT_MM + "mm][t]{" + DESIRED_WIDTH_MM +"mm}\\vskip -1mm");
        writer.newLine();
        writer.write("\\begin{tikzpicture}");
        writer.newLine();


        int mapWidth = gameData.getMap().getMaxX();
        int mapHeight = gameData.getMap().getMaxY();
        Logger.log("Map dimensions are " + mapWidth + "x" + mapHeight);
        double xFactor = (double)(DESIRED_WIDTH_MM) / (double)(mapWidth);
        double yFactor = (double)(DESIRED_HEIGHT_MM) / (double)(mapHeight);


        for (Room r : gameData.getRooms()) {
            writeRoomBox(writer, r.getX()*xFactor, -r.getY()*yFactor, r.getWidth()*xFactor, r.getHeight()*yFactor, getColorForRoomType(r.getType()));
        }

        int doorWidth = 10;
        int doorHeight = (int)(doorWidth * (yFactor / xFactor));
        for (Room r : gameData.getRooms()) {
            double[] doors = r.getDoors();
           // Logger.log("Adding door for " + r.getName(), true);
            for (int i = 0; i < doors.length; i+=2) {
                double x = doors[i]*xFactor;
                double y = doors[i+1]*yFactor;
                if (x > 0 || y > 0) {
                   // Logger.log(String.format("  at (%.1f,%.1f) = (%.1f,%.1f)", doors[i], doors[i+1], x, y));
                    writeRoomBox(writer, Math.abs(x - doorWidth/2), -(y-doorHeight/2), doorWidth, doorHeight, x < 0 ? "Black" : "Gray");
                }
            }
        }

        for (Room r : gameData.getRooms()) {
            boolean portrait = r.getHeight() >= 1.5*r.getWidth();
            writeRoomNode(writer, r.getX()*xFactor + r.getWidth()*xFactor/2.0,
                                -r.getY()*yFactor - r.getHeight()*yFactor/ (portrait?2.0:4.5) ,
                                "\\huge{" + texSafeFix(r.getName()) + "}",
                                portrait, 20);

            if (! (r instanceof AirLockRoom)) {
                int i = 1;
                for (GameObject o : r.getObjects()) {
                    if (! (o instanceof KeyCardLock)) {
                        writeRoomNode(writer, r.getX() * xFactor + r.getWidth() * xFactor / 2.0,
                                -r.getY() * yFactor - r.getHeight() * yFactor / 3.7 - (i++) * 12 - (portrait?50:0),
                                pic(o.getSprite(null), o.getBaseName()) + "\\large{" + texSafeFix(o.getBaseName()) + "}", false, 10);
                    }
                }
                for (GameItem o : r.getItems()) {
                    writeRoomNode(writer, r.getX() * xFactor + r.getWidth() * xFactor / 2.0,
                            -r.getY() * yFactor - r.getHeight() * yFactor / 3.7 - (i++) * 12 - (portrait?50:0),
                            pic(o.getSprite(null), o.getBaseName()) + "\\large{" + texSafeFix(o.getBaseName()) + "}", false, 10);
                }
                for (Actor o : r.getActors()) {
                    writeRoomNode(writer, r.getX() * xFactor + r.getWidth() * xFactor / 2.0,
                            -r.getY() * yFactor - r.getHeight() * yFactor / 3.7 - (i++) * 12 - (portrait?50:0),
                            pic(o.getCharacter().getSprite(null), o.getBaseName()) + "\\large{" + texSafeFix(o.getBaseName()) + "}", false, 10);
                }
            }
        }



        writer.write("\\end{tikzpicture}");
        writer.newLine();
        writer.write("\\end{minipage}%\\hskip 0.7mm DO NOT REMOVE THIS COMMENT!");
        writer.newLine();

        writer.close();
    }

    private static String pic(Sprite sp, String name) {
        String path = "resources/pdf/" + "images/" + name +".png";
        path = path.replace("..", ".");
        path = path.replace(" ", "");
        try {

            ImageIO.write(sp.getImage(), "png", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "\\includegraphics[width=8mm]{" + path + "}";
    }



    private static void writeRoomNode(BufferedWriter writer, double x, double y, String text, boolean portrait, int height) throws IOException {
        String command = "\\roomnode{";
        if (portrait) {
            command = "\\rotroomnode{";
        }
        writer.write(command + (int)x + "}{" + (int)y + "}{" + text + "}{White}{" + 35+ "}{" + height +"}");
        writer.newLine();
    }

    private static String getColorForRoomType(RoomType type) {
        if (type == RoomType.airlock) {
            return "Orange";
        } else if (type == RoomType.command) {
            return "White!50!SkyBlue";
        } else if (type == RoomType.security) {
            return "White!50!BrickRed";
        } else if (type == RoomType.tech) {
            return "Goldenrod";
        } else if (type == RoomType.science) {
            return "Green!50!White";
        } else if (type == RoomType.support) {
            return "Orchid!50!White";
        }
        return "White!50!Gray";

    }

    private static void writeRoomBox(BufferedWriter writer, double xin, double yin,
                                     double win, double hin, String color) throws IOException {
        int x = (int)xin;
        int y = (int)yin;
        int w = (int)win;
        int h = (int)hin;

        writer.write("\\roombox{" + x + "}{" + y +"}{" + w + "}{" + h + "}{" + color +"}");
        writer.newLine();
    }

    private static String texSafeFix(String name) {
        name = name.replace("#", "\\#");
        name = name.replace("%", "\\%");
        name = name.replace("_", "\\_");
        //name = name.replace(" ", " \\\\ ");
        return name.toUpperCase();
    }

    private static void makeTexIntoPdf(File f) {
        Logger.log(Logger.INTERESTING, "Running latex command...", false);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("pdflatex -output-directory resources/pdf " + outFilePath);

            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.log(Logger.INTERESTING, "..done", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

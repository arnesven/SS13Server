package clientview;

import javax.swing.*;
import java.util.ArrayList;

public class MyUtils {
    public static void removeAllWidgets(JPanel panel) {
        while (panel.getComponents().length > 0) {
            panel.remove(0);
        }
        panel.revalidate();
    }


    public static ArrayList<String> deconstructList(String string) {
        //System.out.println("Deconstructing items: " + string);
        ArrayList<String> list = new ArrayList<>();
        String sub = string.substring(1, string.length()-1);
        if (!sub.equals("")) {
            String[] its = sub.split("<list-part>");
            for (String item : its) {
                list.add(item);
            }
            //	GWT.log("Result:" + list.toString());
        }
        return list;
    }

}

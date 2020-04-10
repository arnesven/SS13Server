package clientlogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterStyle {
    private String preview;
    private List<String> hairList;
    private List<String> facialList;
    private Color hairColor;
    private Color facialColor;
    private int selectedHair;
    private int selectedFacial;
    private boolean selectedGender;

    public void parseStyleData(String result) {
        System.out.println(result);
        String[] parts = result.split("<style-delim>");
        preview = parts[0];
        System.out.println(parts[1]);
        String[] hairs = parts[1].substring(1, parts[1].length()-1).split("<list-part>");
        hairList = new ArrayList<>();
        for (String hair : hairs) {
            hairList.add(hair);
        }
        System.out.println(parts[2]);
        String[] facials = parts[2].substring(1, parts[2].length()-1).split("<list-part>");
        facialList = new ArrayList<>();
        for (String facial : facials) {
            facialList.add(facial);
        }

        String rest = parts[0].replace("nakedmanbase", "");
        if (rest.charAt(0) == 'm') {
            selectedGender = true;
            rest = rest.substring(3, rest.length()-1);
        } else {
            selectedGender = false;
            rest = rest.substring(5, rest.length()-1);
        }
        System.out.println("Scanning rest is: " + rest);
        Scanner scanner = new Scanner(rest);
        scanner.useDelimiter("\\D");
        selectedHair = scanner.nextInt();
        scanner.skip("-r");
        int red = scanner.nextInt();
        scanner.skip("g");
        int green = scanner.nextInt();
        scanner.skip("b");
        int blue = scanner.nextInt();
        hairColor = new Color(red, green, blue);
        scanner.skip("-");
        selectedFacial = scanner.nextInt();
        scanner.skip("r");
        red = scanner.nextInt();
        scanner.skip("g");
        green = scanner.nextInt();
        scanner.skip("b");
        blue = scanner.nextInt();
        facialColor = new Color(red, green, blue);


    }

    public String getPreviewSpriteName() {
        return preview;
    }

    public List<String> getFacialList() {
        return facialList;
    }

    public List<String> getHairList() {
        return hairList;
    }

    public void setPreviewSpriteName(String result) {
        preview = result;
    }

    public int getSelectedHair() {
        return selectedHair;
    }

    public int getSelectedFacialHair() {
        return selectedFacial;
    }

    public Color getSelectedHairColor() {
        return hairColor;
    }

    public Color getSelectedFacialHairColor() {
        return facialColor;
    }

    public boolean getSelectedGender() {
        return selectedGender;
    }

    public void setSelectedHairColor(Color selected) {
        hairColor = selected;
    }
}

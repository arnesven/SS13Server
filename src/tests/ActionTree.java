package tests;

import sounds.Sound;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by erini02 on 26/09/17.
 */
public class ActionTree {

    private List<ActionTree> subactions = new ArrayList<>();
    private String name;


    private ActionTree() {
        name = "root";
    }

    public ActionTree(String substring) {
        name = substring;
       // System.out.println("Created node " + name);
    }


    public static ActionTree parseTree(String treestring) {
        treestring = treestring.substring(1, treestring.length());
        ActionTree node = new ActionTree();
        makeStringIntoTree(treestring, node);
        return node;

    }

    private static int makeStringIntoTree(String treeString, ActionTree node) {
        int start = 0;
        if (treeString.charAt(0) != '}') {
            while (treeString.charAt(start) != '}') {
                int ptr = start;
                while (treeString.charAt(ptr) != '{') {
                    ptr++;
                }
                ActionTree innerNode = new ActionTree(treeString.substring(start, ptr));
                node.subactions.add(innerNode);
                start = ptr + 2 + makeStringIntoTree(treeString.substring(ptr+1), innerNode);
            }
        }
        return start;
    }

    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return name + subactions.toString();
    }

    public List<String> randomAction() {
        List<String> res = new ArrayList<>();
        res.add(name);

        if (subactions.size() > 0) {
            ActionTree random = MyRandom.sample(subactions);
            res.addAll(random.randomAction());
        }

        return res;
    }

    public List<ActionTree> getSubactions() {
        return subactions;
    }
}

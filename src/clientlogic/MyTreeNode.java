package clientlogic;

import java.util.ArrayList;

public class MyTreeNode {
    public String name;
    public ArrayList<MyTreeNode> children = new ArrayList<>();

    public MyTreeNode(String string) {
        this.name = string;
    }

    @Override
    public String toString() {

        return name + children.toString();
    }


}

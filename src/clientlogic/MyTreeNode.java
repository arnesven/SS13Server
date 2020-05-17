package clientlogic;

import java.util.ArrayList;

public class MyTreeNode {
    private final long uid;
    public String name;
    public ArrayList<MyTreeNode> children = new ArrayList<>();

    public MyTreeNode(String string) {
        String[] parts = string.split("<actpart>");
        this.uid = Long.parseLong(parts[0]);
        this.name = parts[1];
    }

    @Override
    public String toString() {

        return name + children.toString();
    }


}

package model.plebOS;

import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystemNode {

    private List<FileSystemNode> subdirs;
    private Directory parent;

    public Directory(String name, Directory parent) {
        super(name);
        subdirs = new ArrayList<>();
        this.parent = parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public void add(FileSystemNode fsn) {
        subdirs.add(fsn);
    }

    public List<FileSystemNode> getContents() {
        return subdirs;
    }

    public String getPath() {
        if (parent == this) {
            return "";
        }
        return parent.getPath() + "/" + getName();
    }
}

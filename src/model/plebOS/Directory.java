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
            return "/";
        } else if (parent.getParent() == parent) {
            return "/" + getName();
        }
        return parent.getPath() + "/" + getName();
    }

    public Directory getParent() {
        return parent;
    }

    public FileSystemNode getNodeForeName(String name) {
        for (FileSystemNode fsn : subdirs) {
            if (fsn.getName().equals(name)) {
                return fsn;
            }
        }
        throw new NoSubDirectoryFoundException();
    }

    public class NoSubDirectoryFoundException extends RuntimeException {
    }
}

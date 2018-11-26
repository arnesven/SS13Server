package model.plebOS;

import java.io.Serializable;

public class FileSystemNode  implements Serializable {

    private final String name;

    public FileSystemNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

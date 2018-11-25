package model.plebOS;

import java.io.Serializable;

public class Directory implements Serializable {

    private final String name;

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

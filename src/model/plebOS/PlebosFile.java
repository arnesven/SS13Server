package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;

import java.util.ArrayList;
import java.util.List;

public class PlebosFile extends FileSystemNode {
    private final boolean executable;
    private final PlebOSCommandHandler handler;
    private List<String> textualContents = new ArrayList<>();

    public PlebosFile(String name, boolean executable, PlebOSCommandHandler handler) {
        super(name);
        this.executable = executable;
        this.handler = handler;
    }

    public PlebosFile(String name) {
        this(name, false, null);
    }


    public boolean isExecutable() {
        return executable;
    }

    public PlebOSCommandHandler getExecutable() {
        if (!executable) {
            throw new PlebosFileNotExecutableException();
        }
        return handler;
    }

    public void appendLine(String line) {
        textualContents.add(line);
    }

    public List<String> getTextualContents() {
        return textualContents;
    }

    private class PlebosFileNotExecutableException extends RuntimeException {
    }
}

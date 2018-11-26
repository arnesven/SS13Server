package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;

public class PlebosFile extends FileSystemNode {
    private final boolean executable;
    private final PlebOSCommandHandler handler;

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

    private class PlebosFileNotExecutableException extends RuntimeException {
    }
}

package clientview;

public class ConnectData {
    public final boolean returning;
    public final Boolean spectator;
    public final String clid;
    public final ReturningPlayerPanel returnPanel;

    public ConnectData(boolean returning, Boolean spectator, String clid, ReturningPlayerPanel returnPanel) {
        this.returning = returning;
        this.spectator = spectator;
        this.clid = clid;
        this.returnPanel = returnPanel;
    }
}

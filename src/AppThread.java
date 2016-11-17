/**
 * Created by Fai on 17/11/2016.
 */
public abstract class AppThread implements Runnable {
    protected String id;
    protected ElevatorController elevatorController;
    protected MessageBox messageBox = null;

    public AppThread(String id, ElevatorController ec) {
        this.id = id;
        this.elevatorController = ec;
        ec.regThread(this);
        this.messageBox = ec.getMessageBox(this.id);
    }

    public MessageBox getMessageBox () {
        return this.messageBox;
    }

    public String getThreadID () {
        return this.id;
    }

}
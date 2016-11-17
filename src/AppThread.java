/**
 * Created by Fai on 17/11/2016.
 */
public abstract class AppThread implements Runnable {
    protected String id;
    protected ElevatorController elevatorController;
    protected MessageBox messageBox = null;

    public AppThread(String id, ElevatorController elevatorController) {
        this.id = id;
        this.elevatorController = elevatorController;
        this.messageBox = elevatorController.getMessageBox();
        this.elevatorController.regThread(this);
    }

    public MessageBox getMessageBox () {
        return this.messageBox;
    }

    public String getThreadID () {
        return this.id;
    }

}
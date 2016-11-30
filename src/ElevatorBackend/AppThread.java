package ElevatorBackend;

/**
 * Created by Fai on 17/11/2016.
 */
public abstract class AppThread implements Runnable {
    private String id;
    private MessageBox messageBox = null;

    public AppThread(String id, ElevatorController ec) {
        this.id = id;
    }

    public MessageBox getMessageBox () {
        return this.messageBox;
    }

    public String getID () {
        return this.id;
    }


}
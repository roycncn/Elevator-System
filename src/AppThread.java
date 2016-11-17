/**
 * Created by Fai on 17/11/2016.
 */
public abstract class AppThread implements Runnable {
    protected String id;
    protected MessageBox messageBox = null;

    public AppThread(String id) {
        this.id = id;

    }

    public MessageBox getMessageBox () {
        return this.messageBox;
    }

    public String getThreadID () {
        return this.id;
    }

}
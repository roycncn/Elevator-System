import java.util.ArrayList;
import java.util.logging.*;

/**
 * Created by test on 2016/11/16.
 */
public class Panel {
    public String panelName;
    protected ArrayList<Logger> logger;

    public Panel(String panelName) {
        this.panelName = panelName;
    }

    public boolean login(Person person) {
        return true;
    }

    public boolean writeLog(String message) {
        return true;
    }
}

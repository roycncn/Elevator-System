import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */
public class AccessConfiguration extends Configuration {
    private ArrayList<String> accessRules;

    public AccessConfiguration (String path, String configType) {
        super(path, configType);
    }

    public ArrayList<String> getAccessRules() {
        return this.accessRules;
    }

    public boolean addAccessRules(AdminPanel adminPanel, String rule) {
        if ( adminPanel.panelName == "Admin") {
            this.accessRules.add(rule);
            return true;
        }
        return false;
    }

    public boolean removeAccessRules(AdminPanel adminPanel, int ruleIndex) {
        if ( adminPanel.panelName == "Admin") {
            this.accessRules.remove(ruleIndex);
            return true;
        }
        return false;
    }

}

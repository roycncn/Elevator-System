package ElevatorBackend;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */

public class AccessConfiguration extends Configuration<AccessRule>  {
    private static AccessConfiguration accessConfiguration = null;
    private static final String path = "./JSON/AccessRules.json";

    private AccessConfiguration (String path) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        super(path, AccessRule.class.getDeclaredField("personId"), AccessRule[].class);
    }

    public static AccessConfiguration getInstance() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException{
        if (accessConfiguration == null) {
            synchronized (AccessConfiguration.class) {
                if (accessConfiguration == null) {
                    accessConfiguration = new AccessConfiguration(path);
                }
            }
        }
        return accessConfiguration;
    }


    public ArrayList<AccessRule> getAccessRules() {
        return this.content;
    }

    public boolean addAccessRules(AdminPanel adminPanel, AccessRule rule) {
        if ( adminPanel != null ) {
            accessConfiguration.content.add(rule);
            accessConfiguration.index.put(String.valueOf(rule.getPersonId()), rule);
            return true;
        }
        return false;
    }

    public boolean removeAccessRules(AdminPanel adminPanel, int ruleIndex) {
        if ( adminPanel != null ) {
            AccessRule deleted = accessConfiguration.content.remove(ruleIndex);
            accessConfiguration.index.remove(deleted.getPersonId());
            return true;
        }
        return false;
    }

    private AccessRule findAccessRule(String personId) {
        return accessConfiguration.index.get(personId);
    }

    public boolean isAuthenticated(String personId, int toFloor)
    {
        AccessRule rule = this.findAccessRule(personId);
        return rule != null && rule.getAccessibleFloorNumber().contains(toFloor);
    }

    public boolean isAuthenticated(String personId) {
        return this.findAccessRule(personId) != null;
    }
}

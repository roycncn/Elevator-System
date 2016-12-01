package ElevatorBackend;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */

public class AccessConfiguration extends Configuration<AccessRule>  {
    private static AccessConfiguration accessConfiguration = null;
    private static File configFile = null;

    private AccessConfiguration (File configFile) throws NoSuchFieldException {
        super(configFile, AccessRule.class.getDeclaredField("personId"), AccessRule[].class);
    }

    public static AccessConfiguration getInstance() {
        if (accessConfiguration == null) {
            synchronized (AccessConfiguration.class) {
                if (accessConfiguration == null) {
                    try {
                        accessConfiguration = new AccessConfiguration(configFile);
                    } catch (NoSuchFieldException e) {
                        // TODO: 30/11/16 Log down the error
                        e.printStackTrace();
                    }
                }
            }
        }
        return accessConfiguration;
    }

    public static void setConfigFile(File file) {
        if (configFile == null) {
            configFile = file;
        } else {
            // TODO: 30/11/16 Logging here and stop the system
        }

    }


    public ArrayList<AccessRule> getAccessRules() {
        return (ArrayList<AccessRule>) accessConfiguration.content.clone();
    }

    public boolean createAccessRules(AccessRule rule) {
            accessConfiguration.content.add(rule);
            accessConfiguration.index.put(String.valueOf(rule.getPersonId()), rule);
            return true;
    }

    public boolean deleteAccessRules( int ruleIndex) {
            AccessRule deleted = accessConfiguration.content.remove(ruleIndex);
            accessConfiguration.index.remove(deleted.getPersonId());
            return true;
    }

    public AccessRule findAccessRule(String personId) {
        return accessConfiguration.index.get(personId);
    }

    public boolean updateAccessRule(AccessRule toBeUpdated) {
        AccessRule ruleInContent = accessConfiguration.index.get( toBeUpdated.getPersonId() );

        return false;
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

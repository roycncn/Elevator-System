package ElevatorBackend;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/**
 * Created by test on 2016/11/16.
 */
public class ElevatorConfiguration extends Configuration<ElevatorFactorySetting> {
    private static ElevatorConfiguration elevatorConfiguration = null;
    private static File configFile = null;

    private ElevatorConfiguration(File configFile) throws NoSuchFieldException {
        super(configFile, ElevatorFactorySetting.class.getDeclaredField("id"), ElevatorFactorySetting[].class);
    }

    public static ElevatorConfiguration getInstance() {
        if (elevatorConfiguration == null) {
            synchronized (ElevatorConfiguration.class) {
                if (elevatorConfiguration == null) {
                    try {
                        elevatorConfiguration = new ElevatorConfiguration(configFile);
                    } catch (NoSuchFieldException e) {
                        // TODO: 30/11/16 Log down the error
                        e.printStackTrace();
                    }
                }
            }
        }
        return elevatorConfiguration;
    }

    public ElevatorFactorySetting findElevatorFactorySetting(Elevator elevator) {
        return this.index.get(String.valueOf(elevator.getElevatorID()));
    }

    public ElevatorFactorySetting[] getAllSettings() {
        int counter = 0;
        ElevatorFactorySetting[] result = new ElevatorFactorySetting[elevatorConfiguration.content.size()];
        Iterator<ElevatorFactorySetting> it = elevatorConfiguration.content.iterator();
        while(it.hasNext()) {
            ElevatorFactorySetting setting = it.next();
            result[counter++] = setting;
        }
        return result;
    }

    public static void setConfigFile(File file) {
        if (configFile == null) {
            configFile = file;
        } else {
            // TODO: 30/11/16 Logging here and stop the system
        }
    }
}


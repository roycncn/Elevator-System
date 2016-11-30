package ElevatorBackend;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/**
 * Created by test on 2016/11/16.
 */
public class ElevatorConfiguration extends Configuration<ElevatorFactorySetting> {
    private static ElevatorConfiguration elevatorConfiguration = null;
    private static final String path = "./JSON/Elevators.json";

    private ElevatorConfiguration(String path) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        super(path, ElevatorFactorySetting.class.getDeclaredField("id"), ElevatorFactorySetting[].class);
    }

    public static ElevatorConfiguration getInstance() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException{
        if (elevatorConfiguration == null) {
            synchronized (ElevatorConfiguration.class) {
                if (elevatorConfiguration == null) {
                    elevatorConfiguration = new ElevatorConfiguration(path);
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
}


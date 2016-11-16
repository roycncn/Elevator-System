/**
 * Created by test on 2016/11/16.
 */
public class ElevatorConfiguration extends Configuration {

    private float accelerationSpeed;
    private float maximumSpeed;
    private String runMode;

    public ElevatorConfiguration(String path, String configType) {
        super(path, configType);
    }

    public float getAccelerationSpeed() {
        return this.accelerationSpeed;
    }

    public float getMaximumSpeed() {
        return this.maximumSpeed;
    }

    public String getRunMode() {
        return this.runMode;
    }
}

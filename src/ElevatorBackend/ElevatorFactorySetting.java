package ElevatorBackend;

public class ElevatorFactorySetting {
    String id;
    String floorLevel;
    private float accelerationSpeed;
    private float maximumSpeed;
    private String runMode;

    public float getAccelerationSpeed() {
        return this.accelerationSpeed;
    }

    public float getMaximumSpeed() {
        return this.maximumSpeed;
    }

    public String getRunMode() {
        return this.runMode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(String floorLevel) {
        this.floorLevel = floorLevel;
    }
}

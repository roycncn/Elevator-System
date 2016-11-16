import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */
public class Elevator {
    public int elevatorID;
    private Configuration config;
    public ArrayList<Floor> floorQueue;
    public Floor currentFloor;
    public int status;

    public Elevator(int elevatorID, Configuration config) {
        this.elevatorID = elevatorID;
        this.config = config;
        this.floorQueue = new ArrayList<Floor>();
    }

    public boolean addFloorToQueue(Floor toFloor) {
        this.floorQueue.add(toFloor);
        return true;
    }

    public int movingDirection() {
        // Stopped
        if ( this.floorQueue.size() == 0 ) {
            return 0;
        }
        return this.currentFloor.floorLevel - this.floorQueue.get(0).floorLevel;
    }

    public int emergency() {
        return 0;
    }

    public String getRunMode() {
        return "123";
    }
}
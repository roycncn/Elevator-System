import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */
public class Elevator extends AppThread {
    private String elevatorID;
    private Configuration config;
    private ElevatorController ec;
    public ArrayList<Floor> floorQueue;
    public Floor currentFloor;

    public int status;

    public Elevator(String elevatorID, Configuration config, ElevatorController ec) {
        super(elevatorID, ec);
        this.elevatorID = elevatorID;
        this.config = config;
        this.floorQueue = new ArrayList<Floor>();
        this.ec = ec;
        System.out.printf("Elevator %s is created!\n", this.elevatorID);
    }

    public boolean addFloorToQueue(Floor toFloor) {
        this.floorQueue.add(toFloor);
        return true;
    }

    public int movingDirection() {
        // Stopped
        if (this.floorQueue.size() == 0) {
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

    public String getId() {
        return this.elevatorID;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                String msg = this.messageBox.receive();
                System.out.println(this.getId() + " received " + msg);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
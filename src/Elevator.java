import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */
public class Elevator extends AppThread {
    private String elevatorID;
    private Configuration config;
    protected ElevatorController elevatorController;
    public ArrayList<Floor> floorQueue;
    public ArrayList<String> floorQueue_dev;
    public Floor currentFloor;
    public int currentSlot;
    public int status;

    public Elevator(String elevatorID, Configuration config, ElevatorController ec) {
        super(elevatorID);
        this.elevatorController = ec;
        this.elevatorID = elevatorID;
        ec.regThread(this);
        this.messageBox = ec.getMessageBox(elevatorID);
        this.config = config;
        this.floorQueue = new ArrayList<Floor>();
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


            String msg = this.messageBox.receive();

                switch (msg){
                    //      [COL_COUNT(this case 3)]<INT>|GOTO<STRING>|[FloorNumber]<INT>
                    case "GOTO":
                        String m[] = msg.split("|");
                        floorQueue_dev.add(m[2]);
                        break;
                    default:
                        break;
                //Finished fetch and deal with msg from queue

                //Start to Work

                }
                Thread.sleep(1000);
                System.out.println(getId()+"-"+"Tick");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
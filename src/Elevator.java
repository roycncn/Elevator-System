import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */
public class Elevator implements Runnable {
    public int elevatorID;
    private Configuration config;
    private ElevatorController ec;
    public ArrayList<Floor> floorQueue;
    public Floor currentFloor;

    public int status;

    public Elevator(int elevatorID, Configuration config, ElevatorController ec) {
        this.elevatorID = elevatorID;
        this.config = config;
        this.floorQueue = new ArrayList<Floor>();
        this.ec = ec;
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

    public int getId() {
        return this.elevatorID;
    }

    @Override
    public void run() {
        while (true) {

            try {

                if (this.ec.elevatorMsgQueue.peek() != null) {
                    String msg = this.ec.elevatorMsgQueue.poll();
                    System.out.println(this.getId() + " rev " + msg);
                    Thread.sleep(500);
                } else {
                    System.out.println(this.getId()+" is wait Fetching signal from elevator");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();


            }
        }
    }
}
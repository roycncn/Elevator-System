import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */
public class Elevator extends AppThread {
    private Configuration config;
    public ArrayList<Floor> floorQueue;
    public ArrayList<String> floorQueue_dev;
    public Floor currentFloor;
    public int currentSlot;
    public int status;

    public Elevator(String id, Configuration config, ElevatorController ec) {
        super(id, ec);
        this.config = config;
        this.floorQueue = new ArrayList<Floor>();
        this.floorQueue_dev = new ArrayList<String>();
        System.out.printf("Elevator %s is created!\n", this.getID());
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

    @Override
    public void run() {
        while (true) {
            try {

                Thread.sleep(10);
                Message msg = this.getMessageBox().receive();

                switch (msg.getType()) {
                    //      [COL_COUNT(this case 3)]<INT>|GOTO<STRING>|[FloorNumber]<INT>
                    case "GOTO":
                        System.out.println(this.getID() + " Goto Message : " + msg.getDetail());
                        this.floorQueue_dev.add(msg.getDetail());
                        break;
                    case "TIC":
                        String toPrint = "";
                        for (String s : this.floorQueue_dev) {
                            toPrint += s; toPrint += " ";
                        }
                        System.out.println(this.getID() + " Floor Queue : " + toPrint);
                    default:
                        // System.out.println(this.getID()+"Default Message : " + msg.getDetail());
                        break;
                    //Finished fetch and deal with msg from queue

                    //Start to Work

                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
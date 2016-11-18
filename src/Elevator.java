import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

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
    public Date markTime;

    public Elevator(String id, int initLevel, Configuration config, ElevatorController ec) {
        super(id, ec);
        this.currentFloor = new Floor(initLevel);
        this.config = config;
        this.floorQueue = new ArrayList<Floor>();
        this.floorQueue_dev = new ArrayList<String>();
        System.out.printf("%s is created at level %d!\n", this.getID(), this.currentFloor.getFloorLevel());
    }

    public boolean addFloorToQueue(Floor toFloor) {
        this.floorQueue.add(toFloor);
        return true;
    }

    public int getMovingDirection() {
        if (this.floorQueue.size() == 0) {
            return 0;
        }

        if ( this.currentFloor.getFloorLevel() - this.floorQueue.get(0).getFloorLevel() > 0 ) {
            return -1;
        }

        return 1;
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
                        System.out.printf("%s add level %s to floor queue.\n", this.getID(), msg.getDetail());
                        this.addFloorToQueue(new Floor(Integer.parseInt(msg.getDetail())));
                        break;
                    case "TIC":
                        Date currTime = new Date();
                        switch ( this.getMovingDirection() ) {
                            case 0:
                                this.markTime = currTime;
                                break;
                            case 1:
                            case -1:
                                // Travel to next level takes 2000 ms
                                long timeDiff = currTime.getTime() - markTime.getTime();
                                float timeToReach = Math.abs(this.currentFloor.getFloorLevel() - this.floorQueue.get(0).getFloorLevel()) * 2000 - ((int) timeDiff);

                                System.out.printf("%s is moving to level %d. It arrives at %f second(s). \n", this.getID(), this.floorQueue.get(0).getFloorLevel(), timeToReach/1000);
                                if ( timeToReach < 0 ) {
                                    this.markTime = currTime;
                                    this.currentFloor = this.floorQueue.remove(0);
                                    System.out.printf("%s is now reached level %d. \n", this.getID(), this.currentFloor.getFloorLevel());
                                }
                                break;
                            default:
                                break;
                        }
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
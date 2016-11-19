import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by test on 2016/11/16.
 */
public class Elevator extends AppThread {
    private Configuration config;
    public ArrayList<Floor> floorQueue;
    public ArrayList<Floor> floorQueueSpare;
    public ArrayList<String> floorQueue_dev;
    public Floor currentFloor;
    public int currentHeight;
    public int speed;
    public Date markTime;

    public Elevator(String id, int initLevel, Configuration config, ElevatorController ec) {
        super(id, ec);
        this.currentFloor = new Floor(initLevel);
        this.currentHeight = this.currentFloor.getHeight();
        this.speed = 100;
        this.config = config;
        this.floorQueue = new ArrayList<Floor>();
        this.floorQueueSpare = new ArrayList<Floor>();
        this.floorQueue_dev = new ArrayList<String>();
        System.out.printf("%s is created at level %d!\n", this.getID(), this.currentFloor.getFloorLevel());
    }

    public void addFloorToQueue(Floor toFloor) {
        this.floorQueue.add(toFloor);

        if (this.getMovingDirection() == 1) {
            Collections.sort(this.floorQueue, new Comparator<Floor>() {
                @Override
                public int compare(Floor o1, Floor o2) {
                    return o1.getFloorLevel() - o2.getFloorLevel();
                }
            });
        } else {
            Collections.sort(this.floorQueue, new Comparator<Floor>() {
                @Override
                public int compare(Floor o1, Floor o2) {
                    return o2.getFloorLevel() - o1.getFloorLevel();
                }
            });
        }
        this.printFloorQueueToString();
    }

    public int getMovingDirection() {
        if (this.floorQueue.size() == 0) {
            return 0;
        }

        if (this.currentFloor.getFloorLevel() - this.floorQueue.get(0).getFloorLevel() > 0) {
            return -1;
        }

        return 1;
    }

    public int getFloorLevelDifferentToFloor(Floor floor) {
        return Math.abs(this.currentFloor.getFloorLevel() - floor.getFloorLevel());
    }

    public int getTotalFloorToBeVisited (Floor floor) {
        int totalFloorLevelToBeVisited = Math.abs( this.floorQueue.get(0).getFloorLevel() - this.floorQueue.get(this.floorQueue.size() - 1).getFloorLevel() );
        return Math.abs(totalFloorLevelToBeVisited - floor.getFloorLevel());
    }

    public void printFloorQueueToString() {
        String printString = " ";
        for (Floor floor : this.floorQueue) {
            printString += floor.getFloorLevel();
            printString += " ";
        }
        System.out.printf("%s : Floor Queue [%s]. \n", this.getID(), printString);
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
                        this.addFloorToQueue(new Floor(Integer.parseInt(msg.getDetail())));
                        break;
                    case "WAIT":
                        this.floorQueueSpare.add(new Floor(Integer.parseInt(msg.getDetail())));
                        break;
                    case "TIC":
                        Date currTime = new Date();
                        switch (this.getMovingDirection()) {
                            case 0:
                                this.markTime = currTime;
                                break;
                            case 1:
                            case -1:
                                // Travel to next level takes 2000 ms

                                this.currentHeight -= this.speed;

                                if (this.currentHeight < 0) {
                                    this.currentFloor = new Floor(this.currentFloor.getFloorLevel() + this.getMovingDirection());
                                    this.currentHeight = this.currentFloor.getHeight();

                                    if (this.currentFloor.getFloorLevel() == this.floorQueue.get(0).getFloorLevel()) {
                                        this.currentFloor = this.floorQueue.remove(0);
                                        System.out.printf("** %s reached level %d.\n", this.getID(), this.currentFloor.getFloorLevel());

                                        if ( this.floorQueue.size() == 0 ) {
                                            for (Floor floor : this.floorQueueSpare) {
                                                this.addFloorToQueue(floor);
                                            }
                                            this.floorQueueSpare.clear();
                                            this.printFloorQueueToString();
                                        }

                                        break;
                                    }

                                    System.out.printf("%s reached level %d.\n", this.getID(), this.currentFloor.getFloorLevel());
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
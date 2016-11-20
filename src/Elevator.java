import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.nio.cs.ext.MacHebrew;

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
    public Floor currentFloor;
    public int currentHeight;
    public int speed;
    public Date markTime;

    public Elevator(String id, int initLevel, Configuration config, ElevatorController ec) {
        super(id, ec);
        this.currentFloor = new Floor(initLevel);
        this.currentHeight = this.currentFloor.getHeight();
        this.speed = 25;
        this.config = config;
        this.floorQueue = new ArrayList<Floor>();
        this.floorQueueSpare = new ArrayList<Floor>();
        System.out.printf("%s is created at level %d!\n", this.getID(), this.currentFloor.getFloorLevel());
    }

    public void addFloorToQueue(Floor toFloor) {
        if (this.floorQueue.size() == 0) {
            this.floorQueue.add(toFloor);
            return;
        }

        if (getMovingDirection() == this.currentFloor.getDirectionBetweenFloor(toFloor)) {
            this.floorQueue.add(toFloor);
            this.sortFloorQueueInOrder(getMovingDirection());
        } else {
            this.floorQueueSpare.add(toFloor);
        }
    }

    public int getMovingDirection() {
        // Stop
        if (this.floorQueue.size() == 0) {
            return 0;
        }

        // Going down
        if (this.currentFloor.getFloorLevel() > this.floorQueue.get(0).getFloorLevel()) {
            return -1;
        }

        // Going up
        return 1;
    }

    public boolean canPickup(Floor floor) {
        switch (getMovingDirection()) {
            case 1:
                if ( this.currentFloor.getFloorLevel() > floor.getFloorLevel() ) return true;
                if ( this.floorQueue.get(this.floorQueue.size()-1).getFloorLevel() < floor.getFloorLevel() ) return true;
                break;
            case -1:
                if ( this.currentFloor.getFloorLevel() < floor.getFloorLevel() ) return true;
                if ( this.floorQueue.get(this.floorQueue.size()-1).getFloorLevel() > floor.getFloorLevel() ) return true;
                break;
            default:
                break;
        }
        return false;
    }


    public void sortFloorQueueInOrder(int i) {
        // Ascending order if i > 1
        // else Descending order
        if (i > 0) {
            Collections.sort(this.floorQueue);
        } else {
            Collections.sort(this.floorQueue, Collections.reverseOrder());
        }
    }

    public void sortFloorQueueSpareInOrder(int i) {
        // Descending order if i > 1
        // else Ascending order
        if (i > 0) {
            Collections.sort(this.floorQueueSpare, Collections.reverseOrder());
        } else {
            Collections.sort(this.floorQueueSpare);
        }
    }

    public int getFloorLevelDifferentToFloor(Floor floor) {
        return Math.abs(this.currentFloor.getFloorLevel() - floor.getFloorLevel());
    }

    public int getTotalFloorToBeVisited(Floor floor) {
        if (this.floorQueue.size() == 0) {
            return Math.abs(this.currentFloor.getFloorLevel() - floor.getFloorLevel());
        }
        int totalFloorsFromCurrToFinal = Math.abs(this.currentFloor.getFloorLevel() - this.floorQueue.get(this.floorQueue.size() - 1).getFloorLevel());
        int totalFloorsFromFinalToFloor = Math.abs(this.floorQueue.get(this.floorQueue.size() - 1).getFloorLevel() - floor.getFloorLevel());

        return Math.abs(totalFloorsFromCurrToFinal + totalFloorsFromFinalToFloor);
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public int getCurrentHeight() {
        return currentHeight;
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
                        if (this.floorQueue.size() == 0) {
                            this.floorQueue.add(this.floorQueueSpare.remove(0));
                        } else {
                            this.sortFloorQueueSpareInOrder(this.getMovingDirection());
                        }

                        System.out.println(msg.getDetail());
                        break;
                    case "TIC":
                        Date currTime = new Date();
                        switch (this.getMovingDirection()) {
                            case 0:
                                if (this.floorQueue.size() == 0 && this.floorQueueSpare.size() > 0) {
                                    while(this.floorQueueSpare.size() > 0) {
                                        this.floorQueue.add(this.floorQueueSpare.remove(0));
                                    }
                                }
                                this.markTime = currTime;
                                break;
                            case 1:
                            case -1:
                                // Travel to next level takes 2000 ms
                                this.currentHeight -= this.speed;
                                if (this.currentHeight <= 0) {
                                    this.currentFloor = new Floor(this.currentFloor.getFloorLevel() + this.getMovingDirection());
                                    this.currentHeight = this.currentFloor.getHeight();

                                    if (this.currentFloor.getFloorLevel() == this.floorQueue.get(0).getFloorLevel()) {
                                        this.currentFloor = this.floorQueue.remove(0);
                                        this.currentHeight = this.currentFloor.getHeight();
                                        Thread.sleep(500);
                                        System.out.printf("** %s reached level %d.\n", this.getID(), this.currentFloor.getFloorLevel());

                                        if (this.floorQueue.size() == 0 && this.floorQueueSpare.size() > 0) {
                                                this.floorQueue.add(this.floorQueueSpare.remove(0));
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
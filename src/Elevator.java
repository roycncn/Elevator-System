

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by test on 2016/11/16.
 */
public class Elevator extends Thread {
    private Configuration config;
    private ArrayList<Floor> floorQueue;
    private ArrayList<Floor> floorQueueSpare;
    private Floor currentFloor;
    private int currentHeight;
    private int speed;
    private Date markTime;
    private String elevatorID;
    private MessageBox messageBox;

    public Elevator(String id, int initLevel, Configuration config, ElevatorController ec) {
        this.elevatorID = id;
        this.messageBox = ec.getElevatorMessageBox(this);
        this.reachFloor(new Floor(initLevel));
        this.speed = 25;
        this.config = config;
        this.floorQueue = new ArrayList<Floor>();
        this.floorQueueSpare = new ArrayList<Floor>();
        System.out.printf("%s is created at level %d!\n", this.getElevatorID(), this.currentFloor.getFloorLevel());
    }

    public void addFloorToCurrentQueue(Floor toFloor) {
        this.floorQueue.add(toFloor);
        this.sortFloorQueueInOrder(getMovingDirection());
    }

    public void addFloorToSpareQueue(Floor toFloor) {
        this.floorQueueSpare.add(toFloor);
        if (this.floorQueue.size() == 0) {
            this.floorQueue.add(this.floorQueueSpare.remove(0));
        } else {
            this.sortFloorQueueSpareInOrder(this.getMovingDirection());
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
        if (this.currentFloor.getFloorLevel() < this.floorQueue.get(0).getFloorLevel()) {
            return 1;
        }

        return 0;
    }

    public boolean canPickup(Floor fromFloor, Floor toFloor) {
        switch (getMovingDirection()) {
            case 1:
                if (this.currentFloor.getFloorLevel() > fromFloor.getFloorLevel()) return false;
                if (this.floorQueue.get(0).getFloorLevel() < toFloor.getFloorLevel()) return false;
                break;
            case -1:
                if (this.currentFloor.getFloorLevel() < fromFloor.getFloorLevel()) return false;
                if (this.floorQueue.get(0).getFloorLevel() > toFloor.getFloorLevel()) return false;
                break;
            default:
                return false;
        }
        return true;
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

        if (this.floorQueue.size() == 0 && this.floorQueueSpare.size() == 0) {
            return Math.abs(this.currentFloor.getFloorLevel() - floor.getFloorLevel());
        }

        int a = 0; // curr to floorCurr[last]
        int b = 0; // floorCurr[last] to floorSpare[last]
        int c = 0; // floorSpare[last] to floor


        if (this.floorQueue.size() > 0) {
            a = Math.abs(this.currentFloor.getFloorLevel() - this.floorQueue.get(this.floorQueue.size() - 1).getFloorLevel());
        }
        if (this.floorQueue.size() > 0 && this.floorQueueSpare.size() > 0) {
            b = Math.abs(this.floorQueue.get(this.floorQueue.size() - 1).getFloorLevel() - this.floorQueueSpare.get(this.floorQueueSpare.size() - 1).getFloorLevel());
        }
        if (this.floorQueueSpare.size() > 0) {
            c = Math.abs(this.floorQueueSpare.get(this.floorQueueSpare.size() - 1).getFloorLevel() - floor.getFloorLevel());
        }


        return a + b + c;
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

    public void move() {
        Date currTime = new Date();

        if (this.getMovingDirection() == 0) {
            if (this.floorQueue.size() == 0 && this.floorQueueSpare.size() > 0) {
                while (this.floorQueueSpare.size() > 0) {
                    this.floorQueue.add(this.floorQueueSpare.remove(0));
                }
            }
            this.markTime = currTime;
        }

        // Travel to next level takes 2000 ms
        if (this.floorQueue.size() > 0) {
            if (this.currentFloor.getFloorLevel() == this.floorQueue.get(0).getFloorLevel()) {
                this.reachFloor(this.floorQueue.remove(0));
                System.out.printf("** %s reached level %d.\n", this.getElevatorID(), this.currentFloor.getFloorLevel());
            } else {
                this.currentHeight -= this.speed * 2;
                if (currentHeight <= 0) {
                    this.reachFloor(new Floor(this.currentFloor.getFloorLevel() + this.getMovingDirection()));
                    System.out.printf("%s reached level %d.\n", this.getElevatorID(), this.currentFloor.getFloorLevel());
                }
            }
        }


//        this.currentHeight -= this.speed * 2;
//        if (this.currentHeight <= 0) {
//            this.currentFloor = new Floor(this.currentFloor.getFloorLevel() + this.getMovingDirection());
//            this.currentHeight = this.currentFloor.getHeight();
//
//            if (this.currentFloor.getFloorLevel() == this.floorQueue.get(0).getFloorLevel()) {
//                this.currentFloor = this.floorQueue.remove(0);
//                this.currentHeight = this.currentFloor.getHeight();
//                //Thread.sleep(100);
//                System.out.printf("** %s reached level %d.\n", this.getID(), this.currentFloor.getFloorLevel());
//
//                if (this.floorQueue.size() == 0 && this.floorQueueSpare.size() > 0) {
//                    this.floorQueue.add(this.floorQueueSpare.remove(0));
//                }
//
//            }
            //System.out.printf("%s reached level %d.\n", this.getID(), this.currentFloor.getFloorLevel());
//        }

    }

    public void reachFloor(Floor floor) {
        this.currentFloor = floor;
        this.currentHeight = floor.getHeight();
    }

    public String getElevatorID() {
        return elevatorID;
    }

    @Override
    public void run() {
        while (true) {

            //System.out.println((new Date()).getTime()+"Lenth:"+this.getMessageBox().getLength());
            Message msg = this.messageBox.receive();

            switch (msg.getType()) {
                //      [COL_COUNT(this case 3)]<INT>|GOTO<STRING>|[FloorNumber]<INT>
                case "GOTO":
                    this.addFloorToCurrentQueue(new Floor(Integer.parseInt(msg.getDetail())));
                    break;
                case "WAIT":
                    this.addFloorToSpareQueue(new Floor(Integer.parseInt(msg.getDetail())));
                    break;
                case "TIC":
                    this.move();
                    break;
                default:
                    // System.out.println(this.getID()+"Default Message : " + msg.getDetail());
                    break;
                //Finished fetch and deal with msg from queue

                //Start to Work

            }


        }
    }
}
package ElevatorBackend;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by test on 2016/11/16.
 */
public class ElevatorController {
    public ArrayList<Kiosk> kiosks;
    public ArrayList<Elevator> elevators;
    public Building building;
    private BlockingQueue<String> testBoxes = new LinkedBlockingDeque<String>();
    private Ticker ticker;
    private ArrayList<MessageBox> elevatorMessageBoxes = null;
    private ArrayList<MessageBox> kioskMessageBoxes = null;

    public ElevatorController() {


        // New**
        this.elevators = new ArrayList<Elevator>();
        this.elevatorMessageBoxes = new ArrayList<MessageBox>();
        this.kioskMessageBoxes = new ArrayList<MessageBox>();

        System.out.println("Log: ElevatorBackend.ElevatorController Constructed...");
    }

    public void regElevators(ArrayList<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void regKiosks(ArrayList<Kiosk> kiosks) {
        this.kiosks = kiosks;
    }

    public void regBuilding(Building building) {
        this.building = building;
    }

    public void startSystem() throws InterruptedException {
        this.ticker = new Ticker("ElevatorBackend.Ticker", this, 200);
        this.ticker.start();

        for (Elevator elevator : elevators) {
            elevator.start();
        }

        for (Kiosk k : kiosks) {
            new Thread(k).start();
        }

        this.showKiosksStatus();
    }

    public void showKiosksStatus() {
        Message ping = new Message("PING", "");
        for (MessageBox messageBox : this.kioskMessageBoxes) {
            messageBox.send(ping);
        }
    }

    public void showElevatorsStatus() {

    }

    // Find ele algo
    public String findElevator(Floor fromFloor, Floor toFloor) {
        String eleID = "NULL";
        int closestFloorLevel = Integer.MAX_VALUE;
        int direction = fromFloor.getDirectionBetweenFloor(toFloor);

        for (Elevator ele : this.elevators) {
            if (direction == ele.getMovingDirection()) {
                if (closestFloorLevel > ele.getFloorLevelDifferentToFloor(fromFloor) && ele.canPickup(fromFloor, toFloor)) {
                    closestFloorLevel = ele.getFloorLevelDifferentToFloor(fromFloor);
                    eleID =String.valueOf(ele.getElevatorID());
                }
            }
        }

        if (eleID.equals("NULL")) {
            for (Elevator ele : this.elevators) {
                if (closestFloorLevel > ele.getTotalFloorToBeVisited(fromFloor)) {
                    closestFloorLevel = ele.getTotalFloorToBeVisited(fromFloor);
                    eleID =String.valueOf(ele.getElevatorID());
                }
            }
            System.out.printf("%s : Added to spare queue [%d to %d]. \n", eleID, fromFloor.getFloorLevel(), toFloor.getFloorLevel());
            this.getElevator(eleID).addFloorToSpareQueue(fromFloor);
            this.getElevator(eleID).addFloorToSpareQueue(toFloor);
        } else {
            System.out.printf("%s : Added to current queue [%d to %d]. \n", eleID, fromFloor.getFloorLevel(), toFloor.getFloorLevel());
            this.getElevator(eleID).addFloorToCurrentQueue(fromFloor);
            this.getElevator(eleID).addFloorToCurrentQueue(toFloor);
        }

        return eleID;
    }

    public Ticker getTicker() {
        return this.ticker;
    }


    public void sendMessageToAllElevators(Message msg) {
       for (MessageBox messageBox : this.elevatorMessageBoxes) {
           messageBox.send(msg);
       }
    }

    public ArrayList<String> getElevatorsStatus() {
        ArrayList<String> eleStatus = new ArrayList<String>();

        for (Elevator elevator : this.elevators) {
            eleStatus.add(elevator.getElevatorID() + " | " + elevator.getCurrentFloor().getFloorLevel() + " | " + elevator.getMovingDirection());
        }
        if (eleStatus.size() == 0) {
            eleStatus.add("Bye");
        }
        return eleStatus;
    }

    public ArrayList<String> getElevatorsQueueStatus() {
        ArrayList<String> eleQueueStatus = new ArrayList<String>();

        for (Elevator elevator : this.elevators) {
            eleQueueStatus.add(elevator.getElevatorID() + " | " + elevator.getWorkingQueue());
            eleQueueStatus.add(elevator.getElevatorID() + " | " + elevator.getSpareQueue());
        }
        if (eleQueueStatus.size() == 0) {
            eleQueueStatus.add("Bye");
        }
        return eleQueueStatus;
    }


    public Elevator getElevator(String id) {
        for (Elevator ele : this.elevators) {
            if (ele.getElevatorID().equals(id)) {
                return ele;
            }
        }
        return null;
    }

    public MessageBox getElevatorMessageBox(Elevator elevator) {
        MessageBox mb = new MessageBox(elevator.getElevatorID());
        this.elevatorMessageBoxes.add(mb);
        return mb;
    }

    public MessageBox getKioskMessageBox(Kiosk kiosk) {
        MessageBox mb = new MessageBox(kiosk.getKioskID());
        this.kioskMessageBoxes.add(mb);
        return mb;
    }
}

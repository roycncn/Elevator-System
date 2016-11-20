import java.awt.datatransfer.FlavorListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by test on 2016/11/16.
 */
public class ElevatorController {
    public ArrayList<Kiosk> kiosks;
    public ArrayList<Elevator> elevators;
    private Hashtable<String, AppThread> appThreads = null;
    private Hashtable<String, MessageBox> messageBoxes = null;
    private BlockingQueue<String> testBoxes = new LinkedBlockingDeque<String>();
    private AppThread ticker;

    public ElevatorController() {
        this.appThreads = new Hashtable<String, AppThread>();
        this.messageBoxes = new Hashtable<String, MessageBox>();
        System.out.println("Log: ElevatorController Constructed...");
    }

    public void regElevators(ArrayList<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void regKiosks(ArrayList<Kiosk> kiosks) {
        this.kiosks = kiosks;
    }

    public void startSystem() throws InterruptedException {
        this.ticker = new Ticker("Ticker", this, 1, 5000);
        new Thread(this.ticker).start();

        for (Elevator elevator : elevators) {
            new Thread(elevator).start();
        }

        Thread.sleep(50);
        this.findElevator(new Floor(1), new Floor(0));
        Thread.sleep(50);
        this.findElevator(new Floor(2), new Floor(4));
        Thread.sleep(50);
        this.findElevator(new Floor(10), new Floor(3));
        Thread.sleep(50);
        this.findElevator(new Floor(4), new Floor(6));
        Thread.sleep(50);
        this.findElevator(new Floor(7), new Floor(2));
        Thread.sleep(50);
        this.findElevator(new Floor(3), new Floor(9));

    }


    public void showKiosksStatus() {

    }

    public void showElevatorsStatus() {

    }

    public void findElevator(Floor fromFloor, Floor toFloor) {
        String eleID = "NULL";
        int closestFloorLevel = Integer.MAX_VALUE;
        int direciton = fromFloor.getDirectionBetweenFloor(toFloor);

        for (Elevator ele : this.elevators) {
            if (direciton == ele.getMovingDirection()) {
                if (closestFloorLevel > ele.getFloorLevelDifferentToFloor(fromFloor)  && ele.canPickup(fromFloor)) {
                    closestFloorLevel = ele.getFloorLevelDifferentToFloor(fromFloor);
                    eleID = ele.getID();
                }
            }
        }

        if (eleID.equals("NULL")) {
            for (Elevator ele : this.elevators) {
                if (closestFloorLevel > ele.getTotalFloorToBeVisited(fromFloor)) {
                    closestFloorLevel = ele.getTotalFloorToBeVisited(fromFloor);
                    eleID = ele.getID();
                }
            }
            System.out.printf("%s : Added to spare queue [%d to %d]. \n", eleID, fromFloor.getFloorLevel(), toFloor.getFloorLevel());
            sendMessage(eleID, new Message("WAIT", String.valueOf(fromFloor.getFloorLevel())));
            sendMessage(eleID, new Message("WAIT", String.valueOf(toFloor.getFloorLevel())));
        } else {
            System.out.printf("%s : Added to current queue [%d to %d]. \n", eleID, fromFloor.getFloorLevel(), toFloor.getFloorLevel());
            sendMessage(eleID, new Message("GOTO", String.valueOf(fromFloor.getFloorLevel())));
            sendMessage(eleID, new Message("GOTO", String.valueOf(toFloor.getFloorLevel())));
        }


    }

    public void regThread(AppThread appThread) {
        this.appThreads.put(appThread.getID(), appThread);
        this.messageBoxes.put(appThread.getID(), new MessageBox(appThread.getID()));
    }

    public AppThread getThread(String id) {
        return this.appThreads.get(id);
    }

    public AppThread getTicker() {
        return this.ticker;
    }


    public MessageBox getMessageBox(String id) {
        return this.messageBoxes.get(id);
    }

    public void sendMessageToAllElevators(Message msg) {
        for (String key : this.messageBoxes.keySet()) {
            this.sendMessage(this.getMessageBox(key).getID(), msg);
        }
    }

    public String getElevatorsStatus(){
        return "LKF";

    }

    public void sendMessage(String id, Message msg) {
        this.messageBoxes.get(id).send(msg);
    }

}

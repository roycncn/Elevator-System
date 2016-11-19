import java.awt.datatransfer.FlavorListener;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by test on 2016/11/16.
 */
public class ElevatorController {
    public ArrayList<Kiosk> kiosks;
    public ArrayList<Elevator> elevators;
    private Hashtable<String, AppThread> appThreads = null;
    private Hashtable<String, MessageBox> messageBoxes = null;
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
        this.ticker = new Ticker("Ticker", this, 100, 500);
        new Thread(this.ticker).start();

        for (Elevator elevator : elevators) {
            new Thread(elevator).start();
        }

        Thread.sleep(10);
        this.findElevator(new Floor(2), new Floor(5));
        Thread.sleep(10);
        this.findElevator(new Floor(4), new Floor(6));
        Thread.sleep(10);
        this.findElevator(new Floor(14), new Floor(16));
        Thread.sleep(10);
        this.findElevator(new Floor(11), new Floor(25));
        Thread.sleep(10);
        this.findElevator(new Floor(17), new Floor(7));
    }


    public void showKiosksStatus() {

    }

    public void showElevatorsStatus() {

    }

    public void findElevator(Floor fromFloor, Floor toFloor) {
        System.out.printf("Finding elevator from %d to %d. \n", fromFloor.getFloorLevel(), toFloor.getFloorLevel());
        String eleID = "###";
        int closestFloorLevel = Integer.MAX_VALUE;
        int direciton = fromFloor.getDirectionBetweenFloor(toFloor);

        for (Elevator ele : this.elevators) {
            if (direciton == ele.getMovingDirection() || ele.getMovingDirection() == 0) {
                if (closestFloorLevel > ele.getFloorLevelDifferentToFloor(fromFloor)) {
                    closestFloorLevel = ele.getFloorLevelDifferentToFloor(fromFloor);
                    eleID = ele.getID();
                }
            }
        }

        if (eleID.equals("###")) {
            for (Elevator ele : this.elevators) {
                if (closestFloorLevel > ele.getTotalFloorToBeVisited(fromFloor)) {
                    closestFloorLevel = ele.getTotalFloorToBeVisited(fromFloor);
                    eleID = ele.getID();
                }
            }
            sendMessage(eleID, new Message("WAIT", String.valueOf(fromFloor.getFloorLevel())));
            sendMessage(eleID, new Message("WAIT", String.valueOf(toFloor.getFloorLevel())));
        } else {
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

    public void sendMessage(String id, Message msg) {
        this.messageBoxes.get(id).send(msg);
    }

}

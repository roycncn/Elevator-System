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
        this.ticker = new Ticker("Ticker", this, 500, 100);
        new Thread(this.ticker).start();

        for (Elevator elevator : elevators) {
            new Thread(elevator).start();
        }

        Thread.sleep(1000);
        sendMessage("Elevator_0", new Message("GOTO", "1"));
        Thread.sleep(1000);
        sendMessage("Elevator_1", new Message("GOTO", "20"));
//        Thread.sleep(1000);
//        sendMessage("Elevator_2", new Message("GOTO", "12"));
//        Thread.sleep(1000);
//        sendMessage("Elevator_3", new Message("GOTO", "5"));
//        Thread.sleep(1000);
//        sendMessage("Elevator_4", new Message("GOTO", "6"));
//        Thread.sleep(1000);
//        sendMessage("Elevator_4", new Message("GOTO", "1"));
//        Thread.sleep(1000);
//        sendMessage("Elevator_4", new Message("GOTO", "3"));
//        Thread.sleep(1000);
//        sendMessage("Elevator_4", new Message("GOTO", "2"));
//        Thread.sleep(1000);
//        sendMessage("Elevator_4", new Message("GOTO", "20"));
//        Thread.sleep(1000);

    }

    public void showKiosksStatus() {

    }

    public void showElevatorsStatus() {

    }

    public Elevator findElevator(Floor fromFloor, Floor toFloor) {
        return this.elevators.get(0);
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

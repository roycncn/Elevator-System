import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
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

    public void startSystem() {
        this.ticker = new Ticker("Ticker",1000,5);
        new Thread(ticker).start();
        for (Elevator elevator : elevators) {
            new Thread(elevator).start();
        }

            sendMessage("Elevator_0",new Message("0..1"));
            sendMessage("Elevator_1",new Message("1..1"));
            sendMessage("Elevator_2",new Message("2..1"));
            sendMessage("Elevator_3",new Message("3..1"));
            sendMessage("Elevator_4",new Message("4..1"));
            sendMessage("Elevator_4",new Message("4..2"));
            sendMessage("Elevator_4",new Message("4..2"));
            sendMessage("Elevator_4",new Message("4..3"));




    }

    public void showKiosksStatus() {

    }

    public void showElevatorsStatus() {

    }

    public Elevator findElevator(Floor fromFloor, Floor toFloor) {
        return this.elevators.get(0);
    }

    public void regThread(AppThread appThread) {
        this.appThreads.put(appThread.getThreadID(), appThread);
        this.messageBoxes.put(appThread.getThreadID(), new MessageBox(appThread.getThreadID()));
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

    public void sendMessage(String id,Message msg) { this.messageBoxes.get(id).send(msg); }

}

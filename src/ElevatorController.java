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

    public ElevatorController() {
        this.appThreads = new Hashtable<String, AppThread>();
        this.messageBoxes = new Hashtable<String, MessageBox>();
        System.out.println("Log: ElevatorController Constructed...");
    }

    public Elevator findElevator(Floor fromFloor, Floor toFloor) {
        return this.elevators.get(0);
    }

    public void regElevators(ArrayList<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void regKiosks(ArrayList<Kiosk> kiosks) {
        this.kiosks = kiosks;
    }

    public void startSystem() {

        for (Elevator elevator : elevators) {
            new Thread(elevator).start();
        }

        try {
            Thread.sleep(100);
            this.messageBoxes.get("1").send(new Message("1..1"));
            this.messageBoxes.get("2").send(new Message("2..1"));
            this.messageBoxes.get("3").send(new Message("3..1"));
            this.messageBoxes.get("4").send(new Message("4..1"));
            this.messageBoxes.get("1").send(new Message("1..2"));
            this.messageBoxes.get("4").send(new Message("4..2"));
            this.messageBoxes.get("4").send(new Message("4..3"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void showKiosksStatus() {

    }

    public void showElevatorsStatus() {

    }

    public void regThread(AppThread appThread) {
        this.appThreads.put(appThread.getThreadID(), appThread);
        this.messageBoxes.put(appThread.getThreadID(), new MessageBox(appThread.getThreadID()));
    }

    public AppThread getThread(String id) {
        return this.appThreads.get(id);
    }

    public MessageBox getMessageBox(String id) {
        return this.messageBoxes.get(id);
    }

}

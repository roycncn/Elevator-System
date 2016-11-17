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
    public BlockingQueue<String> elevatorMsgQueue; //Msg send to elevators
    public BlockingQueue<String> controllerMsgQueue; // Msg rev from elevators/kiosk
    private Hashtable<String, AppThread> appThreads = null;
    private MessageBox messageBox;

    public ElevatorController() {
        this.elevatorMsgQueue = new LinkedBlockingQueue<String>();
        this.controllerMsgQueue = new LinkedBlockingQueue<String>();
        this.appThreads = new Hashtable<String, AppThread>();
        this.messageBox = new MessageBox("ElevatorController MessageBox");
        System.out.println("Log:ElevatorController Constructed...");
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
            Thread.sleep(10);
            this.messageBox.send(new Message("Ele 2", "Queue : 1"));
            this.messageBox.send(new Message("Ele 1", "Queue : 2"));
            this.messageBox.send(new Message("Ele 1", "Queue : 3"));
            this.messageBox.send(new Message("Ele 2", "Queue : 4"));
            this.messageBox.send(new Message("Ele 3", "Queue : 5"));
            this.messageBox.send(new Message("Ele 0", "Queue : 6"));
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
    }

    public AppThread getThread(String id) {
        return this.appThreads.get(id);
    }

    public MessageBox getMessageBox() {
        return this.messageBox;
    }

}

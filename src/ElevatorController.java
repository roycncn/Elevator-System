import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by test on 2016/11/16.
 */
public class ElevatorController {
    public ArrayList<Kiosk> kiosks;
    public ArrayList<Elevator> elevators;
    public ConcurrentLinkedQueue<String> elevatorMsgQueue; //Msg send to elevators
    public ConcurrentLinkedQueue<String> controllerMsgQueue; // Msg rev from elevators/kiosk

    public ElevatorController () {
        this.elevatorMsgQueue = new ConcurrentLinkedQueue<String>();
        this.controllerMsgQueue = new ConcurrentLinkedQueue<String>();
        System.out.println("Log:ElevatorController Constructed...");
    }

    public Elevator findElevator(Floor fromFloor, Floor toFloor) {
        return this.elevators.get(0);
    }

    public void regElevators(ArrayList<Elevator> elevators){
        this.elevators = elevators;
    }

    public void regKiosks(ArrayList<Kiosk> kiosks){
        this.kiosks = kiosks;
    }

    public void startSystem(){

        for (Elevator elevator : elevators) {
            Thread t = new Thread(elevator);
            t.start();
        }

        elevatorMsgQueue.add("What");
        elevatorMsgQueue.add("The");
        elevatorMsgQueue.add("Fuck");
        elevatorMsgQueue.add("Is");
        elevatorMsgQueue.add("That");




    }
    public void showKiosksStatus() {

    }

    public void showElevatorsStatus() {

    }
}

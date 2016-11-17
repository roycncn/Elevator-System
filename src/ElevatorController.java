import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by test on 2016/11/16.
 */
public class ElevatorController {
    public ArrayList<Kiosk> kiosks;
    public ArrayList<Elevator> elevators;
    public BlockingQueue<String> elevatorMsgQueue; //Msg send to elevators
    public BlockingQueue<String> controllerMsgQueue; // Msg rev from elevators/kiosk

    public ElevatorController () {
        this.elevatorMsgQueue = new LinkedBlockingQueue<String>();
        this.controllerMsgQueue = new LinkedBlockingQueue<String>();
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

        try {
            elevatorMsgQueue.put("What");
            elevatorMsgQueue.put("The");
            elevatorMsgQueue.put("Fuck");
            elevatorMsgQueue.put("Is");
            elevatorMsgQueue.put("That");
            Thread.sleep(1000);
            elevatorMsgQueue.put("我睡醒了！！！！！！！！！！！！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





    }
    public void showKiosksStatus() {

    }

    public void showElevatorsStatus() {

    }
}

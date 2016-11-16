import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */
public class ElevatorController {
    public ArrayList<Kiosk> kiosks;
    public ArrayList<Elevator> elevators;

    public ElevatorController (ArrayList<Kiosk> kiosks, ArrayList<Elevator> elevators) {
        this.kiosks = kiosks;
        this.elevators = elevators;
    }

    public Elevator findElevator(Floor fromFloor, Floor toFloor) {
        return this.elevators.get(0);
    }

    public void showKiosksStatus() {

    }

    public void showElevatorsStatus() {

    }
}

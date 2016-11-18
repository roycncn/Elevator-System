import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by roycn on 2016/11/17.
 */
public class Simulator {

    public static void main(String[] args) {
        ElevatorController elevatorController = new ElevatorController();

        ArrayList<Elevator> elevators = new ArrayList<Elevator>();
        elevators.add(new Elevator("Elevator_0", new Configuration(), elevatorController));
        elevators.add(new Elevator("Elevator_1", new Configuration(), elevatorController));
        elevators.add(new Elevator("Elevator_2", new Configuration(), elevatorController));
        elevators.add(new Elevator("Elevator_3", new Configuration(), elevatorController));
        elevators.add(new Elevator("Elevator_4", new Configuration(), elevatorController));

        elevatorController.regElevators(elevators);

        try {
            elevatorController.startSystem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}

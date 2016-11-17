import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by roycn on 2016/11/17.
 */
public class Simulator {


    public static void main(String[] args){
        ElevatorController elevatorController = new ElevatorController();

        ArrayList<Elevator> elevators = new ArrayList<Elevator>();
        elevators.add(new Elevator(0,new Configuration(),elevatorController));
        elevators.add(new Elevator(1,new Configuration(),elevatorController));
        elevators.add(new Elevator(2,new Configuration(),elevatorController));
        elevators.add(new Elevator(3,new Configuration(),elevatorController));
        elevators.add(new Elevator(4,new Configuration(),elevatorController));

        elevatorController.regElevators(elevators);

        elevatorController.startSystem();



    }
}

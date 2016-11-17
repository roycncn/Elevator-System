import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */
public class Building {

    public ArrayList<Floor> floors;
    public ArrayList<Person> inhousePerson;
    public ElevatorController elevatorController;

    public Building(ArrayList<Floor> floors, ArrayList<Person> inhousePerson, ElevatorController elevatorController) {
        this.floors = floors;
        this.inhousePerson = inhousePerson;
        this.elevatorController = elevatorController;
        System.out.println("Log:Building Constructed...");


    }

    public boolean stopAll() {
        return true;
    }
}

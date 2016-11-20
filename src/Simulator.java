import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by roycn on 2016/11/17.
 */
public class Simulator {


    protected  ElevatorController elevatorController;
    protected  ArrayList<Elevator> elevators;


    public Simulator(){

         this.elevatorController= new ElevatorController();
         this.elevators= new ArrayList<Elevator>();

        try {
            ElevatorConfiguration elevatorConfiguration = ElevatorConfiguration.getInstance();

            for (ElevatorFactorySetting model : elevatorConfiguration.getAllSettings()) {
                elevators.add(new Elevator(model.id, Integer.parseInt(model.floorLevel), elevatorConfiguration, elevatorController));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        elevatorController.regElevators(elevators);

        try {
            elevatorController.startSystem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public ElevatorController getElevatorController(){
       return this.elevatorController;
    }
}

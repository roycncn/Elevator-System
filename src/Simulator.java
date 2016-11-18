import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by roycn on 2016/11/17.
 */
public class Simulator {

    class ElevatorJson {
            String id;
        public String toString() {
            return "id: " + id;
        }
    }

    public static void main(String[] args) {

        ElevatorController elevatorController = new ElevatorController();

        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader("./JSON/Elevators.json"));
            ElevatorJson[] Eles = gson.fromJson(jsonReader, ElevatorJson[].class);
            for (ElevatorJson e : Eles) {
                System.out.println(e.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


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

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by roycn on 2016/11/17.
 */
public class Simulator {

    class ElevatorModel {
        String id;
        String floorLevel;
    }

    public static void main(String[] args) {

        ElevatorController elevatorController = new ElevatorController();
        ArrayList<Elevator> elevators = new ArrayList<Elevator>();

        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader("./JSON/Elevators.json"));
            ElevatorModel[] elevatorModels = gson.fromJson(jsonReader, ElevatorModel[].class);
            for (ElevatorModel model : elevatorModels) {
                elevators.add(new Elevator(model.id, Integer.parseInt(model.floorLevel), new Configuration(), elevatorController));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        elevatorController.regElevators(elevators);

        try {
            elevatorController.startSystem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

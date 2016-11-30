import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by roycn on 2016/11/17.
 */
public class Simulator {

    private static Simulator singleton = new Simulator( );

    public static Simulator getInstance() {
        return singleton;
    }

    protected ElevatorController elevatorController;
    protected ArrayList<Elevator> elevators;
    protected Building building;
    protected ArrayList<Kiosk> kioskArrayList;

    // TODO: 20/11/16 Need to put the constants to property file or config file in key-value pair
    private static final int NUM_FLOOR = 20;
    private static final int NUM_KIOSK_PER_FLOOR = 1;
    private static final int FLOOR_HEIGHT = 500;


    private  Simulator() {

        this.elevatorController= new ElevatorController();
        this.elevators= new ArrayList<Elevator>();
        this.building = new Building(null,null,this.elevatorController);
        this.kioskArrayList = new ArrayList<Kiosk>();

        ArrayList<Floor> floors = null;
        try {
            floors = new ArrayList<>();
            for (int floorNum = 1; floorNum <= NUM_FLOOR; floorNum++) {
                ArrayList<Kiosk> kiosks = new ArrayList<>();
                Floor floor = new Floor(floorNum, FLOOR_HEIGHT, kiosks);

                for (int kioskNum = 1; kioskNum <= NUM_KIOSK_PER_FLOOR; kioskNum++) {
                    String id = "KIOSK_" + floorNum + "_" + kioskNum;
                    Kiosk kiosk = new Kiosk(id, elevatorController, AccessConfiguration.getInstance(), floor);
                    kiosks.add( kiosk );
                    kioskArrayList.add(kiosk);
                }
                floors.add(floor);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        building.floors = floors;

        try {
            ElevatorConfiguration elevatorConfiguration = ElevatorConfiguration.getInstance();

            for (ElevatorFactorySetting model : elevatorConfiguration.getAllSettings()) {
                elevators.add(new Elevator(model.id, Integer.parseInt(model.floorLevel), elevatorConfiguration, elevatorController));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        elevatorController.regElevators(elevators);
        elevatorController.regKiosks(kioskArrayList);
        elevatorController.regBuilding(building);

        try {
            elevatorController.startSystem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected ElevatorController getElevatorController(){
       return this.elevatorController;
    }
    protected ArrayList<Kiosk> getkioskArrayList(){
        return this.kioskArrayList;
    }
}

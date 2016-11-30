package ElevatorBackend;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
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
    private static final String ACCESS_CONFIG = "JSON/AccessRules.json";
    private static final String ELEVAT_CONFIG = "JSON/Elevators.json";


    private  Simulator() {

        this.elevatorController= new ElevatorController();
        this.elevators= new ArrayList<Elevator>();
        this.building = new Building(null,null,this.elevatorController);
        this.kioskArrayList = new ArrayList<Kiosk>();

        File accessFile = new File(this.ACCESS_CONFIG);
        AccessConfiguration.setConfigFile(accessFile);
        AccessConfiguration accessConfiguration = AccessConfiguration.getInstance();


        ArrayList<Floor> floors = null;
        try {
            floors = new ArrayList<>();
            for (int floorNum = 0; floorNum <= NUM_FLOOR; floorNum++) {
                ArrayList<Kiosk> kiosks = new ArrayList<>();
                Floor floor = new Floor(floorNum, FLOOR_HEIGHT, kiosks);

            for (int kioskNum = 0; kioskNum <= NUM_KIOSK_PER_FLOOR; kioskNum++) {
                String id = "KIOSK_" + floorNum + "_" + kioskNum;
                Kiosk kiosk = new Kiosk(id, elevatorController, accessConfiguration, floor);
                kiosks.add( kiosk );
                kioskArrayList.add(kiosk);
            }
            floors.add(floor);
        }

        building.floors = floors;

        try {
            File elevatorFile = new File(this.ELEVAT_CONFIG);
            ElevatorConfiguration.setConfigFile(elevatorFile);
            ElevatorConfiguration elevatorConfiguration = ElevatorConfiguration.getInstance();

            for (ElevatorFactorySetting model : elevatorConfiguration.getAllSettings()) {
                elevators.add(new Elevator(model.getId(), Integer.parseInt(model.getFloorLevel()), elevatorConfiguration, elevatorController));
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

    public ElevatorController getElevatorController(){
       return this.elevatorController;
    }
    public ArrayList<Kiosk> getkioskArrayList(){
        return this.kioskArrayList;
    }
    public ArrayList<Elevator> getElevators() {
        return elevators;
    }
    public Building getBuilding() {
        return building;
    }

}

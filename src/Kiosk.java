/**
 * Created by test on 2016/11/16.
 */
public class Kiosk extends AppThread {
    private AccessConfiguration config;
    private ElevatorController elevatorController;
    private final Floor location;

    public Kiosk(String id, ElevatorController elevatorController, AccessConfiguration accessConfiguration, Floor location) {
        super(id, elevatorController);
        this.config = accessConfiguration;
        this.location = location;
        this.elevatorController = elevatorController;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(10); // need revise
                Message msg = this.getMessageBox().receive();
                String personId;
                String[] idFloorPair;
                switch (msg.getType()) {
                    case "PAUT":
                        personId = msg.getDetail();
                        System.out.println(this.config.isAuthenticated(personId) ? "[ OK ] " : "[FAIL] "
                                + "Authentication of Person with ID = " + personId);
                        break;
                    case "FAUT":
                        idFloorPair = msg.getDetail().split("\\|");
                        System.out.println(this.accessible(idFloorPair[0], idFloorPair[1]) ? "[ OK ] " : "[FAIL] "
                                + "Authentication of Person with ID = " + idFloorPair[0]
                                + "to" + idFloorPair[1] + " floor");
                        break;
                    case "GOTO":
                        idFloorPair = msg.getDetail().split("\\|");
                        String result = this.goToFloor(idFloorPair[0], idFloorPair[1]);
                        System.out.printf("[ OK ] %s : Elevator# %s is assigned to Person# %s\n", this.getID(),result, idFloorPair[0]);
                        break;
                    case "PING":
                        System.out.printf("[PING] Kiosk# %s at Floor#%2d ONLINE\n", this.getID(), this.location.getFloorLevel());
                        break;
                    default:
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean accessible(String personId, String level) {
        return config.isAuthenticated(personId, Integer.parseInt(level));
    }

    /**
     * @param personId ID of Elevator which serves the request
     * @param level
     */
    private String goToFloor(String personId, String level) {
//        if (this.accessible(personId, level)) {
        Floor tmpF = new Floor(Integer.parseInt(level));
        return this.elevatorController.findElevator(this.location, tmpF);
//        }
//        return "321";
    }
}

enum MsgStatus {
    AUTH("AUTH");

    private final String value;

    MsgStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
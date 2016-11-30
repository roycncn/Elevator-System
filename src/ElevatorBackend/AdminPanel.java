package ElevatorBackend;

/**
 * Created by test on 2016/11/16.
 */
public class AdminPanel extends Panel {

    private AccessConfiguration config;

    public AdminPanel(String panelName, AccessConfiguration config) {
        super(panelName);
        this.config = config;
    }

    public boolean setFloorAccess(Floor floor, Person person) {
        return person.addAccessFloor(floor);
    }

    public void showAccessRecord() {

    }
}

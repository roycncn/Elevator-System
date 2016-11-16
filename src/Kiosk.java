/**
 * Created by test on 2016/11/16.
 */
public class Kiosk {
    private Configuration config;
    private Floor currentLocation;

    public Kiosk(Configuration config, Floor currentLocation) {
        this.config = config;
        this.currentLocation = currentLocation;
    }

    public boolean accessable(Person person, Floor toFloor) {
        return true;
    }

    public int goToFloor(Floor toFloor) {
        return 1;
    }
}

/**
 * Created by test on 2016/11/16.
 */
import java.util.ArrayList;

public class Floor {
    private int floorLevel;
    private int height;
    private ArrayList<Kiosk> kiosks;

    public Floor(int floorLevel) {
        this.floorLevel = floorLevel;
    }

    public Floor(int floorLevel, int height, ArrayList<Kiosk> kiosks) {
        this.floorLevel = floorLevel;
        this.height = height;
        this.kiosks = kiosks;
    }

    public int getFloorLevel() {
        return floorLevel;
    }
}

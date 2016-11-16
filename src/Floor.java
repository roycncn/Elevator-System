/**
 * Created by test on 2016/11/16.
 */
import java.util.ArrayList;

public class Floor {
    public int floorLevel;
    public int height;
    public ArrayList<Kiosk> kiosks;

    public Floor(int floorLevel, int height, ArrayList<Kiosk> kiosks) {
        this.floorLevel = floorLevel;
        this.height = height;
        this.kiosks = kiosks;
    }

}

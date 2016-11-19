/**
 * Created by test on 2016/11/16.
 */

import java.util.ArrayList;
import java.util.Comparator;

public class Floor implements Comparable<Floor> {
    private int floorLevel;
    private int height;
    private ArrayList<Kiosk> kiosks;

    public Floor(int floorLevel) {
        this.floorLevel = floorLevel;
        this.height = 5 * 100;
    }

    public Floor(int floorLevel, int height, ArrayList<Kiosk> kiosks) {
        this.floorLevel = floorLevel;
        this.height = height;
        this.kiosks = kiosks;
    }

    public int getDirectionBetweenFloor(Floor floor) {

        if (this.getFloorLevel() > floor.getFloorLevel()) {
            return -1;
        }

        return 1;
    }

    public int getFloorLevel() {
        return floorLevel;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int compareTo(Floor floor) {
        return this.getFloorLevel() - floor.getFloorLevel();
    }
}

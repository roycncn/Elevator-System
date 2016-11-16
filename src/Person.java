import java.util.ArrayList;

/**
 * Created by test on 2016/11/16.
 */
public class Person {

    public static enum Role {
        ADMIN,USER
    }

    public int personID;
    public ArrayList<Floor> accessableFloors;

    public Person(int personID, ArrayList<Floor> accessableFloors) {
        this.personID = personID;
        this.accessableFloors = accessableFloors;
    }

    public boolean addAccessFloor(Floor floor) {
        for (Floor f : this.accessableFloors) {
            if ( f.floorLevel == floor.floorLevel ) {
                return false;
            }
        }
        this.accessableFloors.add(floor);
        return true;
    }
}

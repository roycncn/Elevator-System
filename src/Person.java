import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import com.google.gson.*;

/**
 * Created by test on 2016/11/16.
 */
public class Person {

    public int personID;
    private Role[] roles;
    public ArrayList<Floor> accessibleFloors;

    public Person(int personID, ArrayList<Floor> accessibleFloors, Role[] roles) {
        this.personID = personID;
        this.accessibleFloors = accessibleFloors;
        this.roles = roles;
    }

    public boolean addAccessFloor(Floor floor) {
        for (Floor f : this.accessibleFloors) {
            if ( f.getFloorLevel() == floor.getFloorLevel() ) {
                return false;
            }
        }
        this.accessibleFloors.add(floor);
        return true;
    }

    public HashSet<String> getAccessibleFloorNumbers() {
        HashSet<String> accessibleFloorNumbers = new HashSet<>();
        accessibleFloors.stream().forEachOrdered((floor -> {
            accessibleFloorNumbers.add( String.valueOf(floor.getFloorLevel()) );
        }));
        return accessibleFloorNumbers;
    }

    public Role[] getRoles() {
        return roles;
    }

    public int getPersonID() {
        return personID;
    }
}

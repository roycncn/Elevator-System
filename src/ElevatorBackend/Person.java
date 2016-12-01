package ElevatorBackend;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by test on 2016/11/16.
 */
public class Person {

    public int personID;
    private Role[] roles;
    public ArrayList<Floor> accessibleFloors;
    public String access;

    public Person(int personID, ArrayList<Floor> accessibleFloors, Role[] roles) {
        this.personID = personID;
        this.accessibleFloors = accessibleFloors;
        this.roles = roles;
    }

    public Person(int personID, String floor, Role[] roles) {
        this.personID = personID;
        this.roles = roles;
        this.access = floor;
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

    public HashSet<Integer> getAccessibleFloorNumbers() {
        HashSet<Integer> accessibleFloorNumbers = new HashSet<>();

        for (Floor floor :
                accessibleFloors)
            accessibleFloorNumbers.add(floor.getFloorLevel());

        return accessibleFloorNumbers;
    }

    public Role[] getRoles() {
        return roles;
    }

    public int getPersonID() {
        return personID;
    }
}

package ElevatorBackend;

import com.google.gson.Gson;

import java.util.HashSet;

public class AccessRule {
    private HashSet<Integer> accessibleFloorNumber = new HashSet<>();
    private final long personId;
    private Role[] roles;



    public AccessRule(Person person) {
        this.personId = person.personID;
        this.accessibleFloorNumber = person.getAccessibleFloorNumbers();
        this.roles = person.getRoles();
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this, this.getClass());
    }

    public long getPersonId() {
        return personId;
    }

    public Role[] getRoles() {
        return roles;
    }

    public HashSet<Integer> getAccessibleFloorNumber() {
        return (HashSet<Integer>) accessibleFloorNumber.clone();
    }

    @Override
    public String toString() {
        return String.format("==========\n\tPerson ID: %5s\n\tRoles: %s\n\tAccessible Floors: %s",
                this.personId, this.roles.toString(), this.accessibleFloorNumber.toString());
    }
}

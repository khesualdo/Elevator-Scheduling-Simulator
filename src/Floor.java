import java.util.ArrayList;

public class Floor {

    private ArrayList<Passenger> passengers; // Holds passengers (floor calls) at this floor

    public Floor(){
        this.passengers = new ArrayList<>();
    }

    /**
     * Create a Passenger object (simulating a passenger arriving at a floor and pressing a button).
     * Generate Passenger ID.
     *
     * Randomly select the direction in which the passenger wants to go from the floorCall.
     * Randomly select the floor number for floorCall. - Type 1
     *
     * Set the direction of the exitFloor to be the same as the direction of the floorCall.
     * Randomly select the floor number for exitFloor, but make sure the floor number is
     * in the direction of the exitFloor. - Type 0
     *
     * Remember to assign passage number to the floorCall and exitFloor.
     * Assign Passenger ID to each call.
     */
    public void generatePassenger(){

    }
}

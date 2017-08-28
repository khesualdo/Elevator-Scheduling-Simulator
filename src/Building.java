public class Building {

    private int N; // Number of floors
    private int L; // Number of elevators
    private int U; // Building population
    private int algorithm; // Desired algorithm will be passed as a CL arg

    private Elevator elevatorGroup[]; // An array of L elevators
    private Floor floors[]; // An array of N floors
    private GroupElevatorController controller; // reference to the controller used for controller setup

    /**
     * Randomly select a floor from the floors array and
     * call the generatePassenger method on the floor.
     *
     * Generates a passenger on one of the floors.
     */
    public void generatePassenger(){

    }

    public static void main(String[] args) {

    }
}

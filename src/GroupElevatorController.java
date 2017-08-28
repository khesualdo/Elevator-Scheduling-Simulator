public class GroupElevatorController {

    private int algorithm; // Get from Building Class
    private Elevator elevatorGroup[]; // Get from Building Class
    private Floor floors[]; // Get from Building Class

    /**
     * Scan the floors array and collect Passengers from each floor (remove them after recording).
     * Based of the algorithm, assign a Passenger(s) to one of the elevators
     * from the elevatorGroup array.
     * Break apart the passenger object, put Passenger.boardingFloor to the pickupCalls array
     * put Passenger.exitFloor to the carCalls array
     */
    public void scheduler(){

        // Will have a switch statement
        // Select an algorithm and passes it the elevatorGroup array
        // Algorithm returns the index of the chosen elevator
    }
}
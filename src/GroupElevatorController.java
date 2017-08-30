public class GroupElevatorController {

    private Elevator elevatorGroup[];
    private Floor floors[];

    private int algorithm;

    public GroupElevatorController(Elevator[] elevatorGroup, Floor[] floors){
        this.elevatorGroup = elevatorGroup;
        this.floors = floors;
    }

    public void setAlgorithm(int algorithm){ this.algorithm = algorithm; }

    /**
     * Scan the floors array and collect Passengers from each floor (remove them after recording).
     * Based on the algorithm, assign a Passenger(s) to one of the elevators
     * from the elevatorGroup array.
     * Break apart the passenger object, put Passenger.boardingFloor to the floorCalls array
     * put Passenger.exitFloor to the carCalls array
     */
    public void scheduler(){

        // Will have a switch statement
        // Select an algorithm and passes it the elevatorGroup array
        // Algorithm returns the index of the chosen elevator
    }
}
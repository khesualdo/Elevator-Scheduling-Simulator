public class GroupElevatorController {

    private Elevator elevatorGroup[];
    private Floor floors[];

    private int algorithm;

    private RR rr;

    public GroupElevatorController(Elevator[] elevatorGroup, Floor[] floors){
        this.elevatorGroup = elevatorGroup;
        this.floors = floors;

        this.rr = new RR();
    }

    public void setAlgorithm(int algorithm){ this.algorithm = algorithm; }

    /**
     * Scan the floors array and collect Passengers from each floor (remove them after recording).
     * Based on the algorithm, assign a Passenger(s) to one of the elevators
     * from the elevatorGroup array.
     *
     * Break apart the passenger object, put Passenger.boardingFloor to the floorCalls array
     * put Passenger.exitFloor to the carCalls array
     */
    public void scheduler(){

        // Will have a switch statement
        // Select an algorithm and passes it the elevatorGroup array
        // Algorithm returns the index of the chosen elevator

        int chosenElevator = 0;

        switch (this.algorithm) {
            case 1:
                chosenElevator = rr.choseElevator(elevatorGroup);
                break;
            default:
                chosenElevator = rr.choseElevator(elevatorGroup);
                break;
        }

        this.elevatorGroup[chosenElevator].receiveJob(); // Assign a passenger to an elevator

        for(Floor floor : floors){
            if(floor.getPassengers().size() > 0){
                System.out.printf("\n\nFloor %d has passengers\n\n", floor.getID());
            }
        }

    }
}
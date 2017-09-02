public class GroupElevatorController {

    private Elevator elevatorGroup[];
    private Floor floors[];

    private int algorithm;
    private int start;

    private RR rr;

    public GroupElevatorController(Elevator[] elevatorGroup, Floor[] floors){
        this.elevatorGroup = elevatorGroup;
        this.floors = floors;

        this.start = 0;

        this.rr = new RR();
    }

    public void setAlgorithm(int algorithm){ this.algorithm = algorithm; }

    /**
     * Scan the floors array, looks for a floor with at least one passenger.
     * Based on the algorithm, assigns a Passenger to one of the elevators
     * from the elevatorGroup array.
     */
    public void scheduler() throws InterruptedException {

        int chosenElevator = 0;
        boolean foundPassenger = false;

        // Each algorithm returns the index of the chosen elevator
        switch (this.algorithm) {
            case 1:
                chosenElevator = rr.choseElevator(elevatorGroup);
                break;
            default:
                chosenElevator = rr.choseElevator(elevatorGroup);
                break;
        }

        Passenger tempPassenger = null; // Create a dummy Passenger object

        // Look for a floor with at least one passenger
        Floor floor = null;
        for(int i=this.start; i<floors.length; ++i){

            System.out.printf("Currently checking floor %d.\n", i);
            floor = floors[i];

            if(floor.getPassengers().size() > 0){

                System.out.printf("Floor %d has %d passenger(s)\n", floor.getID(), floor.getPassengers().size());

                tempPassenger = floor.getPassengers().poll(); // Remove the first passenger

                System.out.printf("Registered passenger from floor %d\n", floor.getID());

                if(i == (floors.length - 1)){
                    this.start = 0;
                }else {
                    this.start = i + 1;
                }

                foundPassenger = true;
                break;
            }

            if(i == (floors.length - 1)){
                this.start = 0;
            }else {
                this.start = i + 1;
            }
        }

        if(foundPassenger)
            System.out.printf("Elevator %d has received a job from %d floor.\n", chosenElevator, tempPassenger.getFloorCall().getFloor());

        // this.elevatorGroup[chosenElevator].receiveJob(temp); // Assign a passenger to an elevator

    }
}
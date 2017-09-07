public class GroupElevatorController implements Runnable {

    private Elevator elevatorGroup[];
    private Floor floors[];

    private int algorithm;
    private int start;

    private RR rr;

    public GroupElevatorController(Elevator[] elevatorGroup, Floor[] floors){

        super(); // Call the constructor of the super class - Thread

        this.elevatorGroup = elevatorGroup;
        this.floors = floors;

        this.start = 0;

        this.rr = new RR();
    }

    public void setAlgorithm(int algorithm){ this.algorithm = algorithm; }

    @Override
    public void run(){
        try {
            while(true) {
                scheduler();
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println("GroupElevatorController thread failed!");
        }
    }

    /**
     * Scan the floors array, looks for a floor with at least one passenger.
     * Based on the algorithm, assigns a Passenger to one of the elevators
     * from the elevatorGroup array.
     */
    private void scheduler() throws InterruptedException {

        int chosenElevator = 0;
        boolean foundPassenger = false;

        Passenger tempPassenger = null; // Create a dummy Passenger object

        // Look for a floor with at least one passenger
        for(int i=this.start; i<floors.length; ++i){

            System.out.printf("Currently checking floor %d.\n", i);
            Floor floor = floors[i];

            if(floor.getPassengers().size() > 0){

                System.out.printf("Floor %d has %d passenger(s)\n", floor.getID(), floor.getPassengers().size());

                tempPassenger = floor.getPassengers().take(); // Remove the passenger from queue

                System.out.printf("Registered a passenger from floor %d\n", floor.getID());

                // Remembers from which index to start scanning next time
                if(i == (floors.length - 1)){
                    this.start = 0;
                }else {
                    this.start = i + 1;
                }

                foundPassenger = true;
                break;
            }

            // Remembers from which index to start scanning next time
            // even though no passenger was found
            if(i == (floors.length - 1)){
                this.start = 0;
            }else {
                this.start = i + 1;
            }
        }

        if(foundPassenger) {

            // Each algorithm returns the index of the chosen elevator
            // The chosen elevator will be given a task (receive job)
            switch (this.algorithm) {
                case 1:
                    chosenElevator = rr.choseElevator(elevatorGroup);
                    break;
                default:
                    chosenElevator = rr.choseElevator(elevatorGroup);
                    break;
            }

            System.out.printf("Elevator %d has received a job from %d floor.\n", chosenElevator, tempPassenger.getFloorCall().getFloor());
            this.elevatorGroup[chosenElevator].receiveJob(tempPassenger); // Assign a passenger to an elevator
        }

    }
}
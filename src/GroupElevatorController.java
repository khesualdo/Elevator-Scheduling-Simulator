public class GroupElevatorController implements Runnable {

    private Elevator elevatorGroup[];
    private Floor floors[];

    private int algorithm;
    private int start;

    private int N; // Number of floors
    private int L; // Number of elevators
    private int U; // Building population

    private RoundRobin roundRobin;
    private Zoning zoning;
    private ThreePassage threePassage;

    public GroupElevatorController(Elevator[] elevatorGroup, Floor[] floors){

        super();

        this.elevatorGroup = elevatorGroup;
        this.floors = floors;

        this.start = 0;

        this.roundRobin = new RoundRobin();
        this.zoning = new Zoning();
        this.threePassage = new ThreePassage();
    }

    public void setAlgorithm(int algorithm){ this.algorithm = algorithm; }

    public void setL(int l) { L = l; }

    public void setN(int n) { N = n; }

    public void setU(int u) { U = u; }

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
        for (int i=this.start; i<floors.length; ++i) {

            Floor floor = floors[i];

            if(floor.getPassengers().size() > 0){

                tempPassenger = floor.getPassengers().take(); // Remove the passenger from queue

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
                    chosenElevator = roundRobin.choseElevator(elevatorGroup, this.L);
                    break;
                case 2:
                    chosenElevator = roundRobin.choseElevator(elevatorGroup, this.L);
                    break;
                case 3:
                    chosenElevator = zoning.choseElevator(this.L, this.N, tempPassenger.getFloorCall().getFloor());
                    break;
                case 4:
                    chosenElevator = threePassage.choseElevator(elevatorGroup);
                    break;
                default:
                    chosenElevator = roundRobin.choseElevator(elevatorGroup, this.L);
                    break;
            }

            this.elevatorGroup[chosenElevator].receiveJob(tempPassenger); // Assign a passenger to an elevator
        }

    }
}
import java.util.Random;
import java.util.Scanner;

public class Building {

    private int N; // Number of floors
    private int L; // Number of elevators
    private int U; // Building population
    private int algorithm; // Desired algorithm will be passed as a CL arg

    private Elevator elevatorGroup[]; // An array of L elevators
    private Floor floors[]; // An array of N floors
    private GroupElevatorController controller; // reference to the controller used for controller setup

    Random rand;

    public Building(int N, int L, int U){
        this.N = N;
        this.L = L;
        this.U = U;

        this.elevatorGroup = new Elevator[this.L];
        this.floors = new Floor[this.N];

        this.controller = new GroupElevatorController(this.elevatorGroup, this.floors);

        this.rand = new Random();
    }

    public int getN() {
        return N;
    }

    /**
     * Sets the default algorithm throughout the program
     * @param algorithm - desired algorithm
     */
    private void setAlgorithm(int algorithm){
        this.algorithm = algorithm;
        this.controller.setAlgorithm(this.algorithm);
    }

    /**
     * Creates L Elevator objects in the elevatorGroup array.
     */
    private void createElevators(){
        for(int i=0; i<this.L; ++i){
            this.elevatorGroup[i] = new Elevator(this.algorithm, 1,
                    1, 1, this.U / 4, 3);
        }
    }

    /**
     * Creates N Floor objects in the floors array.
     */
    private void createFloors(){
        for(int i=0; i<this.N; ++i){
            this.floors[i] = new Floor(i);
        }
    }

    /**
     * Randomly selects a floor from the floors array and
     * calls the generatePassenger method on the Floor(randFloor) object.
     */
    private void generatePassenger(int N) throws InterruptedException {

        int randFloor = rand.nextInt(N);

        floors[randFloor].generatePassenger(N);
        System.out.printf("floorCall from floor %d.\n", randFloor);
    }

    /**
     * Calls the scheduler method from GroupElevatorController.
     */
    public void activateScheduler() throws InterruptedException {
        this.controller.scheduler();
    }

    public static void main(String[] args) throws InterruptedException {

        Scanner reader = new Scanner(System.in);

        int N = Integer.parseInt(args[0]);
        int L = Integer.parseInt(args[1]);
        int U = Integer.parseInt(args[2]);

        if((args.length == 3) && (N > 1) && (L > 0) && (U > 0)){

            Building building = new Building(N, L, U);

            // Chose algorithm
            building.setAlgorithm(1);
            // System.out.println("Choose algorithm:");
            // System.out.println("1 - Round Robin");
            // building.setAlgorithm(reader.nextInt());


            // Create N number of Floor objects
            building.createFloors();

            // Create L number of Elevator objects
            building.createElevators();

            while(true){

                // Generate a passenger on one of the floors
                building.generatePassenger(building.getN());
                Thread.sleep(2000);

                // Activate the GroupElevatorController to scan the floors array
                building.activateScheduler();
                Thread.sleep(2000);
            }

        }else{
            System.out.println("Usage: java Building <number of floors, at least 2>\n\t\t<number of elevators, at least 1>\n\t\t<building population, at least 1>");
        }
    }
}

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

    private Random rand;
    private Scanner reader;

    public Building(int N, int L, int U){
        this.N = N;
        this.L = L;
        this.U = U;

        this.elevatorGroup = new Elevator[this.L];
        this.floors = new Floor[this.N];

        this.controller = new GroupElevatorController(this.elevatorGroup, this.floors);
        this.controller.setN(this.N);
        this.controller.setL(this.L);
        this.controller.setU(this.U);

        this.rand = new Random();
        this.reader = new Scanner(System.in);
    }

    public int getN() { return N; }

    public void setAlgorithm(int algorithm){ this.algorithm = algorithm; }

    /**
     * Creates L Elevator objects in the elevatorGroup array.
     */
    private void createElevators(){
        for(int i=0; i<this.L; ++i){
            this.elevatorGroup[i] = new Elevator(i, this.algorithm, 1,
                    1, 1, this.U / 4, 3);
            this.elevatorGroup[i].setCurrentFloor(N/2); // This will depend on the algorithm
            this.elevatorGroup[i].setDirection(1);
            this.elevatorGroup[i].setN(N);
            this.elevatorGroup[i].setL(L);
        }

        // Create elevator threads
        for(int i=0; i<this.L; ++i){
            this.elevatorGroup[i].elevatorControllerThread();
            this.elevatorGroup[i].performJobThread();

            // Start this thread only if user chose Up-peak
            if (this.algorithm == 2) {
                this.elevatorGroup[i].upPeakThread();
            }

            // Start this thread only if user chose Zoning
            if (this.algorithm == 3){
                this.elevatorGroup[i].zoningThread();
            }
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

    public GroupElevatorController getController() {
        return controller;
    }

    /**
     * Randomly selects a floor from the floors array and
     * calls the generatePassenger method on the Floor(randFloor) object.
     */
    private void generatePassenger(int N) throws InterruptedException {
        int randFloor = this.rand.nextInt(N);
        floors[randFloor].generatePassenger(N);
    }

    public static void main(String[] args) throws InterruptedException {

        if((args.length == 3) && (Integer.parseInt(args[0]) > 1) &&
                (Integer.parseInt(args[1]) > 0) && (Integer.parseInt(args[2]) > 0)) {

            if (Integer.parseInt(args[0]) > Integer.parseInt(args[1])) {

                Building building = new Building(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

                // Chose algorithm
                building.setAlgorithm(4);
                building.getController().setAlgorithm(4);
                // System.out.println("Choose algorithm:");
                // System.out.println("1 - Round-Robin");
                // System.out.println("2 - Up-Peak");
                // System.out.println("3 - Zoning");
                // System.out.println("4 - Three Passage");
                // building.setAlgorithm(reader.nextInt());

                // Create N number of Floor objects
                building.createFloors();

                // Create L number of Elevator objects
                building.createElevators();

                // Start the GroupElevatorController thread
                new Thread(building.getController()).start(); // Activates the GroupElevatorController to scan the floors array

                // Keep generating passengers on different floors of the building
                while (true) {

                    // Generate a passenger on one of the floors
                    building.generatePassenger(building.getN());
                    Thread.sleep(40000);
                }
            } else {
                System.out.println("The number of floors cannot be less than number of elevators.");
            }

        }else{
            System.out.println("Usage: java Building <number of floors, at least 2>\n\t\t<number of elevators, at least 1>\n\t\t<building population, at least 1>");
        }
    }
}

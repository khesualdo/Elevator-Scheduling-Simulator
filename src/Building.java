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

    public Building(int N, int L, int U){
        this.N = N;
        this.L = L;
        this.U = U;

        this.elevatorGroup = new Elevator[this.L];
        this.floors = new Floor[this.N];

        this.controller = new GroupElevatorController(this.elevatorGroup, this.floors);
    }

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
            this.floors[i] = new Floor();
        }
    }

    /**
     * Randomly select a floor from the floors array and
     * call the generatePassenger method on the Floor(i) object.
     *
     * Generates a passenger on one of the floors.
     */
    public void generateFloorCall(){
        Random rand = new Random();
    }

    /**
     * Call this method from GroupElevatorController.
     * So we don't have multiple while TRUE loops - only one
     */
    public void activateScheduler(){

    }

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        
        if(false) {
            Building building = new Building(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

            // Chose algorithm
            building.setAlgorithm(1);
            // System.out.println("Choose algorithm:");
            // System.out.println("1 - Round Robin");
            // building.setAlgorithm(reader.nextInt());


            // Create N number of Floor objects
            building.createFloors();

            // Create L number of Elevator objects
            building.createElevators();

        }else{
            System.out.println("Usage: java Building <number of floors> <number of elevators> <building population>");
        }
    }
}

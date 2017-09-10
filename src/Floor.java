import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class Floor {

    LinkedBlockingQueue<Passenger> passengers; // Holds passengers at this floor
    private int ID;
    private boolean DEBUG = false;

    public Floor(int ID){
        this.passengers = new LinkedBlockingQueue<>();
        this.ID = ID;
    }

    public LinkedBlockingQueue<Passenger> getPassengers() {
        return passengers;
    }

    public int getID() {
        return ID;
    }

    /**
     * Create a Passenger object (simulating a passenger arriving at a floor and pressing a button).
     * Generate Passenger ID.
     *
     * Randomly select the direction in which the passenger wants to go from the floorCall.
     * Randomly select the floor number for floorCall. - Type 1
     *
     * Set the direction of the exitFloor to be the same as the direction of the floorCall.
     * Randomly select the floor number for exitFloor, but make sure the floor number is
     * in the direction of the exitFloor. - Type 0
     *
     * Remember to assign passage number to the floorCall and exitFloor.
     * Assign Passenger ID to each call.
     */
    public void generatePassenger(int numberOfFloors) throws InterruptedException {

        Random rand = new Random();

        String ID = UUID.randomUUID().toString(); // Create passenger ID

        int randFloor = this.ID;

        // If randFloor is the top floor, then the direction is always down
        // Else if randFloor is the main floor, then the direction is always up
        // Else the direction is chosen randomly
        int direction = 0;
        if(randFloor == 0) {
            direction = 1; // Direction is up
        }else if(randFloor == (numberOfFloors - 1)){
            direction = 0; // Direction is down
        }else {
            direction = rand.nextInt(2); // Randomly select direction
        }

        if (DEBUG) {
            System.out.println(direction);
            System.out.println(randFloor);
        }

        Call floorCall = new Call(1, randFloor, direction, ID);

        // Randomly generate an exitCall, based on randFloor
        int exitFloor = 0;
        if(direction == 1) {

            // Generate random number, until it's greater than randFloor
            exitFloor = rand.nextInt(numberOfFloors);
            while (exitFloor <= randFloor){
                exitFloor = rand.nextInt(numberOfFloors);
            }
        }else{

            // Generate random number, until it's smaller than randFloor
            exitFloor = rand.nextInt(numberOfFloors);
            while (exitFloor >= randFloor){
                exitFloor = rand.nextInt(numberOfFloors);
            }
        }

        if (DEBUG) {
            System.out.println(exitFloor);
        }

        Call carCall = new Call(0, exitFloor, direction, ID);

        this.passengers.put(new Passenger(floorCall, carCall, ID)); // Create a Passenger object and add it the to the passengers array
    }
}

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Floor {

    private ArrayList<Passenger> passengers; // Holds passengers (floor calls) at this floor
    private int ID;

    public Floor(int ID){
        this.passengers = new ArrayList<>();
        this.ID = ID;
    }

    public ArrayList<Passenger> getPassengers() {
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
    public void generatePassenger(int numberOfFloors){

        Random rand = new Random();

        String ID = UUID.randomUUID().toString(); // Create passenger ID

        int randFloor = this.ID;

        // If randFloor is the top floor, then the direction is always down
        // Else if randFloor is the main floor, hen the direction is always up
        // Else the direction is chosen randomly
        int direction = 0;
        if(randFloor == (numberOfFloors - 1)){
            direction = 0; // Direction is down
        }else if(randFloor == 0){
            direction = 1; // Direction is up
        }else {
            direction = rand.nextInt(1); // Randomly select direction
        }

        System.out.println(direction);
        System.out.println(randFloor);
        Call callFloor = new Call(1, randFloor, direction, ID); // Create a callFloor object

        // Randomly generate an exitCall, based on randFloor
        int exitFloor = 0;
        if(direction == 1) {
            exitFloor = rand.nextInt(numberOfFloors);
            while (exitFloor <= randFloor){
                exitFloor = rand.nextInt(numberOfFloors);
            }
        }else{
            exitFloor = rand.nextInt(numberOfFloors);
            while (exitFloor >= randFloor){
                exitFloor = rand.nextInt(numberOfFloors);
            }
        }

        System.out.println(exitFloor);

        Call exitCall = new Call(0, exitFloor, direction, ID); // Create a exitFloor object

        this.passengers.add(new Passenger(callFloor, exitCall, ID)); // Create a Passenger object and add it the to the passengers array
    }
}

import java.util.ArrayList;

public class Elevator {

    private ArrayList<Call> floorCalls; // Holds floorCalls
    private ArrayList<Call> carCalls; // Holds carCalls
    private ArrayList<Call> sequence; // Holds sorted floorCalls and carCalls

    private int algorithm; // Set at the time of elevator creation

    private int passengerLoadingTime; // Always 1 second
    private int passengerUnloadingTime; // Always 1 second
    private int speed; // Always 1 meter per second
    private int capacity; // The capacity if always 1/4 of the entire building population
    private int interFloorHeight; // Always 3 meters

    public Elevator(int algorithm, int passengerLoadingTime, int passengerUnloadingTime,
                    int speed, int capacity, int interFloorHeight){
        this.algorithm = algorithm;
        this.passengerLoadingTime = passengerLoadingTime;
        this.passengerUnloadingTime = passengerUnloadingTime;
        this.speed = speed;
        this.capacity = capacity;
        this.interFloorHeight = interFloorHeight;

        this.floorCalls = new ArrayList<>();
        this.carCalls = new ArrayList<>();
        this.sequence = new ArrayList<>();
    }

    public void receiveJob(){

    }

    /**
     * Must sort out Floor calls and Car calls based on the algorithm
     * and put them into the sequence array - will follow the passage rules
     */
    public void elevatorController(){

        // Each passenger has and ID
        // Each Call has the same ID as the passenger (same ID for two calls)
        // We append the floorCall to the floorCalls array and
        // append the carCall to the carCalls array
        // Once the elevator "brings the passenger on board" - completes a call of type floor
        // we search the carCalls for the call with the same ID
        // and update the sequence once again.


        // The elevator answers all calls along the current movement direction until
        // the last floor with calls - the reversal floor - is reached,
        // where direction is reversed and all calls along the new direction are answered until
        // the next reversal floor.

    }
}

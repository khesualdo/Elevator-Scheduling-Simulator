import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

class CallComparator implements Comparator<Call> {

    /**
     * Sorts calls based on passage and floor number
     */
    @Override
    public int compare(Call x, Call y) {

        // -1 The element pointed by x goes before the element pointed by y
        // 0  The element pointed by x is equivalent to the element pointed by y
        // 1 The element pointed by x goes after the element pointed by y

        // If x or y are up-peak calls
        if (x.isSpecialCall() || y.isSpecialCall()) {
            if (x.isSpecialCall() && !y.isSpecialCall()) {
                return -1;
            } else if (!x.isSpecialCall() && y.isSpecialCall()) {
                return 1;
            } else {
                return 0;
            }
        }

        if(x.getPassage() == y.getPassage()){

            if ((x.getPassage() == 1) || (x.getPassage() == 3)){

                if (x.getFloor() < y.getFloor()) {
                    return -1;
                } else if (x.getFloor() > y.getFloor()) {
                    return 1;
                }

                return 0;

            }else if(x.getPassage() == 2){

                if (x.getFloor() > y.getFloor()) {
                    return -1;
                } else if (x.getFloor() < y.getFloor()) {
                    return 1;
                }

                return 0;

            }

        }else if(x.getPassage() > y.getPassage()) {
            return 1;
        }

        return -1;
    }
}

public class Elevator {

    private List<Call> floorCalls; // Holds floorCalls
    private List<Call> carCalls; // Holds carCalls
    private Comparator<Call> comparator;
    private PriorityBlockingQueue<Call> sequence;

    private int algorithm; // Set at the time of elevator creation
    private int ID;
    private int currentFloor;
    private int direction; // 1- Up, 0 - Down
    private boolean idle = true;
    private boolean DEBUG = true;

    private int passengerLoadingTime; // Always 1 second
    private int passengerUnloadingTime; // Always 1 second
    private int velocity; // Always 1 meter per second
    private int capacity; // The capacity if always 1/4 of the entire building population
    private int interFloorHeight; // Always 3 meters
    private int N;
    private int L;

    public Elevator(int ID, int algorithm, int passengerLoadingTime, int passengerUnloadingTime,
                    int velocity, int capacity, int interFloorHeight){
        this.ID = ID;
        this.algorithm = algorithm;
        this.passengerLoadingTime = passengerLoadingTime;
        this.passengerUnloadingTime = passengerUnloadingTime;
        this.velocity = velocity;
        this.capacity = capacity;
        this.interFloorHeight = interFloorHeight;

        this.floorCalls = new CopyOnWriteArrayList<>();
        this.carCalls = new CopyOnWriteArrayList<>();

        this.comparator = new CallComparator();
        this.sequence = new PriorityBlockingQueue<>(100, this.comparator);
    }

    public void setN(int n){ this.N = n; }

    public void setL(int l) { L = l; }

    public void setCurrentFloor(int currentFloor) { this.currentFloor = currentFloor; }

    /**
     * Used during Elevator initialization
     */
    public void setDirection(int direction) { this.direction = direction; }

    public int getCapacity() { return capacity; }

    public int getPassengerLoadingTime() {
        return passengerLoadingTime;
    }

    public int getPassengerUnloadingTime() {
        return passengerUnloadingTime;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getInterFloorHeight() {
        return interFloorHeight;
    }

    public PriorityBlockingQueue<Call> getSequence() { return sequence; }

    public boolean isIdle() { return idle; }

    public int getCurrentFloor() { return currentFloor; }

    public int getID() {
        return ID;
    }

    public int getN() {
        return N;
    }

    public int getL() {
        return L;
    }

    /**
     * Generates a carCall with floor 0 in order to relocate the car to the lobby.
     * The idea is to reduce the waiting time for future passengers arriving at the lobby.
     */
    public void upPeakThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    // Check if the elevator is idle
                    if (isIdle()) {

                        // Wait 7 seconds
                        try {
                            Thread.sleep(7000);
                        } catch (InterruptedException e) {
                            System.out.println("Error in upPeak thread.");
                        }

                        // Check if the elevator is still idle and
                        // is not already on the main floor
                        if (isIdle() && getCurrentFloor() != 0) {

                            // Create the carCall and add it to the sequence
                            Call tempCall = new Call(0, 0, 0, "");
                            tempCall.setPassage(1);
                            tempCall.setSpecialCall(true);

                            getSequence().add(tempCall);
                        }
                    }

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Elevators in idle state are repositioned to the zone’s lowest floor.
     */
    public void zoningThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                int Z = new Double(Math.ceil((double) getN() / getL())).intValue();

                while (true){

                    // Check if the elevator is idle
                    if (isIdle()) {

                        // Wait 7 seconds
                        try {
                            Thread.sleep(7000);
                        } catch (InterruptedException e) {
                            System.out.println("Error in zoning thread.");
                        }

                        // Check if the elevator is still idle and
                        // is not already in it's zone
                        if (isIdle() && getCurrentFloor() != (getID() * Z)) {

                            // Create the carCall and add it to the sequence
                            Call tempCall = new Call(0, (getID() * Z), 0, "");
                            tempCall.setPassage(1);
                            tempCall.setSpecialCall(true);

                            getSequence().add(tempCall);
                        }
                    }

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void performJobThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        performJob();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Error in performJob thread.");
                    }
                }
            }
        }).start();
    }

    public void elevatorControllerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                // System.out.println("Started the elevatorController thread.");

                while (true){
                    try {
                        elevatorController();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Error in elevatorController thread.");
                    }
                }
            }
        }).start();
    }

    /**
     * Checks the sequence queue to find any Calls that need to be removed or added.
     */
    private void checkSequence(Call tempCall) throws InterruptedException {

        // Here we are looking for the carCall of the current floorCall
        if (tempCall.getType() == 1 && tempCall.getFloor() == this.currentFloor) {

            int removeIndex = 0;
            boolean foundCarCall = false;

            // Traverse carFloors array to look for a
            // carCall with the same ID as tempCall
            for (int i = 0; i < this.carCalls.size(); ++i) {

                Call tempCarCall = this.carCalls.get(i);

                if (tempCarCall.getID().equals(tempCall.getID())) {

                    removeIndex = i;

                    // Assign passage to carCall
                    // Same direction and higher than currentFloor - P1
                    // Opposite direction - P2

                    if (this.direction == 1) {
                        if ((tempCarCall.getFloor() > this.currentFloor) && (tempCarCall.getDirection() == this.direction)) {
                            tempCarCall.setPassage(1);
                        } else {
                            tempCarCall.setPassage(2);
                        }
                    }else{
                        if ((tempCarCall.getFloor() < this.currentFloor) && (tempCarCall.getDirection() == this.direction)) {
                            tempCarCall.setPassage(1);
                        } else {
                            tempCarCall.setPassage(2);
                        }
                    }

                    // Add carCall to sequence
                    this.sequence.add(tempCarCall);

                    foundCarCall = true;
                    break;
                }
            }

            // Remove carCall from carCalls array
            if (foundCarCall) {
                this.carCalls.remove(removeIndex);
            }
        }

        // Check the Calls in the sequence, if the sequence is not empty
        // Here we are looking for all carCalls and floorCalls that can be removed from sequence
        if (this.sequence.size() > 0) {


            // Traverse the Calls in the sequence to find out if
            // any Calls need to be remove, because their floor matches the currentFloor of the elevator
            for (Call call : sequence) {

                // Remove all carCalls whose floor is the current floor of the elevator
                // The passengers whose carCall is the same as currentFloor have already arrived
                if (call.getType() == 0 && call.getFloor() == this.currentFloor) {
                    this.sequence.remove(call);
                }

                // Remove all floorCalls whose floor is the current floor of the elevator,
                // and add carCalls with the same ID to the sequence
                // The passengers whose floorCall is the same as currentFloor have boarded the elevator
                // and pressed a button inside the elevator (made a carCall)
                if (call.getType() == 1 && call.getFloor() == this.currentFloor) {

                    int removeIndex = 0;
                    boolean foundCarCall = false;

                    // Traverse carFloors array
                    for (int i = 0; i < this.carCalls.size(); ++i) {

                        Call tempCarCall = this.carCalls.get(i);

                        if (tempCarCall.getID().equals(call.getID())) {

                            removeIndex = i;

                            // Assign passage to carCall
                            // Same direction and higher than currentFloor - P1
                            // Opposite direction - P2

                            if (this.direction == 1) {
                                if ((tempCarCall.getFloor() > this.currentFloor) && (tempCarCall.getDirection() == this.direction)) {
                                    tempCarCall.setPassage(1);
                                } else {
                                    tempCarCall.setPassage(2);
                                }
                            }else{
                                if ((tempCarCall.getFloor() < this.currentFloor) && (tempCarCall.getDirection() == this.direction)) {
                                    tempCarCall.setPassage(1);
                                } else {
                                    tempCarCall.setPassage(2);
                                }
                            }

                            // Add carCall to sequence
                            this.sequence.add(tempCarCall);
                            foundCarCall = true;
                            break;
                        }
                    }

                    // Remove carCall from carCalls array
                    if (foundCarCall) {
                        this.carCalls.remove(removeIndex);
                    }

                    // Remove the floorCall from the sequence
                    this.sequence.remove(call);
                }

            }
        }
    }

    /**
     * Assigns passage to calls in the sequence
     */
    private void redefinePassage(){

        for(Call tempCall : sequence) {

            if (!tempCall.isSpecialCall()) {

                // Same direction and higher than currentFloor - P1
                // Opposite direction - P2
                // Same direction and lower than currentFloor - P3
                if (this.direction == 1) {
                    if ((tempCall.getFloor() > this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                        tempCall.setPassage(1);
                    } else if ((tempCall.getFloor() < this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                        tempCall.setPassage(3);
                    } else {
                        tempCall.setPassage(2);
                    }
                } else {

                    // Same direction and lower than currentFloor - P1
                    // Opposite direction - P2
                    // Same direction and higher than currentFloor - P3
                    if ((tempCall.getFloor() < this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                        tempCall.setPassage(1);
                    } else if ((tempCall.getFloor() > this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                        tempCall.setPassage(3);
                    } else {
                        tempCall.setPassage(2);
                    }
                }
            }
        }
    }

    private void performJob() throws InterruptedException {

        if (this.sequence.size() > 0) {

            // Get Call from sequence
            Call tempCall = this.sequence.take();

            if (DEBUG) {
                System.out.println("\n\n**************************");
                System.out.printf("Elevator %d, direction: %d, current floor: %d.\n", this.ID, this.direction, this.currentFloor);
                System.out.printf("Got a Job | direction: %d, passage: %d, floor: %d, type: %d, ID: %s, upPeakCall: %b.\n", tempCall.getDirection(), tempCall.getPassage(), tempCall.getFloor(), tempCall.getType(), tempCall.getID(), tempCall.isSpecialCall());
                System.out.println("**************************\n\n");
            }

            if (tempCall.getFloor() == this.currentFloor){
                checkSequence(tempCall);
            }else {

                // Update the direction of the elevator based
                // on the position of the current floor
                // Since the direction has changed, we must
                // reassign passage to all calls in the sequence
                if (tempCall.getFloor() < this.currentFloor) {
                    this.direction = 0;
                    redefinePassage();
                } else if (tempCall.getFloor() > this.currentFloor) {
                    this.direction = 1;
                    redefinePassage();
                }

                // Simulate elevator movement through the floors of the building
                while ((this.currentFloor != tempCall.getFloor()) &&
                        (this.currentFloor >= 0) &&
                        (this.currentFloor <= (this.N - 1))) {

                    this.idle = false;

                    // Direction is up
                    if (this.direction == 1 && this.currentFloor != (this.N - 1)) {

                        this.currentFloor += 1;
                        Thread.sleep(this.velocity * this.interFloorHeight * 1000);

                        if (DEBUG) {
                            System.out.printf("\n\n+++++ Elevator %d, direction: %d, current floor: %d, target floor: %d. +++++\n", this.ID, this.direction, this.currentFloor, tempCall.getFloor());
                            System.out.printf("+++++ Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s. +++++\n\n", tempCall.getDirection(), tempCall.getPassage(), tempCall.getFloor(), tempCall.getType(), tempCall.getID());
                        }

                        checkSequence(tempCall);

                    } else if (this.direction == 0 && this.currentFloor != 0) {

                        this.currentFloor -= 1;
                        Thread.sleep(this.velocity * this.interFloorHeight * 1000);

                        if (DEBUG) {
                            System.out.printf("\n\n+++++ Elevator %d, direction:%d, current floor: %d, target floor: %d. +++++\n", this.ID, this.direction, this.currentFloor, tempCall.getFloor());
                            System.out.printf("+++++ Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s. +++++\n\n", tempCall.getDirection(), tempCall.getPassage(), tempCall.getFloor(), tempCall.getType(), tempCall.getID());
                        }

                        checkSequence(tempCall);

                    } else {
                        System.out.println("\n\n\n\n! + ! + ! Elevator is out of range - performJob() ! + ! + !\n\n\n\n");
                        System.exit(0);
                    }
                }
            }

            this.idle = true;
        }
    }

    /**
     * Breaks apart the Passenger object.
     * Puts Passenger.floorCall to the floorCalls array.
     * Puts Passenger.carCall to the carCalls array.
     */
    public void receiveJob(Passenger temp) throws InterruptedException {

        Call floorCall = temp.getFloorCall(); // Has floor, needs passage
        Call carCall = temp.getCarCall(); // Has floor, needs passage

        this.floorCalls.add(floorCall);
        this.carCalls.add(carCall);

        if (DEBUG) {
            System.out.println("--------------------------");
            for (Call call : sequence) {
                System.out.printf("Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s.\n", call.getDirection(), call.getPassage(), call.getFloor(), call.getType(), call.getID());
            }
            System.out.println("--------------------------");
            for (Call call : floorCalls) {
                System.out.printf("Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s.\n", call.getDirection(), call.getPassage(), call.getFloor(), call.getType(), call.getID());
            }
            System.out.println("--------------------------");
            for (Call call : carCalls) {
                System.out.printf("Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s.\n", call.getDirection(), call.getPassage(), call.getFloor(), call.getType(), call.getID());
            }
            System.out.println("--------------------------");
        }
    }

    /**
     * Responsible for sorting calls assigned by the GroupElevatorController
     * into the elevator’s internal sequence list.
     */
    private void elevatorController() throws InterruptedException {

        if (this.floorCalls.size() > 0) {

            Call tempCall = this.floorCalls.get(0);
            this.floorCalls.remove(0);

            // Assign passage to a newly arrived floorCall

            // Same direction and higher than currentFloor - P1
            // Opposite direction - P2
            // Same direction and lower than currentFloor - P3
            if (this.direction == 1) {
                if ((tempCall.getFloor() > this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(1);
                } else if ((tempCall.getFloor() < this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(3);
                } else {
                    tempCall.setPassage(2);
                }
            }else {

                // Same direction and lower than currentFloor - P1
                // Opposite direction - P2
                // Same direction and higher than currentFloor - P3
                if ((tempCall.getFloor() < this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(1);
                } else if ((tempCall.getFloor() > this.currentFloor) && (tempCall.getDirection() == this.direction)) {
                    tempCall.setPassage(3);
                } else {
                    tempCall.setPassage(2);
                }
            }

            this.sequence.add(tempCall);
        }
    }
}

import java.util.ArrayList;
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
    private volatile boolean updatingSequence;
    private boolean DEBUG = false;

    private int passengerLoadingTime; // Always 1 second
    private int passengerUnloadingTime; // Always 1 second
    private int velocity; // Always 1 meter per second
    private int capacity; // The capacity if always 1/4 of the entire building population
    private int interFloorHeight; // Always 3 meters
    private int numberOfFloors;

    public Elevator(int ID, int algorithm, int passengerLoadingTime, int passengerUnloadingTime,
                    int velocity, int capacity, int interFloorHeight){
        this.ID = ID;
        this.algorithm = algorithm;
        this.passengerLoadingTime = passengerLoadingTime;
        this.passengerUnloadingTime = passengerUnloadingTime;
        this.velocity = velocity;
        this.capacity = capacity;
        this.interFloorHeight = interFloorHeight;

        this.updatingSequence = false;

        this.floorCalls = new CopyOnWriteArrayList<>();
        this.carCalls = new CopyOnWriteArrayList<>();

        this.comparator = new CallComparator();
        this.sequence = new PriorityBlockingQueue<>(100, this.comparator);
    }

    public void setNumberOfFloors(int numberOfFloors){
        this.numberOfFloors = numberOfFloors;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void performJobThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                // System.out.println("Started the performJob thread.");

                while (true){
                    try {
                        performJob();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Error with performJob thread");
                    }
                }
            }
        }).start();
    }

    private void checkSequence(Call tempCall) throws InterruptedException {

        // Remove all floorCalls whose floor is the current floor of the elevator,
        // and add carCalls with the same ID to the sequence
        // The passengers whose floorCall is the same as currentFloor have boarded the elevator
        // and pressed a button inside the elevator (made a carCall)
        if (tempCall.getType() == 1 && tempCall.getFloor() == this.currentFloor) {

            int removeIndex = 0;
            boolean foundCarCall = false;

            // Traverse carFloors array
            for (int i = 0; i < this.carCalls.size(); ++i) {

                Call tempCarCall = this.carCalls.get(i);

                if (tempCarCall.getID().equals(tempCall.getID())) {

                    removeIndex = i;

                    // Assign passage to carCall
                    // Same direction and higher than currentFloor - P1
                    // Opposite direction - P2
                    if ((tempCarCall.getFloor() > this.currentFloor) && (tempCarCall.getDirection() == this.direction)) {
                        tempCarCall.setPassage(1);
                    } else {
                        tempCarCall.setPassage(2);
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
            } else {
                System.out.println("\n\n======== Did NOT find a matching carCall ! ========\n\n");
            }
        }

        if (this.sequence.size() > 0) {

            this.updatingSequence = true;
            Thread.sleep(1000);

            // Traverse the Calls in the sequence to find out if
            // any Calls need to be remove, because their floor matches the currentFloor of the elevator
            for (Call call : sequence) {

//                System.out.printf("\n1. Checking Call with ID: %s, and type: %d in sequence.\n\n", call.getID(), call.getType());

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

//                        System.out.printf("\n2. Checking carCall with ID: %s in carCalls,\n\n", this.carCalls.get(i).getID());

                        Call tempCarCall = this.carCalls.get(i);

                        if (tempCarCall.getID().equals(call.getID())) {

                            removeIndex = i;

                            // Assign passage to carCall
                            // Same direction and higher than currentFloor - P1
                            // Opposite direction - P2
                            if ((tempCarCall.getFloor() > this.currentFloor) && (tempCarCall.getDirection() == this.direction)) {
                                tempCarCall.setPassage(1);
                            } else {
                                tempCarCall.setPassage(2);
                            }

                            // Add carCall to sequence
                            this.sequence.add(tempCarCall);
                            foundCarCall = true;
                            break;
                        }
                    }

                    // Remove carCall from carCalls array
                    if (foundCarCall) {
//                        System.out.printf("\n3. Removed carCall with ID: %s from carCalls,\n\n", this.carCalls.get(removeIndex).getID());
                        this.carCalls.remove(removeIndex);
                    } else {
                        System.out.println("\n\n======== Did NOT find a matching carCall ! ========\n\n");
                    }

//                    System.out.printf("\n4. Removed floorCall with ID: %s from sequence,\n\n", call.getID());
                    // Remove the floorCall from the sequence
                    this.sequence.remove(call);
                }

            }

            this.updatingSequence = false;
        }
    }

    private void performJob() throws InterruptedException {

        // Get Call from sequence
        Call tempCall = this.sequence.take();

        // System.out.printf("Job | direction: %d, passage: %d, floor: %d.\n", tempCall.getDirection(), tempCall.getPassage(), tempCall.getFloor());

        if (tempCall.getFloor() < this.currentFloor){
            this.direction = 0;
        }else if( tempCall.getFloor() > this.currentFloor ){
            this.direction = 1;
        }

//        // If Call is of type P2 or P3, then we have reached the reversal floor and
//        // are going in the opposite direction
//        if ((tempCall.getPassage() == 2) || (tempCall.getPassage() == 3)) {
//
//            // Set the flag to True, so no more Calls are added
//            // to the sequence, while we are updating current Calls
//            this.updatingSequence = true;
//            Thread.sleep(1000);
//
//            System.out.printf("Current Direction %d.\n", this.direction);
//
//            // Change the direction of the elevator
//            if (this.direction == 0) {
//                this.direction = 1;
//            } else {
//                this.direction = 0;
//            }
//
//            System.out.printf("Changed Direction %d.\n", this.direction);
//
//            // Create a temporally queue
//            PriorityBlockingQueue<Call> tempSequence = new PriorityBlockingQueue<>(100, this.comparator);
//
//            // Decrement the passage for current Calls in the sequence
//            while (!this.sequence.isEmpty()) {
//
//                Call temp = this.sequence.take();
//                temp.setPassage(temp.getPassage() - 1);
//
//                tempSequence.add(temp);
//            }
//
//            // Copy the updated calls to the sequence
//            while (!tempSequence.isEmpty()) {
//                this.sequence.add(tempSequence.take());
//            }
//
//            this.updatingSequence = false;
//        }

        // Simulate elevator movement through the floors of the building
        while ((this.currentFloor != tempCall.getFloor()) &&
                (this.currentFloor >= 0) &&
                (this.currentFloor <= (this.numberOfFloors - 1))) {

            // Direction is up
            if (this.direction == 1 && this.currentFloor != (this.numberOfFloors - 1)) {

                this.currentFloor += 1;
                Thread.sleep(this.velocity * this.interFloorHeight * 1000);

                System.out.printf("\n\n+++++ Elevator %d, direction: %d, current floor: %d, target floor: %d. +++++\n", this.ID, this.direction,this.currentFloor, tempCall.getFloor());
                System.out.printf("+++++ Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s. +++++\n\n", tempCall.getDirection(), tempCall.getPassage(), tempCall.getFloor(), tempCall.getType(), tempCall.getID());

                checkSequence(tempCall);

            } else if (this.direction == 0 && this.currentFloor != 0) {

                this.currentFloor -= 1;
                Thread.sleep(this.velocity * this.interFloorHeight * 1000);

                System.out.printf("\n\n+++++ Elevator %d, direction:%d, current floor: %d, target floor: %d. +++++\n", this.ID, this.direction, this.currentFloor, tempCall.getFloor());
                System.out.printf("+++++ Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s. +++++\n\n", tempCall.getDirection(), tempCall.getPassage(), tempCall.getFloor(), tempCall.getType(), tempCall.getID());

                checkSequence(tempCall);

            } else {
                System.out.println("\n\n+++++ Elevator is out of range - performJob() +++++\n\n");
            }
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

        System.out.println("-------------------------");
        for(Call call : sequence){
            System.out.printf("Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s.\n", call.getDirection(), call.getPassage(), call.getFloor(), call.getType(), call.getID());
        }
        System.out.println("-------------------------");
        for(Call call : floorCalls){
            System.out.printf("Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s.\n", call.getDirection(), call.getPassage(), call.getFloor(), call.getType(), call.getID());
        }
        System.out.println("-------------------------");
        for(Call call : carCalls){
            System.out.printf("Call direction: %d, Call passage: %d, Call floor: %d, Call type: %d, Call ID: %s.\n", call.getDirection(), call.getPassage(), call.getFloor(), call.getType(), call.getID());
        }
        System.out.println("-------------------------");

        if (DEBUG) {
            System.out.printf("Elevator %d has received floorCall %d and carCall %d.\n", this.ID, temp.getFloorCall().getFloor(), temp.getCarCall().getFloor());
        }
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
                        System.out.println("Error with elevatorController thread.");
                    }
                }
            }
        }).start();
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

             while (updatingSequence) {}

            this.sequence.add(tempCall);
        }
    }
}

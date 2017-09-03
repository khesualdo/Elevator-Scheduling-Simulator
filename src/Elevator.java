import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;

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

public class Elevator implements Runnable {

    private LinkedBlockingQueue<Call> floorCalls; // Holds floorCalls
    private LinkedBlockingQueue<Call> carCalls; // Holds carCalls
    // private LinkedBlockingQueue<Call> sequence; // Holds sorted floorCalls and carCalls
    private Comparator<Call> comparator;
    private PriorityQueue<Call> sequence;

    private int algorithm; // Set at the time of elevator creation
    private int ID;
    private int currentFloor;
    private int direction; // 1- Up, 0 - Down

    private int passengerLoadingTime; // Always 1 second
    private int passengerUnloadingTime; // Always 1 second
    private int speed; // Always 1 meter per second
    private int capacity; // The capacity if always 1/4 of the entire building population
    private int interFloorHeight; // Always 3 meters

    public Elevator(int ID, int algorithm, int passengerLoadingTime, int passengerUnloadingTime,
                    int speed, int capacity, int interFloorHeight){
        this.ID = ID;
        this.algorithm = algorithm;
        this.passengerLoadingTime = passengerLoadingTime;
        this.passengerUnloadingTime = passengerUnloadingTime;
        this.speed = speed;
        this.capacity = capacity;
        this.interFloorHeight = interFloorHeight;

        this.floorCalls = new LinkedBlockingQueue<>();
        this.carCalls = new LinkedBlockingQueue<>();

        this.comparator = new CallComparator();
        this.sequence = new PriorityQueue<>(100, this.comparator);
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void run() {
        System.out.printf("Created thread for elevator %d.\n", this.ID);

          while (true){
              try {
                  elevatorController();
              } catch (InterruptedException e) {
                  System.out.printf("elevatorController has failed for elevator %d.\n", this.ID);
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

        this.floorCalls.put(floorCall);
        this.carCalls.put(carCall);

        System.out.printf("Elevator %d has received floorCall %d and carCall %d.\n", this.ID, temp.getFloorCall().getFloor(), temp.getCarCall().getFloor());
    }

    public void performTask(){

        new Thread(new Runnable() {
            public void run() {

                // Pop a call from sequence queue and count up or down from the elevators current floor

            }
        }).start();
    }

    /**
     * Responsible for sorting calls assigned by the GroupElevatorController
     * into the elevator’s internal sequence list.
     */
    public void elevatorController() throws InterruptedException {

        Call tempCall = this.floorCalls.take();

        // Same direction and higher than currentFloor - P1
        // Opposite direction - P2
        // Same direction and lower than currentFloor - P3
        if((tempCall.getFloor() >= this.currentFloor ) && (tempCall.getDirection() == this.direction)){
            tempCall.setPassage(1);
        }else if((tempCall.getFloor() < this.currentFloor ) && (tempCall.getDirection() == this.direction)){
            tempCall.setPassage(3);
        }else{
            tempCall.setPassage(2);
        }

        this.sequence.add(tempCall);
        System.out.printf("--- Elevator's direction is %d.\n", this.direction);
        System.out.printf("--- Added a request to the queue, direction: %d, floor: %d.\n", tempCall.getDirection(), tempCall.getFloor());
        System.out.printf("--- First element in sequence queue has direction: %d, floor: %d.\n", this.sequence.peek().getDirection(), this.sequence.peek().getFloor());

        // Once the elevators current floor == floorCall(i), search the carCalls queue for the carCall with the same ID
        // Once the carCall is found, assign it a passage - P1 or P2, and insert it into the sequence queue
    }
}

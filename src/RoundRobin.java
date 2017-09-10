public class RoundRobin {

    private int counter;

    public RoundRobin(){
        this.counter = -1;
    }

    /**
     * Round-Robin Group Elevator Scheduling.
     * First-come-first-served approach.
     *
     * The goal is to achieve an equal load for all cars.
     *
     * Calls are assigned in the order they arrive in a sequential way to single elevators.
     * Call 0 is assigned to car 0, call 1 to car 1, . . . call L to car L, call L + 1 to car 0, and so on.
     */
    public int choseElevator(Elevator[] elevatorGroup, int L) {

        int pick = (++this.counter) % L;

        // Finding the first elevator whose capacity is not reached
        while(elevatorGroup[pick].getCapacity() < (elevatorGroup[pick].getSequence().size() + 1)) {
            pick = (++this.counter) % L;
        }

        return pick;
    }
}

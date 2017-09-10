public class ThreePassage {

    /**
     * Three Passage Group Elevator Scheduling.
     *
     * Estimates the costs that would result from assigning the new call to the elevator.
     *
     * Stop costs are static and include the period of time necessary for opening the door,
     * unloading and loading each one passenger and closing the door.
     *
     * In case every elevator already reached 80% load (number of calls > 80% elevator capacity),
     * calls will not get assigned until at least one elevator falls below this mark.
     *
     * The call is assigned to the elevator with the lowest costs.
     */
    public int choseElevator(Elevator[] elevatorGroup) {

        int pick = 0;
        boolean flag = true;
        int cost = Integer.MAX_VALUE;

        while (flag) {

            // Find the elevator with lowest cost
            for (Elevator elevator : elevatorGroup) {

                int calls = elevator.getSequence().size(); // Current number of calls in sequence
                int elevatorCost = calls * ((elevator.getVelocity() * elevator.getInterFloorHeight()) +
                        elevator.getPassengerLoadingTime() + elevator.getPassengerUnloadingTime()); // Total cost of all calls

                if (elevatorCost < cost) {
                    cost = elevatorCost;
                    pick = elevator.getID();
                }
            }

            // Check if thresholds is not reached
            if ((double) (elevatorGroup[pick].getSequence().size() / elevatorGroup[pick].getCapacity()) < 0.8) {
                flag = false;
            }
        }

        return pick;
    }

}

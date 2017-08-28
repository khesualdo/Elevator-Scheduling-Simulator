public class Elevator {

    private Call floorCalls[];
    private Call carCalls[];
    private int sequence[]; // Holds sorted floor calls and car calls
    private int algorithm;

    /**
     * Must sort out Floor calls and Car calls based on the algorithm
     * and put them into the sequence array
     */
    public void elevatorController(){

        // Passenger(1), floorCall = 3(1), exitCall = 5(1)
        // Passenger(2), floorCall = 1(2), exitCall = 4(2)
        // Passenger(3), floorCall = 2(3), exitCall = 3(3)
        // Passenger(4), floorCall = 5(4), exitCall = 3(4)

        // pickupCalls = [3(1), 1(2), 2(3), 5(4)]
        // carCalls = [5(1), 4(2), 3(3), 3(4)]

        // sequence = []



        
        // Each passenger has and ID
        // Each Call has the same ID as the passenger (same ID for two calls)
        // We append the floorCall to the floorCalls array and
        // append the carCall to the carCalls array
        // Once the elevator "brings the passenger on board" we search the carCalls for the ID
        // and update the sequence once again




        // The elevator answers all calls along the current movement direction until
        // the last floor with calls - the reversal floor - is reached,
        // where direction is reversed and all calls along the new direction are answered until
        // the next reversal floor.

    }
}

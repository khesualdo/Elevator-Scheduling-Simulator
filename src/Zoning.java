public class Zoning {

    /**
     * Zoning Group Elevator Scheduling.
     *
     * Splits a building into several adjacent zones.
     *
     * Every elevator only serving floor calls that occur in the zone assigned to the respective car.
     * The primary objective is to reduce the number of car stops and therefore the total journey time.
     *
     * A building served by L elevators can be split into up to L zones, where these m zones are either disjoint or not.
     * Cars in idle state are repositioned to the zone’s center level, therefore minimizing waiting time for passengers on adjacent floors.
     *
     * Static Zoning, zones are assigned permanently to a group of elevators.
     */
    public int choseElevator(int L, int N, int floor){

        // int Z = new Double(Math.ceil((double) N / L)).intValue(); // Z is the number of zones
        int Z = Double.valueOf(Math.ceil((double) N / L)).intValue(); // Z is the number of zones


        // return (new Double(Math.floor((double) floor / Z)).intValue());
        return Double.valueOf(Math.floor((double) floor / Z)).intValue();

    }
}

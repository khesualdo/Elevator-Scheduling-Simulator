public class Passenger {

    private Call floorCall;
    private Call carCall;
    private String ID;

    public Passenger(Call floorCall, Call carCall, String ID) {
        this.floorCall = floorCall;
        this.carCall = carCall;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public Call getFloorCall() {
        return floorCall;
    }

    public Call getCarCall() {
        return carCall;
    }
}

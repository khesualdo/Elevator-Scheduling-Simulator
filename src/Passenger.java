public class Passenger {

    private Call callFloor;
    private Call exitFloor;
    private String ID;

    public Passenger(Call callFloor, Call exitFloor, String ID) {
        this.callFloor = callFloor;
        this.exitFloor = exitFloor;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
}

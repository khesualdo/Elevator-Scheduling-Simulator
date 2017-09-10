public class Call {

    private int type; // 1 - floor call, 0 - car call
    private int passage; // 1- P1, 2 - P2, 3 - P3
    private int floor; // floor call - from where, car call - to where
    private int direction; // 1 - Up, 0 - Down
    private String ID;
    private boolean specialCall;

    public Call(int type, int floor, int direction, String ID) {
        this.type = type;
        this.floor = floor;
        this.direction = direction;
        this.ID = ID;
        this.specialCall = false;
    }

    public void setPassage(int passage) {
        this.passage = passage;
    }

    public int getType() {
        return type;
    }

    public int getFloor() {
        return floor;
    }

    public int getPassage() {
        return passage;
    }

    public int getDirection() {
        return direction;
    }

    public String getID() {
        return ID;
    }

    public void setSpecialCall(boolean specialCall) {
        this.specialCall = specialCall;
    }

    public boolean isSpecialCall() {
        return specialCall;
    }
}

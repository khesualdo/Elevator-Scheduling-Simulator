import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

class Tor{
    private int number;
    private int ID;

    public Tor(int number, int ID) {
        this.number = number;
        this.ID = ID;
    }

    public int getNumber() {
        return number;
    }

    public int getID() {
        return ID;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

//class CallComparator implements Comparator<Call> {
//
//    /**
//     * Sorts calls based on passage and floor number
//     */
//    @Override
//    public int compare(Call x, Call y) {
//
//        // -1 The element pointed by x goes before the element pointed by y
//        // 0  The element pointed by x is equivalent to the element pointed by y
//        // 1 The element pointed by x goes after the element pointed by y
//
//        if(x.getPassage() == y.getPassage()){
//
//            if ((x.getPassage() == 1) || (x.getPassage() == 3)){
//
//                if (x.getFloor() < y.getFloor()) {
//                    return -1;
//                } else if (x.getFloor() > y.getFloor()) {
//                    return 1;
//                }
//
//                return 0;
//
//            }else if(x.getPassage() == 2){
//
//                if (x.getFloor() > y.getFloor()) {
//                    return -1;
//                } else if (x.getFloor() < y.getFloor()) {
//                    return 1;
//                }
//
//                return 0;
//
//            }
//
//        }else if(x.getPassage() > y.getPassage()) {
//            return 1;
//        }
//
//        return -1;
//    }
//}

public class QueueTest {

    private PriorityBlockingQueue<Call> sequence;
    private Random rand;
    private int counter;
    private Comparator<Call> comparator;

    public QueueTest(){
//        this.comparator = new CallComparator();
        this.sequence = new PriorityBlockingQueue<>(100, this.comparator);
        this.rand = new Random();
        counter = 0;
    }

//    public void displayItems(){
//
//        for(Tor i : sequence){
//            System.out.printf("Item %d with value %d.\n", i.getID(), i.getNumber());
//        }
//
//        System.out.println("-------\n");
//
//    }
//
//    public void modifyItems(){
//
//        Tor[] tempArr = new Tor[sequence.size()]; // Create an array of the same size as the queue
//        sequence.toArray(tempArr); // Populate the array
//        sequence.clear(); // Remove all elements from the queue
//
//        // Decrement every element in the array
//        // and append it to the queue
//        for(Tor i : tempArr){
//            sequence.put(new Tor(i.getNumber() - 1, i.getID()));
//        }
//
//    }

//    public void addItems(){
//        this.sequence.add(new Tor(this.rand.nextInt(10), this.counter++));
//        displayItems();
//    }
//
//    public void addItemsThread(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    while (true) {
//                        addItems();
//                        Thread.sleep(5000);
//                    }
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    public static void main(String[] args) throws InterruptedException {

        Random rand = new Random();
//        Comparator<Call> comparator = new CallComparator();
//        PriorityBlockingQueue<Call> sequence = new PriorityBlockingQueue<>(100, comparator);

//        Call temp = new Call(1, 6, 0, "");
//        temp.setPassage(2);
//        sequence.add(temp);
//
//        temp = new Call(2, 2, 0, "");
//        temp.setPassage(2);
//        sequence.add(temp);
//
//        temp = new Call(1, 0, 1, "");
//        temp.setPassage(3);
//        sequence.add(temp);
//
//        temp = new Call(1, 1, 1, "");
//        temp.setPassage(3);
//        sequence.add(temp);
//
//        temp = new Call(1, 7, 1, "");
//        temp.setPassage(1);
//        sequence.add(temp);
//
//        temp = new Call(1, 5, 1, "");
//        temp.setPassage(1);
//        sequence.add(temp);
//
//        while(!sequence.isEmpty()){
//            Call i = sequence.take();
//            System.out.printf("Passage: %d, Floor: %d.\n", i.getPassage(), i.getFloor());
//        }


//        for(Tor i : sequence){
//            System.out.printf("Item %d with value %d.\n", i.getID(), i.getNumber());
//        }
//
//        System.out.println("---");
//
//        Tor[] t = new Tor[sequence.size()];
//        sequence.toArray(t);
//
//        for(Tor i : t){
//            System.out.printf("Item %d with value %d.\n", i.getID(), i.getNumber());
//        }
//
//        System.out.println("---");

//        new Thread(new Runnable() {
//
//            int counter = 20;
//
//            @Override
//            public void run() {
//                while (true){
//                    sequence.add(new Tor(counter++, counter++));
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

//        while(!sequence.isEmpty()){
//            Tor temp = sequence.take();
//            temp.setNumber(temp.getNumber() - 1);
//            tempSequence.add(temp);
//        }
//
//        while(!tempSequence.isEmpty()){
//            Tor temp = tempSequence.take();
//            System.out.printf("Item %d with value %d.\n", temp.getID(), temp.getNumber());
//        }
//
//        System.out.println(sequence.isEmpty());
//
//        System.out.println("-----");
//
//        while(!sequence.isEmpty()){
//            Tor temp = sequence.take();
//            System.out.printf("Item %d with value %d.\n", temp.getID(), temp.getNumber());
//        }
//
//        QueueTest qt = new QueueTest();



//        qt.addItemsThread();

//        while (true){
//
//            System.out.println("\n\n---------------\n\n");
//            qt.displayItems();
////            qt.modifyItems();
////            qt.displayItems();
//            System.out.println("\n\n---------------\n\n");
//
//            Thread.sleep(5000);
//        }


//        for(Integer i : sequence){
//            System.out.println(i);
//
//            if (i == 5){
//                sequence.remove(5);
//            }
//
//        }


//        Integer[] tempArr = new Integer[sequence.size()]; // Create an array of the same size as the queue
//        sequence.toArray(tempArr); // Populate the array
//        sequence.clear(); // Remove all elements from the queue
//
//        // Decrement every element in the array
//        // and append it to the queue
//        for(Integer i : tempArr){
//            sequence.put(i - 1);
//        }

//        for(Integer i : sequence){
//            System.out.println(i);
//        }

    }
}

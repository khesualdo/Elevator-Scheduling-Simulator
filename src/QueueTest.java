import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

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

    public static void main(String[] args) throws InterruptedException {

//        PriorityBlockingQueue<Integer> sequence = new PriorityBlockingQueue<>(2);
//        if (sequence.size() > 1)
//            sequence.take();
//        System.out.println("Here");
//        System.out.println(sequence.size());

//        System.out.println(Math.ceil((double)3/4));
//        System.out.println(2/3);

        for(int i = 0; i < 25; i++){
            System.out.println(Math.floor((double)i / 7));
        }

//        Random rand = new Random();
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

    }
}

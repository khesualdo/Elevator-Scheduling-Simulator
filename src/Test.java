// Test.java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Test
{
    public static int j;

    public static void setJ(int j) {
        Test.j = j;
    }

    public static void main(String[] args)
    {
//
//        Test t1 = new Test();
//        t1.setJ(20);
//        Test t2 = new Test();
//        t2.setJ(30);
//
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(10);
        queue.add(1);
        queue.add(2);
        queue.add(3);

        for(Integer integer : queue){

            if (integer == 2){
                queue.remove(integer);
                queue.add(0);
            }

        }

        for(Integer integer : queue){

            System.out.println(integer);

        }
//
//        Comparator<Integer> comparator = new StringLengthComparator();
//        PriorityQueue<Integer> queue = new PriorityQueue<>(2, comparator);
//
//        queue.add(2);
//        queue.add(1);
//        queue.add(3);
//
//        while (queue.size() != 0)
//        {
//            System.out.println(queue.remove());
//        }

//        while
//        try {
//            ArrayList<Integer> n = new ArrayList<>();
//            System.out.println(n.get(0));
//        }catch (IndexOutOfBoundsException e){
//            System.out.println();
//        }
//
//        System.out.println("asd");

    }
}

class StringLengthComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer x, Integer y) {

        // <0 The element pointed by p1 goes before the element pointed by p2
        // 0  The element pointed by p1 is equivalent to the element pointed by p2
        // >0 The element pointed by p1 goes after the element pointed by p2

        System.out.println(Test.j);

        if (x < y) {
            return -1;
        } else if (x > y) {
            return 1;
        }

        return 0;
    }

    //    @Override
//    public int compare(String x, String y)
//    {
//        // Assume neither string is null. Real code should
//        // probably be more robust
//        // You could also just return x.length() - y.length(),
//        // which would be more efficient.
//        if (x.length() < y.length())
//        {
//            return -1;
//        }
//        if (x.length() > y.length())
//        {
//            return 1;
//        }
//        return 0;
//    }
}
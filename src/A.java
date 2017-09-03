class B {
    private int b;

    public B(int b) {
        this.b = b;
    }

    public synchronized void countDown(int turn){

        while(b < 3){
            System.out.printf("Counting b: %d. Turn: %d.\n", b, turn);
            b += 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Final b: %d. Turn: %d.\n", b, turn);
        b = 0;
    }
}

public class A implements Runnable{

    private B b;

    public A() {
        this.b = new B(0);
    }

    @Override
    public void run() {

        int i = 0;

        while(true){
            b.countDown(i++);
        }
    }

//    public void IDK(){
//        new Thread(new Runnable() {
//            public void run() {
//                while(true){
//                    b.countDown();
//                }
//            }
//        }).start();
//    }

    public static void main(String[] args) {

        A a = new A();
        new Thread(a).start();

    }
}

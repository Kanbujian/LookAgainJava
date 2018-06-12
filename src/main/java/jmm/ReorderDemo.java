package jmm;

public class ReorderDemo {
    private static int a, b = 0;
    private static int x, y = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        while(true){
            a = 0;  b = 0;  x = 0; y = 0;
            Thread t1 = new Thread(()->{
                a = 1;
                x = b;
            });

            Thread t2 = new Thread(()->{
                b = 1;
                y = a;
            });

            t2.start();
            t1.start();
            t1.join();
            t2.join();

            System.out.println(String.format("%d times: x is %s, y is %s", ++i, x, y));

            if(x == 0 && y == 0){
                break;
            }
            // (0, 1)
            // (1, 0)
            // (1, 1)
            // (0, 0)
        }

    }

}

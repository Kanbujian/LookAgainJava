package thread;

public class ThreadStatusDemo {
    public static void main(String[] args) {
        new ThreadStatusDemo().exec();
    }

    public void exec(){
        Thread t1 = new Thread(new Worker());
        System.out.println("1. " + t1.getState().name());
        t1.start();
        System.out.println("2. " + t1.getState().name());

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3. " + t1.getState().name());
    }

    class Worker implements Runnable{

        @Override
        public void run() {
            try {
                System.out.println("working");
                Thread.sleep(5000);
                System.out.println("done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

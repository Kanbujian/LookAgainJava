package thread;

public class ThreadNotificationDemo {
    public static void main(String[] args) {
      ThreadNotificationDemo demo = new ThreadNotificationDemo();
      demo.exec();
    }

    public void exec(){
        Food food = new Food();
        Thread t1 = new Thread(new Mom(food));
        t1.setName("mom");
        Thread t2 = new Thread(new Child(food));
        t2.setName("child");
        t1.start();
        t2.start();
    }

    class Mom implements Runnable{
        private Food food;

        public Mom(Food food) {
            this.food = food;
        }

        @Override
        public void run() {
            synchronized (food){
                try {
                    while (!food.prepared){
                        food.wait();
                    }
                    System.out.println(String.format("3. %s: I'm cooking ", Thread.currentThread().getName()));
                    Thread.sleep(3000);
                    System.out.println(String.format("4. %s: cooked ", Thread.currentThread().getName()));
                    food.setCooked(true);
                    food.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class Child implements Runnable{
        private Food food;

        public Child(Food food) {
            this.food = food;
        }

        @Override
        public void run() {

            synchronized (food){
                System.out.println(String.format("1. %s: I'm preparing food ", Thread.currentThread().getName()));
                food.prepare();
                food.setPrepared(true);
                System.out.println(String.format("2. %s: Prepared food ", Thread.currentThread().getName()));
                food.notifyAll();
            }

            synchronized (food){
                while(!food.isCooked()){
                    try {
                        food.wait();
                        System.out.println(String.format("5. %s: eating ", Thread.currentThread().getName()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }




        }
    }

    class Food {
        private boolean prepared = false;
        private boolean cooked = false;

        public boolean isCooked() {
            return cooked;
        }

        public void setCooked(boolean cooked) {
            this.cooked = cooked;
        }

        public boolean isPrepared() {
            return prepared;
        }

        public void setPrepared(boolean prepared) {
            this.prepared = prepared;
        }

        public void prepare(){
            try {
                Thread.sleep(3000);
                prepared = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

package test.multithread;

public class NumberPrinter
        implements Runnable {

    public void run() {

        for (int i = 1; i <= 20; i++) {
            if (Thread.currentThread().getName().equals("EVEN") && i % 2 == 0) {
                System.out.println(i);
            } else if (Thread.currentThread().getName().equals("ODD") && i % 2 != 0) {
                System.out.println(i);
            }

        }
    }

    public static void main(String[] args) {
        NumberPrinter numberPrinter = new NumberPrinter();
        Thread todd = new Thread(numberPrinter);
        todd.setName("ODD");
        Thread teven = new Thread(numberPrinter);
        teven.setName("EVEN");
        todd.start();
        teven.start();
    }

}

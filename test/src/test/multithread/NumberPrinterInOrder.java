package test.multithread;

public class NumberPrinterInOrder
        implements Runnable {

    private String lastPrinted = "EVEN";

    public void run() {
        for (int i = 1; i <= 20; i++) {
            synchronized (this) {
                if (lastPrinted.equals(Thread.currentThread().getName())) {
                    notify();
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (Thread.currentThread().getName().equals("EVEN") && i % 2 == 0) {
                System.out.println(i);
                lastPrinted = "EVEN";
            } else if (Thread.currentThread().getName().equals("ODD") && i % 2 != 0) {
                System.out.println(i);
                lastPrinted = "ODD";
            }

        }
    }

    public static void main(String[] args) {
        NumberPrinterInOrder numberPrinter = new NumberPrinterInOrder();
        Thread todd = new Thread(numberPrinter);
        todd.setName("ODD");
        Thread teven = new Thread(numberPrinter);
        teven.setName("EVEN");
        todd.start();
        teven.start();
    }

}

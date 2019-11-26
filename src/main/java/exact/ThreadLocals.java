package exact;

import java.util.concurrent.TimeUnit;

public class    ThreadLocals {

    private static final ThreadLocal<byte[]> locals = new ThreadLocal<>();

    private static volatile Object sink;

    public static void main(String[] args) throws InterruptedException {
        for(long l = 0; l < Long.MAX_VALUE; l++) {
            System.out.println(l);
            Thread thread = new Thread(ThreadLocals::toThreadLocal);
            thread.start();

            sink = new byte[1024 * 1024];
        }
    }

    private static void toThreadLocal() {
        locals.set(new byte[16 * 1024 * 1024]);

        long endTime = System.nanoTime() + TimeUnit.MICROSECONDS.toNanos(100);

        while(System.nanoTime() < endTime);
    }

}

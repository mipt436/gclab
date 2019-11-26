package exact;

public class MaxMemory {
    // Запустить программу с разными флагами, объяснить полученные результаты

    // -Xmx512m
    // -Xmx512m -Xms512m
    // -Xmx512m -Xms512m -XX:+PrintGCDetails - псмотреть на survivor space, куда уходят 20 mb at cms, but not g1
    // -Xmx512m -XX:+UseG1GC
    // -Xms512m -Xmx512m -XX:SurvivorRatio=100

    public static void main(final String[] args) {
        final long maxMemory = Runtime.getRuntime().maxMemory();

        System.out.printf(
                "Max memory: %d MB", maxMemory / 1024 / 1024
        );
    }
}
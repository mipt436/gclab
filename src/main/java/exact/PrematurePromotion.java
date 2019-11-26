package exact;

import java.util.ArrayList;
import java.util.Collection;

public class PrematurePromotion {
// сравнить с трешхолдом и без, только в old, только не в old. Сравнить результаты и сделать небольшой отчет
//  За 30 сек посмотреть сколько minor, сколько full, объяснить результаты   
    
    // 1.  -verbose:gc -Xmx24m -XX:NewSize=16m
    //  -XX:MaxTenuringThreshold=1 -XX:-UseAdaptiveSizePolicy
    // 2.  -verbose:gc -Xmx64m -XX:NewSize=32m
    //              -XX:MaxTenuringThreshold=1 -XX:-UseAdaptiveSizePolicy
    // 3.  -Dmax.chunks=1000 -verbose:gc -Xmx24m -XX:NewSize=16m
    //              -XX:MaxTenuringThreshold=1 -XX:-UseAdaptiveSizePolicy

    private static final int MAX_CHUNKS = Integer.getInteger("max.chunks", 10_000);

    private static final Collection<byte[]> accumulatedChunks = new ArrayList<>();

    private static void onNewChunk(byte[] bytes) {
        accumulatedChunks.add(bytes);

        if(accumulatedChunks.size() > MAX_CHUNKS) {
            processBatch(accumulatedChunks);
            accumulatedChunks.clear();
        }
    }

    public static void main(String[] args) {
        while(true) {
            onNewChunk(produceChunk());
        }
    }

    private static byte[] produceChunk() {
        byte[] bytes = new byte[1024];

        for(int i = 0; i < bytes.length; i ++) {
            bytes[i] = (byte) (Math.random() * Byte.MAX_VALUE);
        }

        return bytes;
    }

    public static volatile byte sink;

    public static void processBatch(Collection<byte[]> bytes) {
        byte result = 0;

        for(byte[] chunk : bytes) {
            for(byte b : chunk) {
                result ^= b;
            }
        }

        sink = result;
    }
}

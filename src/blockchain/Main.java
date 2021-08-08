package blockchain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(8);

        final Blockchain blockchain = Blockchain.getInstance("blockchain.txt");

        for (int i = 0; i < 5; i++) {
            Thread.sleep(100);
            executor.submit(blockchain::createBlock);
        }
        executor.shutdown();
        boolean isTerminated = executor.awaitTermination(5000, TimeUnit.SECONDS);
        if (isTerminated) {
            blockchain.printInfo();
        }
    }
}

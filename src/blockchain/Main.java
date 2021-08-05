package blockchain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        final Blockchain blockchain = Blockchain.getInstance("blockchain.txt");

        for (int i = 0; i < 8; i++) {
            executor.submit(blockchain::createBlock);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(5000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        blockchain.printInfo();
    }
}

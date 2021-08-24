package blockchain;

import blockchain.message.MessageGenerator;
import blockchain.message.MessageHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final Blockchain blockchain = Blockchain.getInstance("blockchain.txt");
        final int blocksToGenerate = 10;
        ExecutorService executor = Executors.newFixedThreadPool(8);

        for (int i = 0; i < blocksToGenerate; i++) {
            Thread.sleep(100);
            executor.submit(blockchain::createBlock);
        }
        executor.shutdown();

        //sending messages while blocks keep generating
        while (!executor.awaitTermination(700, TimeUnit.MILLISECONDS)) {
            MessageHandler.getInstance().addMessage(MessageGenerator.next());
        }

        blockchain.printInfo();
    }
}

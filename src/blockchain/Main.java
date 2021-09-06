package blockchain;

import blockchain.cryptocurrency.Transaction;
import blockchain.cryptocurrency.TransactionGenerator;
import blockchain.cryptocurrency.TransactionHandler;
import blockchain.message.MessageGenerator;
import blockchain.message.MessageHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        final Blockchain blockchain = Blockchain.getInstance("blockchain.txt");
        final int blocksToGenerate = 4;

        ExecutorService executor = Executors.newFixedThreadPool(8);

        for (int i = 0; i < blocksToGenerate; i++) {
            executor.submit(blockchain::createBlock);
            Thread.sleep(2000);
        }
        executor.shutdown();

        //sending transactions while blocks keep generating
        TransactionGenerator.init();
        while (!executor.awaitTermination(700, TimeUnit.MILLISECONDS)) {
            TransactionHandler.getInstance().addTransaction(TransactionGenerator.next());
        }

        blockchain.printInfo();

        TransactionGenerator.printUsers();
    }
}

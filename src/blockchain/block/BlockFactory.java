package blockchain.block;

import blockchain.cryptocurrency.Transaction;
import blockchain.cryptocurrency.TransactionHandler;

import java.util.List;

public class BlockFactory {
    public static Block createBlock(long id, String previousHash, HashInfo hashInfo) {
        //gathering block info
        String minerInfo = "miner" + Thread.currentThread().getId();
        List<Transaction> transactions = TransactionHandler.getInstance().collectTransactions();

        return new Block(hashInfo, minerInfo, id, previousHash, transactions);
    }

}

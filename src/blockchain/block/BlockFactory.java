package blockchain.block;

import blockchain.cryptocurrency.Transaction;

import java.util.List;

public class BlockFactory {
    public static Block createBlock(long id, String previousHash, HashInfo hashInfo) {
        //gathering block info
        String minerInfo = "# " + Thread.currentThread().getId();
        List<Transaction> transactions = null;

        return new Block(hashInfo, minerInfo, id, previousHash, transactions);
    }

}

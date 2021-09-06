package blockchain.block;

import blockchain.cryptocurrency.Transaction;
import blockchain.cryptocurrency.TransactionHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Block implements Serializable {
    private static final long serialVersionUID = 3699397713609871929L;

    private final HashInfo hashInfo;
    private final String minerInfo;
    private final long id;
    private final String previousHash;
    private final List<Transaction> transactions;

    public Block(HashInfo hashInfo, String minerInfo, long id, String previousHash, List<Transaction> transactions) {
        this.hashInfo = hashInfo;
        this.minerInfo = minerInfo;
        this.id = id;
        this.previousHash = previousHash;
        this.transactions = new ArrayList<>(transactions);
    }

    public String getMinerInfo() {
        return minerInfo;
    }

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return hashInfo.getTimeStamp();
    }

    public String getHash() {
        return hashInfo.getHash();
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getMagicNumber() {
        return hashInfo.getMagicNumber();
    }

    public int getGenerationTime() {
        return hashInfo.getGenerationTime();
    }

    public int getAmountOfZeros() {
        return hashInfo.getAmountOfZeros();
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public String toString() {
        return "Block:" + "\n" +
                "Created by: " + minerInfo + "\n" +
                "Id:" + id + "\n" +
                "Timestamp: " + getTimestamp() + "\n" +
                "Magic number: " + getMagicNumber() + "\n" +
                "Hash of the previous block:\n" +
                previousHash + "\n" +
                "Hash of the block:\n" +
                getHash() + "\n" +
                "Block data: " + TransactionHandler.groupTransactionsToString(transactions) +
                "Block was generating for " + getGenerationTime() + " seconds";
    }
}
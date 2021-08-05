package blockchain;

import java.io.Serializable;
import java.util.Random;

public class Block implements Serializable {
    private static final long serialVersionUID = 3699397713609871929L;

    private final String minerInfo;
    private final long id;
    private final long timestamp;
    private final String hash;
    private final String previousHash;
    private final int magicNumber;
    private final int generationTime; //generation time in seconds

    public Block(String minerInfo, long id, long timestamp, String hash, String previousHash, int magicNumber, int generationTime) {
        this.minerInfo = minerInfo;
        this.id = id;
        this.timestamp = System.currentTimeMillis();
        this.hash = hash;
        this.previousHash = previousHash;
        this.magicNumber = magicNumber;
        this.generationTime = generationTime;
    }

    public String getMinerInfo() {
        return minerInfo;
    }

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getMagicNumber() {
        return magicNumber;
    }

    public int getGenerationTime() {
        return generationTime;
    }

    @Override
    public String toString() {
        return "Block:" + "\n" +
                "Created by miner " + minerInfo + "\n" +
                "Id:" + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" +
                previousHash + "\n" +
                "Hash of the block:\n" +
                hash + "\n" +
                "Block was generating for " + generationTime + " seconds";
    }
}
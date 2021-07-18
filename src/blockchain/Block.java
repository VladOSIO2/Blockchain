package blockchain;

import java.util.Date;

public class Block {
    private final long id;
    private final long timestamp;
    private final String hash;
    private final String previousHash;

    public Block(long id, String previousHash) {
        this.id = id;
        this.timestamp = new Date().getTime();
        this.hash = StringUtils.applySha256(Long.toString(timestamp + id));
        this.previousHash = previousHash;
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

    @Override
    public String toString() {
        return "Block:" + "\n" +
                "Id:" + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Hash of the previous block:\n" +
                previousHash + "\n" +
                "Hash of the block:\n" + hash;
    }
}
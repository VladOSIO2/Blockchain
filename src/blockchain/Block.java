package blockchain;

import java.io.Serializable;
import java.util.Random;

public class Block implements Serializable {
    private static final long serialVersionUID = 3699397713609871929L;
    private final long id;
    private final long timestamp;
    private final String hash;
    private final String previousHash;
    private int magicNumber;
    private final long generationTime; //generation time in seconds

    public Block(long id, String previousHash, int amountOfZeros) {
        this.id = id;
        this.previousHash = previousHash;
        this.magicNumber = 0;

        long startTime = System.currentTimeMillis();
        this.hash = generateHash(amountOfZeros);
        this.timestamp = System.currentTimeMillis();
        this.generationTime = (timestamp - startTime) / 1000;
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

    @Override
    public String toString() {
        return "Block:" + "\n" +
                "Id:" + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" +
                previousHash + "\n" +
                "Hash of the block:\n" +
                hash + "\n" +
                "Block was generating for " + generationTime + " seconds";
    }

    private String generateHash(int amountOfZeros) {
        //getting the String prefix out of zeros
        String zerosStr = "0".repeat(Math.max(0, amountOfZeros));

        //generating the hash
        String hash;
        Random random = new Random();
        do {
            magicNumber = random.nextInt();
            String numberToHash = Long.toString(magicNumber + timestamp + id);
            hash = Util.applySha256(numberToHash + Block.class);
        }while (!hash.startsWith(zerosStr));
        return hash;
    }
}
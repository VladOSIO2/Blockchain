package blockchain;

public class HashInfo {
    private final long startTime;
    private final long timeStamp;
    private final int magicNumber;
    private final int amountOfZeros;
    private final String hash;

    public HashInfo(long startTime, long timeStamp, int magicNumber, int amountOfZeros, String hash) {
        this.startTime = startTime;
        this.timeStamp = timeStamp;
        this.magicNumber = magicNumber;
        this.amountOfZeros = amountOfZeros;
        this.hash = hash;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public int getAmountOfZeros() {
        return amountOfZeros;
    }

    public String getHash() {
        return hash;
    }
}

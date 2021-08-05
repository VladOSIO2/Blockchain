package blockchain;

public class HashInfo {
    private final long startTime;
    private final long timeStamp;
    private final int magicNumber;
    private final String hash;

    public HashInfo(long startTime, long timeStamp, int magicNumber, String hash) {
        this.startTime = startTime;
        this.timeStamp = timeStamp;
        this.magicNumber = magicNumber;
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

    public String getHash() {
        return hash;
    }
}

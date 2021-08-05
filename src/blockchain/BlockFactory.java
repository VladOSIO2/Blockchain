package blockchain;

public class BlockFactory {
    public static Block createBlock(long id, String previousHash, HashInfo hashInfo) {
        //gathering block info
        String minerInfo = "# " + Thread.currentThread().getId();

        String hash = hashInfo.getHash();
        long startTime = hashInfo.getStartTime();
        long timestamp = hashInfo.getTimeStamp();
        int magicNumber = hashInfo.getMagicNumber();

        int generationTime = (int) ((timestamp - startTime) / 1000);

        return new Block(minerInfo, id, timestamp, hash, previousHash, magicNumber, generationTime);
    }

}

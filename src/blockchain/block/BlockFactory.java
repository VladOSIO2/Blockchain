package blockchain.block;

public class BlockFactory {
    public static Block createBlock(long id, String previousHash, HashInfo hashInfo) {
        //gathering block info
        String minerInfo = "# " + Thread.currentThread().getId();

        return new Block(hashInfo, minerInfo, id, previousHash);
    }

}

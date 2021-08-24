package blockchain.block;

import blockchain.message.MessageHandler;

public class BlockFactory {
    public static Block createBlock(long id, String previousHash, HashInfo hashInfo) {
        //gathering block info
        String minerInfo = "# " + Thread.currentThread().getId();
        String messages = MessageHandler.getInstance().collectMessages();

        return new Block(hashInfo, minerInfo, id, previousHash, messages);
    }

}

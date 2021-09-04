package blockchain.block;

import blockchain.message.Message;
import blockchain.message.MessageHandler;

import java.util.List;

public class BlockFactory {
    public static Block createBlock(long id, String previousHash, HashInfo hashInfo) {
        //gathering block info
        String minerInfo = "# " + Thread.currentThread().getId();
        List<Message> messages = MessageHandler.getInstance().collectMessages();

        return new Block(hashInfo, minerInfo, id, previousHash, messages);
    }

}

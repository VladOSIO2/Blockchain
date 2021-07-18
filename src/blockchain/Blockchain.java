package blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> blockchain;
    private int blockCount;

    public Blockchain() {
        this.blockchain = new ArrayList<>();
        this.blockCount = 0;
    }

    public void createBlock() {
        String previousHash =
                blockCount == 0 ? "0" : blockchain.get(blockCount - 1).getHash();
        blockCount++;
        blockchain.add(new Block(blockCount, previousHash));
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        for (Block block : blockchain) {
            info.append(block.toString()).append("\n\n");
        }
        return info.toString().trim();
    }

    public boolean validate() {
        if (!(blockchain.get(0).getPreviousHash().equals("0"))) return false;
        for (int i = 1; i < blockCount; i++) {
            /* each previous hash in current block
             * must correspond to the current hash
             * in previous block
             */
            if (!(blockchain.get(i).getPreviousHash().equals(
                    blockchain.get(i-1).getHash()))) {
                return false;
            }
        }
        return true;
    }

    public void printInfo() {
        System.out.println(this.toString());
    }
}
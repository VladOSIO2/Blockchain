package blockchain;

import blockchain.block.Block;
import blockchain.block.BlockFactory;
import blockchain.block.HashFactory;
import blockchain.block.HashInfo;
import blockchain.cryptocurrency.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Blockchain implements Serializable {

    private static Blockchain instance;

    /** minimum block generation time in seconds
     * amount of zeroes increases if this time less than minimum */
    public static final int MIN_GEN_TIME = 1;
    /** maximum block generation time in seconds
     * amount of zeroes decreases if this time exceeded */
    public static final int MAX_GEN_TIME = 3;

    /** amount of VirtualCoins generated per each created block */
    public static final int VC_PER_BLOCK = 100;


    private static final long serialVersionUID = 3519709832155525779L;
    private final List<Block> blockList;
    private volatile int blockCount;
    private volatile int amountOfZeros;
    private final String destination;
    private volatile int nextMsgID;

    private Blockchain(String destination) {
        this.blockList = new ArrayList<>();
        this.blockCount = 0;
        this.amountOfZeros = 0;
        this.destination = destination;
        this.nextMsgID = 1;
        instance = this;
    }

    public static synchronized Blockchain getInstance() {
        return getInstance("blockchain.txt");
    }

    public static synchronized Blockchain getInstance(String destination) {
        Util.createFIleIfNotExists(destination);
        if (instance == null) {
            instance = new Blockchain(destination);
        }
        if (Util.isEmptyFile(destination)) {
            //writing blockchain to an empty file
            instance.writeBlockchain(destination);
        } else {
            //reading blockchain from file
            instance = instance.readBlockchain(destination);
        }
        return instance;
    }

    public void createBlock() {
        //for simulation, minerInfo is miner + thread number
        HashInfo hashInfo;
        do {
            hashInfo = HashFactory.generateHash("miner" + Thread.currentThread().getId());
        } while (!makeAndSaveBlock(hashInfo));

    }

    /**
     * creates block and adds it into a blockchain if
     * hashInfo for block creation is valid
     * @param hashInfo hashInfo instance with information for block creation
     * @return is provided hashInfo is currently valid for block creation
     */
    private synchronized boolean makeAndSaveBlock(HashInfo hashInfo) {
        if (hashInfo == null || hashInfo.getAmountOfZeros() < this.amountOfZeros) {
            return false;
        }
        String previousHash =
                blockCount == 0 ? "0" : blockList.get(blockCount - 1).getHash();
        blockCount++;
        Block newBlock = BlockFactory.createBlock(blockCount, previousHash, hashInfo);
        blockList.add(newBlock);
        if (newBlock.getGenerationTime() < MIN_GEN_TIME) {
            amountOfZeros++;
        } else if (newBlock.getGenerationTime() > MAX_GEN_TIME) {
            amountOfZeros--;
        }

        writeBlockchain(destination);

        return true;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        for (Block block : blockList) {
            info.append(block.toString()).append("\n");
            int genTime = block.getGenerationTime();
            int zeros = block.getAmountOfZeros();
            info.append(
                    genTime > Blockchain.MAX_GEN_TIME ? "N was decreased to " + --zeros
                            : genTime < Blockchain.MIN_GEN_TIME ? "N was increased to " + ++zeros
                            : "N stays the same"
            );
            info.append("\n\n");
        }
        return info.toString().trim();
    }

    public synchronized boolean validate() {
        if (blockList.size() == 0) return true;
        if (!(blockList.get(0).getPreviousHash().equals("0"))) return false;
        int currMsgID = 1;
        for (int i = 1; i < blockCount; i++) {
            Block currBlock = blockList.get(i);
            Block prevBlock = blockList.get(i - 1);
            /* previous hash in current block
             * must correspond to the current hash
             * in previous block
             */
            if (!(currBlock.getPreviousHash().equals(
                    prevBlock.getHash()))) {
                return false;
            }
        }
        return true;
    }

    public void printInfo() {
        System.out.println(this);
    }

    private Blockchain readBlockchain(String dest) {
        File file = new File(dest);
        if (file.isDirectory()) {
            System.out.println(Util.getRedString(dest + " : is a directory"));
            return null;
        }
        try (
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (Blockchain) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeBlockchain(String destination) {
        File file = new File(destination);
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized int getAmountOfZeros() {
        return instance == null ? 0 : instance.amountOfZeros;
    }

    public synchronized int getBlockCount() {
        return blockCount;
    }

    public synchronized int getNextMsgID() {
        return nextMsgID++;
    }

    public int getMinerMoney(String minerInfo) {
        if (instance == null) return 0;
        int VC = 0;
        List<Block> blocks;
        synchronized (this) {
            blocks = new ArrayList<>(instance.blockList);
        }
        //searching for miner's gains & transactions
        for (Block block : blocks) {
            //miner mined current block
            if (block.getMinerInfo().equals(minerInfo)) {
                VC += 100;
            }
            //transaction reading
            for (Transaction transaction : block.getTransactions()) {
                //adding all received and subtracting all sent VCs
                if (transaction.getSender().equals(minerInfo)) {
                    VC -= transaction.getAmount();
                }
                if (transaction.getReceiver().equals(minerInfo)) {
                    VC += transaction.getAmount();
                }
            }
        }
        if (VC < 0) {
            throw new IllegalArgumentException(minerInfo + " VC amount is less than 0 (" + VC + ")");
        }
        return VC;
    }

    public Set<String> getMiners() {
        Set<String> miners = new HashSet<>();
        blockList.forEach(block -> miners.add(block.getMinerInfo()));
        return miners;
    }
}
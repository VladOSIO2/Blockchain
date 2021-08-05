package blockchain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Blockchain implements Serializable {

    private static Blockchain instance;

    /** minimum block generation time in seconds
     * amount of zeroes increases if this time less than minimum */
    private static final int MINIMUM_GENERATION_TIME = 5;
    /** maximum block generation time in seconds
     * amount of zeroes decreases if this time exceeded */
    private static final int MAXIMUM_GENERATION_TIME = 60;


    private static final long serialVersionUID = 3519709832155525779L;
    private final List<Block> blockList;
    private volatile int blockCount;
    private volatile int amountOfZeros;
    private final String destination;

    private Blockchain(int amountOfZeros, String destination) {
        this.blockList = new ArrayList<>();
        this.blockCount = 0;
        this.amountOfZeros = amountOfZeros;
        this.destination = destination;
        instance = this;
    }

    public static Blockchain getInstance(String destination) {
        Util.createFIleIfNotExists(destination);
        if (Util.isEmptyFile(destination)) {
            //writing blockchain to an empty file
            instance = new Blockchain(requestNumberOfZeros(), destination);
            instance.writeBlockchain(destination);
        } else {
            //reading blockchain from file
            //setting amountOfZeros to 0, then amount will be read from file
            instance = new Blockchain(0, destination);
            instance = instance.readBlockchain(destination);
        }
        return instance;
    }

    private static int requestNumberOfZeros() {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        int num = scanner.nextInt();
        System.out.println();
        return num;
    }

    public void createBlock() {
        HashInfo hashInfo = HashFactory.generateHash(amountOfZeros);
        String previousHash;
        synchronized (this) {
            previousHash =
                    blockCount == 0 ? "0" : blockList.get(blockCount - 1).getHash();
            blockCount++;

            Block newBlock = BlockFactory.createBlock(blockCount, previousHash, hashInfo);
            blockList.add(newBlock);/*
            if (newBlock.getGenerationTime() < MINIMUM_GENERATION_TIME) {
                amountOfZeros++;
            } else if (newBlock.getGenerationTime() > MAXIMUM_GENERATION_TIME) {
                amountOfZeros--;
            }*/

            writeBlockchain(destination);
        }
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        for (Block block : blockList) {
            info.append(block.toString()).append("\n\n");
        }
        return info.toString().trim();
    }

    public boolean validate() {
        if (blockList.size() == 0) return true;
        if (!(blockList.get(0).getPreviousHash().equals("0"))) return false;
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
}
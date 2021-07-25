package blockchain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface BlockchainInt extends Serializable {
    static Blockchain init() {
        return null;
    }
    void createBlock();
    boolean validate();
    String toString();
    void printInfo();

}

public class Blockchain implements BlockchainInt {

    private static final long serialVersionUID = 3519709832155525778L;
    private final ArrayList<Block> blockchain;
    private int blockCount;
    private final int amountOfZeros;
    private final String destination;

    private Blockchain(int amountOfZeros, String destination) {
        this.blockchain = new ArrayList<>();
        this.blockCount = 0;
        this.amountOfZeros = amountOfZeros;
        this.destination = destination;
    }

    public static Blockchain init(String destination) {
        Blockchain blockchain = new Blockchain(requestNumberOfZeros(), destination);
        if (Util.isEmptyFile(destination)) {
            //writing blockchain to an empty file
            blockchain.writeBlockchain(destination);
        } else {
            //reading blockchain from file
            blockchain = blockchain.readBlockchain(destination);
        }
        System.out.println(blockchain);
        return blockchain;
    }

    private static int requestNumberOfZeros() {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        int num = scanner.nextInt();
        System.out.println();
        return num;
    }

    public void createBlock() {
        String previousHash =
                blockCount == 0 ? "0" : blockchain.get(blockCount - 1).getHash();
        Block block = new Block(blockCount + 1, previousHash, amountOfZeros);
        blockCount++;
        blockchain.add(block);
        writeBlockchain(destination);

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
        if (blockchain.size() == 0) return true;
        if (!(blockchain.get(0).getPreviousHash().equals("0"))) return false;
        for (int i = 1; i < blockCount; i++) {
            Block currBlock = blockchain.get(i);
            Block prevBlock = blockchain.get(i - 1);
            /* previous hash in current block
             * must correspond to the current hash
             * in previous block
             */
            if (!(currBlock.getPreviousHash().equals(
                    prevBlock.getHash()))) {
                return false;
            }
            /* block ID must be greater by 1 than previous */
            if (currBlock.getId() - 1 != prevBlock.getId()) {
                return false;
            }
            /* block must start with amount of zeroes */
            if (!currBlock.getHash().startsWith("0".repeat(amountOfZeros))) {
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
        try (
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (Blockchain) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(Util.getRedString(e.getMessage()));
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
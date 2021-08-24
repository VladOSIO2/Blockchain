package blockchain.block;

import blockchain.Blockchain;
import blockchain.Util;

import java.util.Random;

public class HashFactory {
    public static HashInfo generateHash() {
        //getting a magic number which hash starts with certain amount of zeros
        Random random = new Random();
        String zerosStr;
        int magicNumber;
        String hash;
        int zeros;
        long startTime = System.currentTimeMillis();
        do {
            zeros = Blockchain.getAmountOfZeros();
            zerosStr = "0".repeat(Math.max(0, zeros));
            magicNumber = random.nextInt();
            hash = Util.applySha256(Long.toHexString(magicNumber + startTime));
        } while (!hash.startsWith(zerosStr));
        long timeStamp = System.currentTimeMillis();
        return new HashInfo(startTime, timeStamp, magicNumber, zeros, hash);
    }
}

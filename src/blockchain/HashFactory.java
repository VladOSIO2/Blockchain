package blockchain;

import java.util.Random;

public class HashFactory {
    public static HashInfo generateHash(int amountOfZeros) {
        //getting a magic number which hash starts with certain amount of zeros
        Random random = new Random();
        String zerosStr = "0".repeat(Math.max(0, amountOfZeros));
        int magicNumber;
        String hash;
        long startTime = System.currentTimeMillis();
        do {
            magicNumber = random.nextInt();
            hash = Util.applySha256(Long.toHexString(magicNumber + startTime));
        } while (!hash.startsWith(zerosStr));
        long timeStamp = System.currentTimeMillis();
        return new HashInfo(startTime, timeStamp, magicNumber, amountOfZeros, hash);
    }
}

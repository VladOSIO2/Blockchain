package blockchain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Util {
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isEmptyFile(String dest) {
        //returns true if file in given destination exists and is empty
        File file = new File(dest);
        if (!file.exists() || !file.isFile()) return false;
        try (FileInputStream fos = new FileInputStream(file)) {
            if (fos.available() == 0) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createFIleIfNotExists(String dest) {
        File file = new File(dest);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getRedString(String str) {
        return "\033[0;31m" + str + "\033[0m";
    }


}

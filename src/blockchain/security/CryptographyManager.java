package blockchain.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptographyManager {
    private static final String KEY_DIRECTORY = "keys";
    private static final int KEY_LENGTH = 1024;


    /**
     * gets a sender name and returns its public key read from file
     * creates a new public key for this sender if not exists
     * @param sender - sender name
     * @return - public key of this sender
     */
    public static synchronized PublicKey getPublic(String sender) throws Exception {
        if (sender == null) {
            return null;
        }
        registerSenderIfNew(sender);
        byte[] keyBytes = Files.readAllBytes(
                new File(getSenderPath(sender) + "\\" + "public.txt").toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    /**
     * gets a sender name and returns its private key read from file
     * creates a new private key for this sender if not exists
     * @param sender - sender name
     * @return - private key of this sender
     */
    public static synchronized PrivateKey getPrivate(String sender) throws Exception {
        if (sender == null) {
            return null;
        }
        registerSenderIfNew(sender);
        byte[] keyBytes = Files.readAllBytes(
                new File(getSenderPath(sender) + "\\" + "private.txt").toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    public static String encryptText(String msg, PrivateKey key)
            throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(msg.getBytes()));
    }

    public static String decryptText(String msg, PublicKey key)
            throws InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(msg.getBytes())));
    }

    private static synchronized boolean createKeyDirectory() {
        return new File(KEY_DIRECTORY).mkdir();
    }

    /** creates public and private keys for a given sender if they not exist */
    private static synchronized void registerSenderIfNew(String sender) throws IOException, NoSuchAlgorithmException {
        if (!new File(KEY_DIRECTORY).exists()) {
            createKeyDirectory();
        }
        String senderPath = getSenderPath(sender);
        if (new File(senderPath).mkdir()) {
            File privateKeyFile = new File(senderPath + "\\" + "private.txt");
            File publicKeyFile = new File(senderPath + "\\" + "public.txt");
            if (privateKeyFile.createNewFile() || publicKeyFile.createNewFile()) {
                /* if one of key-containing files was created then
                 * keys are invalid or user is new
                 * key pair re-creation is required
                 */
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(KEY_LENGTH);
                KeyPair keyPair = kpg.generateKeyPair();
                writeToFile(privateKeyFile, keyPair.getPrivate().getEncoded());
                writeToFile(publicKeyFile, keyPair.getPublic().getEncoded());
            }
        }
    }

    private static void writeToFile(File output, byte[] toWrite)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(toWrite);
        fos.flush();
        fos.close();
    }

    private static String getSenderPath(String sender) {
        return KEY_DIRECTORY + "\\" + sender;
    }

}

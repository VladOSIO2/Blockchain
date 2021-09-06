package blockchain.cryptocurrency;

import blockchain.security.CryptographyManager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class Transaction implements Serializable {
    private static final long serialVersionUID = -7121182173689385071L;

    private final String sender;
    private final String receiver;
    private final int amount;
    private final PublicKey publicKey;

    public Transaction(String sender, String receiver, String amountEncrypted, PublicKey publicKey)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {
        this.sender = sender;
        this.receiver = receiver;
        //decrypting gotten amount simulation
        this.amount = Integer.parseInt(CryptographyManager.decryptText(amountEncrypted, publicKey));
        this.publicKey = publicKey;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return sender + " sent " + amount + " VC to " + receiver;
    }
}

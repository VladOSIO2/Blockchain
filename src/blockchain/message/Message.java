package blockchain.message;

import blockchain.security.CryptographyManager;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

public class Message implements Serializable {
    private static final long serialVersionUID = -9101578407549641210L;
    private final String message;
    private final String sender;
    private final int ID;
    private final PublicKey key;

    public Message(String sender, String message, int ID, PublicKey key) {
        this.sender = sender;
        this.message = message;
        this.ID = ID;
        this.key = key;
    }

    @Override
    public String toString() {
        try {
            return sender + " : " + CryptographyManager.decryptText(message, key);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getID() {
        return ID;
    }
}


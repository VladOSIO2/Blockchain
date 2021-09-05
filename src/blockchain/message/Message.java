package blockchain.message;

import java.security.PublicKey;

public class Message {
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
        return sender + " : " + message;
    }

    public int getID() {
        return ID;
    }
}


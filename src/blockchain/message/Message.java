package blockchain.message;

public class Message {
    private final String message;
    private final String sender;
    private final int ID;

    public Message(String sender, String message, int ID) {
        this.sender = sender;
        this.message = message;
        this.ID = ID;
    }

    @Override
    public String toString() {
        return sender + " : " + message;
    }

    public int getID() {
        return ID;
    }
}


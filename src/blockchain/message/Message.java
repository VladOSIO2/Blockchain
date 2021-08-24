package blockchain.message;

public class Message {
    private final String message;
    private final String sender;

    public Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public String toString() {
        return sender + " : " + message;
    }
}


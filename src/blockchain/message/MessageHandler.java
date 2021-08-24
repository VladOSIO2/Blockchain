package blockchain.message;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    private static MessageHandler instance;
    List<Message> messageList;

    public static MessageHandler getInstance() {
        if (instance == null) {
            instance = new MessageHandler();
        }
        return instance;
    }

    private MessageHandler() {
        messageList = new ArrayList<>();
    }

    public synchronized void addMessage(Message message) {
        messageList.add(message);
    }

    public synchronized String collectMessages() {
        StringBuilder messages = new StringBuilder();
        messageList.forEach(msg -> messages.append(msg.toString()).append("\n"));
        messageList.clear();
        return messages.toString().equals("") ? "no messages\n" : "\n" + messages;
    }
}

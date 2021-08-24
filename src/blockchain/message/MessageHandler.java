package blockchain.message;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    List<Message> messageList;

    public MessageHandler() {
        messageList = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public String collectMessages() {
        StringBuilder messages = new StringBuilder();
        messageList.forEach(msg -> messages.append(msg.toString()).append("\n"));
        messageList.clear();
        return messages.toString();
    }
}
